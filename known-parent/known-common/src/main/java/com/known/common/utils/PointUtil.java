package com.known.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.known.common.vo.Point;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PointUtil {
	static String host = "http://saip.market.alicloudapi.com";
	private static Map<String,Object> map = new HashMap<>();
	public static Point getPoint(String ip){
		String jsonResult = request(ip);
		if(StringUtil.isEmpty(jsonResult)){
			return null;
		}
		map.put("data", JSON.parseObject(jsonResult).get("showapi_res_body"));
		Point point= JSON.parseObject(JSON.toJSONString(map), Point.class);
		map.clear();
		return point;
	}

	public static String request(String ip) {
		String path = "/ip";
		String method = "GET";
		String appcode = "7dffedc955d6496eb08722d589c0b19e";
		Map<String, String> headers = new HashMap<>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<>();
		querys.put("ip", ip);
		String result = null;
		try {
			HttpResponse response = HttpUtil.doGet(host, path, method, headers, querys);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = inputStreamToString(entity.getContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private static String inputStreamToString(InputStream is) {
		String line = "";
		StringBuilder total = new StringBuilder();
		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = null;
		try {
			 rd = new BufferedReader(new InputStreamReader(is,"utf-8"));
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return total.toString();
	}

	public static void main(String[] args) {
		JSONObject objec = JSON.parseObject(request("39.105.199.202"));
		// "showapi_res_body":{"country":"中国","city":"北京","isp":"阿里云","ip":"39.105.199.202","en_name_short":"CN","county":"","city_code":"110100","continents":"亚洲","lnt":"116.405285","en_name":"China","region":"北京","ret_code":0,"lat":"39.904989"}
		System.out.println(objec.toJSONString());
	}
}
