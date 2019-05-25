package com.known.service_impl;

import com.known.common.enums.*;
import com.known.common.model.Ask;
import com.known.common.model.Comment;
import com.known.common.model.MessageParams;
import com.known.common.model.User;
import com.known.common.utils.ImageUtil;
import com.known.common.utils.StringUtil;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.AskMapper;
import com.known.manager.query.AskQuery;
import com.known.manager.query.UpdateQuery4ArticleCount;
import com.known.service.AskService;
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
public class AskServiceImpl implements AskService {
	
	@Autowired
	private AskMapper<Ask, AskQuery> askMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private FormateAtService formateAtService;
	
	@Autowired
	private MessageService messageService;
	
	public PageResult<Ask> findAskByPage(AskQuery askQuery) {
		int count = this.findCount(askQuery);
		int pageNum = askQuery.getPageNum() == 1 ? 1 : askQuery.getPageNum();
		int pageSize = PageSizeEnum.PAGE_SIZE20.getSize();
		askQuery.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		Page page = new Page(pageNum, count, pageSize);
		List<Ask> list = this.askMapper.selectList(askQuery);
		return new PageResult<Ask>(page, list);
	}

	public int findCount(AskQuery askQuery) {
		return this.askMapper.selectCount(askQuery);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BussinessException.class)
	public void addAsk(Ask ask) throws BussinessException {
		User user = this.userService.findUserByUserid(ask.getUserId());
		if(user == null || StringUtil.isEmpty(ask.getContent()) || StringUtil.isEmpty(ask.getTitle()) ||
				 ask.getTitle().length() > TextLengthEnum.TEXT_200_LENGTH
				.getLength() || ask.getContent().length() > TextLengthEnum.LONGTEXT.getLength()){
			throw new BussinessException("参数错误");
		}
		if(user.getMark() < ask.getMark()){
			throw new BussinessException("积分不足 " + user.getMark() + " 分");
		}
		String title = ask.getTitle();
		ask.setTitle(HtmlUtils.htmlEscape(title));
		String content = ask.getContent();
		String summary = StringUtil.cleanHtmlTag(HtmlUtils.htmlUnescape(content));
		if (summary.length() > TextLengthEnum.TEXT_200_LENGTH.getLength()) {
			summary = summary.substring(0,
					(int) TextLengthEnum.TEXT_200_LENGTH.getLength())
					+ "......";
		}
		Set<Integer> userIds = new HashSet<Integer>();
		String formatContent = formateAtService.generateRefererLinks(content,
				userIds);
		// TODO 给用户发消息
		ask.setSummary(summary);
		ask.setContent(formatContent);
		String askImage = ImageUtil.getImages(content);
		ask.setAskImage(askImage);
		String askImageSmall = ImageUtil.createThumbnail(askImage, false);
		ask.setAskImageThum(askImageSmall);
		ask.setCreateTime(new Date());
		ask.setSolveType(SolveEnum.WAIT_SOLVE);
		this.askMapper.insert(ask);
		this.userService.changeMark(ask.getUserId(), -ask.getMark());
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(ask.getAskId());
		messageParams.setArticleType(ArticleTypeEnum.Ask);
		messageParams.setArticleUserId(ask.getUserId());
		messageParams.setMessageType(MessageTypeEnum.AT_ARTICLE_MESSAGE);
		messageParams.setSendUserName(ask.getUserName());
		messageParams.setSendUserId(ask.getUserId());
		messageParams.setReceiveUserIds(userIds);
		messageService.createMessage(messageParams);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = BussinessException.class)
	public void setBestAnswer(Integer bestAnswerId, Integer askId,
			Integer userId) throws BussinessException {
		Ask ask = this.getAskById(askId);
		if(ask == null || bestAnswerId == null || userId == null || ask.getSolveType() == SolveEnum.SOLVED){
			throw new BussinessException("参数错误");
		}
		Comment comment = this.commentService.getCommentById(bestAnswerId);
		if(comment == null || comment.getArticleId().intValue() != askId){
			throw new BussinessException("参数错误");
		}
		ask.setBestAnswerId(bestAnswerId);
		ask.setAskId(askId);
		ask.setBestAnswerUserId(comment.getUserId());
		ask.setBestAnswerUserName(comment.getUserName());
		ask.setBestAnswerUserIcon(comment.getUserIcon());
		ask.setSolveType(SolveEnum.SOLVED);
		this.askMapper.updateBestAnswer(ask);
		this.userService.addMark(ask.getMark(), userId);
		//TODO给用户发消息
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(askId);
		messageParams.setArticleType(ArticleTypeEnum.Ask);
		messageParams.setArticleUserId(ask.getUserId());
		messageParams.setMessageType(MessageTypeEnum.ADOPT_ANSWER);
		messageParams.setSendUserName(ask.getUserName());
		messageParams.setSendUserId(userId);
		Set<Integer> userIds = new HashSet<Integer>();
		userIds.add(comment.getUserId());
		messageParams.setReceiveUserIds(userIds);
		messageService.createMessage(messageParams);
	}

	public Ask getAskById(Integer askId) {
		if(askId == null){
			return null;
		}
		AskQuery askQuery = new AskQuery();
		askQuery.setAskId(askId);
		askQuery.setShowContent(Boolean.TRUE);
		List<Ask> list = this.askMapper.selectList(askQuery);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	public Ask showAsk(Integer askId,String flag) throws BussinessException {
		Ask ask = this.getAskById(askId);
		if(ask == null){
			throw new BussinessException("问答不存在或已删除");
		}
		if(ask.getSolveType() == SolveEnum.SOLVED){
			ask.setBestAnswer(this.commentService.getCommentById(ask.getBestAnswerId()));
		}
		if(StringUtil.isEmpty(flag)){
			UpdateQuery4ArticleCount updateQuery4ArticleCount = new UpdateQuery4ArticleCount();
			updateQuery4ArticleCount.setAddReadCount(Boolean.TRUE);
			updateQuery4ArticleCount.setArticleId(ask.getAskId());
			this.askMapper.updateInfoCount(updateQuery4ArticleCount);
		}
		return ask;
	}

	public List<Ask> findTopUsers() {
		AskQuery askQuery = new AskQuery();
		askQuery.setTopCount(10);
		return this.askMapper.selectTopUser(askQuery);
	}

	@Override
	public List<Ask> findAskList() {
		AskQuery askQuery = new AskQuery();
		askQuery.setOrderBy(OrderByEnum.CREATE_TIME_DESC);
		List<Ask> asks = askMapper.selectList(askQuery);
		return asks;
	}
	
	@Override
	public void deleteBatch(Integer[] ids) throws BussinessException {
		if(ids == null){
			throw new BussinessException("参数错误");
		}
		
		for(int id:ids){
			askMapper.delete(id);
		}
	}

}
