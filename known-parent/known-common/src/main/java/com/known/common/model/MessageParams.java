package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleTypeEnum;
import com.known.common.enums.MessageTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageParams {
	private String articleId;
	private String articleUserId;
	private MessageTypeEnum messageType;
	private ArticleTypeEnum articleType;
	private Set<String> receiveUserIds;
	private String sendUserId;
	private String sendUserName;
	private String commentId;
	private Integer pageNum;
	private String des;
	
}
