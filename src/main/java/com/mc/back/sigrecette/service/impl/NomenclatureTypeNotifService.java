package com.mc.back.sigrecette.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mc.back.sigrecette.model.NomenclatureTypeNotif;
import com.mc.back.sigrecette.repository.INomenclatureTypeNotifRepository;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.INomenclatureTypeNotifService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;

@Service
public class NomenclatureTypeNotifService implements INomenclatureTypeNotifService{
	private static final Logger logger = LogManager.getLogger(NomenclatureTypeNotifService.class);

    @Autowired
    private INomenclatureTypeNotifRepository  nomenTypeNotifRepo;
    
    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;
	@Override
	public List<NomenclatureTypeNotif> getList() {
		try {
            return nomenTypeNotifRepo.findAll();
        } catch (Exception e) {
            logger.error("Error NomenclatureTypeNotifService in method getList :: {}", String.valueOf(e));
            return null;
        }
	}

	@Override
	public NomenclatureTypeNotif findById(Long id) {
		try {
            SendObject sendObject = commonService.getObjectById(new NomenclatureTypeNotif(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (NomenclatureTypeNotif) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error NomenclatureTypeNotifService in method findById :: {}", String.valueOf(e));
            return null;
        }
	}

	@Override
	public SendObject getListNomenclatureTypeNotifWs() {
		try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error NomenclatureTypeNotifService in method getListNomenclatureTypeNotifWs() :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
	}

}
