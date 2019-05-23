package com.known.manager.query;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShuoShuoQuery extends BaseQuery{

	private Integer id;

	private Integer userId;

	private Integer shuoShuoId;
	
	private String startDate;
	
	private String endDate;
	

}
