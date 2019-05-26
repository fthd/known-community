package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.known.common.utils.CustomDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShuoShuoLike {
    private String id;

    private String shuoShuoId;

	private String userId;

    private String userIcon;

    private String userName;

    private Date createTime;

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

 
}