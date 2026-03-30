package com.mc.back.sigrecette.service;

import java.util.List;

import com.mc.back.sigrecette.model.NotificationUser;
import com.mc.back.sigrecette.tools.model.SendObject;

public interface INotificationUserService {

	List<NotificationUser> getList();

	NotificationUser findById(Long id);
	
	NotificationUser saveOrUpdate(NotificationUser entity);

    Boolean deleteById(Long id);

    SendObject findNotificationUserByIdWs(Long id);

    SendObject getListNotificatioUsernWs();

    SendObject saveOrUpdateNotificationUserWs(NotificationUser entity);

    SendObject deleteNotificationUserByIdWs(Long id);

    SendObject setAsReadWs(Long id);

    
}
