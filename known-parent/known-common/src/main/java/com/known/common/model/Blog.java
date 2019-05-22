package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.BlogStatusEnum;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.utils.DateUtil;
import com.known.common.utils.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {
    private Integer blogId;

    private Integer categoryId;
    
    private String name;

    private String title;

    private Integer userId;

    private String userIcon;

    private String userName;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;

    private Integer commentCount = 0;

    private Integer readCount = 0;

    private Integer collectionCount = 0;

    private Integer likeCount = 0;

    private BlogStatusEnum status;

    private String content;

    private String summary;

    private String blogImage;
    
    private String[] blogImageArray;
    
    private Attachment attachment;

    private String blogImageThum;

	public String[] getBlogImageArray() {
    	if(StringUtil.isEmpty(this.blogImage)){
			return null;
		}
		return blogImage.split("\\|");
	}


    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }


    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }



    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    
	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}
}