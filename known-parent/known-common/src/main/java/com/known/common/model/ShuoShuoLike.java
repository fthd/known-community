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
    private Integer id;

    private Integer shuoShuoId;

	private Integer userId;

    private String userIcon;

    private String userName;

    private Date createTime;

	public Integer getId() {
		return id;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

 
}