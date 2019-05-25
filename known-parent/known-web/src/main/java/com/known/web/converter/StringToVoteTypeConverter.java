package com.known.web.converter;

import com.known.common.enums.VoteTypeEnum;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToVoteTypeConverter implements
		Converter<String, VoteTypeEnum> {

	public VoteTypeEnum convert(String source) {
		String value = source.trim();
		if(StringUtil.isEmpty(value)){
			return null;
		}
		return VoteTypeEnum.getVoteTypeByValue(Integer.parseInt(value));
	}

}
