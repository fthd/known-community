package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.VoteTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicVote {
    private Integer voteId;

    private Integer topicId;

    private VoteTypeEnum voteType;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date endDate;
    
    private String endDateString;
    
    private int sumCount;
    
	private List<TopicVoteDetail> topicVoteDetailList = new ArrayList<TopicVoteDetail>();
	
	private List<TopicVoteUser> topicVoteUserList = new ArrayList<TopicVoteUser>();
	
	private boolean canVote;
 
}