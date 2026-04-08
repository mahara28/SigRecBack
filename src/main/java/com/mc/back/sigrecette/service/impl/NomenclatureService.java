package com.mc.back.sigrecette.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import com.mc.back.sigrecette.model.tool.NomenclatureDTO;
import com.mc.back.sigrecette.repository.IParametrageNomenclaturesRepository;
import com.mc.back.sigrecette.repository.NomenclatureRepository;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class NomenclatureService {

    @Autowired
    private NomenclatureRepository nomenclatureRepository;

    @Autowired
    private IParametrageNomenclaturesRepository paramRepo;

    @Autowired
    private UtilsWs utilsWs;

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
}
