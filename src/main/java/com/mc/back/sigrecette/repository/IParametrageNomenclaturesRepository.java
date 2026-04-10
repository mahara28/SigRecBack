package com.mc.back.sigrecette.repository;

import com.mc.back.sigrecette.model.ParametrageNomenclatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IParametrageNomenclaturesRepository extends JpaRepository<ParametrageNomenclatures, Long> {

    Optional<ParametrageNomenclatures> findByNomTable(String nomTable);
    
    @Query(value = "SELECT public.fn_insert_dynamic_table(:table, CAST(:data AS jsonb))", nativeQuery = true)
	String callInsertFunction(
	        @Param("table") String tableName,
	        @Param("data") String jsonData
	);
    
    @Query(value = "SELECT public.fn_update_dynamic_table(:table, CAST(:data AS jsonb))", nativeQuery = true)
	String callUpdateFunction(
	        @Param("table") String tableName,
	        @Param("data") String jsonData
	);
    
	@Query(value = "SELECT public.fn_find_all(:table)", nativeQuery = true)
	String getDataFromTable(@Param("table") String table);
    
	@Query(value = "SELECT public.fn_find_by_id(:tableName, :id)", nativeQuery = true)
	String findByIdFromDynamicTable(
	        @Param("tableName") String tableName,
	        @Param("id") Long id
	);
}
