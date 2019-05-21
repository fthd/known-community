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
import com.known.annotation.RequirePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
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
	public String statistics() {
		return "page/adminpage/statistics";
	}
	
	@RequirePermissions(key="console:statistics:list")
	@ResponseBody
	@RequestMapping("/statistics/load")
	public OutResponse<Object> statisticsLoad(){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	public String task() {
		return "page/adminpage/task";
	}
	
	
	@RequirePermissions(key="console:task:list")
	@ResponseBody
	@RequestMapping("/task/load")
	public OutResponse<Object> taskLoad(){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	@ResponseBody
	@RequestMapping("/task/delete")
	public OutResponse<Object> taskDelete(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	@ResponseBody
	@RequestMapping("/task/pause")
	public OutResponse<Object> taskPause(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	@ResponseBody
	@RequestMapping("/task/excute")
	public OutResponse<Object> taskExcute(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	@ResponseBody
	@RequestMapping("/task/save")
	public OutResponse<Object> taskAdd(Task task, boolean isImmediateExcute){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	
	@ResponseBody
	@RequestMapping("/task/update")
	public OutResponse<Object> taskUpdate(Task task, boolean isImmediateExcute){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	public String loginlog() {
		return "page/adminpage/loginlog";
	}
	
	@RequirePermissions(key="console:loginlog:list")
	@ResponseBody
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
	public String syslog() {
		return "page/adminpage/syslog";
	}
	
	@RequirePermissions(key="console:syslog:list")
	@ResponseBody
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
	public String userlocation() {
		return "page/adminpage/userlocation";
	}
	
	@RequirePermissions(key="console:userlocation:list")
	@ResponseBody
	@RequestMapping("/userlocation/load")
	public OutResponse<Object> userlocationLoad(){
		OutResponse<Object> outResponse = new OutResponse<Object>();
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
	public String druid() {
		return "page/adminpage/druid";
	}
	
}
