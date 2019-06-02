package com.known.service;

import com.known.common.model.SysRole;
import com.known.exception.BussinessException;

import java.util.List;
import java.util.Set;

public interface SysRoleService {
	
	List<SysRole> findSysRoleList();
	
	void deleteSysRole(String[] ids)throws BussinessException;
	
	void addSysRole(SysRole sysRole) throws BussinessException;
	
	void updateSysRole(SysRole sysRole) throws BussinessException;
	
	SysRole findSysRoleById(String id) throws BussinessException;
	
	List<Integer> findResourceIdByRoleId(String id)throws BussinessException;
	
	void updateAuthority(String roleId, String[] resIds) throws BussinessException;
	
	Set<String> findRoleIdsByUserId(String userId);
	
}
