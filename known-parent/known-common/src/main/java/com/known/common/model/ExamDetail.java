package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamDetail {
    private Integer id;

    private Integer examId;

    private String answer;

    private Integer isRightAnswer;

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    
}