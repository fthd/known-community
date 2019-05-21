package com.known.service;

import com.known.common.enums.FileTopicType;
import com.known.common.model.Attachment;
import com.known.common.model.UserRedis;
import com.known.exception.BussinessException;

public interface AttachmentService {
	
	void addAttachment(Attachment attachment) throws BussinessException;
	
	Attachment getAttachmentByTopicIdAndFileType(Integer topicId, FileTopicType fileTopicType);
	
	Attachment getAttachmentById(Integer attachmentId);
	
	Attachment downloadAttachment(UserRedis sessionUser, Integer attachmentId) throws BussinessException;
	
	void checkDownloadPermission(Integer topicUserId, Integer userId, Integer downloadMark,
                                        Integer attachmentId) throws BussinessException;
	
	void checkDownload(Integer attachmentId, Integer topicId, UserRedis sessionUser) throws BussinessException;
	
	void deleteFile(Integer attachmentId);
	
}
