package com.known.quartz;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskMessage {
	
	private String id;
	
	private String taskClassz;
	
	private String taskMethod;
	
	private String taskTime;
	
}
