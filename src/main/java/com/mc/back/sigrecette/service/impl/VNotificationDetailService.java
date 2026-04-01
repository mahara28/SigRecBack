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
    public List<VNotificationDetail> getList() {
        try {
            return vNotificationDetailRepository.findAll();
        } catch (Exception e) {
            logger.error("Error NotificationService in method getList :: {}", e.toString());
            return null;
        }
    }

    @Override
    public SendObject getListNotificationDetailWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error NotificationDetailService in method getListNotificationDetailWs() :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }
}