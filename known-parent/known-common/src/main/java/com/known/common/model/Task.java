package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.utils.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    private String id;

    private String taskClassz;

    private String taskMethod;

    private String taskTime;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date lastUpdateTime;

    private Integer status;

    private String des;

    public String getTaskTime() {
        // 替换前台转义字符
        return StringUtil.isEmpty(taskTime) ? null : taskTime.replaceAll("&nbsp;", " ").trim();
    }
}