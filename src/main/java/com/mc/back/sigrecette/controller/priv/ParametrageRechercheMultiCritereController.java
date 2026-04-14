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

import com.mc.back.sigrecette.model.ParametrageRechercheMultiCritere;
import com.mc.back.sigrecette.model.tool.StatsRequestDto;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.service.impl.ParametrageRechercheMultiCritereService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.SearchObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/stats")
public class ParametrageRechercheMultiCritereController {
	private static final Logger logger = LogManager.getLogger(ParametrageRechercheMultiCritereController.class);
	@Autowired
    private ParametrageRechercheMultiCritereService statsService;
	
	 @Autowired
	 private ISendWsService sendWsService;

	 @Autowired
	 private ICommonService commonService;
	
	 @Operation(summary = "Recuperation liste de donnees avec filtre de recherche")
	 @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
	 @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "Un ou plus param�tre(s) est null", content = @Content),
	 @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Erreur du methode", content = @Content),
	 @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Erreur du service", content = @Content)})
	 @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<?> getDataWs(HttpServletRequest request, @RequestBody SearchObject obj) {
	        try {
	            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new ParametrageRechercheMultiCritere(), null));
	        } catch (Exception argEx) {
	            logger.error("Error ParametrageRechercheMultiCritereController in method getDataWs :: {}", String.valueOf(argEx));
	            return sendWsService.sendResultException(request);
	        }
	    }
	 
	@Operation(summary = "get Stats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or more parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)
    })
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStats(HttpServletRequest request, @RequestBody StatsRequestDto entity) {
        try {
            return sendWsService.sendResult(request, statsService.getStats(entity));
        } catch (Exception argEx) {
            logger.error("Error ParametrageRechercheMultiCritereController in getStats method: {}", argEx.toString());
            return null;
        }
    }
}
