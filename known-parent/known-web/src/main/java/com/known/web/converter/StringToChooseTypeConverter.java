package com.known.web.converter;

import com.known.common.enums.ExamChooseType;
import com.known.common.utils.StringUtil;
import org.springframework.core.convert.converter.Converter;

public class StringToChooseTypeConverter implements
        Converter<String, ExamChooseType> {

	public ExamChooseType convert(String source) {
		String value = source.trim();
		if(StringUtil.isEmpty(value)){
			return null;
		}
		return ExamChooseType.getExamChooseTypeByValue(Integer.parseInt(value));
	}

}
