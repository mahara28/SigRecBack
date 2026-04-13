package com.mc.back.sigrecette.model.tool;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatsRequestDto {
	private String table;
    private Map<String, Object> filters; // JSON string
    private String[] groupBy;
    private String aggColumn;
    private String aggFunc;
    private String orderBy;
    private Integer limit;
    private Integer offset;
}
