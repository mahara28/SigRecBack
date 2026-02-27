package com.mc.back.sigrecette.service;

import org.springframework.web.server.ServerWebExchange;

public interface IServerWebService {

	String ipAddressFormWeb(ServerWebExchange exchange);
	
}
