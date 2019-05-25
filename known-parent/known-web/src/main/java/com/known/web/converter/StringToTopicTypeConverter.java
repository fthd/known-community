package com.known.web.converter;

import com.known.common.enums.TopicTypeEnum;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToTopicTypeConverter implements
        Converter<String, TopicTypeEnum> {

	public TopicTypeEnum convert(String source) {
		String value = source.trim();
		if(StringUtil.isEmpty(value)){
			return null;
		}
		return TopicTypeEnum.getTopicTypeByValue(Integer.parseInt(value));
	}

}
