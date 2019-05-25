package com.known.service_impl;


import com.known.common.enums.*;
import com.known.common.model.Attachment;
import com.known.common.model.Knowledge;
import com.known.common.model.MessageParams;
import com.known.common.utils.ImageUtil;
import com.known.common.utils.StringUtil;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.KnowledgeMapper;
import com.known.manager.query.KnowledgeQuery;
import com.known.manager.query.UpdateQuery4ArticleCount;
import com.known.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
	@Autowired
	private KnowledgeMapper<Knowledge, KnowledgeQuery> knowledgeMapper;

	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private FormateAtService formateAtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private SysResService sysResService;
	
	@Autowired
	private SysRoleService sysRoleService;
	
	public PageResult<Knowledge> findKnowledgeByPage(
			KnowledgeQuery knowledgeQuery) {
		knowledgeQuery.setStatus(StatusEnum.AUDIT);
		int count = this.knowledgeMapper.selectCount(knowledgeQuery);
		int pageSize = PageSizeEnum.PAGE_SIZE20.getSize();
		int pageNum = knowledgeQuery.getPageNum() == 1 ? 1 : knowledgeQuery.getPageNum();
		Page page = new Page(pageNum, count, pageSize);
		knowledgeQuery.setPage(page);
		knowledgeQuery.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Knowledge> list = this.knowledgeMapper.selectList(knowledgeQuery);
		PageResult<Knowledge> pageResult = new PageResult<Knowledge>(page, list);
		return pageResult;
	}

	public Knowledge getKnowledge(Integer knowledgeId) {
		if(knowledgeId == null){
			return null;
		}
		KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
		knowledgeQuery.setKnowledgeId(knowledgeId);
		knowledgeQuery.setShowContent(Boolean.TRUE);
		List<Knowledge> list = this.knowledgeMapper.selectList(knowledgeQuery);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	public Knowledge showKnowledge(Integer knowledgeId, Integer userId) throws BussinessException {
		Knowledge knowledge = this.getKnowledge(knowledgeId);
	/*	Set<Integer> roleSet = sysRoleService.findRoleIdsByUserId(userId);
		List<SysRes> list = sysResService.findMenuByRoleIds(roleSet);
		Set<String> permkey = new HashSet<>();
		if(list != null){
			for(SysRes sysRes : list){
				permkey.add(sysRes.getKey());
			}
		}*/
		if(knowledge == null || knowledge.getStatus() == StatusEnum.INIT
				){
			throw new BussinessException("话题不存在,或已删除");
		}
		knowledge.setAttachment(this.attachmentService.getAttachmentByTopicIdAndFileType(knowledge.getKnowledgeId(), FileTopicTypeEnum.KNOWLEDGE));
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddReadCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(knowledgeId);
		this.knowledgeMapper.updateInfoCount(updateQuery4ArticleCount);
		return knowledge;
	}
	
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void addKnowledge(Knowledge knowledge, Attachment attachment) throws BussinessException {
		if(knowledge.getTitle() == null || knowledge.getTitle().length() > TextLengthEnum.TEXT_300_LENGTH.getLength()
				|| knowledge.getpCategoryId() == null || knowledge.getCategoryId() == null ||
				StringUtil.isEmpty(knowledge.getContent()) || knowledge.getContent().length() > TextLengthEnum.LONGTEXT.getLength()
				){
			throw new BussinessException("参数错误");
		}
		String title = HtmlUtils.htmlUnescape(knowledge.getTitle());
		String content = knowledge.getContent();
		String summary = StringUtil.cleanHtmlTag(HtmlUtils.htmlUnescape(content));
		if (summary.length() > TextLengthEnum.TEXT_200_LENGTH.getLength()) {
			summary = summary.substring(0,
					(int) TextLengthEnum.TEXT_200_LENGTH.getLength())
					+ "......";
		}
		Set<Integer> userIds = new HashSet<>();
		//TODO给用户发消息
		String formatContent = formateAtService.generateRefererLinks(content,
				userIds);
		knowledge.setSummary(summary);
		knowledge.setTitle(title);
		knowledge.setContent(formatContent);
		String knowledgeImage = ImageUtil.getImages(content);
		knowledge.setKnowledgeImage(knowledgeImage);
//		String knowledgeImageSmall = ImageUtil.createThumbnail(knowledgeImage, true);
//		knowledge.setKnowledgeImageThum(knowledgeImageSmall);
		Date curDate = new Date();
		knowledge.setCreateTime(curDate);
		knowledge.setLastCommentTime(curDate);
		knowledge.setStatus(StatusEnum.INIT);
		this.knowledgeMapper.insert(knowledge);
		this.userService.changeMark(knowledge.getUserId(),
				MarkEnum.MARK_TOPIC.getMark());
		
		if(!StringUtil.isEmpty(attachment.getFileName()) &&
				!StringUtil.isEmpty(attachment.getFileUrl())){
			attachment.setArticleId(knowledge.getKnowledgeId());
			attachment.setFileTopicType(FileTopicTypeEnum.KNOWLEDGE);
			this.attachmentService.addAttachment(attachment);
		}
		
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(knowledge.getKnowledgeId());
		messageParams.setArticleType(ArticleTypeEnum.KNOWLEDGE);
		messageParams.setArticleUserId(knowledge.getUserId());
		messageParams.setMessageType(MessageTypeEnum.AT_ARTICLE_MESSAGE);
		messageParams.setSendUserName(knowledge.getUserName());
		messageParams.setSendUserId(knowledge.getUserId());
		messageParams.setReceiveUserIds(userIds);
		messageService.createMessage(messageParams);
	}
	
	@Override
	public List<Knowledge> findKnowledgeList() {
		KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
		knowledgeQuery.setStatus(StatusEnum.INIT);
		knowledgeQuery.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		return knowledgeMapper.selectList(knowledgeQuery);
	}

	@Override
	public void deleteBatch(Integer[] ids) throws BussinessException {
		if(ids == null){
			throw new BussinessException("参数错误");
		}		
		for(int id : ids){
			knowledgeMapper.delete(id);
		}
	}

	@Override
	public void updateStatusBatch(Integer[] ids) throws BussinessException {
		if(ids == null){
			throw new BussinessException("参数错误");
		}		
		
		knowledgeMapper.updateKnowledgeStatus(StatusEnum.AUDIT, ids);
	}

}
