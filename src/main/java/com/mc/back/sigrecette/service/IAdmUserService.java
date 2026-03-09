package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.AdmUser;
import com.mc.back.sigrecette.model.tool.AuthRequest;
import com.mc.back.sigrecette.tools.model.SendObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

public interface IAdmUserService {
    List<AdmUser> getList();

    AdmUser findById(Long id);

    AdmUser saveOrUpdate(AdmUser entity);

    Boolean deleteById(Long id);

    SendObject findAdmUserByIdWs(Long id);

    SendObject getListAdmUserWs();

    SendObject saveOrUpdateAdmUserWs(AdmUser entity);

    SendObject deleteAdmUserByIdWs(Long id);

    SendObject authenticateUserWs(AuthRequest authRequest, String ipAddress,HttpServletRequest exchange);

    SendObject authenticateUserNo2FA(AuthRequest authRequest, String ipAddress);

    SendObject whoAmI(Long idUser);

    Long getUserIdFromToken(HttpServletRequest exchange);

    Long getUserIdFromToken(ServerWebExchange exchange);

    Long getUserIdFromTokenString(String token);

    SendObject getCurrentUserIdByToken(String token);

    SendObject getCurrentUserByTokenWs(String token);

    AdmUser findUserById(Long id);
}
