package com.known.service_impl;

import com.known.common.enums.*;
import com.known.common.model.Attachment;
import com.known.common.model.MessageParams;
import com.known.common.model.Topic;
import com.known.common.model.TopicVote;
import com.known.common.utils.ImageUtils;
import com.known.common.utils.StringUtils;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.TopicMapper;
import com.known.manager.query.TopicQuery;
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
public class TopicServiceImpl implements TopicService {
	@Autowired
	private FormateAtService formateAtService;

	@Autowired
	private TopicMapper<Topic, TopicQuery> topicMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private TopicVoteService topicVoteService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private MessageService messageService;

	public PageResult<Topic> findTopicByPage(TopicQuery topicQuery) {
		int count = this.topicMapper.selectCount(topicQuery);
		int pageSize = PageSize.PAGE_SIZE20.getSize();
		int pageNum = 1;
		if (topicQuery.getPageNum() != 1) {
			pageNum = topicQuery.getPageNum();
		}
		Page page = new Page(pageNum, count, pageSize);
		topicQuery.setPage(page);
		topicQuery.setOrderBy(OrderByEnum.LAST_COMMENT_TIME_DESC_CREATE_TIME_DESC);
		List<Topic> list = this.topicMapper.selectList(topicQuery);
		PageResult<Topic> pageResult = new PageResult<Topic>(page, list);
		return pageResult;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BussinessException.class)
	public void addTopic(Topic topic, TopicVote topicVote, String[] voteTitle,
						 Attachment attachment) throws BussinessException {
		if (topic.getTopicType() == null
				|| topic.getTitle() == null
				|| topic.getTitle().length() > TextLengthEnum.TEXT_200_LENGTH
						.getLength()
				|| topic.getCategoryId() == null
				|| StringUtils.isEmpty(topic.getContent())
				|| topic.getContent().length() > TextLengthEnum.LONGTEXT
						.getLength()) {
			throw new BussinessException("参数错误");
		}
		String title = topic.getTitle();
		topic.setTitle(HtmlUtils.htmlEscape(title));
		String content = topic.getContent();
		String summary = StringUtils.cleanHtmlTag(HtmlUtils.htmlUnescape(content));
		if (summary.length() > TextLengthEnum.TEXT_200_LENGTH.getLength()) {
			summary = summary.substring(0,
					(int) TextLengthEnum.TEXT_200_LENGTH.getLength())
					+ "......";
		}
		Set<Integer> userIds = new HashSet<Integer>();
		String formatContent = formateAtService.generateRefererLinks(content,
				userIds);
		// TODO 给用户发消息
		topic.setSummary(summary);
		topic.setContent(formatContent);
		String topicImage = ImageUtils.getImages(content);
		topic.setTopicImage(topicImage);
		String topicImageSmall = ImageUtils.createThumbnail(topicImage, true);
		topic.setTopicImageThum(topicImageSmall);
		Date curDate = new Date();
		topic.setCreateTime(curDate);
		topic.setLastCommentTime(curDate);
		this.topicMapper.insert(topic);
		this.userService.changeMark(topic.getUserId(),
				MarkEnum.MARK_TOPIC.getMark());
		if (topic.getTopicType() == TopicType.VOTE) {// 判断是否是投票话题
			topicVote.setTopicId(topic.getTopicId());
			this.topicVoteService.addVote(topicVote, voteTitle);
		}
		if(!StringUtils.isEmpty(attachment.getFileName()) &&
				!StringUtils.isEmpty(attachment.getFileUrl())){
			attachment.setTopicId(topic.getTopicId());
			attachment.setFileTopicType(FileTopicType.TOPIC);
			this.attachmentService.addAttachment(attachment);
		}
		
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(topic.getTopicId());
		messageParams.setArticleType(ArticleType.TOPIC);
		messageParams.setArticleUserId(topic.getUserId());
		messageParams.setMessageType(MessageType.AT_ARTICLE_MESSAGE);
		messageParams.setSendUserName(topic.getUserName());
		messageParams.setSendUserId(topic.getUserId());
		messageParams.setReceiveUserIds(userIds);
		messageService.createMessage(messageParams);
	}

	public Topic showTopic(Integer topicId) throws BussinessException {
		Topic topic = this.getTopic(topicId);
		if(topic == null){
			throw new BussinessException("话题不存在或已删除");
		}
		topic.setAttachment(this.attachmentService.getAttachmentByTopicIdAndFileType(topic.getTopicId(), FileTopicType.TOPIC));
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddReadCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(topicId);
		this.topicMapper.updateInfoCount(updateQuery4ArticleCount);
		return topic;
	}

	public Topic getTopic(Integer topicId) {
		if(topicId == null){
			return null;
		}
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setShowContent(Boolean.TRUE);
		topicQuery.setTopicId(topicId);
		List<Topic> list = this.topicMapper.selectList(topicQuery);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	public List<Topic> findActiveUsers() {
		return this.topicMapper.selectActiveUser4Topic();
	}

	public Integer findCount(TopicQuery topicQuery) {
		return this.topicMapper.selectCount(topicQuery);
	}

	@Override
	public List<Topic> findTopicList() {
		TopicQuery topicQuery = new TopicQuery();
		topicQuery.setOrderBy(OrderByEnum.LAST_COMMENT_TIME_DESC_CREATE_TIME_DESC);
		List<Topic> topics = topicMapper.selectList(topicQuery);
		return topics;
	}

	@Override
	public void updateTopicEssence(Integer[] topicId, int essence) throws BussinessException {
		if(topicId == null){
			throw new BussinessException("参数错误");
		}
		
		for(int id : topicId){
			Topic topic = new Topic();
			topic.setTopicId(id);
			topic.setEssence(essence);
			topicMapper.update(topic);
		}
	}

	@Override
	public void updateTopicStick(Integer[] topicId, int stick) throws BussinessException {
		if(topicId == null){
			throw new BussinessException("参数错误");
		}
		
		for(int id : topicId){
			Topic topic = new Topic();
			topic.setTopicId(id);
			topic.setGrade(stick);
			topicMapper.update(topic);
		}
		
	}
	
	@Override
	public void deleteBatch(Integer[] topicIds) throws BussinessException {
		if(topicIds == null){
			throw new BussinessException("参数错误");
		}
		
		topicMapper.delete(topicIds);
	}

}
