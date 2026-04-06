package com.mc.back.sigrecette.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parametrage_nomenclature", schema = "public")
public class ParametrageNomenclatures {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "param_nomen_id_gen")
    @SequenceGenerator(name = "param_nomen_id_gen", sequenceName = "param_nomen_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false )
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nom_table", nullable = false, length = 50, unique = true)
    private String nomTable;

    @Size(max = 50)
    @NotNull
    @Column(name = "code_libe", nullable = false, length = 50)
    private String codeLibe;


    @Column(name = "ordr_affi")
    private Integer ordrAffi;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_active", nullable = false, precision = 1)
    private Integer isActive;

}
