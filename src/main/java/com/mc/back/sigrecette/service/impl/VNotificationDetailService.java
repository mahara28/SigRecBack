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
import java.util.Optional;

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
            if (idUserRec == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idUserRec cannot be null"));
            }

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserRec(idUserRec);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error in getNotificationsByIdUserRecWs :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject getNotificationsByIdUserEmWs(Long idUserEm) {
        try {
            if (idUserEm == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idUserEm cannot be null"));
            }

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserEm(idUserEm);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error in getNotificationsByIdUserEmWs :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject getUnreadNotificationsByIdUserRecWs(Long idUserRec) {
        try {
            if (idUserRec == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idUserRec cannot be null"));
            }

            List<VNotificationDetail> list = vNotificationDetailRepository.findUnreadByIdUserRec(idUserRec);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error in getUnreadNotificationsByIdUserRecWs :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject countUnreadNotificationsByIdUserRecWs(Long idUserRec) {
        try {
            if (idUserRec == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idUserRec cannot be null"));
            }

            Long count = vNotificationDetailRepository.countUnreadByIdUserRec(idUserRec);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS,
                    new JSONObject().put("count", count).put("idUserRec", idUserRec));
        } catch (Exception e) {
            logger.error("Error in countUnreadNotificationsByIdUserRecWs :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject getNotificationsByIdUserRecAndIdTypeNotifWs(Long idUserRec, Long idTypeNotif) {
        try {
            if (idUserRec == null || idTypeNotif == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idUserRec and idTypeNotif cannot be null"));
            }

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserRecAndIdTypeNotif(idUserRec, idTypeNotif);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error in getNotificationsByIdUserRecAndIdTypeNotifWs :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject getNotificationsByIdUserRecAndPriorityWs(Long idUserRec, Integer priority) {  // Renommé
        try {
            if (idUserRec == null || priority == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idUserRec and priority cannot be null"));
            }

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdUserRecAndPriority(idUserRec, priority);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error in getNotificationsByIdUserRecAndPriorityWs :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public SendObject getNotificationsByIdMessageParentWs(Long idMessageParent) {
        try {
            if (idMessageParent == null) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM,
                        new JSONObject().put("message", "idMessageParent cannot be null"));
            }

            List<VNotificationDetail> list = vNotificationDetailRepository.findByIdMessageParent(idMessageParent);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(list));
        } catch (Exception e) {
            logger.error("Error in getNotificationsByIdMessageParentWs :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    new JSONObject().put("error", e.getMessage()));
        }
    }

    @Override
    public VNotificationDetail getNotificationById(Long id) {
        try {
            if (id == null) {
                return null;
            }
            return vNotificationDetailRepository.findNotificationById(id);
        } catch (Exception e) {
            logger.error("Error in getNotificationById :: {}", e.getMessage(), e);
            return null;
        }
    }
}