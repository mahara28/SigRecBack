package com.mc.back.sigrecette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mc.back.sigrecette.model.NotificationUser;

@Repository
public interface INotificationUserRepository extends JpaRepository<NotificationUser, Long>{
	@Query(value = """
		    SELECT public.fn_set_notification_user_as_read(
		        CAST(:listIds AS BIGINT[])
		    )
		    """, nativeQuery = true)
	Integer setNotificationUsersAsRead(@Param("listIds") Long[] listIds);
}
