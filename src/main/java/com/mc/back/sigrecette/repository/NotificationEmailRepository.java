package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.NotificationEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationEmailRepository extends JpaRepository<NotificationEmail, Long> {
    // Trouver par email
    Optional<NotificationEmail> findByEmail(String email);

    // Trouver tous les emails d'une notification
    List<NotificationEmail> findByIdNotif(Long idNotif);

    // Vérifier si un email existe
    boolean existsByEmail(String email);

    // Supprimer par idNotif
    void deleteByIdNotif(Long idNotif);
}

