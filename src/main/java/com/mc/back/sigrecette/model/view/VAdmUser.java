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
@Table(name = "v_adm_user")
public class VAdmUser {
    @Id
    @Column(name = "id")
    private Long id;

    @Size(max = 60)
    @Column(name = "mail", length = 60)
    private String mail;

    @Size(max = 300)
    @Column(name = "user_name", length = 300)
    private String username;

    @Column(name = "date_expire")
    private Date dateExpire;

    @Size(max = 100)
    @Column(name = "pwd", length = 100)
    private String pwd;

    @Column(name = "is_active", precision = 1)
    private Integer isActive;

    @Size(max = 50)
    @Column(name = "code", length = 50)
    private String code;

    @Size(max = 40)
    @Column(name = "code_profes", length = 40)
    private String codeProfes;

    @Size(max = 300)
    @Column(name = "des_profes_fr", length = 300)
    private String desProfesFr;

    @Size(max = 300)
    @Column(name = "des_profes_en", length = 300)
    private String desProfesEn;

    @Column(name = "state_fr", length = Integer.MAX_VALUE)
    private String stateFr;

    @Column(name = "state_en", length = Integer.MAX_VALUE)
    private String stateEn;

    @Column(name = "des_profil_fr", length = Integer.MAX_VALUE)
    private String desProfilFr;

    @Column(name = "des_profil_en", length = Integer.MAX_VALUE)
    private String desProfilEn;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "code_profil", length = Integer.MAX_VALUE)
    private String codeProfil;

}