package com.known.web.controller;

import com.aliyun.oss.OSSClient;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.Code;
import com.known.common.utils.DateUtil;
import com.known.common.vo.UeditorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/imageUploader")
public class ImageUploader{

	private static final int MAX_FILE_MAX = 3 * 1024 * 1024;

	@Value("${Absolute_Path}")
	private String Absolute_Path;

	@ResponseBody
	@RequestMapping("/imageUpload.action")
	public Map<String, Object> imageUpload(HttpSession session, MultipartHttpServletRequest multirequest,
										   HttpServletResponse response){
			/*	String realPath = ServerUtils.getRealPath() + "/upload";*/
				//String realPath = ServerUtils.getRealPath() + "/upload";
				//初始化OSSClient
//				OSSClient ossClient= AliyunOSSClientUtil.getOSSClient();
				//String realPath = ServerUtils.getRealPath() + "/upload";
				String realPath = "upload/";
				Map<String, Object> map = new HashMap<String, Object>();
				Iterator<String> itr = multirequest.getFileNames();
				if(itr.hasNext()){
					MultipartFile multipartFile = multirequest.getFile(itr.next());
					long size = multipartFile.getSize();
					if(size > MAX_FILE_MAX){
						map.put("code", Code.BUSSINESSERROR);
						map.put("msg", "文件不能超过3M");
						return map;
					}
					String fileName = multipartFile.getOriginalFilename();
					String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
					if(!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
							!"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)){
						map.put("code", Code.BUSSINESSERROR);
						map.put("msg", "只能上传图片");
						return map;
					}
					String current = String.valueOf(System.currentTimeMillis());
					fileName = current + "." + suffix;
					String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
					String savePath = realPath +  saveDir+"/";
					/*String savePath = saveDir + "/" + fileName;
 					String fileDir = realPath + "/" + saveDir;*/
 				/*	File dir = new File(fileDir);
 					if(!dir.exists()){
 						dir.mkdirs();
 					}
					String filePath = fileDir + "/" + fileName;
					File file = new File(filePath);*/
					try {
//						AliyunOSSClientUtil.uploadObject2OSS(ossClient, multipartFile.getInputStream(),multipartFile.getOriginalFilename(),multipartFile.getSize() , savePath);
						//multipartFile.transferTo(file);
						map.put("code", Code.SUCCESS);
//						map.put("savePath", AliyunOSSClientUtil.getUrl(ossClient,savePath+multipartFile.getOriginalFilename()));
						return map;
						} catch (Exception e) {
							map.put("code", Code.SERVERERROR);
							map.put("msg", "服务器异常,上传失败");
							return map;
					}
				}
				return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/ueditorImageUpload.action")
	public UeditorResponse ueditorImageUpload(HttpSession session, MultipartHttpServletRequest multirequest,
											  HttpServletResponse response){
				//String realPath = ServerUtils.getRealPath() + "/upload";
				//初始化OSSClient
//				OSSClient ossClient= AliyunOSSClientUtil.getOSSClient();
				//String realPath = ServerUtils.getRealPath() + "/upload";
				String realPath = "upload/";
				UeditorResponse ueditorResponse = new UeditorResponse();
				Iterator<String> itr = multirequest.getFileNames();
				if(itr.hasNext()){
					MultipartFile multipartFile = multirequest.getFile(itr.next());
					long size = multipartFile.getSize();
					if(size > MAX_FILE_MAX){
						ueditorResponse.setState("图片过大");
						return ueditorResponse;
					}
					String fileName = multipartFile.getOriginalFilename();
					String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
					if(!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
							!"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)){
						ueditorResponse.setState("只能是图片");
						return ueditorResponse;
					}
					String current = String.valueOf(System.currentTimeMillis());
					fileName = current + "." + suffix;
					String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
					String savePath = realPath +  saveDir+"/";

					/*String savePath = saveDir + "/" + fileName;
 					String fileDir = realPath + "/" + saveDir;
 					File dir = new File(fileDir);
 					if(!dir.exists()){
 						dir.mkdirs();
 					}
					String filePath = fileDir + "/" + fileName;
					File file = new File(filePath);*/
					try {
						//multipartFile.transferTo(file);
//						AliyunOSSClientUtil.uploadObject2OSS(ossClient, multipartFile.getInputStream(),multipartFile.getOriginalFilename(),multipartFile.getSize() , savePath);
						ueditorResponse.setState("SUCCESS");
						//ueditorResponse.setUrl(this.getRealPath(multirequest) + "/upload/" + savePath);
//						ueditorResponse.setUrl(AliyunOSSClientUtil.getUrl(ossClient,savePath+multipartFile.getOriginalFilename()));
						return ueditorResponse;
						} catch (Exception e) {
							ueditorResponse.setState("上传参数出错");
							e.printStackTrace();
							return ueditorResponse;
					}
				}
				return ueditorResponse;
	}
	
	/*@ResponseBody
	@RequestMapping("imageUpload2Temp.action")
	public Map<String, Object> imageUpload2Temp(HttpSession session, MultipartHttpServletRequest multirequest, 
			HttpServletResponse response){

				//初始化OSSClient
				OSSClient ossClient= AliyunOSSClientUtil.getOSSClient();
				//String realPath = ServerUtils.getRealPath() + "/upload";
		        String realPath = "upload/";
				Map<String, Object> map = new HashMap<String, Object>();
				Iterator<String> itr = multirequest.getFileNames();
				if(itr.hasNext()){
					MultipartFile multipartFile = multirequest.getFile(itr.next());
					long size = multipartFile.getSize();
					if(size > MAX_FILE_MAX){
						map.put("code", Code.BUSSINESSERROR);
						map.put("msg", "文件不能超过3M");
						return map;
					}
					String fileName = multipartFile.getOriginalFilename();
					String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
					if(!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
							!"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)){
						map.put("code", Code.BUSSINESSERROR);
						map.put("msg", "只能上传图片");
						return map;
					}
					String current = String.valueOf(System.currentTimeMillis());
					fileName = current + "." + suffix;
					String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
					String savePath = realPath  + saveDir+"/";
					*//*String savePath = saveDir + "/" + fileName;
 					String fileDir = realPath + "/" + saveDir;
 					File dir = new File(fileDir);
 					if(!dir.exists()){
 						dir.mkdirs();
 					}
					String filePath = fileDir + "/" + fileName;
					File file = new File(filePath);*//*
					try {
						//multipartFile.transferTo(file);
						AliyunOSSClientUtil.uploadObject2OSS(ossClient, multipartFile.getInputStream(),multipartFile.getOriginalFilename(),multipartFile.getSize() , savePath);
						map.put("code", Code.SUCCESS);
						map.put("savePath", AliyunOSSClientUtil.getUrl(ossClient,savePath+multipartFile.getOriginalFilename()));
						return map;
						} catch (Exception e) {
							map.put("code", Code.SERVERERROR);
							map.put("msg", "服务器异常,上传失败");
							return map;
					}
				}
				return map;
	}*/

	@ResponseBody
	@RequestMapping("/imageUpload2Temp.action")
	public Map<String, Object> imageUpload2Temp(HttpSession session, MultipartHttpServletRequest multirequest,
                                                HttpServletResponse response){

		String realPath = Absolute_Path + "/upload";
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<String> itr = multirequest.getFileNames();
		if(itr.hasNext()){
			MultipartFile multipartFile = multirequest.getFile(itr.next());
			long size = multipartFile.getSize();
			if(size > MAX_FILE_MAX){
				map.put("code", Code.BUSSINESSERROR);
				map.put("msg", "文件不能超过3M");
				return map;
			}
			String fileName = multipartFile.getOriginalFilename();
			String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
			if(!"JPG".equalsIgnoreCase(suffix) && !"BMP".equalsIgnoreCase(suffix) &&
					!"gif".equalsIgnoreCase(suffix) && !"PNG".equalsIgnoreCase(suffix)){
				map.put("code", Code.BUSSINESSERROR);
				map.put("msg", "只能上传图片");
				return map;
			}
			String current = String.valueOf(System.currentTimeMillis());
			fileName = current + "." + suffix;
			String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
			String savePath = saveDir + "/" + fileName;
			String fileDir = realPath + "/" + saveDir;
			File dir = new File(fileDir);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String filePath = fileDir + "/" + fileName;
			File file = new File(filePath);
			try {
				multipartFile.transferTo(file);
				map.put("code", Code.SUCCESS);
				map.put("savePath", savePath);
				return map;
			} catch (Exception e) {
				map.put("code", Code.SERVERERROR);
				map.put("msg", "服务器异常,上传失败");
				return map;
			}
		}
		return map;
	}
	
	private String getRealPath(HttpServletRequest request){
		String port = request.getServerPort() == 80 ? "": ":" + request.getServerPort();
		String realpath = "http://" + request.getServerName() + port;
		return realpath;
	}
}
