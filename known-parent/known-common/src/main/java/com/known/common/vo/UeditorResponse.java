package com.known.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UeditorResponse {

	private String original;
	private String name;
	private String url;
	private String size;
	private String type;
	private String state;

}
