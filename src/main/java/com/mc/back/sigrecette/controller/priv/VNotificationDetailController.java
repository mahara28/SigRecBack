
package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.service.IVNotificationDetailService;
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
@RequestMapping("/vNotificationDetail")
public class VNotificationDetailController {

    private static final Logger logger = LogManager.getLogger(VNotificationDetailController.class);

    @Autowired
    private IVNotificationDetailService vNotificationDetailService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Operation(summary = "Get all notifications received by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/received/{idUserRec}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationsByIdUserRecWs(HttpServletRequest request,
                                                           @PathVariable(name = "idUserRec") Long idUserRec) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.getNotificationsByIdUserRecWs(idUserRec));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getNotificationsByIdUserRecWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get all notifications sent by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/sent/{idUserEm}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationsByIdUserEmWs(HttpServletRequest request,
                                                          @PathVariable(name = "idUserEm") Long idUserEm) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.getNotificationsByIdUserEmWs(idUserEm));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getNotificationsByIdUserEmWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get unread notifications for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/unread/{idUserRec}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUnreadNotificationsByIdUserRecWs(HttpServletRequest request,
                                                                 @PathVariable(name = "idUserRec") Long idUserRec) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.getUnreadNotificationsByIdUserRecWs(idUserRec));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getUnreadNotificationsByIdUserRecWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Count unread notifications for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/unread/count/{idUserRec}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> countUnreadNotificationsByIdUserRecWs(HttpServletRequest request,
                                                                   @PathVariable(name = "idUserRec") Long idUserRec) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.countUnreadNotificationsByIdUserRecWs(idUserRec));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method countUnreadNotificationsByIdUserRecWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get notifications by type for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/type/{idUserRec}/{idTypeNotif}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationsByIdUserRecAndIdTypeNotifWs(HttpServletRequest request,
                                                                         @PathVariable(name = "idUserRec") Long idUserRec,
                                                                         @PathVariable(name = "idTypeNotif") Long idTypeNotif) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.getNotificationsByIdUserRecAndIdTypeNotifWs(idUserRec, idTypeNotif));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getNotificationsByIdUserRecAndIdTypeNotifWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get notifications by parent message (thread)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/thread/{idmessageParent}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationsByIdmessageParentWs(HttpServletRequest request,
                                                                 @PathVariable(name = "idmessageParent") Long idmessageParent) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.getNotificationsByIdmessageParentWs(idmessageParent));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getNotificationsByIdmessageParentWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get notifications by priority for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/priority/{idUserRec}/{priorite}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationsByIdUserRecAndPrioriteWs(HttpServletRequest request,
                                                                      @PathVariable(name = "idUserRec") Long idUserRec,
                                                                      @PathVariable(name = "priorite") Integer priorite) {
        try {
            return sendWsService.sendResult(request, vNotificationDetailService.getNotificationsByIdUserRecAndPrioriteWs(idUserRec, priorite));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getNotificationsByIdUserRecAndPrioriteWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get notification by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationByIdWs(HttpServletRequest request,
                                                   @PathVariable(name = "id") Long id) {
        try {
            VNotificationDetail notification = vNotificationDetailService.getNotificationById(id);
            return sendWsService.sendResult(request,
                    notification.getId() != null
                            ? new com.mc.back.sigrecette.tools.model.SendObject(ConstanteWs._CODE_WS_SUCCESS, notification)
                            : new com.mc.back.sigrecette.tools.model.SendObject(ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, null));
        } catch (Exception argEx) {
            logger.error("Error VNotificationUserController in method getNotificationByIdWs :: {}", argEx.toString());
            return sendWsService.sendResultException(request);
        }
    }

    @Operation(summary = "Get list of notifications with search filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_SUCCESS, description = "Success"),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_ALIAS_PARAM, description = "One or many parameter(s) is null", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR_IN_METHOD, description = "Method Error", content = @Content),
            @ApiResponse(responseCode = ConstanteWs._CODE_WS_ERROR, description = "Service Error", content = @Content)})
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataNotificationUserWs(HttpServletRequest request, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(request, commonService.getListPaginator(obj, new VNotificationDetail(), null));
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