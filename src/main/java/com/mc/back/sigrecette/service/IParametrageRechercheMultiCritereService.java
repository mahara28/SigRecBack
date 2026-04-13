package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.tool.StatsRequestDto;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface IParametrageRechercheMultiCritereService {
	
	SendObject getStats(StatsRequestDto request);

}
