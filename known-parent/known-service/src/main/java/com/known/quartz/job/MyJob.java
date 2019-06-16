package com.known.quartz.job;


import com.known.common.utils.Constants;
import com.known.quartz.TaskMessage;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * job接口实现
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 17:31
 */
public class MyJob implements Job {


	public void execute(JobExecutionContext context)  {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		TaskMessage task = (TaskMessage) jobDataMap.get(Constants.TaskKey);
		Class<?> classz = null;
		try {
			classz = Class.forName(task.getTaskClassz());//得到对应的类
			
			Method method = classz.getDeclaredMethod(task.getTaskMethod());//得到方法
			
			method.invoke(classz.newInstance());//调用方法
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
