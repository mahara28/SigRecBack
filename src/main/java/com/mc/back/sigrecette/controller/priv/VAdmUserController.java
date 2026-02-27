package com.mc.back.sigrecette.controller.priv;


import com.mc.back.sigrecette.model.view.VAdmUser;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.service.IVAdmUserService;
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
@RequestMapping("/vAdmUser")
public class VAdmUserController {
    private static final Logger logger = LogManager.getLogger(VAdmUserController.class);

    @Autowired
    private IVAdmUserService vAdmUserService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Operation(summary = "Get all users list data without search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getListAdmUserWs(HttpServletRequest request) {
        try {
            return sendWsService.sendResult(request, vAdmUserService.getListAdmUserWs());
        } catch (Exception argEx) {
            logger.error("Error VAdmUserController in method getListAdmUserWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdmUserByIdWs(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        try {
            return sendWsService.sendResult(request, vAdmUserService.getVAdmUserByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error VAdmUserController in method getAdmUserByIdWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get list of users with search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataAdmUserWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new VAdmUser(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method getDataAdmUserWs :: {}", argEx.toString());
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
            return sendWsService.downloadFile(request, commonService.exportDataWs(obj, new VAdmUser(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method exportDataWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }
}
