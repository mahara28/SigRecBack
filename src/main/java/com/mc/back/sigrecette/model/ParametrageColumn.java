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
@Table(name = "parametrage_column", schema = "public")
public class ParametrageColumn {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "param_nomen_id_gen")
    @SequenceGenerator(name = "param_nomen_id_gen", sequenceName = "param_nomen_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false )
    private Long id;
	
	@Column(name = "id_param_nomen")
    private Long idParamNomen;

    @Size(max = 50)
    @Column(name = "colu_name", length = 50)
    private String coluName;

    @Size(max = 50)
    @Column(name = "code_libe_colu", length = 50)
    private String codeLibeColu;
    
    @NotNull
    @ColumnDefault("0")
    @Column(name = "fore_key", nullable = false, precision = 1)
    private Integer foreKey;
    
    @Size(max = 50)
    @Column(name = "fore_table_name", length = 50)
    private String foreTableName;
    
    
    @Size(max = 50)
    @Column(name = "type_donnee", length = 50)
    private String typeDonnee;
}
