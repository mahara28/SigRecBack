package com.mc.back.sigrecette.service;

import java.util.List;
import java.util.Map;

public interface IEmailService {
	void sendHtmlEmail(List<String> recipients, String subject, String htmlContent);
	
	String buildEmailTemplate(String templateName, Map<String, String> placeholders);
	
	String buildNotificationTemplate(String title, String message, String senderName);
	
	String buildVerificationCodeTemplate(String title, String code, Long expirationMinutes);
	
	String buildNotificationTemplate2(String title, String message, String senderName);
}
