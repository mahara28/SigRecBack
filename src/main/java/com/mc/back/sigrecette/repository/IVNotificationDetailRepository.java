package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.view.VNotificationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface IVNotificationDetailRepository extends JpaRepository<VNotificationDetail, Long> {

    // Toutes les notifications reçues par un utilisateur

    List<VNotificationDetail> findByIdUserRec(@Param("idUserRec") Long idUserRec);

    // Toutes les notifications envoyées par un utilisateur

    List<VNotificationDetail> findByIdUserEm(@Param("idUserEm") Long idUserEm);

    // Notifications non lues d'un utilisateur

    List<VNotificationDetail> findUnreadByIdUserRec(@Param("idUserRec") Long idUserRec);

    // Compter les notifications non lues

    Long countUnreadByIdUserRec(@Param("idUserRec") Long idUserRec);

    // Notifications par type
    List<VNotificationDetail> findByIdUserRecAndIdTypeNotif(@Param("idUserRec") Long idUserRec, @Param("idTypeNotif") Long idTypeNotif);

    // Notifications d'un thread (par message parent)
    List<VNotificationDetail> findByIdmessageParent(@Param("idmessageParent") Long idmessageParent);

    // Notifications par priorité pour un utilisateur
    List<VNotificationDetail> findByIdUserRecAndPriorite(@Param("idUserRec") Long idUserRec, @Param("priorite") Integer priorite);
}

