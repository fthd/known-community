package com.known.manager.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.known.common.enums.FileTopicTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentQuery extends BaseQuery {

	private String attachmentId;

    private String topicId;
    
    private FileTopicTypeEnum fileTopicType;

    
}
