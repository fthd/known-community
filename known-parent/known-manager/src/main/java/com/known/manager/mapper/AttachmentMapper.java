package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentMapper<T, Q> extends BaseMapper<T, Q> {
	void updateDownloadCount(@Param("attachmentId") String attachmentId);
	
	void delete(@Param("attachmentId") String attachmentId);
}