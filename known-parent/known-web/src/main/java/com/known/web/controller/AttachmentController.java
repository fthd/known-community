package com.known.web.controller;

import com.known.common.config.UrlConfig;
import com.known.common.config.UserConfig;
import com.known.common.enums.CodeEnum;
import com.known.common.model.Attachment;
import com.known.common.model.SessionUser;
import com.known.common.utils.AbsolutePathUtil;
import com.known.common.vo.OutResponse;
import com.known.exception.BussinessException;
import com.known.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 附件信息
 * @author tangjunxiang
 * @version 1.0
 * @date 2019-05-24 00:08
 */
@RestController
@RequestMapping("/attachment")
public class AttachmentController extends BaseController{
	private Logger logger  = LoggerFactory.getLogger(AttachmentController.class);
	
	@Autowired
	private AttachmentService attachmentService;

	@Resource
	private UserConfig userConfig;

	@Resource
	private UrlConfig urlConfig;

	@RequestMapping("/checkDownload")
	public OutResponse<Boolean> checkDownload(HttpSession session, String topicId, String attachmentId){
		OutResponse<Boolean> outResponse = new OutResponse<>();
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		if(sessionUser==null){
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
			outResponse.setMsg("请先登录");
			return outResponse;
		}
		try {
			attachmentService.checkDownload(attachmentId, topicId, sessionUser);
			outResponse.setData(Boolean.TRUE);
			outResponse.setCode(CodeEnum.SUCCESS);
		} catch (BussinessException e) {
			logger.error("{}获取下载权限异常", e);
			outResponse.setData(Boolean.FALSE);
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(CodeEnum.BUSSINESSERROR);
		}catch (Exception e) {
			logger.error("{}获取下载权限异常", e);
			outResponse.setData(Boolean.FALSE);
			outResponse.setMsg("系统异常");
			outResponse.setCode(CodeEnum.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("/downloadAction")
	public ModelAndView downloadAttachment(HttpSession session, HttpServletRequest request,
                                           HttpServletResponse response, String attachmentId){
		SessionUser sessionUser = (SessionUser) session.getAttribute(userConfig.getSession_User_Key());
		ModelAndView view = new ModelAndView("redirect:" + urlConfig.getError_404());
		InputStream in = null;
		OutputStream os = null;
		try {
			Attachment attachment = attachmentService.downloadAttachment(sessionUser, attachmentId);

			String filepath = AbsolutePathUtil.getAbsoluteStaticPath() + attachment.getFileUrl();
			File file = new File(filepath);
			in = new FileInputStream(file);
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
