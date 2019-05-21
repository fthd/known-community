package com.known.service;

import com.known.common.model.SysRole;
import com.known.exception.BussinessException;

import java.util.List;
import java.util.Set;

public interface SysRoleService {
	
	List<SysRole> findSysRoleList();
	
	void deleteSysRole(Integer[] ids)throws BussinessException;
	
	void addSysRole(SysRole sysRole) throws BussinessException;
	
	void updateSysRole(SysRole sysRole) throws BussinessException;
	
	SysRole findSysRoleById(Integer id) throws BussinessException;
	
	List<Integer> findResourceIdByRoleId(Integer id)throws BussinessException;
	
	void updateAuthority(Integer roleId, Integer[] resIds) throws BussinessException;
	
	Set<Integer> findRoleIdsByUserId(Integer userId);
	
}
