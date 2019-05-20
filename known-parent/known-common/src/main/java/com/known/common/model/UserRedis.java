package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 用户登录信息
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-06 18:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRedis {

	private Integer userid;
	private String userName;
	private String userIcon;

}
