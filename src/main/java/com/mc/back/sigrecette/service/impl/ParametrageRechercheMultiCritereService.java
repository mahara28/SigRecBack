package com.mc.back.sigrecette.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.back.sigrecette.model.tool.StatsRequestDto;
import com.mc.back.sigrecette.repository.IParametrageRechercheMultiCritereRepository;
import com.mc.back.sigrecette.service.IParametrageRechercheMultiCritereService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;

@Service
public class ParametrageRechercheMultiCritereService implements IParametrageRechercheMultiCritereService{
	private static final Logger logger = LogManager.getLogger(ParametrageRechercheMultiCritereService.class);
	@Autowired
    private IParametrageRechercheMultiCritereRepository statsRepository;

    @Autowired
    private UtilsWs utilsWs;
    
    @Override
    public SendObject getStats(StatsRequestDto request) {
        try {

            ObjectMapper mapper = new ObjectMapper();

            // ✅ sécuriser filters
            String filtersJson = "{}";
            if (request.getFilters() != null) {
                filtersJson = mapper.writeValueAsString(request.getFilters());
            }

            // ✅ valeurs par défaut
            String aggColumn = request.getAggColumn() != null ? request.getAggColumn() : "*";
            String aggFunc = request.getAggFunc() != null ? request.getAggFunc() : "COUNT";
            String orderBy = request.getOrderBy() != null ? request.getOrderBy() : "value DESC";
            Integer offset = request.getOffset() != null ? request.getOffset() : 0;

            // ✅ appel repo
            String result = statsRepository.getStats(
                    request.getTable(),
                    filtersJson,
                    request.getGroupBy(),
                    aggColumn,
                    aggFunc,
                    orderBy,
                    request.getLimit(),
                    offset
            );

            // ✅ parsing sécurisé
            JSONObject jsonResult;
            if (result != null && !result.trim().isEmpty()) {
                jsonResult = new JSONObject(result);
            } else {
                jsonResult = new JSONObject();
            }

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, jsonResult);

        } catch (Exception e) {
            logger.error("Error getStats :: {}", e.getMessage(), e);
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

}
