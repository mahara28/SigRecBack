package com.mc.back.sigrecette.controller.priv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.back.sigrecette.model.NomenclatureTypeNotif;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.INomenclatureTypeNotifService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.CriteriaSearch;
import com.mc.back.sigrecette.tools.model.SearchObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/nomen-typeNotif")
public class NomenclatureTypeNotifController {

	private static final Logger logger = LogManager.getLogger(NomenclatureTypeNotifController.class);

    @Autowired
    private INomenclatureTypeNotifService nomenTypeNotifService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;
    
    @Operation(summary = "Get all users list data with search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataNomenTypeNotifWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            if (obj.getDataSearch() != null && !obj.getDataSearch().isEmpty()) {
                for (CriteriaSearch oneSearchElement : obj.getDataSearch()) {
                    if (oneSearchElement.getValue().toString().contains("'")) {
                        oneSearchElement.setValue(oneSearchElement.getValue().toString().replace("'", "''"));
                    }
                }
            }

            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new NomenclatureTypeNotif(), null));
        } catch (Exception argEx) {
            logger.error("Error NomenclatureTypeNotifController in method getDataNomenTypeNotifWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }
}
