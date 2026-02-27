package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification", schema = "public")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notification")
    @SequenceGenerator(name = "seq_notification", sequenceName = "seq_notification", allocationSize = 1)
    @Column(name = "id", nullable = false, precision = 22)
    private Long id;

    @Column(name = "id_nm_type_notif", precision = 22)
    private Long idNmTypeNotif;

    @Column(name = "notif_en", length = Integer.MAX_VALUE)
    private String notifEn;

    @Column(name = "notif_fr", length = Integer.MAX_VALUE)
    private String notifFr;

    @Column(name = "date_create")
    private Instant dateCreate;

    @Column(name = "date_visual")
    private Instant dateVisual;

    @Column(name = "id_user", precision = 22)
    private Long idUser;


}
