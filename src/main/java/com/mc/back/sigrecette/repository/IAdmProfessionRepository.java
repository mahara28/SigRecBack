package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.AdmProfession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdmProfessionRepository extends JpaRepository<AdmProfession, Long> {
}
