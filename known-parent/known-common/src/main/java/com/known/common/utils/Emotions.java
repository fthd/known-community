package com.known.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 情绪表情包
 *
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-20 23:35
 */
public class Emotions {
	/**
	 * 
	 */
	private static String[] mobile = {"[围观]", "[威武]", "[给力]", "[浮云]", "[奥特曼]", "[兔子]", "[熊猫]", "[飞机]", "[冰棍]", "[干杯]", "[给跪了]", "[囧]", "[风扇]", "[呵呵]", "[嘻嘻]", "[哈哈]", "[爱你]", "[晕]", "[泪]", "[馋嘴]", "[抓狂]", "[哼]", "[抱抱]", "[可爱]", "[怒]", "[汗]", "[困]", "[害羞]", "[睡觉]", "[钱]", "[偷笑]", "[酷]", "[衰]", "[吃惊]", "[闭嘴]", "[鄙视]", "[挖鼻屎]", "[花心]", "[鼓掌]", "[失望]", "[思考]", "[生病]", "[亲亲]", "[怒骂]", "[太开心]", "[懒得理你]", "[右哼哼]", "[左哼哼]", "[嘘]", "[委屈]", "[吐]", "[可怜]", "[打哈气]", "[黑线]", "[顶]", "[疑问]", "[握手]", "[耶]", "[好]", "[弱]", "[不要]", "[没问题]", "[赞]", "[来]", "[蛋糕]", "[心]", "[伤心]", "[钟]", "[猪头]", "[话筒]", "[月亮]", "[下雨]", "[太阳]", "[蜡烛]", "[礼花]", "[玫瑰]"};
	/**
	 * 
	 */
	private static String prefix = "<img src='" + Constants.DOMAIN + "/resources/images/emotions/";
	/**
	 * 
	 */
	private static String end = ".gif'>";
	/**
	 * 
	 */
	private static Map<String, String> mobileToWebEmotions = new HashMap<>();
	/**
	 * 
	 */
	private static Map<String, String> webToMobileEmotions = new HashMap<>();
	/**
	 * 
	 */
	static {
		for(int i = 0; i < mobile.length; i++){
				mobileToWebEmotions.put(mobile[i], prefix + i + end);
				webToMobileEmotions.put(prefix + i + end, mobile[i]);
		}
	}
	/**
	 * 
	 * @author CL
	 *
	 */
	public static enum Dev{
		MOBILE(prefix + "[0-9]+" + end, webToMobileEmotions),
		WEB("\\[[\u4E00-\u9fA5]*\\]", mobileToWebEmotions);
		
		private String regex;
		private Map<String, String> map;
		private Dev(String regex, Map<String, String> map) {
			this.regex = regex;
			this.map = map;
		}	
	}
	
	public static String formatEmotion(String message, Dev dev){
		Pattern pattern = null;
		Matcher matcher = null;
		StringBuffer buffer = new StringBuffer();
		pattern  = Pattern.compile(dev.regex);
		matcher = pattern.matcher(message);
		while (matcher.find()) {
			String str = dev.map.get(matcher.group());
			if(str == null){
				continue;
			}
			matcher.appendReplacement(buffer, str);
		}
		matcher.appendTail(buffer);
		return buffer.toString();
	}
}
