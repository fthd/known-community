package com.known.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentDownload{

	 private String attachmentId;

	 private String userId;
	 
    private String userIcon;

    private String userName;

}