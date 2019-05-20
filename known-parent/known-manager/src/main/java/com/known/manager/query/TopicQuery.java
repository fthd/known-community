package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.OrderByEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicQuery extends BaseQuery{
	private Integer topicId;
	
	private Integer userId;
	
	private boolean showContent;
	
	private OrderByEnum orderBy;
	
	private String startDate;
	
	private String endDate;
	
	private Integer pCategoryId;
	
	private Integer categoryId;
	
}
