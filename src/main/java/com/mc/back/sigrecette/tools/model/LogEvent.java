package com.mc.back.sigrecette.tools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogEvent {

	private String ipAddress;
	private String token;
	private String uri;
	private String httpEvent;
	private String httpCodeUser;
	
}
