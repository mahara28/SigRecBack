package com.mc.back.sigrecette.service.impl;
import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import com.mc.back.sigrecette.repository.IParametrageNomenclaturesRepository;
import com.mc.back.sigrecette.service.IParametrageNomenclaturesService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ParametrageNomenclaturesService implements IParametrageNomenclaturesService {

    private static final Logger logger = LogManager.getLogger(ParametrageNomenclaturesService.class);
    @Autowired
    private IParametrageNomenclaturesRepository ParametrageNomenclaturesRepository;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public List<ParametrageNomenclatures> getList() {
        try {
            return ParametrageNomenclaturesRepository.findAll();
        } catch (Exception e) {
            logger.error("Error ParametrageNomenclaturesService in method getList :: {}", e.toString());
            return null;
        }
    }
    @Override
    public SendObject getListParametrageNomenclaturesWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error ParametrageNomenclaturesService in method getListParametrageNomenclaturesWs() :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }
}
