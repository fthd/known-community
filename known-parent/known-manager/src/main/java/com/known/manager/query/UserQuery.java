package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserQuery extends BaseQuery {

    private String userid;

    private String email;

    private String userName;
    
	private String startDate;
	
	private String endDate;
	
	private String loginStartDate;
	
	private String loginEndDate;

}
