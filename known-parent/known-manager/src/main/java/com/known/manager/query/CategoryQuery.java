package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryQuery extends BaseQuery{

	private String categoryId;

	private String pid;

	private String showInTopic;

	private String showInAsk;

	private String showInKnowledge;

	private String startDate;

	private String endDate;

}