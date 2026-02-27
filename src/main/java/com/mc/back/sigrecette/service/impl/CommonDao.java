package com.mc.back.sigrecette.service.impl;

import com.mc.back.sigrecette.service.ICommonDao;
import com.mc.back.sigrecette.tools.ConstanteDao;
import com.mc.back.sigrecette.tools.ConstanteService;
import com.mc.back.sigrecette.tools.UtilsDao;
import com.mc.back.sigrecette.tools.model.ColValue;
import com.mc.back.sigrecette.tools.model.DaoObject;
import com.mc.back.sigrecette.tools.model.SearchObject;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Repository
public class CommonDao implements ICommonDao {

    private static final Logger logger = LogManager.getLogger(CommonDao.class);

    @Autowired
    private UtilsDao utilDao;

    private EntityManager entityManager;

    private List<String> listColNative;

    @Autowired
    public CommonDao(EntityManager entityManager) {
       this.entityManager = entityManager;
    }

    private String getScriptHeadCol(String fieldNameSql, String typeField, String sqlCol) {
       if (typeField.equals(ConstanteDao._CODE_DAO_UUID))
          return sqlCol.concat(" CAST(v." + fieldNameSql).concat(" as varchar(255)) as " + fieldNameSql + ", ");
       else
          return sqlCol.concat("v.").concat(fieldNameSql).concat(", ");
    }

    private String initSqlColumnName(Object objClass, List<String> listCol, Boolean nativeNameCol) {
       String result = "";
       this.listColNative = new ArrayList<String>();
       for (int index = 0; index < objClass.getClass().getDeclaredFields().length; index++) {
          Field field = objClass.getClass().getDeclaredFields()[index];
          field.setAccessible(true);
          String fieldNameSql = field.getAnnotation(Column.class).name();
          this.listColNative.add(field.getName());
          if (listCol.isEmpty())
                return "v.*";
          for (String str : listCol) {
             if (str.equals(field.getName())) // && isIdFiled == false)
                result = nativeNameCol ? this.getScriptHeadCol(fieldNameSql, field.getType().getTypeName(), result)
                      : result.concat("v.").concat(field.getName()).concat(", ");
          }
       }
       return result.substring(0, result.length() - 2);
    }
    
    private List<String> getSqlColName(Object objClass, List<String> listCol, Boolean nativeNameCol) {
       List<String> listColName = new ArrayList<String>();
       if (listCol.isEmpty())
          return null;
       for (int index = 0; index < objClass.getClass().getDeclaredFields().length; index++) {
          Field field = objClass.getClass().getDeclaredFields()[index];
          field.setAccessible(true);
          if(field.getAnnotation(Column.class) != null) {
             for (String str : listCol) {
                if (str.equals(field.getName()))
                   listColName.add(field.getName());
             }
          }
       }
       return listColName; 
    }

