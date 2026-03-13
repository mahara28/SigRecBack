package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.ActiveSession;
import com.mc.back.sigrecette.model.AdmFoncProfile;
import com.mc.back.sigrecette.model.AdmUser;
import com.mc.back.sigrecette.model.AdmUserProfil;
import com.mc.back.sigrecette.model.tool.AuthRequest;
import com.mc.back.sigrecette.model.view.VAdmUser;
import com.mc.back.sigrecette.repository.ActiveSessionRepository;
import com.mc.back.sigrecette.repository.IAdmFoncProfileRepository;
import com.mc.back.sigrecette.repository.IAdmUserProfilRepository;
import com.mc.back.sigrecette.repository.IAdmUserRepository;
import com.mc.back.sigrecette.repository.IVAdmUserRepository;
import com.mc.back.sigrecette.security.JwtSecurity;
import com.mc.back.sigrecette.service.IAdmFoncService;
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
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.sql.Timestamp;
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
    private IAdmFoncService admFoncService;
    
    @Autowired
    private  ActiveSessionRepository activeSessionRepository;

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
    public SendObject authenticateUserWs(AuthRequest authRequest, String ipAddress, HttpServletRequest exchange) {

        String accessToken = null;
        String refreshToken = null;
        AdmUser user = null;
        
        try {
            // Get user by email
            user = admUserRepository.findByEmail(authRequest.getEmail());
            if (user == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(authRequest.getPassword(), user.getPwd())) {
                logAccessService.saveLogAccess(ConstanteWs._CODE_WS_USER_ERROR_AUTH, user.getId(), user.getEmail(), ipAddress);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, null);
            }

            // Check if user is active
            if (!user.getIsActive().equals(1)) {
                logAccessService.saveLogAccess(ConstanteWs._CODE_WS_SUCCESS_WAIT_PERMISSION, user.getId(), user.getEmail(), ipAddress);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS_WAIT_PERMISSION, new JSONObject());
            }
            
            Optional<ActiveSession> existingOpt = activeSessionRepository.findById(user.getId());

            if (existingOpt.isPresent()) {
                ActiveSession existing = existingOpt.get();
                String clientRefresh = authRequest.getRefreshToken(); // may be null

                //if (clientRefresh != null && clientRefresh.equals(existing.getRefreshToken())) {
                    // Same device/browser (localStorage present) => allow: just re-issue new access
                    String newAccess = jwtSecurity.generate(user, "ACCESS");
                    existing.setToken(newAccess);
                    existing.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                    //activeSessionRepository.save(existing);

                    JSONObject json = new JSONObject();
                    json.put("accessToken", newAccess);
                    json.put("refreshToken", existing.getRefreshToken()); // unchanged
                    return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, json);
                /*} else {
                    // Different device: block (session already active elsewhere)
                    return utilsWs.resultWs("444", new JSONObject()); // your existing code uses 444
                }*/
            }

            
            // : Check existing active session
//            if (activeSessionRepository.findById(user.getId()).isPresent()) {
//                return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ALREADY_CONNECTED, new JSONObject());
//            }
            
          

        
            
            
            //////session//////
            
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//   
//            Optional<ActiveSession> existingOpt = activeSessionRepository.findById(user.getId());
//            ActiveSession session;
//            if (existingOpt.isPresent()) {
//               
//                session = existingOpt.get();
//                session.setToken(token);
//                session.setCreatedAt(now);
//               
//            } else {
//       
//                session = new ActiveSession(user.getId(), token, now);
//            }
//            activeSessionRepository.save(session);
            
            // generate tokens
             accessToken = jwtSecurity.generate(user, "ACCESS");
             refreshToken = jwtSecurity.generate(user, "REFRESH");

            // single-session policy: delete old session if exists (so new login invalidates old refresh)
//            Optional<ActiveSession> existingOpt = activeSessionRepository.findById(user.getId());
//            if (existingOpt.isPresent()) {
//                activeSessionRepository.delete(existingOpt.get());
//            }

            // Save new session
