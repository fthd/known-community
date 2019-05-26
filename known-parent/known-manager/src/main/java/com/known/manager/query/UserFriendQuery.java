package com.known.manager.query;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFriendQuery extends BaseQuery{

	private String userId;

	private String friendUserId;

	
}
