package com.mc.back.sigrecette.service;

import com.mc.back.sigrecette.tools.model.SearchObject;
import com.mc.back.sigrecette.tools.model.SendObject;
import org.springframework.dao.DataAccessException;
import org.springframework.http.server.reactive.ServerHttpRequest;

public interface ICommonService {

    SendObject getListPaginator(SearchObject obj, Object objClass, String particularSpecifCondi);

    SendObject getListPaginatorNative(SearchObject obj, Object objClass, String particularSpecifCondi);

    SendObject getObjectById(Object objClass, String valueId, Boolean nativeSQL);

    SendObject getObjectById(Object objClass, String valueId, String particularSpecifCondi, Boolean nativeSQL);

    SendObject getListObject(Object objClass, SearchObject obj, Boolean nativeSQL);

    SendObject getListObject(Object objClass, SearchObject obj, String particularSpecifCondi, Boolean nativeSQL);

    SendObject getUniqueCode(Object objClass, String colCode, Object idValue, String codeValue);

    SendObject getSingleObject(Object objClass, String particularSpecifCondi, Boolean nativeSQL);

    SendObject getDateSystemNow();

    SendObject getDateSystemNowWs();

    SendObject getObjectByIdWs(Object objClass, String valueId, Boolean nativeSQL);

    SendObject mapper(Object object);

    SendObject exportDataWs(SearchObject obj, Object objClass, String particularSpecifCondi);

    SendObject isUnicode(DataAccessException ex);

    void saveLogAccess(ServerHttpRequest request, String code);

	SendObject exportDataNativeWs(SearchObject objX, Object objClass, String particularSpecifCondi);
}
