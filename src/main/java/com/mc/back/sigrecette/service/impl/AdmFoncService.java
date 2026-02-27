package com.mc.back.sigrecette.service.impl;


import com.mc.back.sigrecette.model.AdmFonc;
import com.mc.back.sigrecette.model.AdmProfile;
import com.mc.back.sigrecette.repository.IAdmFoncRepository;
import com.mc.back.sigrecette.service.IAdmFoncService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdmFoncService implements IAdmFoncService {

    private static final Logger logger = LogManager.getLogger(AdmFoncService.class);

    @Autowired
    private IAdmFoncRepository admFoncRepository;

    @Autowired
    private UtilsWs utilsWs;

    @Autowired
    private ICommonService commonService;

    List<AdmFonc> getListMenu(List<AdmFonc> listMenu, List<AdmFonc> listParent) {

        for (AdmFonc admFonc : listParent) {

            List<AdmFonc> listChild = listMenu.stream()
                    .filter(x -> x.getIdParent() != null && x.getIdParent().equals(admFonc.getId()))
                    .collect(Collectors.toList());
            if (!listChild.isEmpty()) {
                admFonc.setListSousMenu(listChild);
                this.getListMenu(listMenu, listChild);
            }
        }
        return listParent;
    }

    @Override
    public SendObject getAllMenus() {
        try {
            List<AdmFonc> listMenu = admFoncRepository.getAllMenus();

            List<AdmFonc> listParent = listMenu.stream().filter(x -> x.getIdParent() == null)
                    .collect(Collectors.toList());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, this.getListMenu(listMenu, listParent));
        } catch (Exception e) {
            logger.error("Error AdmFoncService in method getAllMenus :: {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getMenusForIdProfil(List<Long> idProfil) {
        try {
            List<AdmFonc> listMenu = admFoncRepository.getMenusForIdProfil(idProfil);
            List<AdmFonc> listParent = listMenu.stream().filter(x -> x.getIdParent() == null)
                    .collect(Collectors.toList());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, this.getListMenu(listMenu, listParent));
        } catch (Exception e) {
            logger.error("Error AdmFoncService in method getMenusForIdProfil :: {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

    @Override
    public SendObject getAllMenusChecked(Long idProfil) {
        try {
            List<AdmFonc> listMenu = admFoncRepository.getAllMenus();
            List<AdmFonc> listMenubyprofil = admFoncRepository.getAllMenusCheck(idProfil);
            Map<String, Object> resultMap = new HashMap<>();
            if (idProfil != null) {

                SendObject sendObject = commonService.getObjectById(new AdmProfile(), idProfil.toString(), false);
                Object admProfile = sendObject.getPayload();
                resultMap.put("admProfil", admProfile);
            }

            for (AdmFonc menuProfil : listMenubyprofil) {
                int index = listMenu.indexOf(menuProfil);
                if (index != -1) {
                    listMenu.get(index).setChecked(true);
                }
            }
            List<AdmFonc> listParent = listMenu.stream().filter(x -> x.getIdParent() == null)
                    .collect(Collectors.toList());
            resultMap.put("menus", this.getListMenu(listMenu, listParent));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, resultMap);
        } catch (Exception e) {
            logger.error("Error AdmFoncService in method getAllMenusChecked :: {}", String.valueOf(e));
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

}
