package com.known.service;

import com.known.common.model.SysRes;
import com.known.common.vo.Tree;
import com.known.exception.BussinessException;

import java.util.List;
import java.util.Set;

public interface SysResService {
	
	List<SysRes> findAllRes();
	
	void deleteSysRes(Integer[] ids)throws BussinessException;
	
	void addSysRes(SysRes sysRes)throws BussinessException;
	
	void updateSysRes(SysRes sysRes) throws BussinessException;
	
	List<SysRes> findAllMenu()throws BussinessException;
	
	List<Tree> findAllTree()throws BussinessException;
	
	List<SysRes> findMenuByRoleIds(Set<Integer> roleIds);
	
	
}
