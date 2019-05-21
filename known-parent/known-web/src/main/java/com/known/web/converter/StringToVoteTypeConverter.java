package com.known.web.converter;

import com.known.common.enums.VoteType;
import com.known.common.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToVoteTypeConverter implements
		Converter<String, VoteType> {

	public VoteType convert(String source) {
		String value = source.trim();
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return VoteType.getVoteTypeByValue(Integer.parseInt(value));
	}

}
