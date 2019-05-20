package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.known.common.utils.CustomDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserFriend{
	private Integer userId;

    private Integer friendUserId;

	private String userIcon;

    private String userName;

    private String friendUserIcon;

    private String friendUserName;

    private Date createTime;

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon == null ? null : userIcon.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public void setFriendUserIcon(String friendUserIcon) {
        this.friendUserIcon = friendUserIcon == null ? null : friendUserIcon.trim();
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName == null ? null : friendUserName.trim();
    }
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

}