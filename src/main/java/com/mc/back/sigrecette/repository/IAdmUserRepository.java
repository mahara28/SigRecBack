package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.AdmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdmUserRepository extends JpaRepository<AdmUser, Long> {
    @Query("select case when count(u) > 0 then false else true end from AdmUser u where u.email=:mail")
    Boolean uniqueAdmUserByEmail(@Param("mail") String mail);

    @Query("select case when count(u) > 0 then false else true end from AdmUser u where u.code=:code ")
    Boolean uniqueAdmUserByCode(@Param("code") String code);

    AdmUser findByEmail(@Param("email") String email);

    @Query("SELECT u FROM AdmUser u WHERE u.id = :id")
    AdmUser findUserById(@Param("id") Long id);
}
