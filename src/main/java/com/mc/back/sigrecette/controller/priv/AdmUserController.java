package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.AdmUser;
import com.mc.back.sigrecette.service.IAdmUserService;
import com.mc.back.sigrecette.service.ICommonService;
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
@RequestMapping("/admUser")
@CrossOrigin("*")
public class AdmUserController {
    private static final Logger logger = LogManager.getLogger(AdmUserController.class);

    @Autowired
    private IAdmUserService admUserService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Operation(summary = "Get all users list data without search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getListAdmUserWs(HttpServletRequest request) {
        try {
            return sendWsService.sendResult(request, admUserService.getListAdmUserWs());
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method getListAdmUserWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAdmUserByIdWs(HttpServletRequest request, @PathVariable(name = "id") Long id) {
        try {
            return sendWsService.sendResult(request, admUserService.findAdmUserByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method getAdmUserByIdWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Insert a new user object")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)})
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pushAdmUser(HttpServletRequest request, @RequestBody AdmUser entity) {
        try {
            return sendWsService.sendResult(request, admUserService.saveOrUpdateAdmUserWs(entity));
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method pushAdmUserWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Update User object")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)})
    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putAdmUserWs(HttpServletRequest request, @RequestBody AdmUser entity) {
        try {
            return sendWsService.sendResult(request, admUserService.saveOrUpdateAdmUserWs(entity));
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method putAdmUserWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Delete a user object")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_DELETE_ROW, description = "Cannot delete object because it is a reference", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)})
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAdmUserByIdWs(HttpServletRequest request, @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(request, admUserService.deleteAdmUserByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method deleteAdmUserByIdWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get list of users with search filter")
    @ApiResponses(value = {@ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataAdmUserWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new AdmUser(), null));
        } catch (Exception argEx) {
            logger.error("Error AdmUserController in method getDataAdmUserWs :: {}", String.valueOf(argEx));
            return sendWsService.sendResultException(request);
        }
    }
}
