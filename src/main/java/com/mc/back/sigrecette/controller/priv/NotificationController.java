package com.mc.back.sigrecette.controller.priv;

import com.mc.back.sigrecette.model.Notification;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.INotificationService;
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
@RequestMapping("/notification")
public class NotificationController {

    private static final Logger logger = LogManager.getLogger(NotificationController.class);

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private ISendWsService sendWsService;

    @Autowired
    private ICommonService commonService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getListNotificationWs(HttpServletRequest authentication) {
        try {
            return sendWsService.sendResult(authentication, notificationService.getListNotificationWs());
        } catch (Exception argEx) {
            logger.error("Error NotificationController in method getListNotificationWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNotificationByIdWs(HttpServletRequest authentication, @PathVariable(name = "id") Long id) {
        try {
            return sendWsService.sendResult(authentication, notificationService.findNotificationByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error NotificationController in method getNotificationByIdWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    @GetMapping(value = "/ready", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pushNotificationReady(HttpServletRequest authentication, @RequestParam String qid) {
        try {
            return sendWsService.sendResult(authentication, notificationService.pushNotificationReady(qid));
        } catch (Exception argEx) {
            logger.error("Error NotificationController in method pushNotificationReady :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pushNotification(HttpServletRequest authentication, @RequestBody Notification entity) {
        try {
            return sendWsService.sendResult(authentication, notificationService.saveOrUpdateNotificationWs(entity));
        } catch (Exception argEx) {
            logger.error("Error NotificationController in method pushNotificationWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> putNotificationWs(HttpServletRequest authentication, @RequestBody Notification entity) {
        try {
            return sendWsService.sendResult(authentication, notificationService.saveOrUpdateNotificationWs(entity));
        } catch (Exception argEx) {
            logger.error("Error NotificationController in method putNotificationWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> setAsReadWs(HttpServletRequest authentication, @PathVariable Long id) {
        try {
            return sendWsService.sendResult(authentication, notificationService.setAsReadWs(id));
        } catch (Exception argEx) {
            logger.error("Error NotificationController in method setAsReadWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNotificationByIdWs(HttpServletRequest authentication, @PathVariable("id") Long id) {
        try {
            return sendWsService.sendResult(authentication, notificationService.deleteNotificationByIdWs(id));
        } catch (Exception argEx) {
            logger.error("Error NotificationController in method deleteNotificationByIdWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }

    @PostMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDataNotificationWs(HttpServletRequest authentication, @RequestBody SearchObject obj) {
        try {
            return sendWsService.sendResult(authentication, commonService.getListPaginator(obj, new Notification(), null));
        } catch (Exception argEx) {
            logger.error("Error AdministrationController in method getDataNotificationWs :: {}", argEx.toString());
            return sendWsService.sendResultException(authentication);
        }
    }


}
