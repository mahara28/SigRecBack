package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.model.Notification;
import com.mc.back.sigrecette.model.view.VNotificationDetail;
import com.mc.back.sigrecette.tools.model.SendObject;

import java.util.List;

public interface IVNotificationDetailService {

    List<VNotificationDetail> getList();


    SendObject getListNotificationDetailWs();

}