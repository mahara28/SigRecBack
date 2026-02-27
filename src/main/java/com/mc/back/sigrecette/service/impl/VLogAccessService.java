package com.mc.back.sigrecette.service.impl;


import com.mc.back.sigrecette.model.view.VAdmLogAccess;
import com.mc.back.sigrecette.repository.IVLogAccessRepository;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.IVLogAccessService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VLogAccessService implements IVLogAccessService {

    private static final Logger logger = LogManager.getLogger(VLogAccessService.class);

    @Autowired
    private IVLogAccessRepository vLogAccessRepository;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public List<VAdmLogAccess> getList() {
        try {
            return vLogAccessRepository.findAll();
        } catch (Exception e) {
            logger.error("Error VLogAccessService in method getList :: {}", e.toString());
            return null;
        }
    }

    @Override
    public VAdmLogAccess findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new VAdmLogAccess(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (VAdmLogAccess) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error VLogAccessService in method findById :: {}", e.toString());
            return null;
        }
    }

    @Override
    public SendObject findVLogAccessByIdWs(Long id) {
        try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            VAdmLogAccess entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error VLogAccessService in method findVLogAccessByIdWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }
}