    @Override
    public DaoObject getListPaginator(SearchObject obj, Object objClass, String particularSpecifCondi) {
       String sqlCond = "";
       String sqlCol = "v";
       String sqlSort = "";
       String sqlPartSpecCondif = "";
       List<String> listColName = new ArrayList<String>();
       try {
          if (!obj.getDataSearch().isEmpty())
             sqlCond = utilDao.getClauseSql(objClass, obj.getDataSearch(), false);
          if (particularSpecifCondi != null)
             sqlPartSpecCondif = particularSpecifCondi;

          if (obj.getSort().getNameCol() != null)
             sqlSort = obj.getSort().getNameCol() == null ? "" : obj.getSort().getNameCol();
          
          if(!obj.getListCol().isEmpty()) {
             obj.getListCol().add(0, utilDao.getNameColId(objClass));
             obj.setListCol(obj.getListCol().stream().distinct().collect(Collectors.toList()));
             listColName = this.getSqlColName(objClass, obj.getListCol(), false);
             if(listColName == null)
                sqlCol = "v";
             else {
                sqlCol = "";
                for(String str : listColName) {
                   sqlCol = sqlCol.concat(" v."+str+", ");
                }
                sqlCol = sqlCol.substring(0, sqlCol.length() - 2);
             }
          }


          Query query = (Query) entityManager.createQuery("Select " + sqlCol + " from " + objClass.getClass().getName() + " v where 1=1 "
                      .concat(sqlPartSpecCondif).concat(" ").concat(sqlCond).concat(" ").concat(sqlSort));
          if (obj.getPagination().getLimit() != null && obj.getPagination().getOffSet() != null)
             query.setFirstResult(obj.getPagination().getOffSet()).setMaxResults(obj.getPagination().getLimit());

//        Query queryCount = (Query) entityManager.createQuery("Select count(1) from " + objClass.getClass().getName()
//              + " v where 1=1 ".concat(sqlPartSpecCondif).concat(" ").concat(sqlCond));

          return new DaoObject(ConstanteService._CODE_DAO_SUCCESS,
                sqlCol == "v" ? query.getResultList()
                      : utilDao.mappedDataTable(query.getResultList(), listColName),
                null);// (Long) queryCount.getSingleResult());
       } catch (Exception e) {
          logger.error("Error CommonDao in method getListPaginator of class " + objClass.getClass().getName() + " :: "
                + e.toString());
          return new DaoObject(null, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }
    
    @Override
    public DaoObject getCountPaginator(SearchObject obj, Object objClass, String particularSpecifCondi) {
       String sqlCond = "";
       String sqlPartSpecCondif = "";
       this.listColNative = new ArrayList<String>();
       try {
          if (!obj.getDataSearch().isEmpty())
             sqlCond = utilDao.getClauseSql(objClass, obj.getDataSearch(), false);
          if (particularSpecifCondi != null)
             sqlPartSpecCondif = particularSpecifCondi;

          Query queryCount = (Query) entityManager.createQuery("Select count(1) from " + objClass.getClass().getName() + " v where 1=1 ".concat(sqlPartSpecCondif).concat(" ").concat(sqlCond));

          return new DaoObject(ConstanteService._CODE_DAO_SUCCESS, null, (Long) queryCount.getSingleResult());
       } catch (Exception e) {
          logger.error("Error CommonDao in method getCountPaginator of class " + objClass.getClass().getName() + " :: " + e.toString());
          return new DaoObject(ConstanteService._CODE_DAO_ERROR, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    @Override
    public DaoObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi) {
       listColNative = new ArrayList<String>();
       String sqlCol = "";
       String sqlCond = "";
       String sqlSort = "";
       String sqlPartSpecCondif = "";
       try {
          sqlCol =  this.initSqlColumnName(objClass, obj.getListCol(), true);
          if (particularSpecifCondi != null)
             sqlPartSpecCondif = particularSpecifCondi;
          if (!obj.getDataSearch().isEmpty())
             sqlCond = utilDao.getClauseSql(objClass, obj.getDataSearch(), true);

          if (obj.getSort().getNameCol() != null)
             sqlSort = obj.getSort().getNameCol() == null ? "" : obj.getSort().getNameCol();

          Table table = objClass.getClass().getAnnotation(Table.class);
          Query query = (Query) entityManager
                .createNativeQuery("Select " + sqlCol + " from " + table.schema()+"."+table.name() + " v where 1=1 "
                      .concat(sqlPartSpecCondif).concat(" ").concat(sqlCond).concat(" ").concat(sqlSort), objClass.getClass());
          if (obj.getPagination().getLimit() != null && obj.getPagination().getOffSet() != null)
             query.setFirstResult(obj.getPagination().getOffSet()).setMaxResults(obj.getPagination().getLimit());

          Query queryCount = (Query) entityManager.createNativeQuery("Select count(1) from " + table.schema()+"."+table.name()
                + " v where 1=1 ".concat(sqlPartSpecCondif).concat(" ").concat(sqlCond));
          return new DaoObject(ConstanteService._CODE_DAO_SUCCESS,
                sqlCol == "v.*" ? query.getResultList()
                      : utilDao.mappedDataTable(query.getResultList(), listColNative),
                Long.parseLong(queryCount.getSingleResult().toString()));
       } catch (Exception e) {
          logger.error("Error CommonDao in method getListPaginatorNative of class " + objClass.getClass().getName()
                + " :: " + e.toString());
          return new DaoObject(null, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    @Override
    public DaoObject getObjectById(Object objClass, String valueId, Boolean nativeSQL, String specifCondi) {
       String sqlCond = "";
       String colIdWithClause = "";
       try {
          if (specifCondi != null)
             sqlCond = specifCondi;
          colIdWithClause = utilDao.getColIdOfClass(objClass, valueId, nativeSQL);
          sqlCond = colIdWithClause.concat(" ").concat(sqlCond);
          return nativeSQL ? executionNativeSQL(objClass, sqlCond, "", true)
                : executionHibernate(objClass, sqlCond, "", true);
       } catch (Exception e) {
          logger.error("Error CommonDao in method getObjectById of class " + objClass.getClass().getName() + " :: "
                + e.toString());
          return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    @Override
    public DaoObject getListObject(SearchObject obj, Object objClass, String specifCondi, Boolean nativeSQL) {
       String sqlCond = "";
       String sqlSort = "";
       try {

          if (obj.getDataSearch() != null)
             if (!obj.getDataSearch().isEmpty())
                sqlCond = utilDao.getClauseSql(objClass, obj.getDataSearch(), nativeSQL);

          if (specifCondi != null)
             sqlCond = sqlCond.concat(" ").concat(specifCondi);

          if (obj.getSort() != null) {
             String colSort = utilDao.getColName(objClass, obj.getSort().getNameCol(), nativeSQL);
             if (colSort != null)
                sqlSort = " Order by v." + colSort + " " + obj.getSort().getDirection();
          }

          return nativeSQL ? executionNativeSQL(objClass, sqlCond, sqlSort, false)
                : executionHibernate(objClass, sqlCond, sqlSort, false);
       } catch (Exception e) {
          logger.error("Error CommonDao in method getListObject of class " + objClass.getClass().getName()
                + " :: " + e.toString());
          return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    @Override
    public DaoObject getUniqueCode(Object objClass, String colCode, Object idValue, String codeValue) {
       try {
          String codeCol = utilDao.getColName(objClass, colCode, true);
          ColValue colValue = utilDao.getColId(objClass, true);
          Table table = objClass.getClass().getAnnotation(Table.class);
          Query query = (Query) entityManager.createQuery("select case when (count(1) > 0) then 0 else 1 end from "
                + table.name() + "  WHERE 1=1 AND upper(" + codeCol + ")=upper('" + codeValue + "') "
                + (idValue != null ? " and " + colValue.getNameCol() + "!="
                      + (colValue.getTypeFiled().equals(ConstanteDao._CODE_NUMBER) ? idValue : "'" + idValue + "'") : ""));
          return new DaoObject(ConstanteService._CODE_SERVICE_SUCCESS, query.getSingleResult(), null);
       } catch (Exception e) {
          logger.error("Error CommonDao in method getUniqueCode of class " + objClass.getClass().getName()+ " :: " + e.toString());
          return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    @Override
    public DaoObject getSingleObject(Object objClass, String specifCondi, Boolean nativeSQL) {
       try {
          if (specifCondi == null)
             return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
          return nativeSQL ? executionNativeSQL(objClass, specifCondi, "", true)
                : executionHibernate(objClass, specifCondi, "", true);
       } catch (Exception e) {
          logger.error("Error CommonDao in method getSingleObject of class " + objClass.getClass().getName()
                + " :: " + e.toString());
          return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    @Override
    public DaoObject getDateSystemNow() {
       try {
          Query query = (Query) entityManager.createNativeQuery("SELECT CURRENT_TIMESTAMP result  ");
          return new DaoObject(ConstanteService._CODE_DAO_SUCCESS, query.getSingleResult(), null);
       } catch (Exception e) {
          logger.error("Error CommonDao in method getDateSystemNow :: " + e.toString());
          return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    public DaoObject executionNativeSQL(Object objClass, String particularSpecifCondi, String ordreSQL,
          Boolean isUniqueResult) {
       try {
          //@GeneratedValue
          Table table = objClass.getClass().getAnnotation(Table.class);
//        Generate
          Query query = (Query) entityManager.createNativeQuery(
                "select v.* from " + table.schema()+"."+table.name() + "  v where 1=1 " + particularSpecifCondi + " " + ordreSQL, objClass.getClass());
          return new DaoObject(ConstanteService._CODE_SERVICE_SUCCESS,
                isUniqueResult ? query.getSingleResult() : query.getResultList(), null);
       } catch (Exception e) {
          logger.error("Error CommonDao in method executionNativeSQL of class " + objClass.getClass().getName() + " "
                + (isUniqueResult ? " unique result " : " list result ") + " :: " + e.toString());
          return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

    public DaoObject executionHibernate(Object objClass, String particularSpecifCondi, String ordreSQL,
          Boolean isUniqueResult) {
       try {
          Query query = (Query) entityManager.createQuery("Select v from " + objClass.getClass().getName()
                + "  v where 1=1 " + particularSpecifCondi + " " + ordreSQL);
          return new DaoObject(ConstanteService._CODE_SERVICE_SUCCESS,
                isUniqueResult ? query.getSingleResult() : query.getResultList(), null);
       } catch (Exception e) {
          logger.error("Error CommonDao in method executionHibernate of class " + objClass.getClass().getName() + " "
                + (isUniqueResult ? " unique result " : " list result ") + " :: " + e.toString());
          return new DaoObject(ConstanteService._CODE_SERVICE_ERROR_DAO, null, null);
       } finally {
          entityManager.clear();
          entityManager.close();
       }
    }

}
