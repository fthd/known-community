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
import org.springframework.web.util.HtmlUtils;

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
    	// 反转义内容
    	this.showContent = HtmlUtils.htmlUnescape(showContent);
    	return showContent;
	}


    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }


	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}
}