package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {
	
    @Query(value = """
        SELECT public.fn_save_notification_users(
            :idNotif,
            CAST(:listIdUserRec AS BIGINT[]),
            CAST(:listIdProfil AS BIGINT[])
        )
        """, nativeQuery = true)
	Integer saveNotificationUsers(
        @Param("idNotif") Long idNotif,
        @Param("listIdUserRec") Long[] listIdUserRec,
        @Param("listIdProfil") Long[] listIdProfil
    );
    
    @Query(value = """
    	    SELECT public.fn_save_notification_email(
    	        :idNotif,
    	        CAST(:listEmail AS VARCHAR[])
    	    )
    	    """, nativeQuery = true)
    	Integer saveNotificationEmails(
    	    @Param("idNotif") Long idNotif,
    	    @Param("listEmail") String[] listEmail
    	);
    
    @Query(value = """
    	    SELECT public.fn_count_notification_non_lu_by_type_and_user(
    	        :idTypeNotif,
    	        :idUserRec
    	    )
    	    """, nativeQuery = true)
    	Long countNotificationNonLuesByUserAndType(
    		@Param("idTypeNotif") Long  idTypeNotif,
    	    @Param("idUserRec") Long idUserRec
    	);
    
    @Query(value = """
    	    SELECT public.count_unread_notifications(
    	        :idUserRec
    	    )
    	    """, nativeQuery = true)
    	Long countNotificationNonLes(
    	    @Param("idUserRec") Long idUserRec
    	);
}
