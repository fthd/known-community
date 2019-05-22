package com.known.web.controller;


import com.known.common.model.SysRes;
import com.known.common.model.SysRole;
import com.known.common.vo.OutResponse;
import com.known.common.vo.Tree;
import com.known.exception.BussinessException;
import com.known.service.SysResService;
import com.known.service.SysRoleService;
import com.known.service.UserService;
import com.known.annotation.RequirePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysResService sysResService;
	
	@Autowired
	private SysRoleService sysRoleService;
	

	@RequestMapping("/noperm")
	public String noperm(){
		return "page/noperm";
	}
	
	@RequestMapping("/manage")
	public String index(){
		return "page/adminpage/index";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "page/adminpage/login";
	}
	
	
	@RequirePermissions(key="manage:user:list")
	@ResponseBody
	@RequestMapping("/getUsers")
	public Object getUsers() {
		return this.userService.findUserVoList();
	}
	
	@RequirePermissions(key="manage:user:list")
	@RequestMapping("/user/list")
	public String user(){
		return "page/adminpage/user";
	}
	
	
	@RequirePermissions(key="manage:user:delete")
	@ResponseBody
	@RequestMapping("/user/delete")
	public OutResponse<Object> deleteUser(Integer[] userIds){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			this.userService.deleteUser(userIds);
		} catch (BussinessException e) {
			e.printStackTrace();
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {
			e.printStackTrace();
			outResponse.setMsg("操作失败，请刷新重试");
		}
		return outResponse;
	}
	
	@RequirePermissions(key="manage:user:updateUserRole")
	@ResponseBody
	@RequestMapping("/user/updateUserRole")
	public OutResponse<Object> deleteUser(Integer[] userIds, Integer[] roleIds){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			userService.updateBatchUserRole(userIds, roleIds);
		} catch (BussinessException e) {
			e.printStackTrace();
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {
			e.printStackTrace();
			outResponse.setMsg("操作失败，请刷新重试");
		}
		return outResponse;
	}
	
	@RequirePermissions(key="manage:user:markChange")
	@ResponseBody
	@RequestMapping("/user/markChange")
	public OutResponse<Object> markChange(Integer[] userIds, Integer mark, String des){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			userService.markChangeAdvice(userIds, mark, des);
		} catch (BussinessException e) {
			e.printStackTrace();
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {
			e.printStackTrace();
			outResponse.setMsg("操作失败，请刷新重试");
		}
		return outResponse;
	}
	
	
	@RequirePermissions(key="manage:res:list")
	@ResponseBody
	@RequestMapping("/getRess")
	public Object getRess() {
		return this.sysResService.findAllRes();
	}
	
	
	@ResponseBody
	@RequestMapping("/res/allmenu")
	public Object getAllMenu() {
		List<SysRes> list = null;
		try {
			list = this.sysResService.findAllMenu();
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequirePermissions(key="manage:res:list")
	@RequestMapping("/res/list")
	public String res(){
		return "page/adminpage/res";
	}
	
	
	@RequirePermissions(key="manage:res:delete")
	@ResponseBody
	@RequestMapping("/res/delete")
	public OutResponse<Object> resDelete(Integer[] ids) {
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			sysResService.deleteSysRes(ids);
		}catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {
			
		}
		return outResponse;
	}
	
	
	@RequirePermissions(key="manage:res:save")
	@ResponseBody
	@RequestMapping("/res/save")
	public OutResponse<Object> resSave(SysRes sysRes) {
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			if(sysRes.getId() == null){
				sysResService.addSysRes(sysRes);
			}
			else{
				sysResService.updateSysRes(sysRes);
			}
		}catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	
	
	@RequirePermissions(key="manage:role:list")
	@RequestMapping("/role/list")
	public String role(){
		return "page/adminpage/role";
	}

	@RequirePermissions(key="manage:role:list")
	@ResponseBody
	@RequestMapping("/role/load")
	public Object roleLoad() {
		List<SysRole> list = null;
		try {
			 list = this.sysRoleService.findSysRoleList();
		}catch (Exception e) {
			
		}
		return list;
	}
	
	@RequirePermissions(key="manage:role:delete")
	@ResponseBody
	@RequestMapping("/role/delete")
	public OutResponse<Object> roleDelete(Integer[] ids) {
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			sysRoleService.deleteSysRole(ids);
		}catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {
			
		}
		return outResponse;
	}
	
	
	@RequirePermissions(key="manage:role:save")
	@ResponseBody
	@RequestMapping("/role/save")
	public OutResponse<Object> roleSave(SysRole sysRole) {
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			if(sysRole.getId() == null){
				sysRoleService.addSysRole(sysRole);
			}
			else{
				sysRoleService.updateSysRole(sysRole);
			}
		}catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {

		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("/role/getResourceId")
	public OutResponse<Object> getResourceId(Integer id) {
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			List<Integer> list = sysRoleService.findResourceIdByRoleId(id);
			outResponse.setData(list);
		}catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {

		}
		return outResponse;
	}
	 
	@RequirePermissions(key="manage:role:updateAuthority")
	@ResponseBody
	@RequestMapping("/role/updateAuthority")
	public OutResponse<Object> roleUpdateAuthority(Integer roleId, Integer[] resIds) {
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			sysRoleService.updateAuthority(roleId, resIds);
		}catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return outResponse;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/tree/list")
	public Object getAllTree(){
		List<Tree> list = null;
		try {
			list =  sysResService.findAllTree();
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
}
