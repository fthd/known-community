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
	private Integer articleId;
	private Integer articleUserId;
	private MessageTypeEnum messageType;
	private ArticleTypeEnum articleType;
	private Set<Integer> receiveUserIds;
	private Integer sendUserId;
	private String sendUserName;
	private Integer commentId;
	private Integer pageNum;
	private String des;
	
}
