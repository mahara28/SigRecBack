package com.mc.back.sigrecette.model.tool;

import lombok.Data;

@Data
public class AdmFoncProfileDto {
	private Long idFonc;

    private Long isList = 0L;
    private Long isUpdate = 0L;
    private Long isSupp = 0L;
    private Long isDetails = 0L;
    private Long isExport = 0L;
    private Long isImprime = 0L;
    private Long isAdd = 0L;
}	
