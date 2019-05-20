package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateQuery4ArticleCount extends BaseQuery {
	
	private Integer articleId = 0;
	
	private boolean addReadCount;
	
	private boolean addLikeCount;
	
	private boolean addCommentCount;
	
	private boolean addCollectionCount;

}
