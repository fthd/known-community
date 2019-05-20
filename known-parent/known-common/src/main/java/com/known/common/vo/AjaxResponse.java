package com.known.common.vo;

import com.known.common.enums.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AjaxResponse<T> {

	private ResponseCode responseCode;

	private String errorMsg;

	private T data;

}
