package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "adm_profession")
public class AdmProfession {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_profession_id_gen")
    @SequenceGenerator(name = "adm_profession_id_gen", sequenceName = "adm_profession_id_profes_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "code", nullable = false, length = 40)
    private String code;

    @Size(max = 300)
    @Column(name = "des_fr", nullable = false, length = 300)
    private String desFr;

    @Size(max = 300)
    @Column(name = "des_en", length = 300)
    private String desEn;

    @Column(name = "is_active")
    private Integer isActive;

}