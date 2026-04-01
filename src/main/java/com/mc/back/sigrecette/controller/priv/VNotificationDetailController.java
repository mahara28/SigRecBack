
package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.service.IVNotificationDetailService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.SearchObject;
import com.mc.back.sigrecette.tools.model.SendObject;
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
@RequestMapping("/vNotificationDetail")
public class VNotificationDetailController {

    private static final Logger logger = LogManager.getLogger(VNotificationDetailController.class);

    @Autowired
    private IVNotificationDetailService vNotificationDetailService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;


    @Operation(summary = "Get list of notifications with search filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataNotificationUserWs(HttpServletRequest request ) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.getListNotificationDetailWs());
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getDataNotificationUserWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Export notification data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> exportDataWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.downloadFile(request, commonService.exportDataWs(obj, new VNotificationDetail(), null));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method exportDataWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }
}