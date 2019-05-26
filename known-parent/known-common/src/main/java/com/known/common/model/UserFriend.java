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

	private String userId;

    private String friendUserId;

	private String userIcon;

    private String userName;

    private String friendUserIcon;

    private String friendUserName;

    private Date createTime;

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

}