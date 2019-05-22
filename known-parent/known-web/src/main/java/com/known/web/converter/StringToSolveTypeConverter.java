package com.known.web.converter;

import com.known.common.enums.SolveEnum;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToSolveTypeConverter implements
		Converter<String, SolveEnum> {

	public SolveEnum convert(String source) {
		if(StringUtil.isEmpty(source)){
			return null;
		}
		return SolveEnum.getSolveEnumByType(Integer.parseInt(source));
	}

}
