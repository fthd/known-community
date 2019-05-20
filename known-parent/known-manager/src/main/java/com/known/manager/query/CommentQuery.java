package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentQuery extends BaseQuery{

	private Integer pid;
	
	private Integer commentId;
	
	private Integer articleId;
	
	private ArticleType articleType;
	
	private String startDate;
	
	private String endDate;

}
