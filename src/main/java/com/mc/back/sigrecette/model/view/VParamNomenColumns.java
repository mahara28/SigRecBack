package com.mc.back.sigrecette.model.view;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "v_param_nomen_columns")
public class VParamNomenColumns {
	@Id
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
    
    @Column(name = "column_ext_fst_id")
    private Long columnExtFstId;
    
    @Size(max = 50)
    @Column(name = "colu_ext_fst", length = 50)
    private String coluExtFst;
    
    @Size(max = 50)
    @Column(name = "type_donnee_fst", length = 50)
    private String typeDonneeFst;
    
    @Size(max = 50)
    @Column(name = "code_libe_colu_fst", length = 50)
    private String codeLibeColuFst;
    
    @Column(name = "column_ext_sec_id")
    private Long columnExtSecId;
    
    @Size(max = 50)
    @Column(name = "colu_ext_sec", length = 50)
    private String coluExtSec;
    
    @Size(max = 50)
    @Column(name = "code_libe_colu_sec", length = 50)
    private String codeLibeColuSec;
    
    @Size(max = 50)
    @Column(name = "type_donnee_sec", length = 50)
    private String typeDonneeSec;

}
