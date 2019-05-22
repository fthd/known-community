package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.TopicType;
import com.known.common.utils.DateUtil;
import com.known.common.utils.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topic {
    private Integer topicId;

    private TopicType topicType;

    private Integer pCategoryId;

    private Integer categoryId;

    private String title;

    private Integer userId;

    private String userIcon;

    private String userName;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;

    private Date lastCommentTime;

    private Integer readCount = 0;
    
    private Integer commentCount = 0;
    
    private Integer likeCount = 0;
    
    private Integer collectionCount = 0 ;

    private Integer grade=0;

    private Integer essence=0;
    
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


	/*public String getCategoryName() {
		return CategoryCache.getCategoryById(categoryId).getName();
	}

	public String getpCategoryName() {
		return CategoryCache.getCategoryById(pCategoryId).getName();
	}*/


	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}

}