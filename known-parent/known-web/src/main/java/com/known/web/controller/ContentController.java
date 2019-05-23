package com.known.web.controller;

import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.*;
import com.known.annotation.RequirePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ShuoShuoService shuoshuoService;
	
	@Autowired
	private KnowledgeService knowledgeService;
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private AskService askService;

	
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
	public OutResponse<Object> shuoshuoDelete(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			shuoshuoService.deleteBatch(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
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
	public OutResponse<Object> knowledgeDelete(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			knowledgeService.deleteBatch(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
	}
	
	
	@RequirePermissions(key="content:knowledge:updatestatus")
	@ResponseBody
	@RequestMapping("/knowledge/updateStatus")
	public OutResponse<Object> updateKnowledgeStatus(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			knowledgeService.updateStatusBatch(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
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
	public OutResponse<Object> topicDelete(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			topicService.deleteBatch(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
	}

	@RequirePermissions(key="content:topic:essence")
	@ResponseBody
	@RequestMapping("/topic/essence")
	public OutResponse<Object> topicEssence(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			topicService.updateTopicEssence(ids, 1);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
	}
	
	@RequirePermissions(key="content:topic:unessence")
	@ResponseBody
	@RequestMapping("/topic/unessence")
	public OutResponse<Object> topicunEssence(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			topicService.updateTopicEssence(ids, 0);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
	}
	
	@RequirePermissions(key="content:topic:stick")
	@ResponseBody
	@RequestMapping("/topic/stick")
	public OutResponse<Object> topicstick(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			topicService.updateTopicStick(ids, 1);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
	}
	
	@RequirePermissions(key="content:topic:unstick")
	@ResponseBody
	@RequestMapping("/topic/unstick")
	public OutResponse<Object> topicunstick(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			topicService.updateTopicStick(ids, 0);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
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
	public OutResponse<Object> askDelete(Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<>();
		 try {
			askService.deleteBatch(ids);
		} catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return outResponse;
	}
}
