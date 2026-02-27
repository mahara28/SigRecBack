package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface IAdmFoncService {
    SendObject getAllMenus();

    SendObject getMenusForIdProfil(List<Long> idProfil);

    SendObject getAllMenusChecked(Long idProfil);
}
