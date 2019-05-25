package com.known.web.converter;

import com.known.common.enums.MessageStatusEnum;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToMessageStatusConverter implements
		Converter<String, MessageStatusEnum> {

	public MessageStatusEnum convert(String source) {
		String value = source.trim();
		if(StringUtil.isEmpty(value)){
			return null;
		}
		return MessageStatusEnum.getMessageStatusByType(Integer.parseInt(value));
	}

}
