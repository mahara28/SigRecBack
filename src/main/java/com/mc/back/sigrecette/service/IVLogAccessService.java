package com.mc.back.sigrecette.service;


import com.mc.back.sigrecette.model.view.VAdmLogAccess;
import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface IVLogAccessService {
    List<VAdmLogAccess> getList();

    VAdmLogAccess findById(Long id);

    SendObject findVLogAccessByIdWs(Long id);
}
