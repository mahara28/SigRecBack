package com.mc.back.sigrecette.service.impl;


import com.mc.back.sigrecette.model.AdmFoncProfile;
import com.mc.back.sigrecette.model.AdmProfile;
import com.mc.back.sigrecette.model.tool.AdmFoncProfileDto;
import com.mc.back.sigrecette.repository.IAdmFoncProfileRepository;
import com.mc.back.sigrecette.repository.IAdmProfileRepository;
import com.mc.back.sigrecette.service.IAdmProfileService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdmProfileService implements IAdmProfileService {
    private static final Logger logger = LogManager.getLogger(AdmProfileService.class);

    @Autowired
    private IAdmProfileRepository admProfileRepository;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private UtilsWs utilsWs;

    @Autowired
    private IAdmFoncProfileRepository admFoncProfileRepository;

    @Override
    public List<AdmProfile> getList() {
        try {
            return admProfileRepository.findAll();
        } catch (Exception e) {
            logger.error("Error AdmProfileService in method getList :: {}", String.valueOf(e));
            return null;
        }
    }

    @Override
    public AdmProfile findById(Long id) {
        try {
            SendObject sendObject = commonService.getObjectById(new AdmProfile(), id.toString(), false);
            if (sendObject.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return (AdmProfile) sendObject.getPayload();
            return null;
        } catch (Exception e) {
            logger.error("Error AdmProfileService in method findById :: {}", String.valueOf(e));
            return null;
        }
    }

    @Override
    public Boolean deleteById(Long id) {
        try {
            if (id == null)
                return false;
            admProfileRepository.delete(this.findById(id));
            return true;
        } catch (Exception e) {
            logger.error("Error AdmProfileService in method deleteById :: {}", e.toString());
            return false;
        }
    }

    @Override
    public SendObject getListAdmProfileWs() {
        try {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONArray(this.getList()));
        } catch (Exception e) {
            logger.error("Error AdmProfileService in method getListAdmProfilWs() :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject saveAdmProfileWs(AdmProfile entity) {
        try {

            boolean isCodeUnique = admProfileRepository.isCodeUnique(entity.getCode());

            if (!isCodeUnique) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_SAVE_OR_UPDATE, new JSONObject());
            }
            entity.setDateCreate(new Date());
            entity.setDateUpdate(new Date());
            AdmProfile savedEntity = admProfileRepository.save(entity);
            List<AdmFoncProfileDto> listAdmFoncIds = entity.getListAdmFoncIds();

            List<AdmFoncProfile> admFoncProfils = new ArrayList<>();

            for (AdmFoncProfileDto menuId : listAdmFoncIds) {
                AdmFoncProfile admFoncProfil = new AdmFoncProfile();
                admFoncProfil.setIdProfil(savedEntity.getId());
                admFoncProfil.setIdFonc(menuId.getIdFonc());
                admFoncProfil.setIsList(menuId.getIsList());
                admFoncProfil.setIsUpdate(menuId.getIsUpdate());
                admFoncProfil.setIsSupp(menuId.getIsSupp());
                admFoncProfil.setIsDetails(menuId.getIsDetails());
                admFoncProfil.setIsExport(menuId.getIsExport());
                admFoncProfil.setIsImprime(menuId.getIsImprime());
                admFoncProfil.setIsAdd(menuId.getIsAdd());
                
                admFoncProfils.add(admFoncProfil);
            }

            admFoncProfileRepository.saveAll(admFoncProfils);

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error AdmProfileService in method saveAdmProfileWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject updateAdmProfileWs(AdmProfile entity) {
        try {
            Optional<AdmProfile> profile = admProfileRepository.findById(entity.getId());
            if (profile.isPresent()) {
                AdmProfile existingEntity = profile.get();
                existingEntity.setCode(entity.getCode());
                existingEntity.setDesFr(entity.getDesFr());
                existingEntity.setDesEn(entity.getDesEn());
                existingEntity.setIsActive(entity.getIsActive());
                existingEntity.setDateUpdate(new Date());

                AdmProfile updatedEntity = admProfileRepository.save(existingEntity);

                List<AdmFoncProfileDto> listAdmFoncIds = entity.getListAdmFoncIds();
                List<AdmFoncProfile> admFoncProfiles = new ArrayList<>();

                admFoncProfileRepository.deleteByIdAdmProfil(updatedEntity.getId());
                for (AdmFoncProfileDto menuId : listAdmFoncIds) {
                    AdmFoncProfile admFoncProfile = new AdmFoncProfile();
                    admFoncProfile.setIdProfil(updatedEntity.getId());
                    admFoncProfile.setIdFonc(menuId.getIdFonc());
                    admFoncProfile.setIsList(menuId.getIsList());
                    admFoncProfile.setIsUpdate(menuId.getIsUpdate());
                    admFoncProfile.setIsSupp(menuId.getIsSupp());
                    admFoncProfile.setIsDetails(menuId.getIsDetails());
                    admFoncProfile.setIsExport(menuId.getIsExport());
                    admFoncProfile.setIsImprime(menuId.getIsImprime());
                    admFoncProfile.setIsAdd(menuId.getIsAdd());
                    
                    admFoncProfiles.add(admFoncProfile);
                }

                admFoncProfileRepository.saveAll(admFoncProfiles);

                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
            } else {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_SAVE_OR_UPDATE, new JSONObject());
            }
        } catch (Exception e) {
            logger.error("Error AdmProfileService in method updateAdmProfileWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject deleteAdmProfileByIdWs(Long id) {
        try {
            admFoncProfileRepository.deleteByIdAdmProfil(id);
            Boolean resultDelete = this.deleteById(id);

            if (!resultDelete) {
                return utilsWs.resultWs(ConstanteService._CODE_SERVICE_ERROR_DELETE_ROW, new JSONObject());
            }

            return utilsWs.resultWs(ConstanteService._CODE_SERVICE_SUCCESS, new JSONObject());
        } catch (Exception e) {
            logger.error("Error AdmProfileService in method deleteAdmProfileByIdWs {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

}
