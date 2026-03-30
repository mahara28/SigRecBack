package com.mc.back.sigrecette.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.Date;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "v_notification_detail")

public class VNotificationDetail {


    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_notif")
    private Long idNotif;

    @Column(name = "qid")
    private String qid;

    @Column(name = "id_type_notif")
    private Long idTypeNotif;

    @Column(name = "id_user_em")
    private Long idUserEm;

    @Size(max = 300)
    @Column(name = "username_em", length = 300)
    private String usernameEm;

    @Column(name = "id_user_rec")
    private Long idUserRec;

    @Size(max = 300)
    @Column(name = "username_rec", length = 300)
    private String usernameRec;

    @Column(name = "id_email_rec")
    private Long idEmailRec;

    @Size(max = 300)
    @Column(name = "email", length = 300)
    private String email;

    @Size(max = 250)
    @Column(name = "titre", length = 250)
    private String titre;

    @Size(max = 500)
    @Column(name = "sujet", length = 500)
    private String sujet;

    @Column(name = "date_envoi")
    private Date dateEnvoi;

    @Column(name = "date_reception")
    private Date dateReception;

    @Column(name = "lu")
    private Integer lu;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "id_message_parent")
    private Long idMessageParent;

    @Column(name = "sujet_message_parent", length = Integer.MAX_VALUE)
    private String sujetMessageParent;

    @Column(name = "titre_message_parent", length = Integer.MAX_VALUE)
    private String titreMessageParent;
}

