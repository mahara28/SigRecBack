package com.mc.back.sigrecette.service.impl;


import com.mc.back.sigrecette.model.Notification;
import com.mc.back.sigrecette.repository.INotificationRepository;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.IEmailService;
import com.mc.back.sigrecette.service.INotificationService;

import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService implements INotificationService {

    private static final Logger logger = LogManager.getLogger(NotificationService.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private IEmailService emailTemplateService;

    @Autowired
    private INotificationRepository notificationRepository;



    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public List<Notification> getList() {
        try {
            return notificationRepository.findAll();
        } catch (Exception e) {
            logger.error("Error NotificationService in method getList :: {}", e.toString());
            return null;
        }
    }

    @Override
    public Notification findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new Notification(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (Notification) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error NotificationService in method findById :: {}", e.toString());
            return null;
        }
    }

    @Override
    public Notification saveOrUpdate(Notification entity) {
        try {
            if (entity.getId() == null) {
            	entity.setDateEnvoi(((Timestamp) commonService.getDateSystemNow().getPayload()).toInstant());
            }
            return notificationRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error NotificationService in method saveOrUpdate :: {}", e.toString());
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;
            notificationRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error NotificationService in method deleteById :: " + e.toString());
            return false;
        }
    }

    @Override
    public SendObject findNotificationByIdWs(Long id) {
        try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            Notification entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationService in method findNotificationByIdWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getListNotificationWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error NotificationService in method getListNotificationWs() :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject saveOrUpdateNotificationWs(Notification entity) {
        try {
        	// 2) Convertir les listes en tableaux Long[]
            Long[] listIdUserRecArray = entity.getListIdUserRec() != null
                    ? entity.getListIdUserRec().stream()
                        .filter(Objects::nonNull)
                        .distinct()
                        .toArray(Long[]::new)
                    : new Long[0];

            Long[] listIdProfilArray = entity.getListIdProfil() != null
                    ? entity.getListIdProfil().stream()
                        .filter(Objects::nonNull)
                        .distinct()
                        .toArray(Long[]::new)
                    : new Long[0];
            /*
            if (entity == null) {
                Notification notification = new Notification();
                notification.setDateEnvoi((Instant) commonService.getDateSystemNow().getPayload());
                entity = notification;
            }
			*/
            entity = this.saveOrUpdate(entity);
         // 3) Appeler la fonction PostgreSQL
            Integer result = notificationRepository.saveNotificationUsers(
                    entity.getId(),
                    listIdUserRecArray,
                    listIdProfilArray
            );
            
            if (result != null && result == 426 || entity == null) {
            	deleteById(entity.getId());
            	return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            }
            
            /*if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());*/
            
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationService in method saveOrUpdateNotificationWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
    }

    @Override
    public SendObject deleteNotificationByIdWs(Long id) {
        try {
            Boolean resultDelete = this.deleteById(id);
            if (resultDelete == false)
                return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error NotificationService in method getListNotification {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
    }

    @Override
    public SendObject setAsReadWs(Long id) {
        try {
            Notification entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            //entity.setDateRec((Instant) commonService.getDateSystemNow().getPayload());
            this.saveOrUpdate(entity);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationService in method setAsReadWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject sendNotificationWs(Notification entity) {
        try {
            simpMessagingTemplate.convertAndSend(
                    "/topic/receive",
                    String.format(entity.getSujet())
            );
            entity = this.saveOrUpdate(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationService in method sendNotificationWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
    }

    @Override
    public SendObject pushNotificationReady(String message) {
        try {
            if (message == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            Notification notification = new Notification();
            notification.setDateEnvoi(Instant.now());
            notification.setSujet(message);
            //notification.setNotifEn("Result ready for " + qid);
            //notification.setIdUser(1L);
            this.sendNotificationWs(notification);

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(notification));
        } catch (Exception e) {
            logger.error("Error NotificationService in method pushNotificationReady :: {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
    }

	@Override
	public SendObject sendNotificationEmailWs(Notification entity) {
		try {
			List<String> listEmail = entity.getListEmail();
            entity = this.saveOrUpdate(entity);
            
            String[] emailsArray = (listEmail != null && !listEmail.isEmpty())
                    ? listEmail.toArray(new String[0])
                    : new String[0];
            Integer result = notificationRepository.saveNotificationEmails(
                    entity.getId(),
                    emailsArray
            );
            
            if (result != null && result == 426 || entity == null) {
            	deleteById(entity.getId());
            	return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            }
            
         // Envoi email après succès
            if (listEmail != null && !listEmail.isEmpty()) {
                String subject = entity.getTitre() != null ? entity.getTitre() : "Nouvelle notification";
                String message = entity.getSujet() != null ? entity.getSujet() : "Vous avez reçu une nouvelle notification.";
                //String senderName = entity.getCreatedBy() != null ? entity.getCreatedBy() : "Système";
                

                String htmlContent = emailTemplateService.buildNotificationTemplate(
                        subject,
                        message,
                        "Système"
                );
                
                System.out.println(htmlContent);

                emailTemplateService.sendHtmlEmail(listEmail, subject, htmlContent);
            }
            
            /*if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());*/
            
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationService in method sendNotificationEmailWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
	}

	@Override
	public SendObject countNotificationNonLuesByUserAndType(Long idTypeNotif, Long idUserRec) {
		try {
			if (idTypeNotif == null || idUserRec == null) {
            	return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_ALIAS_PARAM, new JSONObject());
            }
			Long result = notificationRepository.countNotificationNonLuesByUserAndType(
					idTypeNotif,
					idUserRec
            );
            
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, result);
        } catch (Exception e) {
            logger.error("Error NotificationService in method countNotificationNonLuesByUserAndType :: {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
	}

}
