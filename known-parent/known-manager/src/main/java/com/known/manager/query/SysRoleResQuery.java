package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysRoleResQuery extends BaseQuery{

	private Integer id;
	
	private Integer roleId;
	
	private Integer resId;

}
