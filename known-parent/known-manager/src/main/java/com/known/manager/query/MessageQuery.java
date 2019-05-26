package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.MessageStatusEnum;
import com.known.common.enums.OrderByEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageQuery extends BaseQuery {

	private String id;
	
	private MessageStatusEnum status;
	
	private String receivedUserId;
	
	private String[] ids;
	
	private String startDate;
	
	private String endDate;
	
	private OrderByEnum orderBy;

}
