package com.mc.back.sigrecette.service;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface INotificationEmailService {
    // Récupérer tous les emails
    SendObject getAllNotificationEmails();

    // Récupérer par idNotif
    SendObject getNotificationEmailsByIdNotif(Long idNotif);

    // Récupérer par id
    SendObject getNotificationEmailById(Long id);

    // Ajouter un email
    SendObject addNotificationEmail(String email, Long idNotif);


    // Supprimer par id
    SendObject deleteNotificationEmailById(Long id);

    // Supprimer tous les emails d'une notification
    SendObject deleteNotificationEmailsByIdNotif(Long idNotif);

    // Vérifier si un email existe
    SendObject existsByEmail(String email);
}
