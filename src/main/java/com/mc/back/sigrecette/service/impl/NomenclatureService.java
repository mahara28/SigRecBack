package com.mc.back.sigrecette.service.impl;
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
    private NomenclatureRepository nomenclatureRepository;

    @Autowired
    private IParametrageNomenclaturesRepository paramRepo;



    public SendObject insertDynamic(NomenclatureDTO dto) {

        try {

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

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(dto.getData());

            String result = nomenclatureRepository.callInsertFunction(tableName, json);

            return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, result);

        } catch (Exception e) {
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_IN_METHOD, e.getMessage());
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

            List<Map<String, Object>> result =
                    nomenclatureRepository.getDataFromTable(tableName);

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


}
