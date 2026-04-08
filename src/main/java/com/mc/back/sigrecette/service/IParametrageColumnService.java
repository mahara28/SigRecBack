package com.mc.back.sigrecette.service;

import java.util.List;
import com.mc.back.sigrecette.model.ParametrageColumn;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface IParametrageColumnService {
	
	ParametrageColumn findById(Long id);
	List<ParametrageColumn> getList();
	
	SendObject findByIdWs(Long id);
    SendObject getListWs();

}
