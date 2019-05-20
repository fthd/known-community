package com.known.common.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class State {
	private boolean opened;
	
	private boolean selected;
	
	public State(boolean opened, boolean selected) {
		this.opened = opened;
		this.selected = selected;
	}

}
