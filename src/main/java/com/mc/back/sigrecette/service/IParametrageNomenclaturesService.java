package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.Notification;
import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface IParametrageNomenclaturesService {

    List<ParametrageNomenclatures> getList();
    SendObject getListParametrageNomenclaturesWs();
}
