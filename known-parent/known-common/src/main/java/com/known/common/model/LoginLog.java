package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginLog {
    private Integer id;

    private String account;

    private String loginIp;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date loginTime;

    private String address;

    private String lat;

    private String lng;


    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public void setLat(String lat) {
        this.lat = lat == null ? null : lat.trim();
    }

    public void setLng(String lng) {
        this.lng = lng == null ? null : lng.trim();
    }
}