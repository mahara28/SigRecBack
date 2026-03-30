package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.NotificationEmail;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.INotificationEmailService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.model.SearchObject;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/notification-email")
public class NotificationEmailController {

    private static final Logger logger = LogManager.getLogger(NotificationEmailController.class);

    @Autowired
    private INotificationEmailService notificationEmailService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    // GET ALL
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllNotificationEmails(HttpServletRequest authentication) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.getAllNotificationEmails());
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getAllNotificationEmails :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    // GET BY ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationEmailById(HttpServletRequest authentication, @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.getNotificationEmailById(id));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getNotificationEmailById :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    // GET BY ID NOTIF
    @GetMapping(value = "/notif/{idNotif}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationEmailsByIdNotif(HttpServletRequest authentication, @PathVariable("idNotif") Long idNotif) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.getNotificationEmailsByIdNotif(idNotif));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getNotificationEmailsByIdNotif :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    // CHECK IF EMAIL EXISTS
    @GetMapping(value = "/exists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> existsByEmail(HttpServletRequest authentication, @RequestParam String email) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.existsByEmail(email));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method existsByEmail :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    // POST - ADD
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNotificationEmail(HttpServletRequest authentication, @RequestBody NotificationEmail entity) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.addNotificationEmail(entity.getEmail(), entity.getIdNotif()));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method addNotificationEmail :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    // DELETE BY ID
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNotificationEmailById(HttpServletRequest authentication, @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.deleteNotificationEmailById(id));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method deleteNotificationEmailById :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    // DELETE BY ID NOTIF
    @DeleteMapping(value = "/notif/{idNotif}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNotificationEmailsByIdNotif(HttpServletRequest authentication, @PathVariable("idNotif") Long idNotif) {
        try {
            return sendWsService.sendResult(authentication, notificationEmailService.deleteNotificationEmailsByIdNotif(idNotif));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method deleteNotificationEmailsByIdNotif :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    // POST - PAGINATOR
    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataNotificationEmailWs(HttpServletRequest authentication, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(authentication, commonService.getListPaginator(obj, new NotificationEmail(), null));
        } catch (Exception argEx) {
            logger.error("Error NotificationEmailController in method getDataNotificationEmailWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }
}



