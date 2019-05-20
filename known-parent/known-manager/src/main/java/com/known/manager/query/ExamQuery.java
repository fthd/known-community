package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamQuery extends BaseQuery{

	private StatusEnum status;

	private Integer categoryId;

	private Integer examId;

	private String[] examIds;

	private Boolean showAnalyse;

	private Integer examMaxTitle;

	private String startDate;
	
	private String endDate;

	
}
