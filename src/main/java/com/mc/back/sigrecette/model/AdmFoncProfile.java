package com.mc.back.sigrecette.model;

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

}