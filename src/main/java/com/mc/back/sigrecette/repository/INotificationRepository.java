package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.Notification;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
	@Modifying
    @Transactional
    @Query(value = """
        SELECT public.fn_save_notification_users(
            :idNotif,
            CAST(:listIdUserRec AS BIGINT[]),
            CAST(:listIdProfil AS BIGINT[])
        )
        """, nativeQuery = true)
    void saveNotificationUsers(
        @Param("idNotif") Long idNotif,
        @Param("listIdUserRec") Long[] listIdUserRec,
        @Param("listIdProfil") Long[] listIdProfil
    );
}
