package com.known.web.converter;

import com.known.common.enums.TopicType;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToTopicTypeConverter implements
        Converter<String, TopicType> {

	public TopicType convert(String source) {
		String value = source.trim();
		if(StringUtil.isEmpty(value)){
			return null;
		}
		return TopicType.getTopicTypeByValue(Integer.parseInt(value));
	}

}
