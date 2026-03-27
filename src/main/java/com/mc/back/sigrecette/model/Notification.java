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

    @Column(name = "id_type_notif", precision = 22)
    private Long idTypeNotif;

    @Column(name = "sujet", length = Integer.MAX_VALUE)
    private String sujet;

    @Column(name = "titre", length = Integer.MAX_VALUE)
    private String titre;

    @Column(name = "date_envoi")
    private Instant dateEnvoi;

    @Column(name = "id_user_em", precision = 22)
    private Long idUserEm;
    @Column(name = "priority")
    private Integer priority;


}
