package com.mc.back.sigrecette.service.impl;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mc.back.sigrecette.service.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService{

	private final JavaMailSender mailSender;
	
	@Override
	public void sendHtmlEmail(List<String> recipients, String subject, String htmlContent) {
		if (recipients == null || recipients.isEmpty()) {
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(recipients.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email", e);
        }
	}
	
	@Override
    public String buildNotificationTemplate(String title, String message, String senderName) {
        return """
            <!DOCTYPE html>
            <html lang="fr">
            <head>
                <meta charset="UTF-8">
                <title>%s</title>
            </head>
            <body style="margin:0; padding:0; font-family:Arial, sans-serif; background-color:#f5f6fa;">
                <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f5f6fa; padding:30px 0;">
                    <tr>
                        <td align="center">
                            <table width="700" cellpadding="0" cellspacing="0" style="background:#ffffff; border-radius:10px; overflow:hidden; box-shadow:0 2px 10px rgba(0,0,0,0.08);">
                                
                                <!-- Header -->
                                <tr>
                                    <td style="background:#0d6efd; padding:25px; text-align:center; color:white;">
                                        <h1 style="margin:0; font-size:24px;">Système de Notification</h1>
                                        <p style="margin:8px 0 0; font-size:14px;">Notification automatique</p>
                                    </td>
                                </tr>

                                <!-- Body -->
                                <tr>
                                    <td style="padding:35px;">
                                        <h2 style="color:#333; margin-top:0;">%s</h2>
                                        <p style="font-size:15px; color:#555; line-height:1.8;">
                                            %s
                                        </p>

                                        <div style="margin-top:30px; padding:15px; background:#f8f9fa; border-left:4px solid #0d6efd; color:#444;">
                                            <strong>Expéditeur :</strong> %s
                                        </div>
                                    </td>
                                </tr>

                                <!-- Footer -->
                                <tr>
                                    <td style="background:#f1f3f5; padding:20px; text-align:center; font-size:13px; color:#777;">
                                        Cet email a été envoyé automatiquement. Merci de ne pas y répondre directement.
                                    </td>
                                </tr>

                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """.formatted(title, title, message, senderName);
    }

}
