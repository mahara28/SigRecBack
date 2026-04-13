package com.mc.back.sigrecette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mc.back.sigrecette.model.ParametrageRechercheMultiCritere;

public interface IParametrageRechercheMultiCritereRepository extends JpaRepository<ParametrageRechercheMultiCritere, Long>{

	@Query(value = "SELECT fn_dynamic_dashboard(:table, CAST(:filters AS jsonb), :groupBy, :aggColumn, :aggFunc, :orderBy, :limit, :offset)::text", nativeQuery = true)
	String getStats(
	    @Param("table") String table,
	    @Param("filters") String filters,
	    @Param("groupBy") String[] groupBy,
	    @Param("aggColumn") String aggColumn,
	    @Param("aggFunc") String aggFunc,
	    @Param("orderBy") String orderBy,
	    @Param("limit") Integer limit,
	    @Param("offset") Integer offset
	);
}
