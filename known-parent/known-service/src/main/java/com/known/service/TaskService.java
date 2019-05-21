package com.known.service;


import com.known.common.model.Task;
import com.known.exception.BussinessException;
import com.known.quartz.TaskMessage;

import java.util.List;

public interface TaskService {
		
	List<Task> findTaskList()throws BussinessException;
	
	void deleteTask(Integer[] ids) throws BussinessException;
	
	Task addTask(Task task, boolean isImmediateExcute) throws BussinessException;
	
	void updateTask(Task task, boolean isImmediateExcute) throws BussinessException;
	
	void pauseTask(Integer[] ids) throws BussinessException;
	
	void excuteTask(Integer[] ids) throws BussinessException;
	
	TaskMessage convert2TaskMessage(Task task);
	
}
