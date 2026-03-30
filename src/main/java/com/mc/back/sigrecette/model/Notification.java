package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

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
    
    @Size(max = 300)
    @NotNull
    @Column(name = "sujet", nullable = false, length = 300)
    private String sujet;
    
    @Size(max = 100)
    @Column(name = "titre", length = 100)
    private String titre;

    @Column(name = "date_envoi")
    private Instant dateEnvoi;

    @Column(name = "id_user_em", precision = 22)
    private Long idUserEm;
    
    @Column(name = "priority")
    private Integer priority;
    
    @Column(name = "id_message_parent", precision = 22)
    private Long idMessageParent;
    
    @Transient
    private List<Long> listIdUserRec;
    
    @Transient
    private List<Long> listIdProfil;


}