//            Timestamp now = new Timestamp(System.currentTimeMillis());
//            Timestamp refreshExpiry = new Timestamp(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // 7 days (example)
//            ActiveSession session = new ActiveSession(user.getId(), accessToken, now, refreshToken, refreshExpiry);
//            session.setRefreshToken(refreshToken);
//            session.setRefreshExpiresAt(refreshExpiry);
//          activeSessionRepository.save(session);

            
            
            
            //////////


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", accessToken);
            jsonObject.put("refreshToken", refreshToken); 
            logAccessService.saveLogAccess(ConstanteWs._CODE_WS_SUCCESS, user.getId(), user.getEmail(), ipAddress);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, jsonObject);

        } catch (Exception e) {
            logger.error("Error AdmUserService in method authenticateUser :: ", e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_USER_ERROR_AUTH, new JSONObject());
        } finally {
            // Log the access error if the token is not generated
            if (accessToken == null)
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
    @Autowired
    private IAdmFoncProfileRepository admFoncProfileRepository;
    
    @Override
    public SendObject whoAmI(Long idUser) {
        try {
            if (idUser == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            AdmUser user = admUserRepository.findById(idUser).orElse(null);
            if (user == null || user.getId() == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<Long> listProfiles = new ArrayList<>();
            List<AdmFoncProfile> listPermissionbyprofil = new ArrayList<>();
            
            for (AdmUserProfil profile : admUserProfilRepository.getListUserProfilesByIdUser(idUser)) {
            	listProfiles.add(profile.getIdProfil());
            	listPermissionbyprofil.addAll(admFoncProfileRepository.getListAdmFoncProfileByIdProfil(profile.getIdProfil())) ;
            //	SendObject sendObject = admFoncService.getAllMenusChecked(profile.getIdProfil());
            //
            //   if (sendObject != null && sendObject.getPayload() != null) {
            //
            //       Map<String, Object> map = (Map<String, Object>) sendObject.getPayload();
            //
            //       List<AdmFonc> menus = (List<AdmFonc>) map.get("menus");
            //
            //       if (menus != null) {
            //           listFonctionPermission.addAll(menus);
            //       }
               }
            
                

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", user.getId());
            jsonObject.put("username", user.getUsername());
            jsonObject.put("email", user.getEmail());
            jsonObject.put("isActive", user.getIsActive());
            jsonObject.put("listProfiles", listProfiles);
            jsonObject.put("listFonctionPermission", listPermissionbyprofil);

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

    @Override
    public Long getUserIdFromToken(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        return jwtSecurity.getIdFromToken(request.getHeaders().getOrEmpty("Authorization").get(0));
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
    
    
    @Override
    public SendObject logout(String token) {
        try {
            // try delete by access token
            ActiveSession byToken = activeSessionRepository.findByToken(token);
            if (byToken != null) {
                activeSessionRepository.delete(byToken);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
            }
            // or try delete by refresh token
            ActiveSession byRefresh = activeSessionRepository.findByRefreshToken(token);
            if (byRefresh != null) {
                activeSessionRepository.delete(byRefresh);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
            }
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error VAdmUserService in method logout :: {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }
    
    @Override
    public SendObject refreshAccessToken(String refreshToken) {
        try {
            if (refreshToken == null) 
                return utilsWs.resultWs(ConstanteWs._CODE_WS_INVALID_REFRESH, new JSONObject());

            ActiveSession session = activeSessionRepository.findByRefreshToken(refreshToken);
            if (session == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_INVALID_REFRESH, new JSONObject());
            }

            // check refresh expiry
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (session.getRefreshExpiresAt() == null || session.getRefreshExpiresAt().before(now)) {
                activeSessionRepository.delete(session);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_INVALID_REFRESH, new JSONObject());
            }

            // get user
            AdmUser user = admUserRepository.findById(session.getUserId()).orElse(null);
            if (user == null) {
                activeSessionRepository.delete(session);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_INVALID_REFRESH, new JSONObject());
            }

            // generate new access only (keep refresh the same)
            String newAccess = jwtSecurity.generate(user, "ACCESS");

            session.setToken(newAccess);
            session.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            activeSessionRepository.save(session);

            JSONObject payload = new JSONObject();
            payload.put("accessToken", newAccess);
            payload.put("refreshToken", session.getRefreshToken()); // keep old refresh
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, payload);

        } catch (Exception e) {
            logger.error("Error refreshAccessToken :: {}", e.getMessage());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }
    
    @EnableScheduling
    public class ActiveSessionCleanupService {

        @Autowired
        private ActiveSessionRepository activeSessionRepository;

        // Runs every day at midnight (00:00)
        @Scheduled(cron = "0 0 0 * * *")
        public void deleteAllSessionsAtMidnight() {
            activeSessionRepository.deleteAll();
            System.out.println("All active sessions deleted at midnight.");
        }
    }

}
