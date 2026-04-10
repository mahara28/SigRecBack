package com.mc.back.sigrecette.service;
import com.mc.back.sigrecette.tools.model.SendObject;
public interface INomenclatureService {

    SendObject getDataWs(String nomTable);
    
    SendObject findByIdWs(String nomTable,Long id);
    

}
