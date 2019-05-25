package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.FileTopicTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentQuery extends BaseQuery {

	private Integer attachmentId;

    private Integer topicId;
    
    private FileTopicTypeEnum fileTopicType;

    
}
