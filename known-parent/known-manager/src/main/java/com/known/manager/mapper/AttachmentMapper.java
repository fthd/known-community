package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentMapper<T, Q> extends BaseMapper<T, Q> {
	void updateDownloadCount(@Param("attachmentId") Integer attachmentId);
	
	void delete(@Param("attachmentId") Integer attachmentId);
}