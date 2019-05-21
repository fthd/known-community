package com.known.aspect;



import com.known.annotation.RequirePermissions;
import com.known.common.enums.Logical;
import com.known.common.model.SysRes;
import com.known.common.model.UserRedis;
import com.known.common.utils.Constants;
import com.known.service.SysResService;
import com.known.service.SysRoleService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aspect
@Component
public class AuthorityAspect {
	
	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysResService sysResService;

	@Around(value="@annotation(com.known.annotation.RequirePermissions)&&@annotation(perm)")
	public Object hasPermission(ProceedingJoinPoint point, RequirePermissions perm) throws Throwable{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		UserRedis sessionUser = (UserRedis) request.getSession().getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser!= null){
			Set<Integer> roleSet = sysRoleService.findRoleIdsByUserId(sessionUser.getUserid());
			List<SysRes> list = sysResService.findMenuByRoleIds(roleSet);
			Set<String> permkey = new HashSet<>();
			if(list != null){
				for(SysRes sysRes : list){
					permkey.add(sysRes.getKey());
				}
			}
			String[] values = perm.key();
			if(permkey != null){
				if(perm.logical().equals(Logical.AND)){
					for(String value : values){
						if(!permkey.contains(value)){
							response.sendRedirect("/manage/noperm" + "?v=" + new Date().getTime());
						}
					}
				}
				else{
					for(String value : values){
						if(permkey.contains(value)){
							return  point.proceed();
						}
						else{
							response.sendRedirect("/manage/noperm" + "?v=" + new Date().getTime());
						}
					}
				}
			}
		}
		else{
			response.sendRedirect("/login?redirect=" + request.getRequestURI());
		}
		return point.proceed();
	}
	
}
