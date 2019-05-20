package com.known.common.enums;

public enum PageSize {
	PAGE_SIZE5(5), PAGE_SIZE10(10), PAGE_SIZE15(15), PAGE_SIZE20(20), PAGE_SIZE21(
			21), PAGE_SIZE50(50), PAGE_SIZE300(300);
	private int size;

	private PageSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
