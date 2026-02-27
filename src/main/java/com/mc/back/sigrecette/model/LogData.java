package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "adm_log_data")
public class LogData {
    @Id
    @SequenceGenerator(allocationSize = 1, initialValue = 1, sequenceName = "seq_log_data", name = "seq_log_data")
    @GeneratedValue(generator = "seq_log_data", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "date_log", nullable = false)
    private Instant dateLog;

    @Size(max = 50)
    @NotNull
    @Column(name = "http_method", nullable = false, length = 50)
    private String httpMethod;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Size(max = 255)
    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Size(max = 255)
    @NotNull
    @Column(name = "result_ws", nullable = false)
    private String resultWs;

    @Size(max = 255)
    @NotNull
    @Column(name = "uri", nullable = false)
    private String uri;

    public LogData(Long idUser, Instant datLog, String uri, String httpMethod, String ipAddress, String resultWs) {
        super();
        this.idUser = idUser;
        this.dateLog = datLog;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.ipAddress = ipAddress;
        this.resultWs = resultWs;
    }

}