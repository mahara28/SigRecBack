package com.mc.back.sigrecette.controller.priv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.back.sigrecette.model.ParametrageColumn;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.IParametrageColumnService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.SearchObject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/paramColu")
@CrossOrigin("*")
public class ParametrageColumnController {
	private static final Logger logger = LogManager.getLogger(ParametrageColumnController.class);
	
	@Autowired
    private IParametrageColumnService paramColuService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;
    
    @Operation(summary = "Get list with search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new ParametrageColumn(), null));
        } catch (Exception argEx) {
            logger.error("Error ParametrageColumnController in method getDataWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }
    
    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByIdWs(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        try {
            return sendWsService.sendResult(request, paramColuService.findByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error ParametrageColumnController in method getByIdWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }
}
