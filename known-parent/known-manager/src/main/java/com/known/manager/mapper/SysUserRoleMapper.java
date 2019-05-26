package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SysUserRoleMapper<T, Q> extends BaseMapper<T, Q> {
	   void insertBatch(@Param("userId") String userId, @Param("roleIds") String[] roleIds) ;
	   
		Set<String> selectRoleIdsByUserId(@Param("userId") String userId);
}