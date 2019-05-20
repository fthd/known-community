package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysLog {
    private Long id;

    private String account;

    private String opContent;

    private String clientIp;

    private String address;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent == null ? null : opContent.trim();
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp == null ? null : clientIp.trim();
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

}