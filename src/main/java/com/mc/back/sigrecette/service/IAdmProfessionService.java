package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.AdmProfession;
import com.mc.back.sigrecette.tools.model.SendObject;


import java.util.List;

public interface IAdmProfessionService {
    List<AdmProfession> getList();

    AdmProfession findById(Long id);

    SendObject findFonctionIdWs(Long id);

    Boolean deleteById(Long id);

    SendObject getListFonctionWs();

    SendObject deleteFonctionByIdWs(Long id);

    SendObject saveOrUpdateFonction(AdmProfession entity);
}
