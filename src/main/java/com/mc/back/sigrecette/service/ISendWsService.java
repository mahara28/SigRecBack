package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.view.VAdmUser;
import com.mc.back.sigrecette.tools.model.SendObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;

public interface ISendWsService {

    ResponseEntity<?> sendResultPublic(SendObject so);

    String ipAddressFormWeb(HttpServletRequest exchange);

    Long getIdCurrentUser(HttpServletRequest request);

    VAdmUser getCurrentUser(HttpServletRequest request);

    ResponseEntity<?> downloadFile(HttpServletRequest request, SendObject so);

    ResponseEntity<?> sendResult(HttpServletRequest request, SendObject listPaginator);

    ResponseEntity<?> sendResult(ServerWebExchange exchange, SendObject listPaginator);

    ResponseEntity<?> sendResultException(ServerWebExchange exchange);

    ResponseEntity<?> sendResultException(HttpServletRequest exchange);
}
