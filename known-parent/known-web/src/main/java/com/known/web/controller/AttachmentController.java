package com.known.web.controller;

import com.aliyun.oss.OSSClient;
import com.known.common.enums.ResponseCode;
import com.known.common.model.Attachment;
import com.known.common.model.UserRedis;
import com.known.common.utils.AliyunOSSClientUtil;
import com.known.common.utils.Constants;
import com.known.common.vo.AjaxResponse;
import com.known.exception.BussinessException;
import com.known.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.OutputStream;


@Controller
public class AttachmentController extends BaseController{
	private Logger logger  = LoggerFactory.getLogger(AttachmentController.class);
	
	@Autowired
	private AttachmentService attachmentService;
	
	@ResponseBody
	@RequestMapping("checkDownload")
	public AjaxResponse<Boolean> checkDownload(HttpSession session, Integer topicId, Integer attachmentId){
		AjaxResponse<Boolean> ajaxResponse = new AjaxResponse<Boolean>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
			ajaxResponse.setErrorMsg("请先登录");
			return ajaxResponse;
		}
		try {
			this.attachmentService.checkDownload(attachmentId, topicId, sessionUser);
			ajaxResponse.setData(Boolean.TRUE);
			ajaxResponse.setResponseCode(ResponseCode.SUCCESS);
		} catch (BussinessException e) {
			logger.error("{}获取下载权限异常", e);
			ajaxResponse.setData(Boolean.FALSE);
			ajaxResponse.setErrorMsg(e.getLocalizedMessage());
			ajaxResponse.setResponseCode(ResponseCode.BUSSINESSERROR);
		}catch (Exception e) {
			logger.error("{}获取下载权限异常", e);
			ajaxResponse.setData(Boolean.FALSE);
			ajaxResponse.setErrorMsg("系统异常");
			ajaxResponse.setResponseCode(ResponseCode.SERVERERROR);
		}
		return ajaxResponse;
	}
	
	@RequestMapping("downloadAction")
	public ModelAndView downloadAttachment(HttpSession session, HttpServletRequest request,
                                           HttpServletResponse response, Integer attachmentId){
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		ModelAndView view = new ModelAndView("redirect:" + Constants.ERROR_404);
		InputStream in = null;
		OutputStream os = null;
		try {
			OSSClient ossClient= AliyunOSSClientUtil.getOSSClient();
			Attachment attachment = this.attachmentService.downloadAttachment(sessionUser, attachmentId);
			 in = AliyunOSSClientUtil.getOSS2InputStream(ossClient,attachment.getFileUrl());
			//String realpath = session.getServletContext().getRealPath("/") + "/upload/";
			//String filepath = realpath + attachment.getFileUrl();
		//	File file = new File(filepath);
			//in = new FileInputStream(file);
			os = response.getOutputStream();
			 response.setContentType("application/x-msdownload; charset=utf-8"); 
			 String fileName = attachment.getFileName();
			 String agent = request.getHeader("USER-AGENT");
			 if(null != agent && -1 != agent.indexOf("Mozilla")){
		            fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		        }
			 else {
				 fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
			}
			 response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			 int byteRead = 0;
             byte[] buffer = new byte[1024 * 5];
             while((byteRead=in.read(buffer)) != -1){
                 os.write(buffer,0,byteRead);
             }
             os.flush();
             return null;
		} catch (Exception e) {
			logger.error("{}下载出错", e);
		}
		finally{
			try {
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				os.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return view;
	}
	
}