package com.known.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 手动获取对象
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-06-05 20:53
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.context = applicationContext;
	}

	public static Object getBean(String name) {
		return context.getBean(name);
	}


}