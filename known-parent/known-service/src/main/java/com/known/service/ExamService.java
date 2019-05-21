package com.known.service;

import com.known.common.model.Exam;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.ExamQuery;

import java.util.List;

public interface ExamService {
	List<Exam> findExamListRand(Integer categoryId);
	
	void saveExam(Exam exam, String[] answer, Integer[] rightAnswer)throws BussinessException;
	
	List<Exam> doMark(String examIds, String rightAnswers)throws BussinessException;
	
	PageResult<Exam> findExamUsers(ExamQuery examQuery);
	
	List<Exam> findExamWithRightAnswer();
	
	void deleteBatch(Integer[] ids)throws BussinessException;
	
	void updateStatusBatch(Integer[] ids) throws BussinessException;
}
