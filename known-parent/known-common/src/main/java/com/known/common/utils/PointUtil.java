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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PointUtil {
	static String httpUrl = "http://apis.baidu.com/chazhao/ipsearch/ipsearch?ip=";
	static String host = "http://saip.market.alicloudapi.com";
	private static Map<String,Object> map = new HashMap<>();
	public static Point getPoint(String ip){
		String jsonResult = request(ip);
		if(StringUtils.isEmpty(jsonResult)){
			return null;
		}
		map.put("data", JSON.parseObject(jsonResult).get("showapi_res_body"));
		Point point= JSON.parseObject(JSON.toJSONString(map), Point.class);
		map.clear();
		return point;
	}

	/**
	 * @param
	 *            :请求接口
	 * @param
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String ip) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + ip;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "1b63b7ef2da1b7bbd56662d8a1aadafb");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	public static String request(String ip) {
		String path = "/ip";
		String method = "GET";
		String appcode = "7dffedc955d6496eb08722d589c0b19e";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("ip", ip);
		String result = null;
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
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
		JSONObject objec = JSON.parseObject(request("116.93.126.166"));
		System.out.println(objec.toJSONString());
	}
}
