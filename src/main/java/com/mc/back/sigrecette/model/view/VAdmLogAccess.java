package com.mc.back.sigrecette.model.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.sql.Timestamp;

@Data
@Entity
@Immutable
@Table(name = "v_adm_log_access", schema = "public")
public class VAdmLogAccess {
    @Id
    @Column(name = "id", precision = 22)
    private Long id;

    @Column(name = "id_user")
    private Long idUser;

    @Size(max = 60)
    @Column(name = "mail", length = 60)
    private String mail;

    @Size(max = 300)
    @Column(name = "user_name", length = 300)
    private String userName;

    @Column(name = "date_auth")
    private Timestamp dateAuth;

    @Size(max = 255)
    @Column(name = "ip_address")
    private String ipAddress;

    @Size(max = 300)
    @Column(name = "des_fr", length = 300)
    private String desFr;

    @Size(max = 300)
    @Column(name = "des_en", length = 300)
    private String desEn;

    @Size(max = 50)
    @Column(name = "code_access", length = 50)
    private String codeAccess;

    @Size(max = 100)
    @Column(name = "code_acces_fr", length = 100)
    private String codeAccesFr;

    @Size(max = 100)
    @Column(name = "code_acces_en", length = 100)
    private String codeAccesEn;

}