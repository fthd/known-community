package com.known.manager.query;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRoleQuery extends BaseQuery {
	
	private String id;
	
	private Integer status;

}
