package com.known.web.directive;

import com.known.common.config.UserConfig;
import com.known.common.model.SessionUser;
import com.known.common.model.SysRes;
import com.known.service.SysResService;
import com.known.service.SysRoleService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * FreeMarker 设置权限宏定义, 避免后台重复操作
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 14:13
 */
@Component
public class CommPermDirective implements TemplateDirectiveModel {

	@Autowired
	private SysResService sysResService;
	
	@Autowired
	private SysRoleService sysRoleService;

	@Resource
	private UserConfig userConfig;
	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
		// 获取参数中的key
		if(params != null && params.containsKey("key")){
			String key = params.get("key").toString();
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(userConfig.getSession_User_Key());
			if(sessionUser!= null){
				Set<Integer> roleSet = sysRoleService.findRoleIdsByUserId(sessionUser.getUserid());
				Integer type = 2; //权限
				List<SysRes> list = sysResService.findLimitByRoleIds(roleSet, type);
				Set<String> permkey = new HashSet<>();
				if(list != null){
					for(SysRes sysRes : list){
						permkey.add(sysRes.getKey());
					}
					if(permkey !=null && permkey.contains(key)){
						body.render(env.getOut());
					}
				}
			}
		}
	}

}
