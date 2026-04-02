package com.mc.back.sigrecette.service;
import com.mc.back.sigrecette.model.AdmVerificationCode;
import com.mc.back.sigrecette.model.tool.AuthRequest;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface IAdmVerificationCodeService {
	
	AdmVerificationCode findById(Long id);
	
	AdmVerificationCode saveOrUpdate(AdmVerificationCode entity);
	
	SendObject findByIdWs(Long id);
	
	SendObject GenerationCodeVerif(AuthRequest email,String ipAdress);
	
	SendObject verifyCode(AdmVerificationCode code,String ipAddress);
	

}
