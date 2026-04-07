package com.mc.back.sigrecette.model.tool;

import lombok.Data;

import java.util.Map;

@Data
public class NomenclatureDTO {
    private String nomTable;
    private Map<String, Object> data;

}