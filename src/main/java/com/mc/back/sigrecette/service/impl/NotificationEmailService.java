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

import java.util.List;

@Service
public class NotificationEmailService  implements INotificationEmailService {

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
            logger.error("Error NotificationEmailService in method getList :: " + e);
            return null;
        }
    }

    public NotificationEmail findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new NotificationEmail(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (NotificationEmail) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method findById :: " + e);
            return null;
        }
    }

    public NotificationEmail save(NotificationEmail entity) {
        try {
            return notificationEmailRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method save :: " + e);
            return null;
        }
    }

    public Boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;
            notificationEmailRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method deleteById :: " + e);
            return false;
        }
    }

    // ===================== MÉTHODES WS =====================

    @Override
    public SendObject getAllNotificationEmails() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method getAllNotificationEmails :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getNotificationEmailById(Long id) {
        try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            NotificationEmail entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method getNotificationEmailById :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getNotificationEmailsByIdNotif(Long idNotif) {
        try {
            if (idNotif == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            List<NotificationEmail> list = notificationEmailRepository.findByIdNotif(idNotif);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method getNotificationEmailsByIdNotif :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject addNotificationEmail(String email, Long idNotif) {
        try {
            if (email == null || idNotif == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            NotificationEmail entity = new NotificationEmail();
            entity.setEmail(email);
            entity.setIdNotif(idNotif);
            entity = this.save(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method addNotificationEmail :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }


    @Override
    public SendObject deleteNotificationEmailById(Long id) {
        try {
            Boolean resultDelete = this.deleteById(id);
            if (!resultDelete)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method deleteNotificationEmailById :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject deleteNotificationEmailsByIdNotif(Long idNotif) {
        try {
            if (idNotif == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            notificationEmailRepository.deleteByIdNotif(idNotif);
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method deleteNotificationEmailsByIdNotif :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject existsByEmail(String email) {
        try {
            if (email == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            boolean exists = notificationEmailRepository.existsByEmail(email);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject().put("exists", exists));
        } catch (Exception e) {
            logger.error("Error NotificationEmailService in method existsByEmail :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }
}
