package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import com.mc.back.sigrecette.repository.IVNotificationDetailRepository;
import com.mc.back.sigrecette.service.IVNotificationDetailService;
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
public class VNotificationDetailService implements IVNotificationDetailService {

    private static final Logger logger = LogManager.getLogger(VNotificationDetailService.class);

    @Autowired
    private IVNotificationDetailRepository vNotificationDetailRepository;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public SendObject getNotificationsByIdUserRecWs(Long idUserRec) {
        try {
            if (idUserRec == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserRec(idUserRec);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getNotificationsByIdUserRecWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getNotificationsByIdUserEmWs(Long idUserEm) {
        try {
            if (idUserEm == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserEm(idUserEm);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getNotificationsByIdUserEmWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getUnreadNotificationsByIdUserRecWs(Long idUserRec) {
        try {
            if (idUserRec == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<VNotificationDetail> list = vNotificationDetailRepository.findUnreadByIdUserRec(idUserRec);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getUnreadNotificationsByIdUserRecWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject countUnreadNotificationsByIdUserRecWs(Long idUserRec) {
        try {
            if (idUserRec == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            Long count = vNotificationDetailRepository.countUnreadByIdUserRec(idUserRec);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject().put("count", count));
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method countUnreadNotificationsByIdUserRecWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getNotificationsByIdUserRecAndIdTypeNotifWs(Long idUserRec, Long idTypeNotif) {
        try {
            if (idUserRec == null || idTypeNotif == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserRecAndIdTypeNotif(idUserRec, idTypeNotif);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getNotificationsByIdUserRecAndIdTypeNotifWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getNotificationsByIdmessageParentWs(Long idmessageParent) {
        try {
            if (idmessageParent == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdmessageParent(idmessageParent);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getNotificationsByIdmessageParentWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getNotificationsByIdUserRecAndPrioriteWs(Long idUserRec, Integer priorite) {
        try {
            if (idUserRec == null || priorite == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserRecAndPriorite(idUserRec, priorite);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getNotificationsByIdUserRecAndPrioriteWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public VNotificationDetail getNotificationById(Long id) {
        try {
            if (id == null)
                return new VNotificationDetail();
            return vNotificationDetailRepository.findById(id).orElse(new VNotificationDetail());
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getNotificationById :: " + e);
            return new VNotificationDetail();
        }
    }

    public List<VNotificationDetail> getList() {
        try {
            return vNotificationDetailRepository.findAll();
        } catch (Exception e) {
            logger.error("Error VNotificationUserService in method getList :: " + e);
            return null;
        }
    }
}