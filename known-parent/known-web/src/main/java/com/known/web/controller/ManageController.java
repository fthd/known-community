package com.known.web.controller;


import com.known.common.model.SysRes;
import com.known.common.model.SysRole;
import com.known.common.vo.OutResponse;
import com.known.common.vo.Tree;
import com.known.exception.BussinessException;
import com.known.service.SysResService;
import com.known.service.SysRoleService;
import com.known.service.UserService;
import com.known.web.annotation.RequirePermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manage")
public class ManageController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysResService sysResService;
	
	@Autowired
	private SysRoleService sysRoleService;
	

	@RequestMapping("/noperm")
	public ModelAndView noperm(){
		return new ModelAndView("page/admin-system/noperm");
	}
	
	@RequestMapping("/manage")
	public ModelAndView index(){
		return new ModelAndView("page/admin-system/index");
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session){
		session.invalidate();
		return new ModelAndView("page/admin-system/login");
	}

	/**
	 * 用户管理
	 * @return
	 */
	@RequestMapping("/user/list")
	@RequirePermissions(key="manage:user:list")
	public ModelAndView user(){
		return new ModelAndView("page/admin-system/system/user");
	}

	/**
	 * 获取所有用户列表
	 * @return
	 */
	@RequirePermissions(key="manage:user:list")
	@RequestMapping("/getUsers")
	public Object getUsers() {
		return userService.findUserVoList();
	}


	/**
	 * 删除用户信息
	 * @param userIds
	 * @return
	 */
	@RequirePermissions(key="manage:user:delete")
	@RequestMapping("/user/delete")
	public OutResponse<Object> deleteUser(String[] userIds){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			userService.deleteUser(userIds);
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
	@RequestMapping("/user/updateUserRole")
	public OutResponse<Object> updateUserRole(String[] userIds, String[] roleIds){
		OutResponse<Object> outResponse = new OutResponse<>();
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
	@RequestMapping("/user/markChange")
	public OutResponse<Object> markChange(String[] userIds, Integer mark, String des){
		OutResponse<Object> outResponse = new OutResponse<>();
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
	@RequestMapping("/res/list")
	public ModelAndView res(){
		return new ModelAndView("page/admin-system/system/res");
	}
	
	@RequirePermissions(key="manage:res:list")
	@RequestMapping("/getRess")
	public Object getRess() {
		return sysResService.findAllRes();
	}
	

	@RequestMapping("/res/allmenu")
	public Object getAllMenu() {
		List<SysRes> list = null;
		try {
			list = sysResService.findAllMenu();
		} catch (BussinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	@RequirePermissions(key="manage:res:delete")
	@RequestMapping("/res/delete")
	public OutResponse<Object> resDelete(String[] ids) {
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
	@RequestMapping("/res/save")
	public OutResponse<Object> resSave(SysRes sysRes) {
		OutResponse<Object> outResponse = new OutResponse<>();
		// 字段补全
		sysRes.setModifydate(new Date());
		sysRes.setLevel(0);
		sysRes.setIconcls("am-icon-file");
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
	public ModelAndView role(){
		return new ModelAndView("page/admin-system/system/role");
	}

	@RequirePermissions(key="manage:role:list")
	@RequestMapping("/role/load")
	public Object roleLoad() {
		List<SysRole> list = null;
		try {
			 list = sysRoleService.findSysRoleList();
		}catch (Exception e) {
			
		}
		return list;
	}
	
	@RequirePermissions(key="manage:role:delete")
	@RequestMapping("/role/delete")
	public OutResponse<Object> roleDelete(String[] ids) {
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
	@RequestMapping("/role/save")
	public OutResponse<Object> roleSave(SysRole sysRole) {
		OutResponse<Object> outResponse = new OutResponse<>();
		// 字段补全
		sysRole.setCreatedate(new Date());
		try {
			if(sysRole.getId() == null){
				sysRoleService.addSysRole(sysRole);
			} else{
				sysRoleService.updateSysRole(sysRole);
			}
		}catch (BussinessException e) {
			outResponse.setMsg(e.getLocalizedMessage());
		}catch (Exception e) {

		}
		return outResponse;
	}

	@RequestMapping("/role/getResourceId")
	public OutResponse<Object> getResourceId(String id) {
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
	@RequestMapping("/role/updateAuthority")
	public OutResponse<Object> roleUpdateAuthority(String roleId, String[] resIds) {
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
