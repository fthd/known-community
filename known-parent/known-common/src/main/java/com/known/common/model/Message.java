package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.MessageStatus;
import com.known.common.utils.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {
    private Integer id;

    private Integer receivedUserId;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createTime;
    
    private String createTimeString;
    
    private MessageStatus status;

    private String description;

    private String url;

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
    
    public String getCreateTimeString() {
    	SimpleDateFormat sdf = new SimpleDateFormat(DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern());
		return DateUtil.friendly_time(sdf.format(createTime));
	}

}