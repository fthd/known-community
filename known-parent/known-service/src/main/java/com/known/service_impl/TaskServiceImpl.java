package com.known.service_impl;

import com.known.common.model.Task;
import com.known.common.utils.StringUtil;
import com.known.common.utils.UUIDUtil;
import com.known.exception.BussinessException;
import com.known.manager.mapper.TaskMapper;
import com.known.manager.query.TaskQuery;
import com.known.quartz.TaskMessage;
import com.known.quartz.trigger.CronTriggerManager;
import com.known.service.TaskService;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private CronTriggerManager cronTriggerManager;

	@Autowired
	private TaskMapper<Task, TaskQuery> taskMapper;

	@Override
	public List<Task> findTaskList() throws BussinessException {
		return taskMapper.selectList(null);
	}

	@Override
	public void deleteTask(String[] ids) throws BussinessException {
		if(ids == null || ids.length == 0){
			throw new BussinessException("参数错误");
		}

		taskMapper.delete(ids);
		for(String id : ids){
			Task task = findTaskById(id);
			TaskMessage taskMessage = convert2TaskMessage(task);
			try {
				cronTriggerManager.delJob(taskMessage);
			} catch (SchedulerException e) {
				throw new BussinessException("删除任务失败，请重试");
			}
		}
	}

public static void main(String[] args) throws UnsupportedEncodingException {

		System.out.println(CronExpression.isValidExpression("0 0/5 * * * ?"));
		System.out.println("0 0/5 * * * ?".equals("0 0/5 * * * ?".trim()));
		System.out.println(new String("0 0/5 * * * ?".getBytes("UTF-8"), "UTF-8"));
  		System.out.println(CronExpression.isValidExpression(new String("0 0/5 * * * ?".getBytes("UTF-8"), "ISO8859-1")));
}

	@Override
	public Task addTask(Task task, boolean isImmediateExcute) throws BussinessException, UnsupportedEncodingException {
		if(null == task){
			throw new BussinessException("参数错误");
		}

		task.setId(UUIDUtil.getUUID());
		task.setLastUpdateTime(new Date());
		if(StringUtil.isEmpty(task.getTaskClassz()) || StringUtil.isEmpty(task.getTaskMethod())
				|| StringUtil.isEmpty(task.getTaskTime())
				) {
			throw new BussinessException("参数错误");
		}

		if(!CronExpression.isValidExpression(task.getTaskTime())){
			throw new BussinessException("任务执行周期格式错误");
		}
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(task.getTaskClassz());
		} catch (ClassNotFoundException e) {
			throw new  BussinessException("输入的类名不存在");
		}
		
		try {
			clazz.getDeclaredMethod(task.getTaskMethod());
		} catch (NoSuchMethodException e) {
			throw new BussinessException("输入的方法名不存在");
		}
		
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setTaskClassz(task.getTaskClassz());
		taskQuery.setTaskMethod(task.getTaskMethod());
		
		int count = taskMapper.selectCount(taskQuery);
		
		if(count > 0){
			throw new BussinessException("该任务已存在");
		}
	
		task.setStatus(0);
		taskMapper.insert(task);
	
		TaskMessage taskMessage = convert2TaskMessage(task);
		try {
			cronTriggerManager.addJob(taskMessage, isImmediateExcute);
		} catch (SchedulerException e) {
			throw new BussinessException("任务调度失败，请重试");
		}
		
		return task;
	}

	public TaskMessage convert2TaskMessage(Task task) {
		TaskMessage taskMessage = new TaskMessage();
		taskMessage.setId(task.getId());
		taskMessage.setTaskClassz(task.getTaskClassz());
		taskMessage.setTaskMethod(task.getTaskMethod());
		taskMessage.setTaskTime(task.getTaskTime());
		return taskMessage;
	}
	@Override
	public void updateTask(Task task, boolean isImmediateExcute) throws BussinessException {
		if(null == task){
			throw new BussinessException("参数错误");
		}
		
		task.setLastUpdateTime(new Date());
		if(StringUtil.isEmpty(task.getTaskClassz()) || StringUtil.isEmpty(task.getTaskMethod())
				|| StringUtil.isEmpty(task.getTaskTime())
				) {
			throw new BussinessException("参数错误");
		}

		if(!CronExpression.isValidExpression(task.getTaskTime())){
			throw new BussinessException("时间格式错误");
		}
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(task.getTaskClassz());
		} catch (ClassNotFoundException e) {
			throw new  BussinessException("输入的类名不存在");
		}
		
		try {
			clazz.getDeclaredMethod(task.getTaskMethod());
		} catch (NoSuchMethodException e) {
			throw new BussinessException("输入的方法名不存在");
		}
		
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setId(task.getId());
		
		int count = taskMapper.selectCount(taskQuery);
		
		if(count == 0){
			throw new BussinessException("该任务不存在");
		}
		
		taskMapper.update(task);
		
		TaskMessage taskMessage = convert2TaskMessage(task);
		try {
			cronTriggerManager.addJob(taskMessage, isImmediateExcute);
		} catch (SchedulerException e) {
			throw new BussinessException("任务调度失败，请重试");
		}
		
	}

	@Override
	public void pauseTask(String[] ids) throws BussinessException {
		for(String id : ids){
			Task task = findTaskById(id);
			task.setLastUpdateTime(new Date());
			task.setStatus(1);
			updateTask(task, false);
			TaskMessage taskMessage = convert2TaskMessage(task);
			try {
				cronTriggerManager.pauseJob(taskMessage);
			} catch (SchedulerException e) {
				throw new BussinessException("暂停任务失败，请重试");
			}
	}
		
	}
	
	
	public Task findTaskById(String id) throws BussinessException{
		if(id == null){
			throw new BussinessException("参数错误");
		}
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setId(id);
		List<Task> tasks = taskMapper.selectList(taskQuery);
		if(!tasks.isEmpty()){
			return tasks.get(0);
		}
		return null;
	}
	
	@Override
	public void excuteTask(String[] ids) throws BussinessException {
		for(String id : ids){
			Task task = findTaskById(id);
			task.setLastUpdateTime(new Date());
			task.setStatus(0);
			updateTask(task, true);
		}
		
	}

}
