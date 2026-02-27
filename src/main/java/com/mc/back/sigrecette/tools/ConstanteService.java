package com.mc.back.sigrecette.tools;

public class ConstanteService {

	// ######################################################################
	// CODE SORT
	// ######################################################################
	public static final String _CODE_SORT_ASC = "ASC";
	public static final String _CODE_SORT_DESC = "DESC";

	// ######################################################################
	// CODE SERVICE
	// ######################################################################
	public static final String _CODE_SERVICE_SUCCESS = "200";
	public static final String _CODE_SERVICE_SUCCESS_WAIT_PERMISSION = "201";
	public static final String _CODE_SERVICE_ACCOUNT_EXPIRED = "202";

	public static final String _CODE_SERVICE_BAD_REQUEST = "400";
	public static final String _CODE_SERVICE_UNAUTHORIZED = "401";
	public static final String _CODE_SERVICE_NO_ACCESS = "403";
	public static final String _CODE_SERVICE_URI_NOT_FOUND = "404";
	public static final String _CODE_SERVICE_METHOD_NOT_ALLOWED = "405";
	public static final String _CODE_SERVICE_ERROR_DAO = "406";

	public static final String _CODE_SERVICE_LOGIN_EXISTS = "411";
	public static final String _CODE_SERVICE_CODE_EXISTS = "413";

	public static final String _CODE_SERVICE_ERROR_IN_METHOD = "420";
	public static final String _CODE_SERVICE_ERROR_ALIAS_PARAM = "421";
	public static final String _CODE_SERVICE_ERROR_NOT_EXISTS_ROW_DATA_BASE = "422";
	public static final String _CODE_SERVICE_WRONG_PARAM = "423";
	public static final String _CODE_SERVICE_ERROR_DELETE_ROW = "424";
	public static final String _CODE_SERVICE_ERROR_UNIQUE_CODE = "425";
	public static final String _CODE_SERVICE_ERROR_SAVE_OR_UPDATE = "426";
	public static final String _CODE_SERVICE_ERROR_GET_LIST = "427";
	public static final String _CODE_SERVICE_ERROR_HORS_COND = "428";

	public static final String _CODE_SERVICE_USER_ERROR_AUTH = "461";
	public static final String _CODE_SERVICE_PRB_IN_CONFIRM_PASSWORD = "462";

	public static final String _CODE_SERVICE_ERROR_CONVERT = "499";
	public static final String _CODE_SERVICE_ERROR = "500";
	public static final String _CODE_SERVICE_ERROR_FILE_UPLOAD = "501";

	// ######################################################################
	// CODE DAO
	// ######################################################################
	public static final String _CODE_DAO_SUCCESS = "200";

	public static final String _CODE_DAO_ERROR_ALIAS_COL_ID_NOT_EXISTS = "401";
	public static final String _CODE_DAO_ERROR = "500";

	// ######################################################################
	// CODE FTP
	// ######################################################################
	
	public static final String _CODE_FTP_NO_CONNECT = "601";
	public static final String _CODE_FTP_NO_FILE = "602";
	public static final String _CODE_FTP_NO_FIRST_PARAM = "603";
	public static final String _CODE_FTP_NO_EMPTY_PARAM = "604";
	public static final String _CODE_FTP_VALUE_TAKE = "605";
	
	
	// ######################################################################
	// CODE ETAT INSCRIPTION GROUPE
	// ######################################################################
	public static final Integer _CODE_NON_ENCORE_TRAITE = 1;
	public static final Integer _CODE_ACCEPTE = 2;
	public static final Integer _CODE_REJETE = 3;
}
