package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVNotificationDetailRepository extends JpaRepository<VNotificationDetail, Long> {

}