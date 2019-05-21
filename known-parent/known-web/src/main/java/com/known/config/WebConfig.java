package com.known.config;

import com.known.common.enums.ArticleType;
import com.known.web.converter.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * SpringBoot关于Spring MVC的配置
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-21 18:36
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToArticleTypeConverter());
        registry.addConverter(new StringToChooseTypeConverter());
        registry.addConverter(new StringToMessageStatusConverter());
        registry.addConverter(new StringToSolveTypeConverter());
        registry.addConverter(new StringToTopicTypeConverter());
        registry.addConverter(new StringToVoteTypeConverter());
    }
}
