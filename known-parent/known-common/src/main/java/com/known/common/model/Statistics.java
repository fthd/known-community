package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Statistics {
    private Date statisticsDate;

    private Integer signinCount;

    private Integer shuoshuoCount;

    private Integer shuoshuoCommentCount;

    private Integer topicCount;

    private Integer topicCommentCount;

    private Integer knowledgeCount;

    private Integer knowledgeCommentCount;

    private Integer askCount;

    private Integer askCommentCount;

    private Integer blogCount;

    private Integer blogCommentCount;

    private Integer examCount;

    private Integer userCount;

    private Integer activeUserCount;

}