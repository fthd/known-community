package com.known.web.directive;

import com.known.common.config.UserConfig;
import com.known.common.model.SessionUser;
import com.known.common.model.SysRes;
import com.known.service.SysResService;
import com.known.service.SysRoleService;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class PermissionDirective implements TemplateDirectiveModel {

	@Autowired
	private SysResService sysResService;
	
	@Autowired
	private SysRoleService sysRoleService;

	@Resource
	private UserConfig userConfig;


	/**
	 * 当页面模板中使用自定义标签时，会自动调用该方法
	 * @param env 表示模板处理期间的运行时环境
	 *               该对象会存储模板创建的临时变量集、模板设置的值、对数据模型根的引用等等
	 *               通常用它来输出相关内容，如Writer out = env.getOut()
	 * @param params 传递给自定义标签的参数
	 * @param loopVars 循环替代变量
	 * @param body 表示自定义标签中嵌套的内容, 说简单点就是自定义标签内的内容体
	 * @throws TemplateException
	 * @throws IOException
	 */
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) throws TemplateException, IOException {

			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

			SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(userConfig.getSession_User_Key());

			if(sessionUser!= null){
				//查找系统用户角色id集合 role_id
				Set roleSet = sysRoleService.findRoleIdsByUserId(sessionUser.getUserid());

				//查找角色结果集 sys_res
				Integer type = 1; //菜单
				List<SysRes> list = sysResService.findLimitByRoleIds(roleSet, type);
				/*list.parallelStream().forEach( r -> {
					System.out.println(r);
				});*/
				if(list != null){
					TemplateModel templateModel = getBeansWrapper().wrap(list);
					env.setGlobalVariable("menu",templateModel);
					body.render(env.getOut());
				}
			} else{
				response.sendRedirect("/user/login?redirect=" + request.getRequestURI());
			}

	}

	public static BeansWrapper getBeansWrapper(){
		BeansWrapper beansWrapper =
				new BeansWrapperBuilder(Configuration.VERSION_2_3_28).build();
		return beansWrapper;
	}

}
