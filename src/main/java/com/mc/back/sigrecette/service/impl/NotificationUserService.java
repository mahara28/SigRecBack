package com.mc.back.sigrecette.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.mc.back.sigrecette.model.NotificationUser;
import com.mc.back.sigrecette.repository.INotificationUserRepository;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.INotificationUserService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;

@Service
public class NotificationUserService implements INotificationUserService{

	private static final Logger logger = LogManager.getLogger(NotificationUserService.class);

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private INotificationUserRepository notificationUserRepository;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;
    
	@Override
	public List<NotificationUser> getList() {
		try {
            return notificationUserRepository.findAll();
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method getList :: {}", e.toString());
            return null;
        }
	}

	@Override
	public NotificationUser findById(Long id) {
		try {
            SendObject sendObject = commonService.getObjectById(new NotificationUser(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (NotificationUser) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method findById :: {}", e.toString());
            return null;
        }
	}

	@Override
	public NotificationUser saveOrUpdate(NotificationUser entity) {
		try {
            /*if (entity.getId() == null) {
            	entity.setDateReception(((Timestamp) commonService.getDateSystemNow().getPayload()).toInstant());
            }*/
            return notificationUserRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method saveOrUpdate :: {}", e.toString());
            return null;
        }
	}

	@Override
	public Boolean deleteById(Long id) {
		try {
            if (id == null)
                return false;
            notificationUserRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method deleteById :: " + e.toString());
            return false;
        }
	}

	@Override
	public SendObject findNotificationUserByIdWs(Long id) {
		try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            NotificationUser entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method findNotificationByIdWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
	}

	@Override
	public SendObject getListNotificatioUsernWs() {
		try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method getListNotificationWs() :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
	}

	@Override
	public SendObject saveOrUpdateNotificationUserWs(NotificationUser entity) {
		try {
            entity = this.saveOrUpdate(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method saveOrUpdateNotificationWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
	}

	@Override
	public SendObject deleteNotificationUserByIdWs(Long id) {
		try {
            Boolean resultDelete = this.deleteById(id);
            if (resultDelete == false)
                return new SendObject(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method getListNotification {}", e.toString());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR, new JSONObject());
        }
	}

	@Override
	public SendObject setAsReadWs(Long id) {
		try {
            NotificationUser entity = this.findById(id);
            System.out.println(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            entity.setDateReception(((Timestamp) commonService.getDateSystemNow().getPayload()).toInstant());
            entity.setLu(1);
            this.saveOrUpdate(entity);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error NotificationUserService in method setAsReadWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
	}

}
