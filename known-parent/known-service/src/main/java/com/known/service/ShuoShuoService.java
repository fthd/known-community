package com.known.service;

import com.known.common.model.ShuoShuo;
import com.known.common.model.ShuoShuoComment;
import com.known.common.model.ShuoShuoLike;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.ShuoShuoQuery;

import java.util.List;

public interface ShuoShuoService {
	
	/**
	 * 添加说说
	 */
	void addShuoShuo(ShuoShuo shuoShuo)throws BussinessException;
	
	
	/**
	 * 查找说说
	 */
	ShuoShuo findShuoShuo(ShuoShuoQuery shuoShuoQuery);
	/**
	 * 分页搜索说说
	 * @param shuoShuoQuery
	 * @return
	 */
	PageResult<ShuoShuo> findShuoShuoList(ShuoShuoQuery shuoShuoQuery);
	
	/**
	 * 添加评论
	 * @param shuoShuoComment
	 * @throws BussinessException
	 */
	void addShuoShuoComment(ShuoShuoComment shuoShuoComment)throws BussinessException;
	
	/**
	 * 加载说说评论
	 * @param shuoShuoId
	 * @return
	 */
	List<ShuoShuoComment> loadShuoShuoComment(Integer shuoShuoId);
	
	/**
	 * 查找活跃用户
	 * @return
	 */
	List<ShuoShuo> findActiveUser4ShuoShuo();
	
	/**
	 *点赞
	 * @throws BussinessException
	 */
	void doShuoShuoLike(ShuoShuoLike shuoShuoLike)throws BussinessException;
	
	/**
	 * 查找说说的点赞
	 * @param shuoShuoLike
	 */
	List<ShuoShuoLike> findShuoShuoLike(ShuoShuoQuery shuoShuoQuery);
	
	
	List<ShuoShuo> findShuoshuos();
	
	void deleteBatch(Integer[] ids) throws BussinessException;
	
 }
