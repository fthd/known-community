package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ExamChooseType;
import com.known.common.enums.StatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Exam {
    private Integer id;

    private Integer categoryId;

    private ExamChooseType chooseType;

	private Integer userId;

    private String userIcon;

    private String userName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    private StatusEnum status;
    
    private String examTitle;
    
    private String analyse;
    
    private boolean isCorrect;
    
    private int examCount;
    
    private List<Integer> correctAnswerIds;
    
	private List<ExamDetail> examDetails = new ArrayList<ExamDetail>();

}