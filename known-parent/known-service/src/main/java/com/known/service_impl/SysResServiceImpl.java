package com.known.service_impl;

import com.known.common.model.SysRes;
import com.known.common.utils.CollectionUtil;
import com.known.common.utils.StringUtil;
import com.known.common.vo.State;
import com.known.common.vo.Tree;
import com.known.exception.BussinessException;
import com.known.manager.mapper.SysResMapper;
import com.known.manager.query.SysResQuery;
import com.known.service.SysResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class SysResServiceImpl implements SysResService {

	@Autowired
	private SysResMapper<SysRes, SysResQuery> sysResMapper;
	
	@Override
	public List<SysRes> findAllRes() {
		return this.sysResMapper.selectList(new SysResQuery());
	}

	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor= BussinessException.class)
	public void deleteSysRes(Integer[] ids) throws BussinessException {
		if(ids == null || ids.length == 0){
			throw new BussinessException("参数错误");
		}
		
		sysResMapper.deletePermission(ids);
		sysResMapper.delete(ids);
	}

	@Override
	public void addSysRes(SysRes sysRes) throws BussinessException {
		if(StringUtil.isEmpty(sysRes.getName()) || sysRes.getPid() == null
				|| StringUtil.isEmpty(sysRes.getUrl()) || sysRes.getType() == null
				|| sysRes.getEnabled() == null || StringUtil.isEmpty(sysRes.getKey())){
			throw new BussinessException("参数错误");
		}
		
		sysRes.setModifydate(new Date());
		
		sysResMapper.insert(sysRes);
	}

	
	@Override
	public void updateSysRes(SysRes sysRes) throws BussinessException {
		if(sysRes.getId() == null || StringUtil.isEmpty(sysRes.getName()) ||
				sysRes.getPid() == null || StringUtil.isEmpty(sysRes.getUrl())
				|| sysRes.getType() == null || sysRes.getEnabled() == null
				|| StringUtil.isEmpty(sysRes.getKey())){
				throw new BussinessException("参数错误");
		}
		
		sysRes.setModifydate(new Date());
		
		sysResMapper.update(sysRes);
	}

	@Override
	public List<SysRes> findAllMenu() throws BussinessException {
		SysResQuery sysResQuery = new SysResQuery();
		sysResQuery.setType(1);
	    return sysResMapper.selectList(sysResQuery);
	}

	@Override
	public List<Tree> findAllTree() throws BussinessException {
		List<Tree> trees = new ArrayList<>();
		
		SysResQuery sysResQuery = new SysResQuery();
		sysResQuery.setPid(0);
		sysResQuery.setEnabled(1);
		
		List<SysRes> mainmenu = sysResMapper.selectList(sysResQuery);
		
		for(SysRes sysRes : mainmenu){
			Tree tree = new Tree();
			tree.setId(sysRes.getId());
			tree.setText(sysRes.getName());
			State state = new State(true, false);
			tree.setState(state);
		
			List<Tree> firstChildren = new ArrayList<>();
			sysResQuery = new SysResQuery();
			sysResQuery.setPid(sysRes.getId());
			sysResQuery.setEnabled(1);
			List<SysRes> childrenMenu = sysResMapper.selectList(sysResQuery);
			
			for(SysRes childrenSysRes : childrenMenu){
				Tree childtree = new Tree();
				childtree.setId(childrenSysRes.getId());
				childtree.setText(childrenSysRes.getName());
				state = new State(true, false);
				childtree.setState(state);
				
				List<Tree> secondChildren = new ArrayList<>();
				sysResQuery = new SysResQuery();
				sysResQuery.setPid(childrenSysRes.getId());
				sysResQuery.setEnabled(1);
				List<SysRes> secondChildrenMenu = sysResMapper.selectList(sysResQuery);
				
				for(SysRes secondChildRes : secondChildrenMenu){
					Tree secondTree = new Tree();
					secondTree.setId(secondChildRes.getId());
					secondTree.setText(secondChildRes.getName());
					state = new State(false, false);
					secondTree.setState(state);
					secondChildren.add(secondTree);
					
					childtree.setChildren(secondChildren);
				}
				
				firstChildren.add(childtree);
			}
			
			tree.setChildren(firstChildren);
			trees.add(tree);
		}
		
		return trees;
	}
	
	
	@Override
	public List<SysRes> findLimitByRoleIds(Set<Integer> roleIds, Integer type) {
		if(roleIds.isEmpty()){
			return null;
		}
		List<SysRes> list = sysResMapper.selectLimitByRoleIds(roleIds, type);
		List<SysRes> listResult = (List<SysRes>) CollectionUtil.removeNullValue(list);
		return listResult;
	}

}
