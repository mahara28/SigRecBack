package com.mc.back.sigrecette.service;

import java.util.List;

import com.mc.back.sigrecette.model.NomenclatureTypeNotif;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface INomenclatureTypeNotifService {

	List<NomenclatureTypeNotif> getList();
	
	NomenclatureTypeNotif findById(Long id);
	
	SendObject getListNomenclatureTypeNotifWs();
}
