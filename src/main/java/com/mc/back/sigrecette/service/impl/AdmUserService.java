package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.AdmUser;
import com.mc.back.sigrecette.model.AdmUserProfil;
import com.mc.back.sigrecette.model.tool.AuthRequest;
import com.mc.back.sigrecette.model.view.VAdmUser;
import com.mc.back.sigrecette.repository.IAdmUserProfilRepository;
import com.mc.back.sigrecette.repository.IAdmUserRepository;
import com.mc.back.sigrecette.repository.IVAdmUserRepository;
import com.mc.back.sigrecette.security.JwtSecurity;
import com.mc.back.sigrecette.service.IAdmUserProfilService;
import com.mc.back.sigrecette.service.IAdmUserService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ILogAccessService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.util.*;

@Service
public class AdmUserService implements IAdmUserService {
    private static final Logger logger = LogManager.getLogger(AdmUserService.class);

    @Autowired
    private IAdmUserRepository admUserRepository;

    @Autowired
    private IVAdmUserRepository vAdmUserRepository;

    @Autowired
    private IAdmUserProfilRepository admUserProfilRepository;

    @Autowired
    private IAdmUserProfilService admUserProfilService;

    @Autowired
    private ILogAccessService logAccessService;

    @Autowired
    private JwtSecurity jwtSecurity;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;


    @Override
    public List<AdmUser> getList() {
        try {
            return admUserRepository.findAll();
        } catch (Exception e) {
            logger.error("Error AdmUserService in method getList :: " + e);
            return null;
        }
    }

