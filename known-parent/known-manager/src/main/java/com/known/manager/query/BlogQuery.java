package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.BlogStatusEnum;
import com.known.common.enums.OrderByEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogQuery extends BaseQuery{
	private String title;
	
	private Integer blogId;
	
	private Integer userId;
	
	private Integer categoryId;
	
	private BlogStatusEnum status;
	
	private boolean showContent;
	
	private OrderByEnum orderBy;
	
	private String startDate;
	
	private String endDate;

}
