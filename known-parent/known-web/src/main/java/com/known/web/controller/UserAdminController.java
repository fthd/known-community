package com.known.web.controller;

import com.aliyun.oss.OSSClient;
import com.known.common.enums.BlogStatusEnum;
import com.known.common.enums.OrderByEnum;
import com.known.common.enums.Code;
import com.known.common.model.*;
import com.known.common.utils.AliyunOSSClientUtil;
import com.known.common.utils.Constants;
import com.known.common.utils.StringUtils;
import com.known.common.vo.OutResponse;
import com.known.common.vo.PageResult;
import com.known.exception.BussinessException;
import com.known.manager.query.BlogQuery;
import com.known.manager.query.CollectionQuery;
import com.known.manager.query.MessageQuery;
import com.known.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/userAdmin")
public class UserAdminController extends BaseController {
	
private Logger logger = LoggerFactory.getLogger(UserAdminController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogCategoryService blogCategoryService;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private CollectionService collectionService;

	@Value("${ABSOLUTE_PATH}")
	private String ABSOLUTE_PATH;
	
	@RequestMapping
	public ModelAndView updateUser(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/update_user");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" +  Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("updateUserInfo")
	public OutResponse<Object> updateUserInfo(HttpSession session, User user){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		user.setUserid(sessionUser.getUserid());
		try {
			this.userService.updateUserInfo(user);
			outResponse.setCode(Code.SUCCESS);
		} catch (BussinessException e) {
			logger.error("修改出错", e);
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
		}catch (Exception e) {
			logger.error("修改出错{}", e);
			outResponse.setMsg("修改出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("preUpdateUserPage")
	public ModelAndView preUpdateUserPage(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/update_userpage");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" +  "Constants.LOGINABSOLUTEPATH");
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	
	@ResponseBody
	@RequestMapping("saveUserPage")
	public OutResponse<Object> saveUserPage(HttpSession session, Integer userPage){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		Integer userId = this.getUserid(session);
		User user = new User();
		user.setUserid(userId);
		try {
			userPage = userPage == null ? 0 : userPage;
			user.setUserPage(userPage);
			this.userService.updateUserWithoutValidate(user);
			outResponse.setCode(Code.SUCCESS);
		} catch (Exception e) {
			logger.error("修改出错{}", e);
			outResponse.setMsg("修改出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	
	@RequestMapping("preUpdatePassword")
	public ModelAndView preUpdatePassword(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/update_password");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" +  "Constants.LOGINABSOLUTEPATH");
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	
	@ResponseBody
	@RequestMapping("modifyPassword")
	public OutResponse<Object> modifyPassword(HttpSession session, String oldPassword, String newPassword){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		try {
			this.userService.updatePassword(sessionUser.getUserid(), oldPassword, newPassword);
			outResponse.setCode(Code.SUCCESS);
		} catch (BussinessException e) {
			logger.error("修改出错", e);
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
		}catch (Exception e) {
			logger.error("修改出错", e);
			outResponse.setMsg("修改出错,请重试{}");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("preUpdateUserIcon")
	public ModelAndView preUpdateUserIcon(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/update_usericon");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" +  "Constants.LOGINABSOLUTEPATH");
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("saveSysUserIcon")
	public OutResponse<Object> saveSysUserIcon(HttpSession session, String userIcon){
		OutResponse<Object> outResponse = new OutResponse<>();
		Integer userId = this.getUserid(session);
		User user = new User();
		user.setUserid(userId);
		try {
			String dest = "/resources/images/user_icon/" + userId + ".jpg";
			this.userService.copyUserIcon(user,userIcon, dest,userId+"");
			//user.setUserIcon(dest);
			this.userService.updateUserWithoutValidate(user);
			outResponse.setCode(Code.SUCCESS);
		} catch (Exception e) {
			logger.error("修改出错{}", e);
			outResponse.setMsg("修改出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("saveUserIcon")
	public OutResponse<Object> saveUserIcon(HttpSession session, String img, Integer x1,
                                             Integer y1, Integer width, Integer height){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		if(StringUtils.isEmpty(img)){
			outResponse.setMsg("修改出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
			return outResponse;
		}
		Integer userId = this.getUserid(session);
		User user = new User();
		user.setUserid(userId);
		try {
			String dest = "user_icon/" + userId + ".jpg";
			String source = ABSOLUTE_PATH + "/upload/" + img;
			File file = new File(source);
			BufferedImage image = ImageIO.read(file);
			BufferedImage subImage = image.getSubimage(x1, y1, width, height);
			String descPath = ABSOLUTE_PATH + "/resources/images/" + dest;
			File destFile = new File(descPath);
			ImageIO.write(subImage, "JPEG", destFile);
			OSSClient ossClient= AliyunOSSClientUtil.getOSSClient();
			AliyunOSSClientUtil.uploadObject2OSS(ossClient, destFile, "user_icon/");
			user.setUserIcon(AliyunOSSClientUtil.getUrl(ossClient,"user_icon/"+userId+".jpg"));
			this.userService.updateUserWithoutValidate(user);
			file.delete();
			destFile.delete();
			outResponse.setCode(Code.SUCCESS);
		} catch (Exception e) {
			logger.error("修改出错{}", e);
			outResponse.setMsg("修改出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("preUpdateUserBg")
	public ModelAndView preUpdateUserBg(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/update_userbg");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("saveSysUserBg")
	public OutResponse<Object> saveSysUserBg(HttpSession session, String background){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		Integer userId = this.getUserid(session);
		User user = new User();
		user.setUserid(userId);
		try {
			user.setUserBg(background);
			this.userService.updateUserWithoutValidate(user);
			outResponse.setCode(Code.SUCCESS);
		} catch (Exception e) {
			logger.error("修改出错{}", e);
			outResponse.setMsg("修改出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("preAddBlog")
	public ModelAndView preAddBlog(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/add_blog");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.addObject("categories", this.blogCategoryService.findBlogCategoryList(sessionUser.getUserid()));
		} catch (BussinessException e) {
			logger.error("获取用户信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("addBlog")
	public OutResponse<Integer> addBlog(HttpSession session, Blog blog, Attachment attachment){
		OutResponse<Integer> outResponse = new OutResponse<Integer>();
		try {
			blog.setStatus(BlogStatusEnum.PUBLIC);
			this.setUserBaseInfo(Blog.class, blog, session);
			if(blog.getBlogId()!=null){
				this.blogService.modifyBlog(blog, attachment);
			}
			else{
				this.blogService.addBlog(blog, attachment);
			}
			outResponse.setCode(Code.SUCCESS);
			outResponse.setData(blog.getBlogId());
		}catch(BussinessException e){
			logger.error("添加话题出错", e);
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
		}
		catch (Exception e) {
			logger.error("添加话题出错{}", e);
			outResponse.setMsg("添加话题出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("addDraftBlog")
	public OutResponse<Integer> addDraftBlog(HttpSession session, Blog blog){
		OutResponse<Integer> outResponse = new OutResponse<Integer>();
		try {
			blog.setStatus(BlogStatusEnum.DRAFT);
			this.setUserBaseInfo(Blog.class, blog, session);
			if(blog.getBlogId()!=null){
				this.blogService.modifyBlog(blog, new Attachment());
			}
			else{
				this.blogService.addBlog(blog, new Attachment());
			}
			outResponse.setCode(Code.SUCCESS);
			outResponse.setData(blog.getBlogId());
		}catch(BussinessException e){
			logger.error("添加话题草稿出错{}", e);
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
		}
		catch (Exception e) {
			logger.error("添加话题草稿出错{}", e);
			outResponse.setMsg("添加话题草稿出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("blogList")
	public ModelAndView blogList(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/blog_list");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.addObject("categories", this.blogCategoryService.findBlogCategoryList(sessionUser.getUserid()));
		} catch (BussinessException e) {
			logger.error("获取话题列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("loadBlog")
	public OutResponse<Object> loadBlog(HttpSession session, BlogQuery blogQuery){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			blogQuery.setUserId(this.getUserid(session));
			blogQuery.setStatus(BlogStatusEnum.PUBLIC);
			PageResult<Blog> result = this.blogService.findBlogByPage(blogQuery);
			outResponse.setData(result);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("获取话题列表出错{}", e);
			outResponse.setMsg("获取话题列表出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("del_blog.action")
	public OutResponse<Object> deleteBlog(HttpSession session, Integer blogId){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			this.blogService.deleteBlog(blogId);
			outResponse.setCode(Code.SUCCESS);
		}catch(BussinessException e){
			logger.error("删除话题出错", e);
			outResponse.setMsg(e.getLocalizedMessage());
			outResponse.setCode(Code.BUSSINESSERROR);
		}
		catch (Exception e) {
			logger.error("删除话题出错{}", e);
			outResponse.setMsg("删除出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("edit_blog.action")
	public ModelAndView preModifyBlog(HttpSession session, Integer blogId){
		ModelAndView view = new ModelAndView("/page/admin/edit_blog");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.addObject("categories", this.blogCategoryService.findBlogCategoryList(sessionUser.getUserid()));
			Blog blog = blogService.showBlog(blogId);
			view.addObject("blog", blog);
		} catch (BussinessException e) {
			logger.error("获取话题信息失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("deleteBlogAttachment")
	public OutResponse<Object> deleteBlogAttachment(HttpSession session, Integer attachmentId){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			this.attachmentService.deleteFile(attachmentId);
			outResponse.setCode(Code.SUCCESS);
		} catch (Exception e) {
			logger.error("删除文件失败{}", e);
			outResponse.setMsg("删除文件失败");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("draftBlogList")
	public ModelAndView draftBlogList(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/draftblog_list");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.addObject("categories", this.blogCategoryService.findBlogCategoryList(sessionUser.getUserid()));
		} catch (BussinessException e) {
			logger.error("获取话题列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("loadDraftBlog")
	public OutResponse<Object> loadDraftBlog(HttpSession session, BlogQuery blogQuery){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			blogQuery.setUserId(this.getUserid(session));
			blogQuery.setStatus(BlogStatusEnum.DRAFT);
			PageResult<Blog> result = this.blogService.findBlogByPage(blogQuery);
			outResponse.setData(result);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("获取话题列表出错{}", e);
			outResponse.setMsg("获取话题列表出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("blog_category")
	public ModelAndView blogCategory(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/blog_category");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.addObject("categories", this.blogCategoryService.findBlogCategoryList(sessionUser.getUserid()));
		} catch (BussinessException e) {
			logger.error("获取话题分类失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("loadBlogCategories.action")
	public OutResponse<Object> loadBlogCategories(HttpSession session){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			List<BlogCategory> list = this.blogCategoryService.findBlogCategoryList(this.getUserid(session));
			outResponse.setData(list);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("获取话题分类出错{}", e);
			outResponse.setMsg("获取话题分类出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	
	@ResponseBody
	@RequestMapping("delBlogCategory.action")
	public OutResponse<Object> delBlogCategory(HttpSession session, Integer categoryId){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			this.blogCategoryService.deleteBlogCategory(categoryId);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("删除话题分类出错{}", e);
			outResponse.setMsg("删除话题分类出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("saveBlogCategory.action")
	public OutResponse<Object> saveBlogCategory(HttpSession session, BlogCategory blogCategory){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			blogCategory.setUserId(this.getUserid(session));
			this.blogCategoryService.saveOrUpdate(blogCategory);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("保存话题分类出错{}", e);
			outResponse.setMsg("保存话题分类出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("messageList")
	public ModelAndView messageList(HttpSession session){
		ModelAndView view = new ModelAndView("/page/admin/message_list");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
		} catch (BussinessException e) {
			logger.error("获取消息列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	
	@ResponseBody
	@RequestMapping("/load_user_message_list.action")
	public OutResponse<Object> load_user_message_list(HttpSession session, MessageQuery messageQuery){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			messageQuery.setReceivedUserId(this.getUserid(session));
			messageQuery.setOrderBy(OrderByEnum.MESSAGE_STATUS_ASC_CREATE_TIME_DESC);
			PageResult<Message> result = this.messageService.findMessageByPage(messageQuery);
			outResponse.setData(result);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("获取消息列表出错{}", e);
			outResponse.setMsg("获取消息列表出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	
	@ResponseBody
	@RequestMapping("mark_message_read.action")
	public OutResponse<Object> mark_message_read(HttpSession session, Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			this.messageService.update(ids, this.getUserid(session));
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("消息标记已读出错{}", e);
			outResponse.setMsg("标记已读出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("load_user_message_count.action")
	public OutResponse<Object> load_user_message_count(HttpSession session, MessageQuery messageQuery){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			messageQuery.setReceivedUserId(this.getUserid(session));
			messageQuery.setOrderBy(OrderByEnum.MESSAGE_STATUS_ASC_CREATE_TIME_DESC);
			Integer count = this.messageService.findMessageCount(messageQuery);
			outResponse.setData(count);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("获取消息列表出错{}", e);
			outResponse.setMsg("获取消息列表出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	
	@ResponseBody
	@RequestMapping("del_message.action")
	public OutResponse<Object> del_message(HttpSession session, Integer[] ids){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			this.messageService.delMessage(this.getUserid(session), ids);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("消息删除出错{}", e);
			outResponse.setMsg("消息删除出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@RequestMapping("readMessage.action")
	public ModelAndView readMessage(HttpSession session, Integer id){
		ModelAndView view = new ModelAndView(Constants.ERROR_404);
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.setViewName("redirect:" + this.messageService.getMessageById(id, this.getUserid(session)).getUrl());
			this.messageService.update(new Integer[]{id}, this.getUserid(session));
		} catch (BussinessException e) {
			logger.error("获取消息列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@RequestMapping("collection_list.action")
	public ModelAndView collection_list(HttpSession session, CollectionQuery collectionQuery){
		ModelAndView view = new ModelAndView("/page/admin/collection_list");
		UserRedis sessionUser = (UserRedis) session.getAttribute(Constants.SESSION_USER_KEY);
		if(sessionUser==null){
			view.setViewName("redirect:" + Constants.LOGINABSOLUTEPATH);
			return view;
		}
		try {
			User user = this.userService.findUserInfo4UserHome(sessionUser.getUserid());
			view.addObject("user", user);
			view.addObject("articleType", collectionQuery.getArticleType().getType());
		} catch (BussinessException e) {
			logger.error("获取收藏列表失败：{}", e);
			view.setViewName("redirect:" + Constants.ERROR_404);
			return view;
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("load_collection.action")
	public OutResponse<Object>load_collection(HttpSession session, CollectionQuery collectionQuery){
		OutResponse<Object> outResponse = new OutResponse<Object>();
		try {
			PageResult<Collection> result = this.collectionService.findCollectionByPage(collectionQuery);
			outResponse.setData(result);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("获取收藏列表出错{}", e);
			outResponse.setMsg("获取收藏列表出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	@ResponseBody
	@RequestMapping("del_collection.action")
	public OutResponse<Object> del_collection(HttpSession session, Collection collection){
		OutResponse<Object> outResponse = new OutResponse<>();
		try {
			this.collectionService.deleteCollection(collection);
			outResponse.setCode(Code.SUCCESS);
		}catch (Exception e) {
			logger.error("收藏删除出错{}", e);
			outResponse.setMsg("收藏删除出错,请重试");
			outResponse.setCode(Code.SERVERERROR);
		}
		return outResponse;
	}
	
	
}
