package com.known.web.converter;

import com.known.common.enums.VoteType;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToVoteTypeConverter implements
		Converter<String, VoteType> {

	public VoteType convert(String source) {
		String value = source.trim();
		if(StringUtil.isEmpty(value)){
			return null;
		}
		return VoteType.getVoteTypeByValue(Integer.parseInt(value));
	}

}
