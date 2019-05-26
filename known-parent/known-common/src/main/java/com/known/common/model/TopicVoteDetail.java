package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicVoteDetail {
    private String id;

    private String voteId;

    private String topicId;

    private String title;

    private Integer count = 0;
}