package com.mc.back.sigrecette.model;

import java.time.Instant;

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
@Table(name = "nomenclature_type_notif", schema = "public")
public class NomenclatureTypeNotif {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "nomen_type_notif_seq")
    @SequenceGenerator(name = "nomen_type_notif_seq", sequenceName = "nomen_type_notif_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, precision = 22)
    private Long id;

	@Size(max = 100)
    @NotNull
    @Column(name = "des", nullable = false, length = 100)
    private String des;
	
	@Size(max = 40)
    @NotNull
    @Column(name = "code", nullable = false, length = 40)
    private String code;
	
	@NotNull
    @ColumnDefault("0")
    @Column(name = "is_active", nullable = false, precision = 1)
    private Integer isActive;

}
