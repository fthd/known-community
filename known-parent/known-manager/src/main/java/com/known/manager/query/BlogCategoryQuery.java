package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.BlogStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogCategoryQuery extends BaseQuery{

	private Integer userId;
	
	private Integer categoryId;
	
	private BlogStatusEnum status;

	
}
