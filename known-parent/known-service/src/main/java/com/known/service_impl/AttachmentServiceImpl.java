package com.known.service_impl;

import com.known.common.enums.FileArticleTypeEnum;
import com.known.common.model.*;
import com.known.common.utils.StringUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.AttachmentDownloadMapper;
import com.known.manager.mapper.AttachmentMapper;
import com.known.manager.query.AttachmentDownloadQuery;
import com.known.manager.query.AttachmentQuery;
import com.known.service.AttachmentService;
import com.known.service.TopicService;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttachmentServiceImpl implements AttachmentService {
	
	@Autowired
	private AttachmentMapper<Attachment, AttachmentQuery> attachmentMapper;
	
	@Autowired
	private AttachmentDownloadMapper<AttachmentDownload, AttachmentDownloadQuery> attachmentDownloadMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TopicService topicService;
	
	public void addAttachment(Attachment attachment) throws BussinessException {
		final int MAXMARK = 10000;
		Integer mark = attachment.getDownloadMark();
		mark = mark == null ? 0 : mark;
		attachment.setDownloadMark(mark);
		if(StringUtil.isEmpty(attachment.getFileName()) ||
			StringUtil.isEmpty(attachment.getFileUrl()) ||
			mark > MAXMARK
				){
			throw new BussinessException("参数错误");
		}
		attachmentMapper.insert(attachment);
	}

	public Attachment getAttachmentByTopicIdAndFileType(String topicId,
			FileArticleTypeEnum fileArticleType) {
		AttachmentQuery attachmentQuery = new AttachmentQuery();
		attachmentQuery.setArticleId(topicId);
		attachmentQuery.setFileArticleType(fileArticleType);
		List<Attachment> attachments = attachmentMapper.selectList(attachmentQuery);
		if(attachments.isEmpty()){
			return null;
		}
		return attachments.get(0);
	}

	public Attachment getAttachmentById(String attachmentId) {
		if(attachmentId == null){
			return null;
		}
		AttachmentQuery attachmentQuery = new AttachmentQuery();
		attachmentQuery.setAttachmentId(attachmentId);
		List<Attachment> attachmentList = attachmentMapper.selectList(attachmentQuery);
		if(attachmentList.isEmpty()){
			return null;
		}
		return attachmentList.get(0);
	}

	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public Attachment downloadAttachment(SessionUser sessionUser,
										 String attachmentId) throws BussinessException {
		Attachment attachment = getAttachmentById(attachmentId);
		if(attachment == null){
			throw new BussinessException("附件不存在");
		}
		String topicId = attachment.getArticleId();
		Topic topic = null;
		FileArticleTypeEnum fileArticleType = attachment.getFileArticleType();
		if(fileArticleType == FileArticleTypeEnum.TOPIC){
			topic = topicService.getTopic(topicId);
			if(topic == null){
				throw new BussinessException("附件对应的话题不存在");
			}
			checkDownloadPermission(topic.getUserId(), sessionUser.getUserid(), attachment.getDownloadMark(), attachmentId);
		}
		AttachmentDownload attachmentDownload = new AttachmentDownload();
		attachmentDownload.setAttachmentId(attachmentId);
		attachmentDownload.setUserId(sessionUser.getUserid());
		attachmentDownload.setUserName(sessionUser.getUserName());
		attachmentDownload.setUserIcon(sessionUser.getUserIcon());
		attachmentDownloadMapper.insert(attachmentDownload);
		attachmentMapper.updateDownloadCount(attachmentId);
		return attachment;
	}

	public void checkDownloadPermission(String topicUserId, String userId,
			Integer downloadMark, String attachmentId)
			throws BussinessException {
		if(!topicUserId.equals(userId) && downloadMark > 0){
			AttachmentDownloadQuery attachmentDownloadQuery = new AttachmentDownloadQuery();
			attachmentDownloadQuery.setUserId(userId);
			attachmentDownloadQuery.setAttachmentId(attachmentId);
			int downCount = attachmentDownloadMapper.selectCount(attachmentDownloadQuery);
			if(downCount == 0){
					int count = userService.changeMark(userId, -downloadMark);
					if(count == 0){
						throw new BussinessException("积分不足");
					}else{
						userService.changeMark(topicUserId, downloadMark);
					}
			}
		}
	}

	public void checkDownload(String attachmentId, String topicId,
			SessionUser sessionUser) throws BussinessException {
		if(attachmentId == null || topicId == null){
			throw new BussinessException("参数错误");
		}
		Attachment attachment = getAttachmentById(attachmentId);
		if(attachment == null){
			throw new BussinessException("附件不存在");
		}
		String topicUserId = null;
		FileArticleTypeEnum fileArticleType = attachment.getFileArticleType();
		if(fileArticleType == FileArticleTypeEnum.TOPIC){
			Topic topic = topicService.getTopic(topicId);
			if(topic == null){
				throw new BussinessException("附件对应的话题不存在");
				}
			topicUserId = topic.getUserId();
			}

		AttachmentDownloadQuery attachmentDownloadQuery = new AttachmentDownloadQuery();
		attachmentDownloadQuery.setUserId(sessionUser.getUserid());
		attachmentDownloadQuery.setAttachmentId(attachmentId);
		int downCount = attachmentDownloadMapper.selectCount(attachmentDownloadQuery);
		if(topicUserId.equals(sessionUser.getUserid()) && attachment.getDownloadMark() > 0 && downCount == 0){
			User user = userService.findUserByUserid(sessionUser.getUserid());
			if(user.getMark() < attachment.getDownloadMark()){
				throw new BussinessException("你的积分只有&nbsp;&nbsp;" + user.getMark() + "&nbsp;&nbsp;分, 积分不足");
			}
		}
		
	}
	public void deleteFile(String attachmentId) {
		attachmentMapper.delete(attachmentId);
	}
	
}
