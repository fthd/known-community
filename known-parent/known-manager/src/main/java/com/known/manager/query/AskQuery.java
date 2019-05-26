package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.OrderByEnum;
import com.known.common.enums.SolveEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AskQuery extends BaseQuery {
	private String askId;
	
	private Integer userId;
	
	private SolveEnum solveType;
	
	private boolean showContent;
	
	private OrderByEnum orderBy;
	
	private String startDate;
	
	private String endDate;
	
	private Integer pCategoryId;
	
	private Integer categoryId;

	private Integer topCount;

}
