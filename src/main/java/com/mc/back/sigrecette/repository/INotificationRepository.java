package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
   
}
