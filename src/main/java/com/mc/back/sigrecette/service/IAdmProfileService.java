package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.AdmProfile;
import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface IAdmProfileService {

    List<AdmProfile> getList();

    AdmProfile findById(Long id);

    Boolean deleteById(Long id);

    SendObject getListAdmProfileWs();

    SendObject saveAdmProfileWs(AdmProfile entity);

    SendObject deleteAdmProfileByIdWs(Long id);

    SendObject updateAdmProfileWs(AdmProfile entity);

}
