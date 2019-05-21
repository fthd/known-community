package com.known.manager.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginLogMapper<T,Q> extends BaseMapper<T, Q> {
   
	List<T> selectListGroupByIp(Q q);
}