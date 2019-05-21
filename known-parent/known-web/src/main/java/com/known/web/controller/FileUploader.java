package com.known.web.controller;

import com.aliyun.oss.OSSClient;
import com.known.common.enums.DateTimePatternEnum;
import com.known.common.enums.ResponseCode;
import com.known.common.utils.AliyunOSSClientUtil;
import com.known.common.utils.DateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class FileUploader {
	private static final int MAX_FILE_MAX = 10 * 1024 * 1024;
	
	@ResponseBody
	@RequestMapping("fileUpload.action")
	public Map<String, Object> fileUpload(HttpSession session, MultipartHttpServletRequest multirequest,
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
						map.put("responseCode", ResponseCode.BUSSINESSERROR);
						map.put("msg", "文件不能超过10M");
						return map;
					}
					String fileName = multipartFile.getOriginalFilename();
					String suffix = fileName.substring(fileName.lastIndexOf(".")  + 1);
					if(!"zip".equalsIgnoreCase(suffix) && !"rar".equalsIgnoreCase(suffix)){
						map.put("responseCode", ResponseCode.BUSSINESSERROR);
						map.put("msg", "只能ZIP或RAR文件");
						return map;
					}
					String current = String.valueOf(System.currentTimeMillis());
					fileName = current + "." + suffix;
					String saveDir = DateUtil.format(new Date(), DateTimePatternEnum.YYYYMM.getPattern());
					String savePath = realPath +  saveDir+"/";
 					/*String fileDir = realPath + "/" + saveDir;
 					File dir = new File(fileDir);
 					if(!dir.exists()){
 						dir.mkdirs();
 					}
					String filePath = fileDir + "/" + fileName;
					File file = new File(filePath);*/
					try {
						AliyunOSSClientUtil.uploadObject2OSS(ossClient, multipartFile.getInputStream(),multipartFile.getOriginalFilename(),multipartFile.getSize() , savePath);
					//	multipartFile.transferTo(file);
						map.put("responseCode", ResponseCode.SUCCESS);
					//	map.put("savePath", AliyunOSSClientUtil.getUrl(ossClient,savePath+multipartFile.getOriginalFilename()));
						map.put("savePath", savePath+multipartFile.getOriginalFilename());
						return map;
						} catch (Exception e) {
							map.put("responseCode", ResponseCode.SERVERERROR);
							map.put("msg", "服务器异常,上传失败");
							return map;
					}
				}
				return map;
	}
	
	
	/**
	 * 删除文件
	 */
	@ResponseBody
	@RequestMapping("fileDelete.action")
	public Map<String, Object> fileDelete(HttpSession session, String fileName, HttpServletResponse response){
			//	String realPath = ServerUtils.getRealPath() + "/upload";
				String realPath = "/upload";
				Map<String, Object> map = new HashMap<String, Object>();
					String filePath = realPath + "/" + fileName;
					File file = new File(filePath);
					try {
						if(file.exists()){
							file.delete();
						}
						map.put("responseCode", ResponseCode.SUCCESS);
						return map;
						} catch (Exception e) {
							map.put("responseCode", ResponseCode.SERVERERROR);
							map.put("msg", "服务器异常,删除失败");
							return map;
					}
	}
	
	
}
