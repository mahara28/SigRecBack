package com.mc.back.sigrecette.service;


import com.mc.back.sigrecette.model.Notification;
import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface INotificationService {
    List<Notification> getList();

    Notification findById(Long id);

    Notification saveOrUpdate(Notification entity);

    Boolean deleteById(Long id);

    SendObject findNotificationByIdWs(Long id);

    SendObject getListNotificationWs();

    SendObject saveOrUpdateNotificationWs(Notification entity);

    SendObject deleteNotificationByIdWs(Long id);

    SendObject setAsReadWs(Long id);

    SendObject sendNotificationWs(Notification entity);

    SendObject pushNotificationReady(String qid);

 
}

