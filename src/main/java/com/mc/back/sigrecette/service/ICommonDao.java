package com.mc.back.sigrecette.service;


import com.mc.back.sigrecette.tools.model.DaoObject;
import com.mc.back.sigrecette.tools.model.SearchObject;

public interface ICommonDao {

	DaoObject getListPaginator(SearchObject obj, Object objClass, String particularSpecifCondi);
	
	DaoObject getCountPaginator(SearchObject obj, Object objClass, String particularSpecifCondi);

	DaoObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi);

	DaoObject getObjectById(Object objClass, String valueId, Boolean nativeSQL, String specifCondi);

	DaoObject getListObject(SearchObject obj, Object objClas, String specifCondi, Boolean nativeSQL);

	DaoObject getUniqueCode(Object objClass, String colCode, Object idValue, String codeValue);

	DaoObject getSingleObject(Object objClass, String specifCondi, Boolean nativeSQL);

	DaoObject getDateSystemNow();
}
