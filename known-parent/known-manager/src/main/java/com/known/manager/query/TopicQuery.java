package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.OrderByEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicQuery extends BaseQuery{

	private String topicId;
	
	private String userId;
	
	private boolean showContent;
	
	private OrderByEnum orderBy;
	
	private String startDate;
	
	private String endDate;
	
	private String pCategoryId;
	
	private String categoryId;
	
}
