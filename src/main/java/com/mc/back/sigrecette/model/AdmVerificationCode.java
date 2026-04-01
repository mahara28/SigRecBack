package com.mc.back.sigrecette.model;

import java.time.Instant;
import java.util.Date;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "adm_verification_code")
public class AdmVerificationCode {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adm_verification_code_id_gen")
    @SequenceGenerator(name = "adm_verification_code_id_gen", sequenceName = "seq_adm_verification_code", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Size(max = 60)
    @NotNull
    @Column(name = "code_verif", nullable = false, length = 60)
    private String codeVerif;
	
	@Size(max = 60)
    @NotNull
    @Column(name = "email", nullable = false, length = 60)
    private String email;
	
	@Column(name = "date_expiration")
    private Instant dateExpiration;
}
