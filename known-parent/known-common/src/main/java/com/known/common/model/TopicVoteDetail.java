package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicVoteDetail {
    private Integer id;

    private Integer voteId;

    private Integer topicId = 0;

    private String title;

    private Integer count = 0;
}