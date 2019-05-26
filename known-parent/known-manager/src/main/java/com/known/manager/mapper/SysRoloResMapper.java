package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoloResMapper<T, Q> extends BaseMapper<T, Q> {
   void insertBatch(@Param("roleId") String roleId, @Param("resIds") String[] resIds) ;
}