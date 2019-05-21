package com.known.quartz.trigger;


import com.known.common.utils.Constants;
import com.known.quartz.TaskMessage;
import com.known.quartz.job.MyJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

/**
 * Cron表达式控制任务运行
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 17:32
 */
@Component
public class CronTriggerManager {
		
	private static final String TASKNAME = "task_";
	
	private static final String GROUPNAME = "group_";
	

	/**
	 * 
	 * @Description 添加任务
	 * @param task
	 * @param isImmediateExcute 是否立即执行
	 * @throws SchedulerException 
	 */
	public void addJob(TaskMessage task, boolean isImmediateExcute) throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();//获取scheduler工厂
		Scheduler scheduler = schedulerFactory.getScheduler();
		
		TriggerKey triggerKey = getTriggerKey(task);//通过任务的名字和所属组得到触发key
		CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);//通过触发key得到触发器
		
		if(cronTrigger == null){//判断触发器是否已经存在 没有就创建该触发器
			JobDetail jobDetail = JobBuilder.newJob(MyJob.class)//通过任务构造器构造任务详情对象
													.withIdentity(TASKNAME + task.getId(), GROUPNAME + task.getId()).build();
			
			jobDetail.getJobDataMap().put(Constants.TASKKEY, task);//加入到jobDetailMap当中，供给实现job接口的类获取

			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskTime());//表达式调度构造器
			
			cronTrigger = TriggerBuilder.newTrigger()//通过触发器构造器构造触发器 
										.withIdentity(TASKNAME + task.getId(),  GROUPNAME + task.getId())
										.withSchedule(cronScheduleBuilder).build();
			
			scheduler.scheduleJob(jobDetail, cronTrigger);//安排任务
			
			scheduler.start();//开启任务		
		}else {//如果已经存在该任务，就改变时间
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(task.getTaskTime());//改变对应的时间表达式
			
			cronTrigger = cronTrigger.getTriggerBuilder()//按照新的时间表达式构造触发器
												.withIdentity(triggerKey)
												.withSchedule(cronScheduleBuilder).build();
			
			scheduler.rescheduleJob(triggerKey, cronTrigger);//按照新的触发器执行		
		}
		
		if(isImmediateExcute){
			triggerJob(task, scheduler	);
		}
		
	}
	
	
	/**
	 * 
	 * @Description 暂停任务
	 * @param task
	 * @throws SchedulerException
	 */
	public void pauseJob(TaskMessage task) throws SchedulerException{
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobKey jobKey = JobKey(task);
		scheduler.pauseJob(jobKey);
	}

	
	/**
	 * 
	 * @Description 删除任务
	 * @param task
	 * @throws SchedulerException
	 */
	public void delJob(TaskMessage task) throws SchedulerException{
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		JobKey jobKey = JobKey(task);
		scheduler.deleteJob(jobKey);
	}

	/**
	 * @Description 立即触发任务执行
	 * @param task
	 * @param scheduler
	 * @throws SchedulerException 
	 */
	private void triggerJob(TaskMessage task, Scheduler scheduler) throws SchedulerException {
		JobKey jobKey = JobKey(task);
		scheduler.triggerJob(jobKey);
	}


	/**
	 * @Description 通过任务获取任务标识key
	 * @param task
	 * @return
	 */
	private JobKey JobKey(TaskMessage task) {
		JobKey jobKey = JobKey.jobKey(TASKNAME + task.getId(), GROUPNAME + task.getId());
		return jobKey;
	}


	/**
	 * @Description 通过task获取对应的触发器key
	 * @param task
	 * @return
	 */
	private TriggerKey getTriggerKey(TaskMessage task) {
		TriggerKey triggerKey = TriggerKey.triggerKey(TASKNAME + task.getId(), GROUPNAME + task.getId());
		return triggerKey;
	}
	
}
