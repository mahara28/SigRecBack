package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "adm_user", schema = "public")
public class AdmUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_user_id_gen")
    @SequenceGenerator(name = "adm_user_id_gen", sequenceName = "seq_adm_user", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "id_profes")
    private Long idProfes;

    @Size(max = 100)
    @NotNull
    @Column(name = "pwd", nullable = false, length = 100)
    private String pwd;

    @Size(max = 60)
    @NotNull
    @Column(name = "mail", nullable = false, length = 60)
    private String email;

    @Size(max = 40)
    @Column(name = "cin", length = 40)
    private String cin;

    @Size(max = 300)
    @NotNull
    @Column(name = "user_name", nullable = false, length = 300)
    private String username;

    @Column(name = "date_birth")
    private Date dateBirth;

    @ColumnDefault("0")
    @Column(name = "id_sex")
    private Long idSex;

    @Column(name = "date_last_con")
    private Date dateLastCon;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_active", nullable = false, precision = 1)
    private Integer isActive;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "f_expire", nullable = false, precision = 1)
    private Integer fExpire;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "f_susp", nullable = false, precision = 1)
    private Integer fSusp;

    @Column(name = "date_expire")
    private Date dateExpire;

    @Column(name = "date_last_update")
    private Date dateLastUpdate;

    @Column(name = "date_create")
    private Date dateCreate;

    @Column(name = "date_susp_start")
    private Date dateSuspStart;

    @Column(name = "date_susp_end")
    private Date dateSuspEnd;

    @Column(name = "date_update")
    private Date dateUpdate;

    @Size(max = 50)
    @Column(name = "code")
    private String code;

    @Transient
    private List<Long> profils;

}
