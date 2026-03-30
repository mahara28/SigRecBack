package com.mc.back.sigrecette.service;

import java.util.List;

public interface IEmailService {
	void sendHtmlEmail(List<String> recipients, String subject, String htmlContent);
	
	String buildNotificationTemplate(String title, String message, String senderName);
}
