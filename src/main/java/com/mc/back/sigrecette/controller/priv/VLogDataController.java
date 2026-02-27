package com.mc.back.sigrecette.controller.priv;


import com.mc.back.sigrecette.model.view.VAdmLogData;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.service.IVLogDataService;
import com.mc.back.sigrecette.tools.ConstanteWs;
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
@RequestMapping("/VAdmLogData")
@CrossOrigin("*")
public class VLogDataController {

    private static final Logger logger = LogManager.getLogger(VLogDataController.class);

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Autowired
    IVLogDataService vLogDataService;

    @Operation(summary = "Recuperation un objet d'apres son id")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLogDataByIdWs(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        try {
            return sendWsService.sendResult(request, vLogDataService.findVLogDataByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error VLogDataController in method getLogDataByIdWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataVLogDataWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(request, commonService.getListPaginatorNative(obj, new VAdmLogData(), null));
        } catch (Exception argEx) {
            logger.error("Error VLogDataController in method getDataVLogAccessWs :: {}", argEx.toString());
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
            return sendWsService.downloadFile(request, commonService.exportDataWs(obj, new VAdmLogData(), null));
        } catch (Exception argEx) {
            logger.error("Error VLogDataController in method exportDataWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }
}
