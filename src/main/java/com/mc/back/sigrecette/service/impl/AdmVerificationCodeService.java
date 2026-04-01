package com.mc.back.sigrecette.service.impl;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mc.back.sigrecette.service.IAdmVerificationCodeService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import com.mc.back.sigrecette.model.AdmProfession;
import com.mc.back.sigrecette.model.AdmUser;
import com.mc.back.sigrecette.model.AdmVerificationCode;
import com.mc.back.sigrecette.repository.IAdmVerificationCodeRepository;

@Service
public class AdmVerificationCodeService implements IAdmVerificationCodeService{
	private static final Logger logger = LogManager.getLogger(AdmVerificationCodeService.class);

	@Autowired
    private IAdmVerificationCodeRepository admVerificationCodeRepository;
	@Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;
    
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
	public SendObject GenerationCodeVerif(String email) {
		if (email == null || email.trim().isEmpty()) {
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_ALIAS_PARAM, new JSONObject());
        }
		
		return null;
	}
	
	private String generateVerificationCode() {
	    int code = (int) (Math.random() * 900000) + 100000;
	    return String.valueOf(code);
	}

	@Override
	public SendObject verifierValiditeCode(AdmVerificationCode entity) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
