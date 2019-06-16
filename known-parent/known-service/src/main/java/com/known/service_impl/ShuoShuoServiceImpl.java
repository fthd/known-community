package com.known.service_impl;

import com.known.common.enums.*;
import com.known.common.model.MessageParams;
import com.known.common.model.ShuoShuo;
import com.known.common.model.ShuoShuoComment;
import com.known.common.model.ShuoShuoLike;
import com.known.common.utils.ImageUtil;
import com.known.common.utils.StringUtil;
import com.known.common.utils.UUIDUtil;
import com.known.common.vo.Page;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.mapper.ShuoShuoCommentMapper;
import com.known.manager.mapper.ShuoShuoLikeMapper;
import com.known.manager.mapper.ShuoShuoMapper;
import com.known.manager.query.ShuoShuoQuery;
import com.known.service.MessageService;
import com.known.service.ShuoShuoService;
import com.known.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ShuoShuoServiceImpl implements ShuoShuoService {
	@Autowired
	private ShuoShuoMapper<ShuoShuo, ShuoShuoQuery> shuoShuoMapper;
	
	@Autowired
	private ShuoShuoCommentMapper<ShuoShuoComment, ShuoShuoQuery> shuoShuoCommentMapper;
	
	@Autowired
	private ShuoShuoLikeMapper<ShuoShuoLike, ShuoShuoQuery> shuoShuoLikeMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FormateAtService formateAtService;

	@Autowired
	private MessageService messageService;
	
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void addShuoShuo(ShuoShuo shuoShuo) throws BussinessException {
		shuoShuo.setId(UUIDUtil.getUUID());
		String content = shuoShuo.getContent();
		if(StringUtil.isEmpty(content) || content.length() > TextLengthEnum.TEXT.getLength()){
			throw new BussinessException("参数错误");
		}
		content = StringUtil.addLink(content);//给网页加链接
		Set<String> userIdSet = new HashSet<>();
		String formatContent = formateAtService.generateRefererLinks(content, userIdSet);
		//TODO给用户发消息
		shuoShuo.setImageUrlSmall(shuoShuo.getImageUrl());
		shuoShuo.setContent(formatContent);
		shuoShuo.setCreateTime(new Date());
		shuoShuo.setLikeCount(0);
		shuoShuo.setCommentCount(0);
		shuoShuoMapper.insert(shuoShuo);
		userService.addMark(MarkEnum.MARK_SHUOSHUO.getMark(), shuoShuo.getUserId());
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(shuoShuo.getId());
		messageParams.setArticleType(ArticleTypeEnum.SHUOSHUO);
		messageParams.setArticleUserId(shuoShuo.getUserId());
		messageParams.setMessageType(MessageTypeEnum.AT_ARTICLE_MESSAGE);
		messageParams.setSendUserName(shuoShuo.getUserName());
		messageParams.setSendUserId(shuoShuo.getUserId());
		messageParams.setReceiveUserIds(userIdSet);
		messageService.createMessage(messageParams);
	}

	public ShuoShuo findShuoShuo(ShuoShuoQuery shuoShuoQuery) {
		List<ShuoShuo> list =  shuoShuoMapper.selectList(shuoShuoQuery);
		if(list.isEmpty()){
			return null;
		}
		return list.get(0);
	}

	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void addShuoShuoComment(ShuoShuoComment shuoShuoComment)
			throws BussinessException {
		shuoShuoComment.setId(UUIDUtil.getUUID());
		String content = shuoShuoComment.getContent();
		if(StringUtil.isEmpty(content) || content.length() > TextLengthEnum.TEXT.getLength()){
			throw new BussinessException("参数错误");
		}
		content = StringUtil.addLink(content);//给网页加链接
		Set<String> userIdSet = new HashSet<>();
		String formatContent = formateAtService.generateRefererLinks(content, userIdSet);
		//TODO给用户发消息
		shuoShuoComment.setContent(formatContent);
		shuoShuoComment.setCreateTime(new Date());

		// 更新说说评论数量
		shuoShuoMapper.updateShuoShuoCommentCount(shuoShuoComment.getShuoShuoId());
		// 插入说说评论信息
		shuoShuoCommentMapper.insert(shuoShuoComment);
		// 修改用户积分
		userService.addMark(MarkEnum.MARK_COMMENT.getMark(), shuoShuoComment.getUserId());	
		
		String  shuoShuoId= shuoShuoComment.getShuoShuoId();
		MessageParams messageParams = new MessageParams();
		messageParams.setArticleId(shuoShuoId);
		messageParams.setArticleType(ArticleTypeEnum.SHUOSHUO);
		ShuoShuoQuery shuoShuoQuery = new ShuoShuoQuery();
		shuoShuoQuery.setShuoShuoId(shuoShuoId);
		ShuoShuo ss = findShuoShuo(shuoShuoQuery);
		messageParams.setArticleUserId(ss.getUserId());
		messageParams.setMessageType(MessageTypeEnum.COMMENT_MESSAGE);
		messageParams.setSendUserName(shuoShuoComment.getUserName());
		messageParams.setSendUserId(shuoShuoComment.getUserId());
		userIdSet.add(ss.getUserId());
		messageParams.setReceiveUserIds(userIdSet);
		messageParams.setCommentId(shuoShuoComment.getId());
		messageService.createMessage(messageParams);
	}

	public List<ShuoShuoComment> loadShuoShuoComment(Integer shuoShuoId) {
		// TODO 用来加载说说的评论
		return null;
	}

	public List<ShuoShuo> findActiveUser4ShuoShuo() {
		// TODO 加载活跃用户
		return null;
	}

	public PageResult<ShuoShuo> findShuoShuoList(ShuoShuoQuery shuoShuoQuery) {
		int count = shuoShuoMapper.selectCount(shuoShuoQuery);
		int size = PageSizeEnum.PAGE_SIZE10.getSize();
		int pageNum = 1;
		if(shuoShuoQuery.getPageNum() != 1){
			pageNum = shuoShuoQuery.getPageNum();
		}
		Page page = new Page(pageNum, count, size);
		shuoShuoQuery.setPage(page);
		List<ShuoShuo> shuoShuos = shuoShuoMapper.selectList(shuoShuoQuery);

		return new PageResult<>(page, shuoShuos);
	}
	
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void doShuoShuoLike(ShuoShuoLike shuoShuoLike) throws BussinessException {
		ShuoShuoQuery shuoShuoQuery = new ShuoShuoQuery();
		shuoShuoQuery.setUserId(shuoShuoLike.getUserId());
		shuoShuoQuery.setShuoShuoId(shuoShuoLike.getShuoShuoId());
		if(findShuoShuoLike(shuoShuoQuery).size() >= 1){
			throw new BussinessException("您已经点过赞了");
		}
		shuoShuoLike.setId(UUIDUtil.getUUID());
		shuoShuoLike.setCreateTime(new Date());
		shuoShuoMapper.updateShuoShuoLikeCount(shuoShuoLike.getShuoShuoId());
		shuoShuoLikeMapper.insert(shuoShuoLike);
	}

	public List<ShuoShuoLike> findShuoShuoLike(ShuoShuoQuery shuoShuoQuery) {
		List<ShuoShuoLike> shuoShuoLikeList = shuoShuoLikeMapper.selectList(shuoShuoQuery);
		System.out.println(shuoShuoLikeList);
		return shuoShuoLikeList;
	}

	@Override
	public List<ShuoShuo> findShuoshuos() {
		List<ShuoShuo> shuoShuos = shuoShuoMapper.selectList(null);
		return shuoShuos;
	}

	@Override
	public void deleteBatch(String[] ids) throws BussinessException {
		if(ids == null){
			throw new BussinessException("参数错误");
		}
		
		for(String id : ids){
			shuoShuoMapper.delete(id);
		}
	}

}
