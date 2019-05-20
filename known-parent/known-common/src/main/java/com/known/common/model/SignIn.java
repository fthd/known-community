package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignIn {
    private Integer userid;

    private String userIcon;

    private String userName;

    private Date signDate;

    private Date signTime;

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

}