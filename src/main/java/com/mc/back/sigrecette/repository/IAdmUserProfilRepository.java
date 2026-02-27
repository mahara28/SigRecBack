package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.AdmUserProfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAdmUserProfilRepository extends JpaRepository<AdmUserProfil, Long> {

    @Query(value = "SELECT * FROM adm_user_profil a WHERE a.id_user =:idUser", nativeQuery = true)
    List<AdmUserProfil> getListUserProfilesByIdUser(@Param("idUser") Long idUser);

}