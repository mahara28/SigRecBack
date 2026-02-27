package com.mc.back.sigrecette.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.back.sigrecette.model.view.VAdmUser;
import com.mc.back.sigrecette.service.ICommonService;
import com.mc.back.sigrecette.service.ISendWsService;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.model.LogEvent;
import com.mc.back.sigrecette.tools.model.SendObject;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Service
public class SendWsService implements ISendWsService {

    private static final Logger logger = LogManager.getLogger(SendWsService.class);

    @Autowired
    private LogDataService logDataService;

    @Autowired
    private AdmUserService admUserService;

    @Autowired
    private ICommonService commonService;

    @Override
    public ResponseEntity<?> sendResult(HttpServletRequest request, SendObject so) {
        try {
            LogEvent logEvent = new LogEvent(request.getRemoteAddr() + ":" + request.getRemotePort(),
                    request.getHeader("Authorization"), request.getRequestURI(), request.getMethod(), so.getCode());
             logDataService.saveLogDataFromMicroService(logEvent);

            return new ResponseEntity<>(so.getPayload().toString(), new HttpHeaders(), so.getHttp());
        } catch (Exception argEx) {
            logger.error("Error SendWsService in method sendResult :: " + argEx);
            return new ResponseEntity<>(so.getPayload().toString(), new HttpHeaders(), so.getHttp());
        }
    }

    @Override
    public ResponseEntity<?> sendResult(ServerWebExchange exchange, SendObject so) {
        try {
            ServerHttpRequest request = exchange.getRequest();
            commonService.saveLogAccess(request, so.getCode());
            return new ResponseEntity<>(so.getPayload().toString(), new HttpHeaders(), so.getHttp());
        } catch (Exception argEx) {
            logger.error("Error SendWsService in method sendResult :: " + argEx);
            return new ResponseEntity<>(so.getPayload().toString(), new HttpHeaders(), so.getHttp());
        }
    }

    @Override
    public ResponseEntity<?> sendResultPublic(SendObject so) {
        return new ResponseEntity<>(so.getPayload().toString(), new HttpHeaders(), so.getHttp());
    }

    @Override
    public String ipAddressFormWeb(HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0];
            }

            ip = request.getHeader("Proxy-Client-IP");
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }

            ip = request.getHeader("WL-Proxy-Client-IP");
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }

            return request.getRemoteAddr();
        } catch (Exception e) {
            logger.error("Error ServerWebService in method ipAddressFormWeb :: {}", String.valueOf(e));
            return null;
        }
    }

    @Override
    public Long getIdCurrentUser(HttpServletRequest request) {
        try {
            SendObject so = admUserService.getCurrentUserIdByToken(request.getHeader("Authorization"));
            if (!so.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return null;
            return Long.valueOf(so.getPayload().toString());
        } catch (Exception argEx) {
            logger.error("Error SendWsService in method getIdCurrentUser :: " + argEx);
            return null;
        }
    }

    @Override
    public VAdmUser getCurrentUser(HttpServletRequest request) {
        VAdmUser user;
        try {
            SendObject so = admUserService.getCurrentUserByTokenWs(request.getHeader("Authorization"));
            if (!so.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return null;
            ObjectMapper mapper = new ObjectMapper();
            user = mapper.convertValue(so.getPayload(), VAdmUser.class);
            return user;
        } catch (Exception argEx) {
            logger.error("Error SendWsService in method getCurrentUser :: " + argEx);
            return null;
        }
    }

    @Override
    public ResponseEntity<?> downloadFile(HttpServletRequest request, SendObject so) {
        File file = null;
        try {
            if (!so.getCode().equals(ConstanteService._CODE_SERVICE_SUCCESS))
                return ResponseEntity.status(500).body(so.getPayload());

            file = (File) so.getPayload();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (FileNotFoundException e) {
            logger.error("Error SendWsService in method getCurrentUserId :: " + e);
            return null;
        } finally {
            if (file != null) {
                file.delete();
            }
        }
    }

    @Override
    public ResponseEntity<?> sendResultException(ServerWebExchange exchange) {
        try {
            ServerHttpRequest request = exchange.getRequest();
            commonService.saveLogAccess(request, ConstanteWs._CODE_WS_ERROR);
            return new ResponseEntity<>(new JSONObject(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception argEx) {
            logger.error("Error SendWsService in method sendResultException :: {}", String.valueOf(argEx));
            return new ResponseEntity<>(new JSONObject(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> sendResultException(HttpServletRequest exchange) {
        return null;
    }

}
