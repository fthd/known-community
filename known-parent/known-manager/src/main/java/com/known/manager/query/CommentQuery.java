package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentQuery extends BaseQuery{

	private String pid;
	
	private String commentId;
	
	private String articleId;
	
	private ArticleTypeEnum articleType;
	
	private String startDate;
	
	private String endDate;

}
