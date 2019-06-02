package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.SolveEnum;
import com.known.common.utils.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ask {
    private String askId;

    private String pCategoryId;

    private String categoryId;

    private String title;

    private String userId;

    private String userIcon;

    private String userName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;

    private Integer readCount;
    
    private Integer commentCount;
    
    private Integer likeCount;
    
    private Integer collectionCount;

    private Integer mark;

    private String bestAnswerId;

    private String bestAnswerUserId;

    private String bestAnswerUserName;
    
    private String bestAnswerUserIcon;

    private SolveEnum solveType;
    
    private String content;

    private String summary;

    private String askImage;

    private String askImageThum;
    
    private Comment bestAnswer;
    
    private Integer solveCount;

    public String getTitle() {
        return HtmlUtils.htmlUnescape(title);
    }

    public String getContent() {
        return HtmlUtils.htmlUnescape(content);
    }

    public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}

}