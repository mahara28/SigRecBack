package com.mc.back.sigrecette.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.back.sigrecette.model.ParametrageColumn;
import com.mc.back.sigrecette.repository.IParametrageColumnRepository;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.IParametrageColumnService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;

@Service
public class ParametrageColumnService implements IParametrageColumnService{
	private static final Logger logger = LogManager.getLogger(ParametrageColumnService.class);

    @Autowired
    public IParametrageColumnRepository parametrageColumnRepository;
    
    @Autowired
    private UtilsWs utilsWs;

    @Autowired
    private ICommonService commonService;
    
	@Override
	public ParametrageColumn findById(Long id) {
		try {
            SendObject sendObject = commonService.getObjectById(new ParametrageColumn(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (ParametrageColumn) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error ParametrageColumnService in method findById :: {}", e.toString());
            return null;
        }
	}

	@Override
	public List<ParametrageColumn> getList() {
		try {
            return parametrageColumnRepository.findAll();
        } catch (Exception e) {
            logger.error("Error ParametrageColumnService in method getList :: {}", e.toString());
            return null;
        }
	}
	
	@Override
	public SendObject findByIdWs(Long id) {
		try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            ParametrageColumn entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error ParametrageColumnService in method findByIdWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
	}

	@Override
	public SendObject getListWs() {
		try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error ParametrageColumnService in method getListWs() :: " + e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
	}

	

}
