package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.known.common.utils.CustomDateSerializer;
import com.known.common.utils.Emotions;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShuoShuoComment {
    private String id;

    private String shuoShuoId;

    private String content;

    private Date createTime;

    private String userId;

    private String userIcon;

    private String userName;
    
    private String showContent;
    
    
    public String getShowContent() {
    	this.showContent = Emotions.formatEmotion(this.content, Emotions.Dev.WEB);
    	return showContent;
	}

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

}