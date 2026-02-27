package com.mc.back.sigrecette.service;


import com.mc.back.sigrecette.tools.model.LogEvent;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface ILogDataService {
    SendObject saveLogDataFromMicroService(LogEvent logEvent);
}
