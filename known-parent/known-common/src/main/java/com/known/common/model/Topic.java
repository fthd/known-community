package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.TopicTypeEnum;
import com.known.common.utils.DateUtil;
import com.known.common.utils.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topic {
    private String topicId;

    private TopicTypeEnum topicType;

    private String pCategoryId;

    private String categoryId;

    private String title;

    private String userId;

    private String userIcon;

    private String userName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;

    private Date lastCommentTime;

    private Integer readCount;
    
    private Integer commentCount;
    
    private Integer likeCount;
    
    private Integer collectionCount;

    private Integer grade;

    private Integer essence;
    
    private String content;

    private String summary;

    private String topicImage;
    
    private String[] topicImageArray;

    private String topicImageThum;
    
    private String categoryName;//二级栏目名称
    
    private String pCategoryName;//一级栏目名称
    
    private Attachment attachment;
    
    private Integer topicCount;
    
    private Integer boolNew;


    public String getTitle() {
        return StringUtil.isEmpty(title) ? null : HtmlUtils.htmlUnescape(title);
    }


    public String getContent() {
        return StringUtil.isEmpty(content) ? null : HtmlUtils.htmlUnescape(content);
    }

    public Integer getBoolNew() {
		if(DateUtil.daysBetween(createTime, new Date()) > 2){
					return 0;
			}
			return 1;
	}



	public String[] getTopicImageArray() {
		if(StringUtil.isEmpty(this.topicImage)){
			return null;
		}
		return topicImage.split("\\|");
	}


	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}

}