package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.AdmFoncProfile;
import com.mc.back.sigrecette.repository.IAdmFoncProfileRepository;
import com.mc.back.sigrecette.service.IAdmFoncProfileService;
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
public class AdmFoncProfileService implements IAdmFoncProfileService {

    private static final Logger logger = LogManager.getLogger(AdmFoncProfileService.class);

    @Autowired
    private IAdmFoncProfileRepository admFoncProfilRepository;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public List<AdmFoncProfile> getList() {
        try {
            return admFoncProfilRepository.findAll();
        } catch (Exception e) {
            logger.error("Error AdmFoncProfileService in method getList :: {}", String.valueOf(e));
            return null;
        }
    }

    @Override
    public AdmFoncProfile findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new AdmFoncProfile(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (AdmFoncProfile) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error AdmFoncProfileService in method findById :: {}", String.valueOf(e));
            return null;
        }
    }

    @Override
    public AdmFoncProfile saveOrUpdate(AdmFoncProfile entity) {
        try {
            return admFoncProfilRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error AdmFoncProfilService in method saveOrUpdate :: {}", String.valueOf(e));
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;
            admFoncProfilRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error AdmFoncProfilService in method deleteById :: {}", String.valueOf(e));
            return false;
        }
    }

    @Override
    public SendObject findAdmFoncProfileByIdWs(Long id) {
        try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            AdmFoncProfile entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error AdmFoncProfilService in method findAdmFoncProfilByIdWs :: {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getListAdmFoncProfileWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error AdmFoncProfilService in method getListAdmFoncProfilWs() :: {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject saveOrUpdateAdmFoncProfileWs(AdmFoncProfile entity) {
        try {
            entity = this.saveOrUpdate(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error AdmFoncProfilService in method saveOrUpdateAdmFoncProfilWs :: {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject deleteAdmFoncProfileByIdWs(Long id) {
        try {
            Boolean resultDelete = this.deleteById(id);
            if (resultDelete == false)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error AdmFoncProfilService in method getListAdmFoncProfil {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public List<AdmFoncProfile> getListAdmFoncProfileByIdProfil(Long idProfil) {
        try {
            return admFoncProfilRepository.getListAdmFoncProfileByIdProfil(idProfil);
        } catch (Exception e) {
            logger.error("Error AdmFoncProfileService in method getListAdmFoncProfileByIdProfil :: {}", String.valueOf(e));
            return null;
        }
    }

}
