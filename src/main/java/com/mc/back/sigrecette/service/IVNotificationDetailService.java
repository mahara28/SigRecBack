package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface IVNotificationDetailService {

    SendObject getNotificationsByIdUserRecWs(Long idUserRec);

    SendObject getNotificationsByIdUserEmWs(Long idUserEm);

    SendObject getUnreadNotificationsByIdUserRecWs(Long idUserRec);

    SendObject countUnreadNotificationsByIdUserRecWs(Long idUserRec);

    SendObject getNotificationsByIdUserRecAndIdTypeNotifWs(Long idUserRec, Long idTypeNotif);

    SendObject getNotificationsByIdUserRecAndPriorityWs(Long idUserRec, Integer priority);  // Renommé

    SendObject getNotificationsByIdMessageParentWs(Long idMessageParent);

    VNotificationDetail getNotificationById(Long id);
}