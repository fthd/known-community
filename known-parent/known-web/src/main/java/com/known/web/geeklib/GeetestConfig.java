package com.known.web.geeklib;

/**
 * GeetestWeb配置文件
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-23 09:49
 */
public class GeetestConfig {

	// 填入自己的captcha_id和private_key
	private static final String geetest_id = "8cd03e610e1710491cdc61d06698bea9";
	private static final String geetest_key = "34f83cddca0a063e7906f7358f6e47cf";

	public static final String getGeetest_id() {
		return geetest_id;
	}

	public static final String getGeetest_key() {
		return geetest_key;
	}

}
