package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Like {
	private String articleId;

	private ArticleTypeEnum articleType;

	private String userId;

	private String title;

	private Date createTime;

}