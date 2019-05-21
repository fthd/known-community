package com.known.web.converter;

import com.known.common.enums.MessageStatus;
import com.known.common.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToMessageStatusConverter implements
		Converter<String, MessageStatus> {

	public MessageStatus convert(String source) {
		String value = source.trim();
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return MessageStatus.getMessageStatusByType(Integer.parseInt(value));
	}

}
