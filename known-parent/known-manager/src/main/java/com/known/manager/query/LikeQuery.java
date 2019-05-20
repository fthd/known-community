package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeQuery extends BaseQuery {
	private Integer articleId;
	
	private ArticleType articleType;
	
	private Integer userId;
	
	private String title;


	public LikeQuery(Integer articleId, ArticleType articleType,
			Integer userId) {
		super();
		this.articleId = articleId;
		this.articleType = articleType;
		this.userId = userId;
	}

}
