package com.mc.back.sigrecette.repository;


import com.mc.back.sigrecette.model.AdmFoncProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IAdmFoncProfileRepository extends JpaRepository<AdmFoncProfile, Long> {
    @Query("select a from AdmFoncProfile a where a.idProfil=:idProfil")
    List<AdmFoncProfile> getListAdmFoncProfileByIdProfil(@Param(value = "idProfil") Long idProfil);

    @Transactional
    @Modifying
    @Query("delete from AdmFoncProfile a where a.idProfil=:idProfil")
    void deleteByIdAdmProfil(@Param(value = "idProfil") Long idProfil);

    @Modifying
    @Query("DELETE FROM AdmFoncProfile afp WHERE afp.idProfil = :idProfil")
    void deleteByProfileId(@Param("idProfil") Long idProfil);
}
