package com.known.service_impl;

import com.known.common.model.SysRole;
import com.known.common.model.SysRoleRes;
import com.known.common.model.SysUserRole;
import com.known.common.utils.StringUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.SysRoleMapper;
import com.known.manager.mapper.SysRoleResMapper;
import com.known.manager.mapper.SysUserRoleMapper;
import com.known.manager.query.SysRoleQuery;
import com.known.manager.query.SysRoleResQuery;
import com.known.manager.query.SysUserRoleQuery;
import com.known.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleMapper<SysRole, SysRoleQuery> sysRoleMapper;
	
	@Autowired
	private SysRoleResMapper<SysRoleRes, SysRoleResQuery> sysRoleResMapper;
	
	@Autowired
	private SysUserRoleMapper<SysUserRole, SysUserRoleQuery> sysUserRoleMapper;

	@Override
	public List<SysRole> findSysRoleList() {
		SysRoleQuery sysRoleQuery = new SysRoleQuery();
		return sysRoleMapper.selectList(sysRoleQuery);
	}

	@Override
	public void deleteSysRole(String[] ids) throws BussinessException {
		if (ids == null || ids.length == 0) {
			throw new BussinessException("参数错误");
		}
		sysRoleMapper.delete(ids);
	}

	@Override
	public void addSysRole(SysRole sysRole) throws BussinessException {
		if(sysRole == null || StringUtil.isEmpty(sysRole.getName())
				|| sysRole.getSeq() == null || sysRole.getStatus() == null
				){
			throw new BussinessException("参数错误");
		}
		
		sysRoleMapper.insert(sysRole);
	}

	@Override
	public void updateSysRole(SysRole sysRole) throws BussinessException {
		if(sysRole == null || StringUtil.isEmpty(sysRole.getName())
				|| sysRole.getSeq() == null || sysRole.getStatus() == null
				){
			throw new BussinessException("参数错误");
		}
		
		if(findSysRoleById(sysRole.getId()) == null){
			throw new BussinessException("角色不存在");
		}
		sysRoleMapper.update(sysRole);
	}

	@Override
	public SysRole findSysRoleById(String id) throws BussinessException {
		if(id == null){
			throw new BussinessException("参数错误");
		}
		SysRoleQuery sysRoleQuery = new SysRoleQuery();
		sysRoleQuery.setId(id);
		List<SysRole> list = sysRoleMapper.selectList(sysRoleQuery);
		if(!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List findResourceIdByRoleId(String id) throws BussinessException {
		if(id == null){
			throw new BussinessException("参数错误");
		}
		List<String> list = sysRoleMapper.selectResourceIdByRoleId(id);
		return list;
	}
	
	@Override
	@Transactional(propagation= Propagation.REQUIRES_NEW, rollbackFor=BussinessException.class)
	public void updateAuthority(String roleId, String[] resIds) throws BussinessException {
		if(roleId == null){
			throw new BussinessException("参数错误");
		}
		
		SysRoleRes sysRoleRes = new SysRoleRes();
		sysRoleRes.setRoleId(roleId);
		sysRoleResMapper.delete(sysRoleRes);
		
		if(resIds!=null && resIds.length != 0){
			sysRoleResMapper.insertBatch(roleId, resIds);
		}
	}

	@Override
	public Set findRoleIdsByUserId(String userId) {
		return userId != null ? sysUserRoleMapper.selectRoleIdsByUserId(userId) :  null;
	}
}
