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
@Table(name = "v_adm_log_data", schema = "public")
public class VAdmLogData {
    @Id
    @Column(name = "id", precision = 22)
    private Long id;

    @Size(max = 60)
    @Column(name = "mail", length = 60)
    private String mail;

    @Size(max = 300)
    @Column(name = "user_name", length = 300)
    private String userName;

    @Column(name = "date_log")
    private Timestamp dateLog;

    @Size(max = 255)
    @Column(name = "ip_address")
    private String ipAddress;

    @Size(max = 255)
    @Column(name = "uri")
    private String uri;

    @Size(max = 50)
    @Column(name = "http_method", length = 50)
    private String httpMethod;

    @Column(name = "result_ws_fr", length = Integer.MAX_VALUE)
    private String resultWsFr;

    @Column(name = "result_ws_en", length = Integer.MAX_VALUE)
    private String resultWsEn;

    @Column(name = "id_user")
    private Long idUser;

}