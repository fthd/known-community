package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.StatusEnum;
import com.known.common.utils.DateUtil;
import com.known.common.utils.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Knowledge {
    private String knowledgeId;

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

    private Integer readCount = 0;
    
    private Integer commentCount = 0;
    
    private Integer likeCount = 0;
    
    private Integer collectionCount = 0 ;

    private String content;

    private String summary;

    private String knowledgeImage;
    
    private String[] knowledgeImageArray;

    private String knowledgeImageThum;
    
    private String categoryName;//二级栏目名称
    
    private String pCategoryName;//一级栏目名称
    
    private Attachment attachment;
    
    private StatusEnum status;


	public void setpCategoryId(String pCategoryId) {
		this.pCategoryId = pCategoryId;
	}

    public String getpCategoryId() {
        return pCategoryId;
    }

    public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}

	public String[] getTopicImageArray() {
		if(StringUtil.isEmpty(this.knowledgeImage)){
			return null;
		}
		return knowledgeImage.split("\\|");
	}

	/*public String getCategoryName() {
		return CategoryCache.getCategoryById(categoryId).getName();
	}

	public String getpCategoryName() {
		return CategoryCache.getCategoryById(pCategoryId).getName();
	}*/
}