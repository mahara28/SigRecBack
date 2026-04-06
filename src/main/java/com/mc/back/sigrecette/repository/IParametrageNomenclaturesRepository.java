package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IParametrageNomenclaturesRepository extends JpaRepository<ParametrageNomenclatures, Long> {

    Optional<ParametrageNomenclatures> findByNomTable(String nomTable);
}
