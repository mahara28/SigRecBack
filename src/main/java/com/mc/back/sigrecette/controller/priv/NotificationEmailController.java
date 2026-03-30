package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.NotificationEmail;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.INotificationEmailService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.model.SearchObject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/notification-email") // Ajout de /api pour meilleure organisation
public class NotificationEmailController {

    private static final Logger logger = LogManager.getLogger(NotificationEmailController.class);

    @Autowired
    private INotificationEmailService notificationEmailService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @Operation(summary = "Get all notification emails", description = "Returns all notification emails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllNotificationEmails(HttpServletRequest authentication) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.getAllNotificationEmails());
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getAllNotificationEmails :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }

    @Operation(summary = "Get notification email by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationEmailById(
            HttpServletRequest authentication,
            @Parameter(description = "Notification email ID", required = true)
            @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.getNotificationEmailById(id));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getNotificationEmailById :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }

    @Operation(summary = "Get notification emails by notification ID")
    @GetMapping(value = "/by-notification/{idNotif}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationEmailsByIdNotif(
            HttpServletRequest authentication,
            @Parameter(description = "Notification ID", required = true)
            @PathVariable("idNotif") Long idNotif) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.getNotificationEmailsByIdNotif(idNotif));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getNotificationEmailsByIdNotif :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }

    @Operation(summary = "Check if email exists")
    @GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> existsByEmail(
            HttpServletRequest authentication,
            @Parameter(description = "Email to check", required = true)
            @RequestParam String email) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.existsByEmail(email));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method existsByEmail :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }

    @Operation(summary = "Add new notification email")
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNotificationEmail(
            HttpServletRequest authentication,
            @Valid @RequestBody NotificationEmail entity) {
        try {
            return sendWsService.sendResult(authentication,
                    notificationEmailService.addNotificationEmail(entity.getEmail(), entity.getIdNotif()));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method addNotificationEmail :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }

    @Operation(summary = "Delete notification email by ID")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNotificationEmailById(
            HttpServletRequest authentication,
            @Parameter(description = "Notification email ID", required = true)
            @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.deleteNotificationEmailById(id));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method deleteNotificationEmailById :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }

    @Operation(summary = "Delete all notification emails for a notification")
    @DeleteMapping(value = "/by-notification/{idNotif}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNotificationEmailsByIdNotif(
            HttpServletRequest authentication,
            @Parameter(description = "Notification ID", required = true)
            @PathVariable("idNotif") Long idNotif) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.deleteNotificationEmailsByIdNotif(idNotif));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method deleteNotificationEmailsByIdNotif :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }

    @Operation(summary = "Get paginated notification emails")
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataNotificationEmailWs(
            HttpServletRequest authentication,
            @Valid @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(authentication,
                    commonService.getListPaginator(obj, new NotificationEmail(), null));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getDataNotificationEmailWs :: {}", argEx.toString(), argEx);
            return sendWsService.sendResultException(authentication);
        }
    }
}