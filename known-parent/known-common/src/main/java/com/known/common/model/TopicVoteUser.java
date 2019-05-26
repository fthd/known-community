package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicVoteUser {
    private String voteDetailId;

    private String userId;

    private Date voteDate;
    
    private String title;

}