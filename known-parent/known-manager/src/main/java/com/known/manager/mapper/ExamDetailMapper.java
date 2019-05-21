package com.known.manager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamDetailMapper<T, Q> extends BaseMapper<T, Q> {
    List<T> selectListWithRightAnswer(@Param("examId") Integer examId);
    
    void insertBatch(@Param("list") List<T> list);
    
    void delete(@Param("examId") Integer examId);
}