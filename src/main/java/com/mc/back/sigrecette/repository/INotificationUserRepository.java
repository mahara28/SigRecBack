package com.mc.back.sigrecette.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mc.back.sigrecette.model.NotificationUser;

public interface INotificationUserRepository extends JpaRepository<NotificationUser, Long>{

}
