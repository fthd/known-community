package com.known.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Tree {

	private String id;
	
	private String text;
	
	private State state;
	
	private List<Tree> children;
	
}
