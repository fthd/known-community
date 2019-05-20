package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleType;
import com.known.common.enums.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageParams {
	private Integer articleId;
	private Integer articleUserId;
	private MessageType messageType;
	private ArticleType articleType;
	private Set<Integer> receiveUserIds;
	private Integer sendUserId;
	private String sendUserName;
	private Integer commentId;
	private Integer pageNum;
	private String des;
	
}
