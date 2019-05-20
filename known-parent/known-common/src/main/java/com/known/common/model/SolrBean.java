package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SolrBean implements Serializable{
	/**
	 * 
	 *//*
	private static final long serialVersionUID = 1L;
	@Field
	private String id;
	@Field
	private String title;
	@Field
	private String content;
	@Field
	private String summary;
	@Field
	private String userName;
	@Field
	private String userId;
	@Field
	private String articleType;
	@Field
	private String createTime;

	*/
}
