package com.known.service;

import com.known.common.enums.FileArticleTypeEnum;
import com.known.common.model.Attachment;
import com.known.common.model.SessionUser;
import com.known.exception.BussinessException;

public interface AttachmentService {
	
	void addAttachment(Attachment attachment) throws BussinessException;
	
	Attachment getAttachmentByTopicIdAndFileType(String topicId, FileArticleTypeEnum fileArticleType);
	
	Attachment getAttachmentById(String attachmentId);
	
	Attachment downloadAttachment(SessionUser sessionUser, String attachmentId) throws BussinessException;
	
	void checkDownloadPermission(String topicUserId, String userId, Integer downloadMark,
                                        String attachmentId) throws BussinessException;
	
	void checkDownload(String attachmentId, String topicId, SessionUser sessionUser) throws BussinessException;
	
	void deleteFile(String attachmentId);
	
}
