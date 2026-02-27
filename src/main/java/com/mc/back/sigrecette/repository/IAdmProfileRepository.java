package com.mc.back.sigrecette.repository;


import com.mc.back.sigrecette.model.AdmProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdmProfileRepository extends JpaRepository<AdmProfile, Long> {

    @Query("SELECT COUNT(p) = 0 FROM AdmProfile p WHERE UPPER(p.code) = UPPER(:code)")
    boolean isCodeUnique(@Param("code") String code);

}
