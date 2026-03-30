package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface IVNotificationDetailService {


    // Récupérer toutes les notifications reçues par un utilisateur
    SendObject getNotificationsByIdUserRecWs(Long idUserRec);

    // Récupérer toutes les notifications envoyées par un utilisateur
    SendObject getNotificationsByIdUserEmWs(Long idUserEm);

    // Récupérer les notifications non lues d'un utilisateur
    SendObject getUnreadNotificationsByIdUserRecWs(Long idUserRec);

    // Compter les notifications non lues d'un utilisateur
    SendObject countUnreadNotificationsByIdUserRecWs(Long idUserRec);

    // Récupérer les notifications par type pour un utilisateur
    SendObject getNotificationsByIdUserRecAndIdTypeNotifWs(Long idUserRec, Long idTypeNotif);

    // Récupérer les notifications d'un thread (par message parent)
    SendObject getNotificationsByIdmessageParentWs(Long idmessageParent);

    // Récupérer les notifications par priorité pour un utilisateur
    SendObject getNotificationsByIdUserRecAndPrioriteWs(Long idUserRec, Integer priorite);

    // Récupérer une notification par son id (usage interne)
    VNotificationDetail getNotificationById(Long id);
}

