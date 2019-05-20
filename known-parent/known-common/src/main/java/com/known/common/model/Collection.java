package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.ArticleType;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.utils.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection{
	private Integer articleId;

    private ArticleType articleType;

    private Integer userId;
    
    private Integer articleUserId;
	
    private String title;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;

	
	public String getCreateTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));

	}
}