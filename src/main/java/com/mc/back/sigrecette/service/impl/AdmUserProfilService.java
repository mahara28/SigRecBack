package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.AdmUserProfil;
import com.mc.back.sigrecette.repository.IAdmUserProfilRepository;
import com.mc.back.sigrecette.service.IAdmUserProfilService;
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
public class AdmUserProfilService implements IAdmUserProfilService {
    private static final Logger logger = LogManager.getLogger(AdmUserProfilService.class);

    @Autowired
    private IAdmUserProfilRepository admUserProfilRepository;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public List<AdmUserProfil> getList() {
        try {
            return admUserProfilRepository.findAll();
        } catch (Exception e) {
            logger.error("Error AdmUserProfilService in method getList :: " + e);
            return null;
        }
    }

    @Override
    public AdmUserProfil findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new AdmUserProfil(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (AdmUserProfil) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error AdmUserProfilService in method findById :: " + e);
            return null;
        }
    }

    @Override
    public AdmUserProfil saveOrUpdate(AdmUserProfil entity) {
        try {
            return admUserProfilRepository.save(entity);
        } catch (Exception e) {
            logger.error("Error AdmUserProfilService in method saveOrUpdate :: " + e);
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;
            admUserProfilRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error AdmUserProfilService in method deleteById :: " + e);
            return false;
        }
    }

    @Override
    public SendObject findAdmUserProfilByIdWs(Long id) {
        try {
            if (id == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            AdmUserProfil entity = this.findById(id);
            if (entity == null)
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error(
                    "Error AdmUserProfilService in method findAdmUserProfilByIdWs :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getListAdmUserProfilWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error(
                    "Error AdmUserProfilService in method getListAdmUserProfilWs() :: " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject saveOrUpdateAdmUserProfilWs(AdmUserProfil entity) {
        try {
            entity = this.saveOrUpdate(entity);
            if (entity == null)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_SAVE_OR_UPDATE, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject(entity));
        } catch (Exception e) {
            logger.error("Error AdmUserProfilService in method saveOrUpdateAdmUserProfilWs :: "
                    + e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject deleteAdmUserProfilByIdWs(Long id) {
        try {
            Boolean resultDelete = this.deleteById(id);
            if (resultDelete == false)
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error AdmUserProfilService in method getListAdmUserProfil " + e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public List<AdmUserProfil> getListUserProfilesByIdUser(Long iduser) {
        try {
            return admUserProfilRepository.getListUserProfilesByIdUser(iduser);
        } catch (Exception e) {
            logger.error("Error AdmUserProfilService in method getListUserProfilesByIdUser :: " + e);
            return null;
        }
    }
}
