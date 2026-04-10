package com.mc.back.sigrecette.service.impl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import com.mc.back.sigrecette.model.tool.NomenclatureDTO;
import com.mc.back.sigrecette.repository.*;
import com.mc.back.sigrecette.security.JwtSecurity;
import com.mc.back.sigrecette.service.IAdmUserProfilService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ILogAccessService;
import com.mc.back.sigrecette.service.INomenclatureService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class NomenclatureService implements INomenclatureService {

    private static final Logger logger = LogManager.getLogger(NomenclatureService.class);


    @Autowired
    private UtilsWs utilsWs;


    @Autowired
    private IParametrageNomenclaturesRepository paramRepo;



    public SendObject insertDynamic(NomenclatureDTO dto) {

        try {

            // 1. Validation
            if (dto.getNomTable() == null || dto.getNomTable().isEmpty()) {
                return utilsWs.resultWs("400", "Nom table obligatoire");
            }

            if (dto.getData() == null || dto.getData().isEmpty()) {
                return utilsWs.resultWs("400", "Data vide");
            }

            String tableName = dto.getNomTable().toLowerCase().trim();

            Optional<ParametrageNomenclatures> param =
                    paramRepo.findByNomTable(tableName);

            if (param.isEmpty()) {
                return utilsWs.resultWs("404", "Table non autorisée");
            }

            // 2. Convert DTO → JSON
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(dto.getData());

            // 3. Appel DB
            String result ;
            
            boolean hasId = dto.getData().containsKey("id") 
                    && dto.getData().get("id") != null
                    && !dto.getData().get("id").toString().isBlank();

	       if (hasId) {
	           result = paramRepo.callUpdateFunction(tableName, json);
	       } else {
	           result = paramRepo.callInsertFunction(tableName, json);
	       }

            // 4. Parse JSON retourné 🔥
            Map<String, Object> resultMap = mapper.readValue(result, Map.class);

            String code = (String) resultMap.get("code");

            // 5. Gestion des cas métier
            switch (code) {

                case ConstanteWs._CODE_WS_SUCCESS:
                    return utilsWs.resultWs(
                            ConstanteWs._CODE_WS_SUCCESS,
                            resultMap.get("data")
                    );
                
                case ConstanteWs._CODE_WS_ERROR_UNIQUE_CODE:
                    return utilsWs.resultWs(
                            ConstanteWs._CODE_WS_ERROR_UNIQUE_CODE,
                            resultMap.get("data")
                    );

                case "421":
                case "422":
                case "404":
                    return utilsWs.resultWs(
                            code,
                            resultMap.get("error")
                    );

                case "500":
                    return utilsWs.resultWs(
                            ConstanteWs._CODE_WS_ERROR,
                            resultMap
                    );

                default:
                    return utilsWs.resultWs(
                            ConstanteWs._CODE_WS_ERROR,
                            "Réponse inconnue de la base"
                    );
            }

        } catch (Exception e) {
            return utilsWs.resultWs(
                    ConstanteWs._CODE_WS_ERROR_IN_METHOD,
                    e.getMessage()
            );
        }
    }

    @Override
    public SendObject getDataWs(String nomTable) {

        try {

            if (nomTable == null || nomTable.trim().isEmpty()) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, new JSONObject());
            }

            String tableName = nomTable.toLowerCase().trim();

            Optional<ParametrageNomenclatures> param =
                    paramRepo.findByNomTable(tableName);

            if (param.isEmpty()) {
                return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, new JSONObject());
            }

            String jsonString =
            		paramRepo.getDataFromTable(tableName);
            ObjectMapper mapper = new ObjectMapper();
            
            List<Map<String, Object>> result =
            	    mapper.readValue(jsonString, new TypeReference<>() {});

            // Conversion vers JSONArray (comme ton projet)
            JSONArray jsonArray = new JSONArray();

            for (Map<String, Object> row : result) {
                JSONObject jsonObject = new JSONObject(row);
                jsonArray.put(jsonObject);
            }

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, jsonArray);

        } catch (Exception e) {
            logger.error("Error NomenclatureService in method getDataDynamicWs :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, new JSONObject());
        }
    }

	@Override
	public SendObject findByIdWs(String nomTable, Long id) {
		String entity = paramRepo.findByIdFromDynamicTable(nomTable, id);
		
		return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject(entity));
	}


}
