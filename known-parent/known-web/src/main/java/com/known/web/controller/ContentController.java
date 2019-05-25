package com.known.web.controller;

import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.*;
import com.known.web.annotation.RequirePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
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
	public ModelAndView shuoshuolist(){
		return new ModelAndView("page/admin-system/content/shuoshuo");
	}
	
	@RequirePermissions(key="content:shuoshuo:list")
	@RequestMapping("/shuoshuo/load")
	public Object shuoshuoload(){
		return shuoshuoService.findShuoshuos();
	}
	
	@RequirePermissions(key="content:shuoshuo:delete")
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
	public ModelAndView knowledgelist(){
		return new ModelAndView("page/admin-system/content/knowledge");
	}
	
	
	@RequirePermissions(key="content:knowledge:list")
	@RequestMapping("/knowledge/load")
	public Object knowledgeload(){
		return knowledgeService.findKnowledgeList();
	}
	
	@RequirePermissions(key="content:knowledge:delete")
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
	public ModelAndView topiclist(){
		return new ModelAndView("page/admin-system/content/topic");
	}
	
	
	@RequirePermissions(key="content:topic:list")
	@RequestMapping("/topic/load")
	public Object topicload(){
		return topicService.findTopicList();
	}
	
	
	@RequirePermissions(key="content:topic:delete")
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
	public ModelAndView asklist(){
		return new ModelAndView("page/admin-system/content/ask");
	}
	
	
	@RequirePermissions(key="content:ask:list")
	@RequestMapping("/ask/load")
	public Object askload(){
		return askService.findAskList();
	}
	
	@RequirePermissions(key="content:ask:delete")
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
