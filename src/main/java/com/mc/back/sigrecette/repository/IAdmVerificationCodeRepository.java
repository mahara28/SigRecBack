package com.mc.back.sigrecette.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mc.back.sigrecette.model.AdmVerificationCode;

@Repository
public interface IAdmVerificationCodeRepository extends JpaRepository<AdmVerificationCode, Long>{

	//AdmVerificationCode findByEmail(@Param("email") String email);
	
	Optional<AdmVerificationCode> findByCodeVerif(String codeVerif);
	
	Optional<AdmVerificationCode> findByEmail(String email);

    Optional<AdmVerificationCode> findByEmailAndCodeVerif(String email, String codeVerif);
    
    Optional<AdmVerificationCode> findByIdAndCodeVerif(Long id, String codeVerif);

    void deleteByEmail(String email);
}