    @Override
    public AdmUser findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new AdmUser(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (AdmUser) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error AdmUserService in method findById :: " + e);
            return null;
        }
    }

    @Override
    public AdmUser saveOrUpdate(AdmUser entity) {
        try {
            // Hash the user's password before saving
            if (entity.getPwd() != null && entity.getId() == null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String hashedPassword = encoder.encode(entity.getPwd());
                entity.setPwd(hashedPassword);
            } else {
                // Check if user password has changed
                if (entity.getPwd() != null) {
                    Optional<AdmUser> userOptional = admUserRepository.findById(entity.getId());
                    if (userOptional.isPresent()) {
                        AdmUser user = userOptional.get();
                        if (!Objects.equals(user.getPwd(), entity.getPwd())) {
                            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                            String hashedPassword = encoder.encode(entity.getPwd());
                            entity.setPwd(hashedPassword);
                        }
                    }
                }
            }
            entity.setDateUpdate(new Date());
            AdmUser result = admUserRepository.save(entity);

            if (result.getId() != null) {
                // Save the profile list
                for (Long id_profil : entity.getProfils()) {
                    AdmUserProfil profileEntity = new AdmUserProfil(entity.getId(), id_profil);
                    try {
                        admUserProfilRepository.save(profileEntity);
                    } catch (Exception e) {
                        logger.error("Error AdmUserService in method saveOrUpdate :: " + e);
                        return null;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            logger.error("Error AdmUserService in method saveOrUpdate :: " + e);
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;
            admUserRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error AdmUserService in method deleteById :: " + e);
            return false;
        }
    }

    @Override
    public SendObject findAdmUserByIdWs(Long id) {
        try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            AdmUser entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error AdmUserService in method findAdmUserByIdWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getListAdmUserWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error AdmUserService in method getListAdmUserWs() :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject saveOrUpdateAdmUserWs(AdmUser entity) {
        try {
            if (entity.getId() == null) {
                // Check unique when adding new user
                if (!admUserRepository.uniqueAdmUserByCode(entity.getCode()))
                    return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_UNIQUE_CODE, new JSONObject());

                if (!admUserRepository.uniqueAdmUserByEmail(entity.getEmail()))
                    return utilsWs.resultWs(ConstanteService._CODE_SERVICE_LOGIN_EXISTS, new JSONObject());
            } else {
                // Check if code has changed before editing
                Optional<AdmUser> user = admUserRepository.findById(entity.getId());
                if (user.isPresent()) {
                    AdmUser foundUser = user.get();
                    if (!foundUser.getCode().equals(entity.getCode())) {
                        // Check if code is unique
                        if (!admUserRepository.uniqueAdmUserByCode(entity.getCode())) {
                            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_UNIQUE_CODE, new JSONObject());
                        }
                    }
                    if (!foundUser.getEmail().equals(entity.getEmail())) {
                        // Check if unique login
                        if (!admUserRepository.uniqueAdmUserByEmail(entity.getEmail()))
                            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_LOGIN_EXISTS, new JSONObject());
                    }
                }
            }
            // Remove previous user's profiles
            List<AdmUserProfil> userProfils = admUserProfilRepository.getListUserProfilesByIdUser(entity.getId());
            if (!userProfils.isEmpty()) {
                for (AdmUserProfil profil : userProfils) {
                    Boolean resultDelete = admUserProfilService.deleteById(profil.getId());
                    if (!resultDelete)
                        return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
                }
            }

            entity = this.saveOrUpdate(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error AdmUserService in method saveOrUpdateAdmUserWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject deleteAdmUserByIdWs(Long id) {
        try {
            // Remove previous user's profiles
            List<AdmUserProfil> userProfils = admUserProfilRepository.getListUserProfilesByIdUser(id);
            if (!userProfils.isEmpty()) {
                for (AdmUserProfil profil : userProfils) {
                    Boolean resultDelete = admUserProfilService.deleteById(profil.getId());
                    if (!resultDelete)
                        return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
                }
            }

            Boolean resultDelete = this.deleteById(id);
            if (!resultDelete)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error AdmUserService in method getListAdmUser " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject authenticateUserWs(AuthRequest authRequest, String ipAddress) {
        String token = null;
        AdmUser user = null;
        try {
            // Get user by email
            user = admUserRepository.findByEmail(authRequest.getEmail());
            if (user == null)
                return new SendObject(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());

            // Generate token
            token = jwtSecurity.generate(user, "ACCESS");

            // Check if user is active
            if (!user.getIsActive().equals(1)) {
                logAccessService.saveLogAccess(ConstanteWs._CODE_WS_SUCCESS_WAIT_PERMISSION, user.getId(), user.getEmail(), ipAddress);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS_WAIT_PERMISSION, new JSONObject());
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            logAccessService.saveLogAccess(ConstanteWs._CODE_WS_SUCCESS, user.getId(), user.getEmail(), ipAddress);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, jsonObject);

        } catch (Exception e) {
            logger.error("Error AdmUserService in method authenticateUser :: ", e);
            return new SendObject(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
        } finally {
            // Log the access error if the token is not generated
            if (token == null)
                logAccessService.saveLogAccess(ConstanteWs._CODE_WS_USER_ERROR_AUTH,
                        user != null ? user.getId() : null, authRequest.getEmail(), ipAddress);
        }
    }

    @Override
    public SendObject authenticateUserNo2FA(AuthRequest authRequest, String ipAddress) {

        String token = null;
        AdmUser admUser = null;
        try {
            admUser = admUserRepository.findByEmail(authRequest.getEmail());

            if (admUser == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, null);

            if (admUser.getDateExpire() != null && admUser.getDateExpire().before(new Date()))
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ACCOUNT_EXPIRED, null);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(authRequest.getPassword(), admUser.getPwd()))
                return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, null);

            String code = admUser.getIsActive().equals(1) ? ConstanteWs._CODE_WS_SUCCESS
                    : ConstanteWs._CODE_WS_SUCCESS_WAIT_PERMISSION;

            token = jwtSecurity.generate(admUser, "ACCESS");

            if (token != null && code.equals(ConstanteWs._CODE_WS_SUCCESS)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", token);
                logAccessService.saveLogAccess(code, admUser.getId(), admUser.getEmail(), ipAddress);
                return utilsWs.resultWs(code, jsonObject);
            } else {
                return utilsWs.resultWs(code, new JSONObject());
            }
        } catch (Exception e) {
            logger.error("Error AdmUserService in method authenticateUserNo2FA :: {}", String.valueOf(e));
        } finally {
            if (token == null)
                logAccessService.saveLogAccess(ConstanteWs._CODE_WS_USER_ERROR_AUTH,
                        admUser != null ? admUser.getId() : null, authRequest.getEmail(), ipAddress);
        }
        return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
    }

    @Override
    public SendObject whoAmI(Long idUser) {
        try {
            if (idUser == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            AdmUser user = admUserRepository.findById(idUser).orElse(null);
            if (user == null || user.getId() == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<Long> listProfiles = new ArrayList<>();
            for (AdmUserProfil profile : admUserProfilRepository.getListUserProfilesByIdUser(idUser))
                listProfiles.add(profile.getIdProfil());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", user.getId());
            jsonObject.put("username", user.getUsername());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("isActive", user.getIsActive());
            jsonObject.put("listProfiles", listProfiles);

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, jsonObject);
        } catch (Exception e) {
            logger.error("Error AdmUserService in method whoAmI :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public Long getUserIdFromToken(HttpServletRequest exchange) {
        return jwtSecurity.getIdFromToken(exchange.getHeader("Authorization"));
    }

    public Long getUserIdFromToken(ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return jwtSecurity.getIdFromToken(token);
    }

    @Override
    public Long getUserIdFromTokenString(String token) {
        token = token.startsWith("Bearer ") ? token.substring(7) : token;
        return jwtSecurity.getIdFromToken(token);
    }

    @Override
    public SendObject getCurrentUserIdByToken(String token) {
        try {
            Long id = this.jwtSecurity.getIdFromToken(token);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, id);
        } catch (Exception e) {
            logger.error("Error AdmUserService in method getCurrentUserIdByToken :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, null);
        }
    }

    @Override
    public SendObject getCurrentUserByTokenWs(String token) {
        token = token.startsWith("Bearer ") ? token.substring(7, token.length()) : token;
        Long id = jwtSecurity.getIdFromToken(token);
        VAdmUser user = this.getVAdmUserById(id);
        return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(user));
    }

    public VAdmUser getVAdmUserById(Long id) {
        try {
            if (id == null)
                return new VAdmUser();
            VAdmUser user = vAdmUserRepository.findVAdmUserById(id);
            return user == null ? new VAdmUser() : user;
        } catch (Exception e) {
            logger.error("Error VAdmUserService in method getVAdmUserById :: " + e);
            return new VAdmUser();
        }
    }

    @Override
    public AdmUser findUserById(Long id) {
        return admUserRepository.findUserById(id);
    }

}
