package com.known.web.controller;

import com.known.common.vo.AjaxResponse;
import com.known.exception.BussinessException;
import com.known.service.*;
import com.known.annotation.RequirePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("content")
public class ContentController {

	@Autowired
	private ExamService examService;
	
	@Autowired
	private ShuoShuoService shuoshuoService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private AskService askService;
	
	
	@RequirePermissions(key="content:exam:list")
	@RequestMapping("/exam/list")
	public String examlist(){
		return "page/adminpage/exam";
	}
	
	
	@RequirePermissions(key="content:exam:list")
	@ResponseBody
	@RequestMapping("/exam/load")
	public Object examload(){
		return examService.findExamWithRightAnswer();
	}
	
	@RequirePermissions(key="content:exam:delete")
	@ResponseBody
	@RequestMapping("/exam/delete")
	public AjaxResponse<Object> examDelete(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			examService.deleteBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	@RequirePermissions(key="content:exam:updatestatus")
	@ResponseBody
	@RequestMapping("/exam/updateStatus")
	public AjaxResponse<Object> updateStatus(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			examService.updateStatusBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	
	@RequirePermissions(key="content:shuoshuo:list")
	@RequestMapping("/shuoshuo/list")
	public String shuoshuolist(){
		return "page/adminpage/shuoshuo";
	}
	
	@RequirePermissions(key="content:shuoshuo:list")
	@ResponseBody
	@RequestMapping("/shuoshuo/load")
	public Object shuoshuoload(){
		return shuoshuoService.findShuoshuos();
	}
	
	@RequirePermissions(key="content:shuoshuo:delete")
	@ResponseBody
	@RequestMapping("/shuoshuo/delete")
	public AjaxResponse<Object> shuoshuoDelete(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			shuoshuoService.deleteBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	
	@RequirePermissions(key="content:blog:list")
	@RequestMapping("/blog/list")
	public String bloglist(){
		return "page/adminpage/blog";
	}
	
	@RequirePermissions(key="content:blog:list")
	@ResponseBody
	@RequestMapping("/blog/load")
	public Object blogload(){
		return blogService.findBlogList();
	}
	
	@RequirePermissions(key="content:blog:delete")
	@ResponseBody
	@RequestMapping("/blog/delete")
	public AjaxResponse<Object> blogDelete(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			blogService.deleteBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	@RequirePermissions(key="content:knowledge:list")
	@RequestMapping("/knowledge/list")
	public String knowledgelist(){
		return "page/adminpage/knowledge";
	}
	
	
	@RequirePermissions(key="content:knowledge:list")
	@ResponseBody
	@RequestMapping("/knowledge/load")
	public Object knowledgeload(){
		return knowledgeService.findKnowledgeList();
	}
	
	@RequirePermissions(key="content:knowledge:delete")
	@ResponseBody
	@RequestMapping("/knowledge/delete")
	public AjaxResponse<Object> knowledgeDelete(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			knowledgeService.deleteBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	
	@RequirePermissions(key="content:knowledge:updatestatus")
	@ResponseBody
	@RequestMapping("/knowledge/updateStatus")
	public AjaxResponse<Object> updateKnowledgeStatus(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			knowledgeService.updateStatusBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	
	@RequirePermissions(key="content:topic:list")
	@RequestMapping("/topic/list")
	public String topiclist(){
		return "page/adminpage/topic";
	}
	
	
	@RequirePermissions(key="content:topic:list")
	@ResponseBody
	@RequestMapping("/topic/load")
	public Object topicload(){
		return topicService.findTopicList();
	}
	
	
	@RequirePermissions(key="content:topic:delete")
	@ResponseBody
	@RequestMapping("/topic/delete")
	public AjaxResponse<Object> topicDelete(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			topicService.deleteBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}

	@RequirePermissions(key="content:topic:essence")
	@ResponseBody
	@RequestMapping("/topic/essence")
	public AjaxResponse<Object> topicEssence(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			topicService.updateTopicEssence(ids, 1);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	@RequirePermissions(key="content:topic:unessence")
	@ResponseBody
	@RequestMapping("/topic/unessence")
	public AjaxResponse<Object> topicunEssence(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			topicService.updateTopicEssence(ids, 0);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	@RequirePermissions(key="content:topic:stick")
	@ResponseBody
	@RequestMapping("/topic/stick")
	public AjaxResponse<Object> topicstick(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			topicService.updateTopicStick(ids, 1);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	@RequirePermissions(key="content:topic:unstick")
	@ResponseBody
	@RequestMapping("/topic/unstick")
	public AjaxResponse<Object> topicunstick(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			topicService.updateTopicStick(ids, 0);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
	
	@RequirePermissions(key="content:ask:list")
	@RequestMapping("/ask/list")
	public String asklist(){
		return "page/adminpage/ask";
	}
	
	
	@RequirePermissions(key="content:ask:list")
	@ResponseBody
	@RequestMapping("/ask/load")
	public Object askload(){
		return askService.findAskList();
	}
	
	@RequirePermissions(key="content:ask:delete")
	@ResponseBody
	@RequestMapping("/ask/delete")
	public AjaxResponse<Object> askDelete(Integer[] ids){
		AjaxResponse<Object> ajaxResponse = new AjaxResponse<>();
		 try {
			askService.deleteBatch(ids);
		} catch (BussinessException e) {
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return ajaxResponse;
	}
}
