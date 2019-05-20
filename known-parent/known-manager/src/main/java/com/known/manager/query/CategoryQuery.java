package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryQuery extends BaseQuery{
	private Integer categoryId;
	private Integer pid;
	private String showInBbs;
	private String showInQuestion;
	private String showInKnowledge;
	private String showInExam;
	private String startDate;
	private String endDate;
}