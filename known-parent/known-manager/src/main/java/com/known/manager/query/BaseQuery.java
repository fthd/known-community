package com.known.manager.query;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.utils.Page;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseQuery {

	private Page page;

	private int pageNum = 1;
	
}
