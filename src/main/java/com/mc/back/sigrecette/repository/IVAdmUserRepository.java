package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.view.VAdmUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IVAdmUserRepository extends JpaRepository<VAdmUser, Long> {

    @Query("select v from VAdmUser v where v.id=:id ")
    VAdmUser findVAdmUserById(@Param("id") Long id);

    @Query("select v from VAdmUser v where v.mail=:mail ")
    VAdmUser findVAdmUserByMail(@Param("mail") String mail);

}
