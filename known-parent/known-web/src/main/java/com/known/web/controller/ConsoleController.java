package com.known.web.controller;

import com.known.common.echart.Echart;
import com.known.common.model.LoginLog;
import com.known.common.model.SysLog;
import com.known.common.model.Task;
import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.IStatisticalDataService;
import com.known.service.LoginLogService;
import com.known.service.SysLogService;
import com.known.service.TaskService;
import com.known.web.annotation.RequirePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/console")
public class ConsoleController {

	@Autowired
	private IStatisticalDataService iStatisticalDataService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Autowired
	private SysLogService sysLogService;

	
	@RequirePermissions(key="console:statistics:list")
	@RequestMapping("/statistics/list")
	public ModelAndView statistics() {
		return new ModelAndView("/page/admin-system/console/statistics");
	}
	
	@RequirePermissions(key="console:statistics:list")
	@RequestMapping("/statistics/load")
	public OutResponse<Object> statisticsLoad(){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			List<Echart> list = this.iStatisticalDataService.findEcharts();
			outResponse.setData(list);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	
	@RequirePermissions(key="console:task:list")
	@RequestMapping("/task/list")
	public ModelAndView task() {
		return new ModelAndView("/page/admin-system/console/task");
	}
	
	
	@RequirePermissions(key="console:task:list")
	@RequestMapping("/task/load")
	public OutResponse<Object> taskLoad(){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			List<Task> tasks = this.taskService.findTaskList();
			outResponse.setData(tasks);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequirePermissions(key="console:task:delete")
	@RequestMapping("/task/delete")
	public OutResponse<Object> taskDelete(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			this.taskService.deleteTask(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequirePermissions(key="console:task:pause")
	@RequestMapping("/task/pause")
	public OutResponse<Object> taskPause(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			this.taskService.pauseTask(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequirePermissions(key="console:task:excute")
	@RequestMapping("/task/excute")
	public OutResponse<Object> taskExcute(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			this.taskService.excuteTask(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequirePermissions(key="console:task:save")
	@RequestMapping("/task/save")
	public OutResponse<Object> taskAdd(Task task, boolean isImmediateExcute){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			if(task.getId() == null){
				this.taskService.addTask(task, isImmediateExcute);
			}
			else{
				this.taskService.updateTask(task, isImmediateExcute);
			}
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequestMapping("/task/update")
	public OutResponse<Object> taskUpdate(Task task, boolean isImmediateExcute){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			this.taskService.updateTask(task, isImmediateExcute);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequirePermissions(key="console:loginlog:list")
	@RequestMapping("/loginlog/list")
	public ModelAndView loginlog() {
		return new ModelAndView("/page/admin-system/console/loginlog");
	}
	
	@RequirePermissions(key="console:loginlog:list")
	@RequestMapping("/loginlog/load")
	public OutResponse<Object> loginLogLoad(){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			List<LoginLog> list = loginLogService.findLoginLog();
			outResponse.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	

	
	@RequirePermissions(key="console:syslog:list")
	@RequestMapping("/syslog/list")
	public ModelAndView syslog() {
		return new ModelAndView("/page/admin-system/console/syslog");
	}
	
	@RequirePermissions(key="console:syslog:list")
	@RequestMapping("/syslog/load")
	public OutResponse<Object> sysLogLoad(){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			List<SysLog> list = sysLogService.findSysLogList();
			outResponse.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequirePermissions(key="console:userlocation:list")
	@RequestMapping("/userlocation/list")
	public ModelAndView userlocation() {
		return new ModelAndView("/page/admin-system/console/userlocation");
	}
	
	@RequirePermissions(key="console:userlocation:list")
	@RequestMapping("/userlocation/load")
	public OutResponse<Object> userlocationLoad(){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			List<LoginLog> list = loginLogService.findLoginLogGroupByIp();
			outResponse.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	@RequirePermissions(key="console:druid:list")
	@RequestMapping("/druid/list")
	public ModelAndView druid() {
		return new ModelAndView("/page/admin-system/console/druid");
	}
	
}
