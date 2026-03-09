package com.mc.back.sigrecette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mc.back.sigrecette.model.ActiveSession;


@Repository
public interface ActiveSessionRepository extends JpaRepository<ActiveSession, Long> {

	ActiveSession findByToken(String token);

	void deleteByToken(String token);
	
	
	

	    ActiveSession findByRefreshToken(String refreshToken);
	    void deleteByRefreshToken(String refreshToken);
}