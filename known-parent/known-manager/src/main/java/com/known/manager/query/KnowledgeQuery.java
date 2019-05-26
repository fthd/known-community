package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.OrderByEnum;
import com.known.common.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KnowledgeQuery extends BaseQuery{
	private String knowledgeId;
	
	private String userId;
	
	private boolean showContent;
	
	private OrderByEnum orderBy;
	
	private String startDate;
	
	private String endDate;
	
	private Integer pCategoryId;
	
	private Integer categoryId;
	
	private StatusEnum status;

}
