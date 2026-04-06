package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.IParametrageNomenclaturesService;
import com.mc.back.sigrecette.service.ISendWsService;
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
@CrossOrigin("*")
@RequestMapping("/parametrageNomenclatures")
public class ParametrageNomenclaturesController {

    private static final Logger logger = LogManager.getLogger(ParametrageNomenclaturesController.class);

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;


    @Operation(summary = "Get list of ParametrageNomenclatures  with search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataParametrageNomenclaturesWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new ParametrageNomenclatures(), null));
        } catch (Exception argEx) {
            logger.error("Error ParametrageNomenclaturesController in method getDataParametrageNomenclaturesWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }
}
