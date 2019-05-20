package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentDownload{
	 private Integer attachmentId;

	 private Integer userId;
	 
    private String userIcon;

    private String userName;
    

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}