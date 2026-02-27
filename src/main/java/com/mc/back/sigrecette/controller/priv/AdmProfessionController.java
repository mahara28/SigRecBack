package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.AdmProfession;
import com.mc.back.sigrecette.service.IAdmProfessionService;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.CriteriaSearch;
import com.mc.back.sigrecette.tools.model.SearchObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/fonction")
public class AdmProfessionController {

    private static final Logger logger = LogManager.getLogger(AdmProfessionController.class);

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    private IAdmProfessionService admProfessionService;

    @Operation(summary = "Recuperation liste de donnees avec filtre de recherche")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataAdmProfessionWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            if (obj.getDataSearch() != null && !obj.getDataSearch().isEmpty()) {
                for (CriteriaSearch oneSearchElement : obj.getDataSearch()) {
                    if (oneSearchElement.getValue().toString().contains("'")) {
                        oneSearchElement.setValue(oneSearchElement.getValue().toString().replace("'", "''"));
                    }
                }
            }

            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new AdmProfession(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmProfessionController in method getDataAdmProfessionWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Recuperation liste de donnees avec filtre de recherche")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @GetMapping(value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> FindById(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(request, admProfessionService.findFonctionIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error AdmProfessionController in method FindById :: {}", argEx.toString());
            return null;
        }
    }

    @Operation(summary = "Delete data by ID with search filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or more parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)
    })
    @DeleteMapping(value = "/deleteById", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteById(HttpServletRequest request, @RequestParam("idFonction") Long idFonction) {
        try {
            return sendWsService.sendResult(request, admProfessionService.deleteFonctionByIdWs(idFonction));
        } catch (Exception argEx) {
            logger.error("Error AdmProfessionController in method deleteDataAdmProfessionWs :: {}", argEx.toString());
            return null;
        }
    }

    @Operation(summary = "Insert a new object")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or more parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)
    })
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addFonction(HttpServletRequest request, @RequestBody AdmProfession entity) {
        try {
            return sendWsService.sendResult(request, admProfessionService.saveOrUpdateFonction(entity));
        } catch (Exception argEx) {
            logger.error("Error AdmProfessionController in addFonction method: {}", argEx.toString());
            return null;
        }
    }

    @Operation(summary = "Mise a jour l'objet")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editFonction(HttpServletRequest request, @RequestBody AdmProfession entity) {
        try {
            return sendWsService.sendResult(request, admProfessionService.saveOrUpdateFonction(entity));
        } catch (Exception argEx) {
            logger.error("Error AdmProfessionController in method editFonction :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Export data")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> exportDataWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.downloadFile(request, commonService.exportDataWs(obj, new AdmProfession(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmProfessionController in method exportDataWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }
}
