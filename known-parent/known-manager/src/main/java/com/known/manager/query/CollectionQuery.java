package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleTypeEnum;
import com.known.common.enums.OrderByEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionQuery extends BaseQuery {
	private String articleId;
	
	private ArticleTypeEnum articleType;
	
	private String userId;
	
	private String title;
	
	private String startDate;
	
	private String endDate;
	
	private OrderByEnum orderBy;


	public CollectionQuery(String articleId, ArticleTypeEnum articleType,
		 String userId) {
		this.articleId = articleId;
		this.articleType = articleType;
		this.userId = userId;
	}

	
}
