package com.mc.back.sigrecette.model;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parametrage_recherche_multi_critere", schema = "public")
public class ParametrageRechercheMultiCritere {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "param_rech_multi_critere_id_gen")
    @SequenceGenerator(name = "param_rech_multi_critere_id_gen", sequenceName = "param_rech_multi_critere_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false )
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nom_table", nullable = false, length = 50, unique = true)
    private String nomTable;
    
    @Size(max = 500)
    @Column(name = "requ", length = 500)
    private String requ;
    
    @Column(name = "ordr_affi")
    private Integer ordrAffi;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_active", nullable = false, precision = 1)
    private Integer isActive;

}
