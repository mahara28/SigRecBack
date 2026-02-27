package com.mc.back.sigrecette.tools.model;

import lombok.Data;

import java.util.Date;

@Data
public class TokenApp {

	private String token;
	private String ipAdresse;
	private Date dateEndToken;
	
	public TokenApp() {
		
	}

	public TokenApp(String token, String ipAdresse, Date dateEndToken) {
		super();
		this.token = token;
		this.ipAdresse = ipAdresse;
		this.dateEndToken = dateEndToken;
	}

}
