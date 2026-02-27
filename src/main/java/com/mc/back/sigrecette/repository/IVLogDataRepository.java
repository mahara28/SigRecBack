package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.view.VAdmLogData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVLogDataRepository extends JpaRepository<VAdmLogData, Long> {

}
