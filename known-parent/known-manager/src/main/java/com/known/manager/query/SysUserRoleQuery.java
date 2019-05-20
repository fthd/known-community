package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysUserRoleQuery extends BaseQuery{

	private Integer id;
	
	private Integer userId;
	
	private Integer roleId;

}
