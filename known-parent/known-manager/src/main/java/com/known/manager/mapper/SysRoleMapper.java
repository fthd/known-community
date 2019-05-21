package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper<T, Q> extends BaseMapper<T, Q> {

	void delete(@Param("ids") Integer[] ids);
	
	List<Integer> selectResourceIdByRoleId(@Param("id") Integer id);
	

}