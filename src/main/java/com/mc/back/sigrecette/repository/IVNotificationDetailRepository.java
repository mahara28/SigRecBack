package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface IVNotificationDetailRepository extends JpaRepository<VNotificationDetail, Long> {

    // Appel à la fonction get_notifications_by_user_rec
    @Query(value = "SELECT * FROM get_notifications_by_user_rec(:idUserRec)",
            nativeQuery = true)
    List<VNotificationDetail> findByIdUserRec(@Param("idUserRec") Long idUserRec);

    // Appel à la fonction get_notifications_by_user_em
    @Query(value = "SELECT * FROM get_notifications_by_user_em(:idUserEm)",
            nativeQuery = true)
    List<VNotificationDetail> findByIdUserEm(@Param("idUserEm") Long idUserEm);

    // Appel à la fonction get_unread_notifications
    @Query(value = "SELECT * FROM get_unread_notifications(:idUserRec)",
            nativeQuery = true)
    List<VNotificationDetail> findUnreadByIdUserRec(@Param("idUserRec") Long idUserRec);

    // Appel à la fonction count_unread_notifications
    @Query(value = "SELECT count_unread_notifications(:idUserRec)",
            nativeQuery = true)
    Long countUnreadByIdUserRec(@Param("idUserRec") Long idUserRec);

    // Appel à la fonction get_notifications_by_type
    @Query(value = "SELECT * FROM get_notifications_by_type(:idUserRec, :idTypeNotif)",
            nativeQuery = true)
    List<VNotificationDetail> findByIdUserRecAndIdTypeNotif(
            @Param("idUserRec") Long idUserRec,
            @Param("idTypeNotif") Long idTypeNotif);

    // Appel à la fonction get_notifications_by_priority
    @Query(value = "SELECT * FROM get_notifications_by_priority(:idUserRec, :priorite)",
            nativeQuery = true)
    List<VNotificationDetail> findByIdUserRecAndPriorite(
            @Param("idUserRec") Long idUserRec,
            @Param("priorite") Integer priorite);

    // Appel à la fonction get_notifications_by_thread
    @Query(value = "SELECT * FROM get_notifications_by_thread(:idMessageParent)",
            nativeQuery = true)
    List<VNotificationDetail> findByIdMessageParent(@Param("idMessageParent") Long idMessageParent);

    // Appel à la fonction get_notification_by_id
    @Query(value = "SELECT * FROM get_notification_by_id(:id)",
            nativeQuery = true)
    VNotificationDetail findNotificationById(@Param("id") Long id);

    // Appel à la fonction de recherche avancée
    @Query(value = "SELECT * FROM search_notifications(:idUserRec, :idTypeNotif, :priorite, :lu, :dateDebut, :dateFin, :searchText, :limit, :offset)",
            nativeQuery = true)
    List<Object[]> searchNotifications(
            @Param("idUserRec") Long idUserRec,
            @Param("idTypeNotif") Long idTypeNotif,
            @Param("priorite") Integer priorite,
            @Param("lu") Integer lu,
            @Param("dateDebut") java.util.Date dateDebut,
            @Param("dateFin") java.util.Date dateFin,
            @Param("searchText") String searchText,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset);
}



