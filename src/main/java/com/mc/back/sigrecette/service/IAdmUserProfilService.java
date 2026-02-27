package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.AdmUserProfil;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdmUserProfilService {

    public List<AdmUserProfil> getList();

    public AdmUserProfil findById(Long id);

    public AdmUserProfil saveOrUpdate(AdmUserProfil entity);

    public Boolean deleteById(Long id);

    public SendObject findAdmUserProfilByIdWs(Long id);

    public SendObject getListAdmUserProfilWs();

    public SendObject saveOrUpdateAdmUserProfilWs(AdmUserProfil entity);

    public SendObject deleteAdmUserProfilByIdWs(Long id);

    public List<AdmUserProfil> getListUserProfilesByIdUser(Long iduser);
}
