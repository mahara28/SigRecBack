package com.mc.back.sigrecette.service;

public interface ILogAccessService {
    void saveLogAccess(String codeAccess, Long idAdmUser, String login, String ipAddress);
}
