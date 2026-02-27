package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "adm_profil")
public class AdmProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_profil_id_gen")
    @SequenceGenerator(name = "adm_profil_id_gen", sequenceName = "adm_profil_id_profil_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @NotNull
    @Column(name = "code", nullable = false, length = 40)
    private String code;

    @Size(max = 300)
    @NotNull
    @Column(name = "des_fr", nullable = false, length = 300)
    private String desFr;

    @Size(max = 300)
    @Column(name = "des_en", length = 300)
    private String desEn;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_active", nullable = false, precision = 1)
    private BigDecimal isActive;

    @ColumnDefault("now()")
    @Column(name = "date_create")
    private Date dateCreate;

    @ColumnDefault("now()")
    @Column(name = "date_update")
    private Date dateUpdate;

    @Transient
    private List<Long> listAdmFoncIds;

    public void setDateCreate(java.util.Date date) {
        this.dateCreate = (date != null) ? new Date(date.getTime()) : null;
    }

    public void setDateUpdate(java.util.Date date) {
        this.dateUpdate = (date != null) ? new Date(date.getTime()) : null;
    }
}