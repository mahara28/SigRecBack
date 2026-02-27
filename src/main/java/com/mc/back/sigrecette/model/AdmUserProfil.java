package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adm_user_profil")
public class AdmUserProfil {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_user_profil_id_gen")
    @SequenceGenerator(name = "adm_user_profil_id_gen", sequenceName = "adm_user_profil_id_user_profil_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @NotNull
    @Column(name = "id_profil", nullable = false)
    private Long idProfil;

    public AdmUserProfil(Long idUser, Long profileId) {
        this.idUser = idUser;
        this.idProfil = profileId;
    }
}