package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.AdmFoncProfile;
import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface IAdmFoncProfileService {
    List<AdmFoncProfile> getList();

    AdmFoncProfile findById(Long id);

    AdmFoncProfile saveOrUpdate(AdmFoncProfile entity);

    Boolean deleteById(Long id);

    SendObject findAdmFoncProfileByIdWs(Long id);

    SendObject getListAdmFoncProfileWs();

    SendObject saveOrUpdateAdmFoncProfileWs(AdmFoncProfile entity);

    SendObject deleteAdmFoncProfileByIdWs(Long id);

    List<AdmFoncProfile> getListAdmFoncProfileByIdProfil(Long idProfil);

}
