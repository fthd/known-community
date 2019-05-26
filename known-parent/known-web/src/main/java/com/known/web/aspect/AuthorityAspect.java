package com.known.web.aspect;

import com.known.common.config.UrlConfig;
import com.known.common.config.UserConfig;
import com.known.common.enums.LogicalEnum;
import com.known.common.model.SessionUser;
import com.known.common.model.SysRes;
import com.known.service.SysResService;
import com.known.service.SysRoleService;
import com.known.web.annotation.RequirePermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
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

    @Resource
    private UserConfig userConfig;

    @Resource
    private UrlConfig urlConfig;

    @Around(value = "@annotation(com.known.web.annotation.RequirePermissions)&&@annotation(perm)")
    public Object hasPermission(ProceedingJoinPoint point, RequirePermissions perm) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        SessionUser sessionUser = (SessionUser) request.getSession().getAttribute(userConfig.getSession_User_Key());

        if (sessionUser != null) {
            Set roleSet = sysRoleService.findRoleIdsByUserId(sessionUser.getUserid());
            // 获取资源列表
            Integer type = 2; //权限
            List<SysRes> list = sysResService.findLimitByRoleIds(roleSet, type);
            Set<String> permkey = new HashSet<>();
            if (list != null) {
                for (SysRes sysRes : list) {
                    permkey.add(sysRes.getKey());
                }
            }
            String[] values = perm.key();
            // 判断权限key为空, 未登录
            if (permkey == null) {
                response.sendRedirect("/user/login?redirect=" + request.getRequestURI());
            }

            // 权限逻辑为AND, 必须都满足
            if (perm.logical().equals(LogicalEnum.AND)) {
                for (String value : values) {
                    // 有一项不满足即无权限访问
                    if (!permkey.contains(value)) {
                        response.sendRedirect("/manage/noperm" + "?v=" + new Date().getTime());
                    }
                }
            } else {
                // 权限逻辑为OR, 只需要满足其一即可
                for (String value : values) {
                    if (permkey.contains(value)) {
                        return point.proceed();
                    }
                }
                // 所有遍历完都为找到, 无权限
                response.sendRedirect("/manage/noperm" + "?v=" + new Date().getTime());
            }
        }

        return point.proceed();
    }

}
