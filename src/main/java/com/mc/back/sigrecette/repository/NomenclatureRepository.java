package com.mc.back.sigrecette.repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NomenclatureRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String callInsertFunction(String tableName, String jsonData) {

        String sql = "SELECT fn_insert_dynamic_table(:table, CAST(:data AS jsonb))";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("table", tableName);
        query.setParameter("data", jsonData);

        Object result = query.getSingleResult();

        return result != null ? result.toString() : null;
    }
}

