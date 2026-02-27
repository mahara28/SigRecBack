package com.mc.back.sigrecette.tools.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SendObject {

	private String code;
	private Object payload;
	private HttpStatus http;
	private Long countPage;
	private Boolean exportFile = false;
	
	public SendObject(String code, Object payload, HttpStatus http, Long countPage) {
		super();
		this.code = code;
		this.payload = payload;
		this.http = http;
		this.countPage = countPage;
	}

	public SendObject(String code, Object payload, Long countPage) {
		super();
		this.code = code;
		this.payload = payload;
		this.countPage = countPage;
	}
	
	public SendObject(String code, Object payload) {
		super();
		this.code = code;
		this.payload = payload;
	}
	
	public SendObject(String code) {
		super();
		this.code = code;
	}

	public SendObject() {
		super();
	}

}
