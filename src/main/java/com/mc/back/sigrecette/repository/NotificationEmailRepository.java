package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.NotificationEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationEmailRepository extends JpaRepository<NotificationEmail, Long> {
    Optional<NotificationEmail> findByEmail(String email);

    List<NotificationEmail> findByIdNotif(Long idNotif);

    boolean existsByEmail(String email);

    boolean existsByIdNotifAndEmail(Long idNotif, String email);

    @Modifying
    @Transactional
    void deleteByIdNotif(Long idNotif);

    @Modifying
    @Transactional
    @Query("DELETE FROM NotificationEmail ne WHERE ne.idNotif = :idNotif AND LOWER(ne.email) = LOWER(:email)")
    void deleteByIdNotifAndEmail(@Param("idNotif") Long idNotif, @Param("email") String email);

    long countByIdNotif(Long idNotif);
}

