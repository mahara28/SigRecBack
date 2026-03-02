package com.mc.back.sigrecette.model;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "adm_fonc_profil")
public class AdmFoncProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_fonc_profil_id_gen")
    @SequenceGenerator(name = "adm_fonc_profil_id_gen", sequenceName = "adm_fonc_profil_id_fonc_profil_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "id_fonc", nullable = false)
    private Long idFonc;

    @NotNull
    private Long idProfil;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_list", nullable = false)
    private Long isList;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_add", nullable = false)
    private Long isAdd;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_update", nullable = false)
    private Long isUpdate;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_supp", nullable = false)
    private Long isSupp;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_details", nullable = false)
    private Long isDetails;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_export", nullable = false)
    private Long isExport;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_imprime", nullable = false)
    private Long isImprime;

}