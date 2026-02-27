package com.mc.back.sigrecette.service.impl;



import com.mc.back.sigrecette.model.LogData;
import com.mc.back.sigrecette.repository.ILogDataRepository;
import com.mc.back.sigrecette.service.IAdmUserService;
import com.mc.back.sigrecette.service.ILogDataService;
import com.mc.back.sigrecette.tools.ConstanteWs;
import com.mc.back.sigrecette.tools.UtilsWs;
import com.mc.back.sigrecette.tools.model.LogEvent;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogDataService implements ILogDataService {

    private static final Logger logger = LogManager.getLogger(LogDataService.class);

    @Autowired
    private IAdmUserService admUserService;

    @Autowired
    private ILogDataRepository logDataRepository;

    @Autowired
    private UtilsWs utilsWs;

    @Override
    public SendObject saveLogDataFromMicroService(LogEvent logEvent) {
        try {
            if (logEvent.getToken() != null) {
                LogData logData = new LogData(admUserService.getUserIdFromTokenString(logEvent.getToken()),
                        Instant.now(), logEvent.getUri(),
                        logEvent.getHttpEvent(), logEvent.getIpAddress(), logEvent.getHttpCodeUser());

                logDataRepository.save(logData);
                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, new JSONObject());
            } else
                return utilsWs.resultWs(ConstanteWs._CODE_WS_SUCCESS, "No token");
        } catch (Exception e) {
            logger.error("Error LogDataService in method saveLogDataFromMicroService :: {}", e.toString());
            return utilsWs.resultWs(ConstanteWs._CODE_WS_ERROR, new JSONObject());
        }
    }

}
