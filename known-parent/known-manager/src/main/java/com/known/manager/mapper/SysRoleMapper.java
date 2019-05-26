package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * 系统角色权限
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 20:30
 */
@Repository
public interface SysRoleMapper<T, Q> extends BaseMapper<T, Q> {

	void delete(@Param("ids") String[] ids);
	
	List<String> selectResourceIdByRoleId(@Param("id") String id);
	

}