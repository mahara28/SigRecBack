package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.view.VAdmLogAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVLogAccessRepository extends JpaRepository<VAdmLogAccess, Long> {
}
