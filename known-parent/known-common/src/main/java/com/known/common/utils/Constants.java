package com.known.common.utils;

/**
 * 常量、前缀定义
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-06 18:25
 */
public class Constants {
	public static final int LENGTH_1 =1,LENGTH_6 =6, LENGTH_16 =16, LENGTH_20 = 20;
	
	public static final String SESSION_USER_KEY = "session_user_key";

	public static final String COOKIE_USER_INFO = "cookie_user_info";

	// 用户信息前缀
	public static final String REDIS_USER_KEY = "redis_user_key_prefix";
	
	public static final int CONTINUOUS_SIGNIN = 7;
	
	public static final int SMALL_IMAGE_WIDTH = 150;
	
	public static final int SMALL_IMAGE_HEIGHT = 100;
	
	public static final int MAX_FILE_NUM = 3;
	
	public static final String USER_ICON_URL = "default_usericon/"
			;
	public static final String USER_BG_URL = "user_bg/";
	
	public static final int EXAM_MAX_TITLE = 50;
	
	public static final String Y = "Y";
	
	public static final String N = "N";
	
	public static final String REALPATH = "realpath";
	
	public static final String CACHE_KEY_BBS_CATEGORY = "bbsCategoryCache";
	
	public static final String CACHE_KEY_EXAM_CATEGORY = "examCategoryCache";
	
	public static final String CACHE_KEY_KNOWLEDGE_CATEGORY = "knowledgeCategoryCache";
	
	public static final String CACHE_KEY_ASK_CATEGORY = "askCategoryCache";
	
	public static final String CACHE_KEY_CATEGORY = "CategoryCache";
	/**
	 * 错误页面
	 */
	public static final String ERROR_404 = "/page/error/404.html";
	
	public static final String LOGINABSOLUTEPATH = "/login";
	
	public static final String TASKKEY = "taskkey";
	
	public static final String SOLRSERVERURL = "http://www.81wda.com/solr";
	
	public static final String ABSOLUTEPATH = "/usr/tomcat/apache-tomcat-8.5.28/webapps/wda";//"G:\\FzqBlog\\blog-web\\target\\blog-web-0.0.1-SNAPSHOT";// /home/default/apache-tomcat-7.0.34/webapps/blog-web"
	//public static final String ABSOLUTEPATH = "G:\\FzqBlog\\blog-web\\target\\blog-web-0.0.1-SNAPSHOT";//"G:\\FzqBlog\\blog-web\\target\\blog-web-0.0.1-SNAPSHOT";// /home/default/apache-tomcat-7.0.34/webapps/blog-web"
	public static final String DOMAIN = "http://www.81wda.com";


}
