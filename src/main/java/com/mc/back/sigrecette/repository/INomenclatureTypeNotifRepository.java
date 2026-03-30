package com.mc.back.sigrecette.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mc.back.sigrecette.model.NomenclatureTypeNotif;

@Repository
public interface INomenclatureTypeNotifRepository extends JpaRepository<NomenclatureTypeNotif,Long>{

}
