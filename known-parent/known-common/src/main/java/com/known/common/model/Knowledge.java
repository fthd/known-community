package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.StatusEnum;
import com.known.common.utils.DateUtil;
import com.known.common.utils.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Knowledge {
    private Integer topicId;

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

    private String content;

    private String summary;

    private String topicImage;
    
    private String[] topicImageArray;

    private String topicImageThum;
    
    private String categoryName;//二级栏目名称
    
    private String pCategoryName;//一级栏目名称
    
    private Attachment attachment;
    
    private StatusEnum status;


	public void setpCategoryId(Integer pCategoryId) {
		this.pCategoryId = pCategoryId;
	}

    public Integer getpCategoryId() {
        return pCategoryId;
    }

    public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}

	public String[] getTopicImageArray() {
		if(StringUtils.isEmpty(this.topicImage)){
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
}