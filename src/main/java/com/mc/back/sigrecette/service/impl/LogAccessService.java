package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.model.LogAccess;
import com.mc.back.sigrecette.repository.ILogAccessRepository;
import com.mc.back.sigrecette.service.ILogAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogAccessService implements ILogAccessService {
    private static final Logger logger = LoggerFactory.getLogger(LogAccessService.class);

    @Autowired
    private ILogAccessRepository logAccessRepository;

    @Override
    public void saveLogAccess(String codeAccess, Long idAdmUser, String login, String ipAddress) {
        try {
            LogAccess logAccess = new LogAccess();
            logAccess.setCodeAccess(codeAccess);
            logAccess.setDateAuth(new Date().toInstant());
            logAccess.setLogin(login);
            logAccess.setIdUser(idAdmUser);
            logAccess.setIpAddress(ipAddress);
            logAccessRepository.save(logAccess);
        } catch (Exception e) {
            logger.error("Error LogAccessService in method saveLogAccess :: " + e);
        }
    }
}
