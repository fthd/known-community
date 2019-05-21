package com.known.manager.mapper;

import com.known.common.enums.StatusEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamMapper<T, Q> extends BaseMapper<T, Q> {
	List<T> selectExamRand(Q q);
	
	List<T> selectListWithRightAnswer(Q q);
	
	List<T> selectExamUsers(Q q);
	
	int selectExamUsersCount(Q q);
	
	void updateExamStatus(@Param("status") StatusEnum status, @Param("ids") Integer[] ids);
	
	void delete(@Param("id") Integer id);
	
}