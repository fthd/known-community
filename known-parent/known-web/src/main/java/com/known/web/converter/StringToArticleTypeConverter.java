package com.known.web.converter;

import com.known.common.enums.ArticleType;
import com.known.common.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToArticleTypeConverter implements
		Converter<String, ArticleType> {

	public ArticleType convert(String source) {
		String value = source.trim();
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return ArticleType.getArticleTypeByType(value);
	}

}
