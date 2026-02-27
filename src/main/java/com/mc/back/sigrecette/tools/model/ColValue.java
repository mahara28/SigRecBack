package com.mc.back.sigrecette.tools.model;

import lombok.Data;

@Data
public class ColValue {

	private String nameCol;
	private String typeFiled;

	public ColValue(String nameCol, String typeFiled) {
		super();
		this.nameCol = nameCol;
		this.typeFiled = typeFiled;
	}

	public ColValue() {
		super();
	}

}
