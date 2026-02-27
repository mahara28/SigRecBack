package com.mc.back.sigrecette.service;


import com.mc.back.sigrecette.model.view.VAdmUser;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface IVAdmUserService {
    SendObject getVAdmUserByIdWs(Long id);

    VAdmUser getVAdmUserById(Long id);

    SendObject getVAdmUserByEmailWs(String email);

    SendObject getListAdmUserWs();
}
