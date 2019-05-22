package com.known.web.converter;

import com.known.common.enums.ArticleType;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToArticleTypeConverter implements
		Converter<String, ArticleType> {

	public ArticleType convert(String source) {
		String value = source.trim();
		if(StringUtil.isEmpty(value)){
			return null;
		}
		return ArticleType.getArticleTypeByType(value);
	}

}
