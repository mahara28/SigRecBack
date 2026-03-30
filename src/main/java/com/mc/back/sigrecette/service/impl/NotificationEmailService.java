package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.NotificationEmail;
import com.mc.back.sigrecette.repository.NotificationEmailRepository;
import com.mc.back.sigrecette.service.INotificationEmailService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationEmailService implements INotificationEmailService {

    private static final Logger logger = LogManager.getLogger(NotificationEmailService.class);

    @Autowired
    private NotificationEmailRepository notificationEmailRepository;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;

    // ===================== MÉTHODES INTERNES =====================

    public List<NotificationEmail> getList() {
        try {
            return notificationEmailRepository.findAll();
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method getList :: {}", e.getMessage(), e);
            return List.of(); // Retourner une liste vide au lieu de null
        }
    }

    public Optional<NotificationEmail> findById(Long id) {
        try {
            if (id == null) {
                return Optional.empty();
            }
            return notificationEmailRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method findById :: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Transactional
    public NotificationEmail save(NotificationEmail entity) {
        try {
            // Validation avant sauvegarde
            if (entity.getEmail() == null || entity.getEmail().trim().isEmpty()) {
                logger.error("Email cannot be null or empty");
                return null;
            }
            if (entity.getIdNotif() == null) {
                logger.error("idNotif cannot be null");
                return null;
            }

            // Normaliser l'email
            entity.setEmail(entity.getEmail().trim().toLowerCase());

            return notificationEmailRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method save :: {}", e.getMessage(), e);
            return null;
        }
    }

    @Transactional
    public Boolean deleteById(Long id) {
        try {
            if (id == null) {
                return false;
            }
            Optional<NotificationEmail> entity = findById(id);
            if (entity.isPresent()) {
                notificationEmailRepository.delete(entity.get());
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method deleteById :: {}", e.getMessage(), e);
            return false;
        }
    }

    // ===================== MÉTHODES WS =====================

    @Override
    public SendObject getAllNotificationEmails() {
        try {
            List<NotificationEmail> list = this.getList();
            if (list.isEmpty()) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray());
            }
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method getAllNotificationEmails :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject getNotificationEmailById(Long id) {
        try {
            if (id == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "ID cannot be null"));
            }

            Optional<NotificationEmail> entity = this.findById(id);
            if (entity.isEmpty()) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE,
                        new JSONObject().put("message", "Notification email not found with id: " + id));
            }

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity.get()));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method getNotificationEmailById :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject getNotificationEmailsByIdNotif(Long idNotif) {
        try {
            if (idNotif == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idNotif cannot be null"));
            }

            List<NotificationEmail> list = notificationEmailRepository.findByIdNotif(idNotif);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method getNotificationEmailsByIdNotif :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public SendObject addNotificationEmail(String email, Long idNotif) {
        try {
            // Validation des paramètres
            if (email == null || email.trim().isEmpty()) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "Email cannot be null or empty"));
            }
            if (idNotif == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idNotif cannot be null"));
            }

            // Normaliser l'email
            String normalizedEmail = email.trim().toLowerCase();

            // Vérifier si l'email existe déjà pour cette notification
            boolean exists = notificationEmailRepository.existsByIdNotifAndEmail(idNotif, normalizedEmail);
            if (exists) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_CODE_EXISTS,
                        new JSONObject().put("message", "Email already exists for this notification"));
            }

            // Créer et sauvegarder l'entité
            NotificationEmail entity = new NotificationEmail();
            entity.setEmail(normalizedEmail);
            entity.setIdNotif(idNotif);

            entity = this.save(entity);
            if (entity == null) {
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE,
                        new JSONObject().put("message", "Failed to save notification email"));
            }

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method addNotificationEmail :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public SendObject deleteNotificationEmailById(Long id) {
        try {
            Boolean resultDelete = this.deleteById(id);
            if (!resultDelete) {
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW,
                        new JSONObject().put("message", "Failed to delete notification email with id: " + id));
            }
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS,
                    new JSONObject().put("message", "Notification email deleted successfully"));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method deleteNotificationEmailById :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public SendObject deleteNotificationEmailsByIdNotif(Long idNotif) {
        try {
            if (idNotif == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idNotif cannot be null"));
            }

            long count = notificationEmailRepository.countByIdNotif(idNotif);
            notificationEmailRepository.deleteByIdNotif(idNotif);

            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS,
                    new JSONObject()
                            .put("message", "Deleted " + count + " notification email(s) successfully")
                            .put("deletedCount", count));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method deleteNotificationEmailsByIdNotif :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject existsByEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "Email cannot be null or empty"));
            }

            boolean exists = notificationEmailRepository.existsByEmail(email.trim().toLowerCase());
            JSONObject result = new JSONObject()
                    .put("exists", exists)
                    .put("email", email);

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, result);
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method existsByEmail :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject().put("error", e.getMessage()));
        }
    }
}