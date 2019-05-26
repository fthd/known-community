package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.known.common.enums.ArticleTypeEnum;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.utils.CustomDateSerializer;
import com.known.common.utils.DateUtil;
import com.known.common.utils.Emotions;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
	
    private String id;

    private String pid;

    private String articleId;

    private String userId;

    private Date createTime;
    
    private String createTimeString;

    private String sourceFrom;

    private ArticleTypeEnum articleType;

    private String userName;

    private String userIcon;

    private String content;
    
    private List<Comment> children = new ArrayList<>();
    
    private String showContent;
    
    private Integer pageNum;
    
    public String getShowContent() {
    	this.showContent = Emotions.formatEmotion(this.content, Emotions.Dev.WEB);
    	return showContent;
	}


    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }



    public void setSourceFrom(String sourceFrom) {
        this.sourceFrom = sourceFrom == null ? null : sourceFrom.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}
	
}