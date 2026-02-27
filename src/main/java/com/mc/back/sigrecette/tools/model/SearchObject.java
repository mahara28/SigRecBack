package com.mc.back.sigrecette.tools.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchObject {

	private Pagination pagination;
	private Sort sort;
	private List<Sort> listSort;
	private List<CriteriaSearch> dataSearch;
	private List<String> listCol;

	//For Export
	private String language;
//	private String title;
	private String typeExport;
	private Object metadata;

}
