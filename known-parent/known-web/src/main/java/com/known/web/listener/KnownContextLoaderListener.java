package com.known.web.listener;

import com.known.cache.CategoryCache;
import com.known.common.model.Task;
import com.known.exception.BussinessException;
import com.known.quartz.TaskMessage;
import com.known.quartz.trigger.CronTriggerManager;
import com.known.service.TaskService;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class KnownContextLoaderListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//初始化SpringContextUtil的context
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		//项目初始化时需要做的事情
		CategoryCache categoryCache = (CategoryCache) ctx.getBean("categoryCache");
		categoryCache.refreshCategoryCache();
		
		TaskService taskService = (TaskService) ctx.getBean("taskServiceImpl");
		CronTriggerManager cronTriggerManager = ctx.getBean(CronTriggerManager.class);
		try {
			List<Task> tasks = taskService.findTaskList();
			for(Task task : tasks){
				if(task.getStatus() == 0){
					TaskMessage taskMessage = taskService.convert2TaskMessage(task);
					cronTriggerManager.addJob(taskMessage, false);
				}
			}
		} catch (BussinessException | SchedulerException e) {
			e.printStackTrace();
		}
		
	}

}