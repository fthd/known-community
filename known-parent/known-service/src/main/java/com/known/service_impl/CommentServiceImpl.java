package com.known.service_impl;

import com.known.common.enums.*;
import com.known.common.model.*;
import com.known.common.utils.StringUtil;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.*;
import com.known.manager.query.*;
import com.known.service.CommentService;
import com.known.service.MessageService;
import com.known.service.UserService;
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
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper<Comment, CommentQuery> commentMapper;
	
	@Autowired
	private TopicMapper<Topic, TopicQuery> topicMapper;
	
	@Autowired
	private com.known.manager.mapper.KnowledgeMapper<Knowledge, KnowledgeQuery> KnowledgeMapper;
	
	@Autowired
	private AskMapper<Ask, AskQuery> askMapper;
	
	@Autowired
	private FormateAtService formateAtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	public PageResult<Comment> findCommentByPage(CommentQuery commentQuery) throws BussinessException {
		if(StringUtil.isEmpty(commentQuery.getArticleId()) ||commentQuery.getArticleType() == null) {
			throw new BussinessException("加载评论出错");
		}
		commentQuery.setPid("");
		int pageNum = commentQuery.getPageNum() == 1 ? 1 : commentQuery.getPageNum();
		int pageSize = PageSizeEnum.PAGE_SIZE10.getSize();
		int count = commentMapper.selectCount(commentQuery);
		Page page = new Page(pageNum, count, pageSize);
		commentQuery.setPage(page);
		List<Comment> list = commentMapper.selectList(commentQuery);
		return new PageResult<>(page, list);
	}

	public Comment getCommentById(String commentId) {
		CommentQuery commentQuery = new CommentQuery();
		commentQuery.setCommentId(commentId);
		List<Comment> list = commentMapper.selectList(commentQuery);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void addComment(Comment comment) throws BussinessException {
		String content = comment.getContent();
		content = StringUtil.replaceLast(content.replaceFirst("<p>", ""), "</p>", "");
		if(StringUtil.isEmpty(content) || content.length() > TextLengthEnum.LONGTEXT.getLength()
				||  StringUtil.isEmpty(comment.getArticleId()) || comment.getArticleType() == null){
			throw new BussinessException("参数错误");
		}
		String pid = comment.getPid();
		pid = pid == null ? "" : pid;
		if(!pid.equals("")){
			content = StringUtil.addLink(content);//给网页加链接
			content = HtmlUtils.htmlEscape(content);
		}
		if(!pid.equals("") && content.length() > TextLengthEnum.TEXT_500_LENGTH.getLength()){
			throw new BussinessException("参数错误");
		}
		
		Set<String> userIds = new HashSet<>();
		String formatContent = formateAtService.generateRefererLinks(content, userIds);
		//TODO给用户发消息
		comment.setContent(formatContent);
		comment.setCreateTime(new Date());
		commentMapper.insert(comment);
		UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
		updateQuery4ArticleCount.setAddCommentCount(Boolean.TRUE);
		updateQuery4ArticleCount.setArticleId(comment.getArticleId());
		String articleUserId = null;
		if(comment.getArticleType() == ArticleTypeEnum.TOPIC){
			topicMapper.updateInfoCount(updateQuery4ArticleCount);
			TopicQuery topicQuery = new TopicQuery();
			topicQuery.setTopicId(comment.getArticleId());
			articleUserId = topicMapper.selectList(topicQuery).get(0).getUserId();
		}
		else if(comment.getArticleType() == ArticleTypeEnum.KNOWLEDGE){
			KnowledgeMapper.updateInfoCount(updateQuery4ArticleCount);
			KnowledgeQuery knowledgeQuery = new KnowledgeQuery();
			knowledgeQuery.setKnowledgeId(comment.getArticleId());
			articleUserId = KnowledgeMapper.selectList(knowledgeQuery).get(0).getUserId();
		}
		else if(comment.getArticleType() == ArticleTypeEnum.Ask){
			askMapper.updateInfoCount(updateQuery4ArticleCount);
			AskQuery askQuery = new AskQuery();
			askQuery.setAskId(comment.getArticleId());
			articleUserId = askMapper.selectList(askQuery).get(0).getUserId();
		}
		else{
			throw new BussinessException("参数错误");
		}
		userService.changeMark(comment.getUserId(), MarkEnum.MARK_COMMENT.getMark());
		if(pid.equals("")){
			userIds.add(articleUserId);
		} else{
			Comment comment2 = getCommentById(pid);
			userIds.add(comment2.getUserId());
		}
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(comment.getArticleId());
		messageParams.setArticleType(comment.getArticleType());
		messageParams.setArticleUserId(articleUserId);
		messageParams.setMessageType(MessageTypeEnum.COMMENT_MESSAGE);
		messageParams.setSendUserName(comment.getUserName());
		messageParams.setSendUserId(comment.getUserId());
		messageParams.setReceiveUserIds(userIds);
		messageParams.setCommentId(comment.getId());
		messageParams.setPageNum(comment.getPageNum());
		messageService.createMessage(messageParams);
	}

}
