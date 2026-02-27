package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "adm_log_access")
public class LogAccess {
    @Id
    @SequenceGenerator(allocationSize = 1, sequenceName = "seq_log_access", name = "seq_log_access")
    @GeneratedValue(generator = "seq_log_access", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, precision = 22)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "code_access", nullable = false, length = 50)
    private String codeAccess;

    @Column(name = "date_auth")
    private Instant dateAuth;

    @Column(name = "id_user")
    private Long idUser;

    @Size(max = 255)
    @Column(name = "ip_address")
    private String ipAddress;

    @Size(max = 255)
    @NotNull
    @Column(name = "login", nullable = false)
    private String login;

}