package com.mc.back.sigrecette.controller.priv;
import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import com.mc.back.sigrecette.model.tool.NomenclatureDTO;
<<<<<<< HEAD
import com.mc.back.sigrecette.repository.IParametrageNomenclaturesRepository;
import com.mc.back.sigrecette.repository.NomenclatureRepository;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.impl.NomenclatureService;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.SendObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
=======
import com.mc.back.sigrecette.model.view.VParamNomenColumns;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.service.impl.NomenclatureService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.SearchObject;
import com.mc.back.sigrecette.tools.model.SendObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

>>>>>>> 38bd612835afff3ce47f04ff312d0dcfbcd340d1
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin("*")
@RequestMapping("/dynamic-nomenclature")
public class DynamicNomenclatureController {
	private static final Logger logger = LogManager.getLogger(DynamicNomenclatureController.class);
	@Autowired
    private ISendWsService sendWsService;

    private static final Logger logger = LogManager.getLogger(DynamicNomenclatureController.class);


    @Autowired
    private ICommonService commonService;
    
    @Autowired
    private  NomenclatureService nomenclatureservice;
    @Autowired
    private ICommonService commonService;
    @Autowired
    private UtilsWs utilsWs;
    @Autowired
    private IParametrageNomenclaturesRepository paramRepo;
    @Autowired
    private NomenclatureRepository nomenclatureRepository;
    @PostMapping("/insert")
    public ResponseEntity<?> insert(HttpServletRequest request,
                                    @RequestBody NomenclatureDTO dto) {

        SendObject result = nomenclatureservice.insertDynamic(dto);
        return ResponseEntity.ok(result);
    }
<<<<<<< HEAD

    @Autowired
    private ISendWsService sendWsService;

    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getData(HttpServletRequest request,
                                     @RequestParam String nomTable) {
        try {
            return sendWsService.sendResult(
                    request,
                    nomenclatureservice.getDataWs(nomTable)
            );
        } catch (Exception e) {
            logger.error("Error DynamicNomenclatureController in method getData :: {}", e.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Export dynamic nomenclature data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Paramètre invalide"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur méthode"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur service")
    })
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> exportData(HttpServletRequest request,
                                        @RequestParam String nomTable) {

        try {

            if (nomTable == null || nomTable.trim().isEmpty()) {
                return sendWsService.sendResult(request,
                        utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, null));
            }

            String tableName = nomTable.toLowerCase().trim();

            // Vérification sécurité
            Optional<ParametrageNomenclatures> param =
                    paramRepo.findByNomTable(tableName);

            if (param.isEmpty()) {
                return sendWsService.sendResult(request,
                        utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, null));
            }

            // Récupération data
            List<Map<String, Object>> data =
                    nomenclatureRepository.getDataFromTable(tableName);

            if (data == null || data.isEmpty()) {
                return sendWsService.sendResult(request,
                        utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR_NOT_EXISTS_ROW_DATA_BASE, null));
            }

            // ===== Création Excel =====
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(tableName);

            // Header
            Row headerRow = sheet.createRow(0);
            Set<String> keys = data.get(0).keySet();

            int colIndex = 0;
            for (String key : keys) {
                headerRow.createCell(colIndex++).setCellValue(key);
            }

            // Data
            int rowIndex = 1;
            for (Map<String, Object> rowData : data) {
                Row row = sheet.createRow(rowIndex++);
                colIndex = 0;

                for (String key : keys) {
                    Object value = rowData.get(key);
                    row.createCell(colIndex++)
                            .setCellValue(value != null ? value.toString() : "");
                }
            }

            // Création fichier
            // Création fichier
            File file = new File(tableName + "_export.xlsx");
            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);

            fos.close();
            workbook.close();

// ✅ Création manuelle du SendObject
            SendObject sendObject = new SendObject();
            sendObject.setCode(ConstanteWs._CODE_WS_SUCCESS);
            sendObject.setPayload(file);

// ✅ download fonctionne correctement
            return sendWsService.downloadFile(request, sendObject);

        } catch (Exception e) {
            logger.error("Error DynamicNomenclatureController in method exportData :: {}", e.toString());
            return sendWsService.sendResultException(request);
        }
    }
=======
    
>>>>>>> 38bd612835afff3ce47f04ff312d0dcfbcd340d1
}
