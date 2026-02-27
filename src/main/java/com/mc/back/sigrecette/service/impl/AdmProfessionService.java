package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.AdmProfession;
import com.mc.back.sigrecette.repository.IAdmProfessionRepository;
import com.mc.back.sigrecette.service.IAdmProfessionService;
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
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdmProfessionService implements IAdmProfessionService {
    private static final Logger logger = LogManager.getLogger(AdmProfessionService.class);

    @Autowired
    public IAdmProfessionRepository admProfessionRepository;

    @Autowired
    private UtilsWs utilsWs;

    @Autowired
    private ICommonService commonService;

    @Override
    public List<AdmProfession> getList() {
        try {
            return admProfessionRepository.findAll();
        } catch (Exception e) {
            logger.error("Error AdmProfessionService in method getList :: {}", e.toString());
            return null;
        }
    }

    @Override
    public AdmProfession findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new AdmProfession(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (AdmProfession) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error AdmProfessionService in method findById :: {}", e.toString());
            return null;
        }
    }

    @Override
    public SendObject findFonctionIdWs(Long id) {
        try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            AdmProfession entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error AdmProfessionService in method findFonctionIdWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;
            admProfessionRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error AdmProfessionService in method deleteById :: " + e.toString());
            return false;
        }
    }

    @Override
    public SendObject getListFonctionWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error AdmProfessionService in method getfonctionWs() :: " + e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }


    @Override
    public SendObject deleteFonctionByIdWs(Long id) {
        try {
            Boolean resultDelete = this.deleteById(id);
            if (resultDelete == false)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error AdmProfessionService in method deleteFonctionByIdWs{}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject saveOrUpdateFonction(AdmProfession entity) {
        try {
            entity = admProfessionRepository.save(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject(entity));
        } catch (DataAccessException ex) {
            return commonService.isUnicode(ex);
        }
    }

}
