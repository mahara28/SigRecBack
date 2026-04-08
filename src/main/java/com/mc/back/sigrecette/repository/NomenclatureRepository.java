package com.mc.back.sigrecette.repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> getDataFromTable(String tableName) {

        String sql = "SELECT * FROM " + tableName;

        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(org.hibernate.transform.AliasToEntityMapResultTransformer.INSTANCE);

        return query.getResultList();
    }

}

