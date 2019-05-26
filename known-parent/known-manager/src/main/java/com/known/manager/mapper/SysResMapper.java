package com.known.manager.mapper;

import com.known.common.model.SysRes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Set;

@Repository
public interface SysResMapper<T, Q> extends BaseMapper<T, Q>{
	
	void delete(@Param("ids") String[] ids);
	
	void  deletePermission(@Param("ids") String[] ids) ;
	
	List<SysRes> selectLimitByRoleIds(@Param("roleIds") Set<String> roleIds, @Param("type") Integer type);
	
}