package com.mc.back.sigrecette.controller.priv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {

    private static final Logger logger = LogManager.getLogger(WebsocketController.class);

    @Value("${server.port}")
    private String port;

    @MessageMapping("/send")
    @SendTo("/topic/receive")
    public String incoming(String message) {
        return String.format("Application on port %s responded to your message: \"%s\"", port, message);
    }



}
