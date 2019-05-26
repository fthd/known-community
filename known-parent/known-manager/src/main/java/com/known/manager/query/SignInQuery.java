package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInQuery extends BaseQuery {
	
	private String userid;

	private Date curDate;

	private Date startDate;

	private Date endDate;

}
