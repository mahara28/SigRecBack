package com.mc.back.sigrecette.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mc.back.sigrecette.service.IAdmVerificationCodeService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.IEmailService;
import com.mc.back.sigrecette.service.ILogAccessService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import com.mc.back.sigrecette.model.AdmUser;
import com.mc.back.sigrecette.model.AdmVerificationCode;
import com.mc.back.sigrecette.model.tool.AuthRequest;
import com.mc.back.sigrecette.repository.IAdmUserRepository;
import com.mc.back.sigrecette.repository.IAdmVerificationCodeRepository;
import com.mc.back.sigrecette.security.JwtSecurity;

@Service
public class AdmVerificationCodeService implements IAdmVerificationCodeService{
	private static final Logger logger = LogManager.getLogger(AdmVerificationCodeService.class);

	@Autowired
    private IAdmVerificationCodeRepository admVerificationCodeRepository;
	
	@Autowired
    private IAdmUserRepository admUserRepository;
	
	@Autowired
    private IEmailService emailTemplateService;
	
	@Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;
    
    @Autowired
    private JwtSecurity jwtSecurity;
    
    @Autowired
    private ILogAccessService logAccessService;
    
    @Value("${app.verification-code.expiration-minutes}")
    private Long expirationMinutes;
    
    
	@Override
	public AdmVerificationCode findById(Long id) {
		try {
            SendObject sendObject = commonService.getObjectById(new AdmUser(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (AdmVerificationCode) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error AdmVerificationCodeService in method findById :: " + e);
            return null;
        }
	}

	@Override
	public AdmVerificationCode saveOrUpdate(AdmVerificationCode entity) {
		try {
            if (entity.getId() == null) {
            	Instant expirationDate = Instant.now()
                        .plusSeconds(expirationMinutes * 60);

                entity.setDateExpiration(expirationDate);
            }
            return admVerificationCodeRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error NotificationService in method saveOrUpdate :: {}", e.toString());
            return null;
        }
	}

	@Override
	public SendObject findByIdWs(Long id) {
		try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            AdmVerificationCode entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error AdmVerificationCodeService in method findByIdWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
	}

	@Override
	public SendObject GenerationCodeVerif(AuthRequest request,String ipAdress) {
		AdmUser user = null;
		String code;
		try {
			if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
	            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_ALIAS_PARAM, new JSONObject());
	        }
			
			user = admUserRepository.findByEmail(request.getEmail());
			
	        if (user == null)
	            return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
	        
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(request.getPassword(), user.getPwd()))
                return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, null);

            
	        if (user.getDateExpire() != null && user.getDateExpire().before(new Date()))
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ACCOUNT_EXPIRED, null);
            
	        
	        
	        if (!user.getIsActive().equals(1)) {
	            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS_WAIT_PERMISSION, new JSONObject());
	        }
	        
	        
	      // Génération du code
	        code = generateVerificationCode();
	        AdmVerificationCode entity = new AdmVerificationCode();
	        
	        entity.setEmail(request.getEmail());
	        entity.setCodeVerif(code);
	        entity.setIdUser(user.getId());
	        
	        entity = this.saveOrUpdate(entity);
	        
	        if (entity == null) {
	            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
	        }
	        
	     // Construire contenu email
	        String subject = "Authentification multifacteur : Code de vérification ";
	        String htmlContent = emailTemplateService.buildEmailTemplate("verification-code-email.html", Map.of(
	        	    "TITLE", "Authentification multifacteur",
	        	    "CODE", code,
	        	    "EXPIRATION_MINUTES", String.valueOf(expirationMinutes)
	        	));

	        // Envoi email
	        emailTemplateService.sendHtmlEmail(
	                List.of(request.getEmail()),
	                subject,
	                htmlContent
	        );
	        
			
			return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS_WAIT_CODE_VERIFICATION, new JSONObject()
	                .put("email", request.getEmail())
	                .put("id", entity.getId()));
		}catch (Exception e) {
            logger.error("Error AdmVerificationCodeService in method GenerationCodeVerif :: ", e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
        }
	}
	
	private String generateVerificationCode() {
	    int code = (int) (Math.random() * 900000) + 100000;
	    return String.valueOf(code);
	}

	@Override
	public SendObject verifyCode(AdmVerificationCode entity, String ipAddress) {
	    AdmUser user = null;
	    String token = null;
	    String refreshToken = null;

	    try {
	        if (entity == null || entity.getId() == null || entity.getCodeVerif() == null || entity.getCodeVerif().trim().isEmpty()) {
	            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_ALIAS_PARAM, new JSONObject());
	        }

	        Optional<AdmVerificationCode> optionalEntity =
	                admVerificationCodeRepository.findByIdAndCodeVerif(entity.getId(), entity.getCodeVerif().trim());

	        if (optionalEntity.isEmpty()) {
	            return utilsWs.resultWs(ConstanteWs._CODE_WS_PERMISSION_DENIED, new JSONObject());
	        }

	        AdmVerificationCode admVerifCode = optionalEntity.get();

	        // Comparer le code saisi avec celui stocké en base
	        /*if (admVerifCode.getCodeVerif() == null ||
	                !admVerifCode.getCodeVerif().equals(entity.getCodeVerif().trim())) {
	            return utilsWs.resultWs(ConstanteWs._CODE_WS_PERMISSION_DENIED, new JSONObject());
	        }*/

	        // Vérifier si le code est expiré (depuis la base, pas depuis le front)
	        if (admVerifCode.getDateExpiration() == null ||
	                Instant.now().isAfter(admVerifCode.getDateExpiration())) {
	            return utilsWs.resultWs(ConstanteWs._CODE_WS_CODE_EXPIRE, new JSONObject());
	        }

	        // Récupérer l'utilisateur depuis l'email stocké en base
	        user = admUserRepository.findByEmail(admVerifCode.getEmail());

	        if (user == null) {
	            return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
	        }

	        // Générer les tokens
	        token = jwtSecurity.generate(user, "ACCESS");
	        refreshToken = jwtSecurity.generate(user, "REFRESH");
	        
	     // Supprimer le code après validation
	        admVerificationCodeRepository.delete(admVerifCode);

	        JSONObject jsonObject = new JSONObject();
	        jsonObject.put("token", token);
	        jsonObject.put("refreshToken", refreshToken);

	        logAccessService.saveLogAccess(ConstanteWs._CODE_WS_SUCCESS, user.getId(), user.getEmail(), ipAddress);

	        return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, jsonObject);

	    } catch (Exception e) {
	        logger.error("Error AdmVerificationCodeService in method verifyCode :: {}", e.toString());
	        return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
	    } finally {
	        // Log access error if token not generated
	        if (token == null) {
	            logAccessService.saveLogAccess(
	                    ConstanteWs._CODE_WS_USER_ERROR_AUTH,
	                    user != null ? user.getId() : null,
	                    entity != null ? entity.getEmail() : null,
	                    ipAddress
	            );
	        }
	    }
	}
}
