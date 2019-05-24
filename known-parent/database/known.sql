SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_user`;
CREATE TABLE `known_user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `user_icon` varchar(500) DEFAULT '' COMMENT '头像',
  `user_bg` varchar(100) DEFAULT NULL COMMENT '用户背景',
  `age` varchar(10) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别',
  `characters` varchar(200) DEFAULT NULL COMMENT '座右铭',
  `mark` int(11) DEFAULT '0' COMMENT '积分',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `work` varchar(200) DEFAULT NULL COMMENT '工作',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `activation_code` varchar(25) DEFAULT NULL COMMENT '激活码',
  `status` int(1) DEFAULT '0' COMMENT '0-未激活 1-已激活',
  `user_page` int(2) DEFAULT '0' COMMENT '用户页面模板',
  PRIMARY KEY (`userid`),
  KEY `user_index_username` (`user_name`),
  KEY `user_index_email` (`email`),
  KEY `user_index_userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=10086 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 用户签到表
-- ----------------------------
DROP TABLE IF EXISTS `known_sign_in`;
CREATE TABLE `known_sign_in` (
  `userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_icon` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `sign_date` date NOT NULL COMMENT '签到日期',
  `sign_time` datetime DEFAULT NULL COMMENT '签到具体时间',
  UNIQUE KEY `idx_userid_signdate` (`userid`,`sign_date`),
  KEY `signin_index_userid` (`userid`),
  KEY `signin_index_signdate` (`sign_date`)
) ENGINE=InnoDB AUTO_INCREMENT=10077 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 用户好友关联表
-- ----------------------------
DROP TABLE IF EXISTS `known_user_friend`;
CREATE TABLE `known_user_friend` (
  `user_id` int(50) NOT NULL COMMENT '用户ID',
  `friend_user_id` int(50) NOT NULL COMMENT '好友ID',
  `user_icon` varchar(1000) DEFAULT NULL COMMENT '头像',
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `friend_user_icon` varchar(1000) DEFAULT NULL COMMENT '好友头像',
  `friend_user_name` varchar(1000) DEFAULT NULL COMMENT '好友名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`,`friend_user_id`),
  KEY `friend_index_userid` (`user_id`),
  KEY `friend_index_friendid` (`friend_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 登录日志表
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(100) DEFAULT NULL COMMENT '账号',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '登录ip',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `lat` varchar(100) DEFAULT NULL COMMENT '纬度',
  `lng` varchar(100) DEFAULT NULL COMMENT '经度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=347 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 系统日志表
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(64) DEFAULT NULL COMMENT '用户名',
  `op_content` longtext COMMENT '操作内容',
  `client_ip` varchar(100) DEFAULT NULL COMMENT '客户端ID',
  `address` varchar(100) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24205 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 系统用户与权限关系表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户表ID',
  `role_id` int(11) DEFAULT NULL COMMENT '系统权限表ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_index` (`user_id`,`role_id`),
  KEY `FK_SYSTME_USER_ROLE_USER_ID` (`user_id`),
  KEY `FK_SYSTME_USER_ROLE_ROLE_ID` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- 系统权限表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(55) NOT NULL DEFAULT '' COMMENT '权限名称',
  `des` varchar(55) DEFAULT NULL COMMENT '描述',
  `seq` int(11) DEFAULT '1' COMMENT '序列',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '1' COMMENT '0-禁用  1-启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- 系统权限与权限详情关系表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(11) DEFAULT NULL COMMENT '权限表ID',
  `res_id` int(11) DEFAULT NULL COMMENT '系统权限详情表ID',
  PRIMARY KEY (`id`),
  KEY `FK_sys_ROLE_RES_RES_ID` (`res_id`),
  KEY `FK_sys_ROLE_RES_ROLE_ID` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5116 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- 系统权限详情表
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` int(11) DEFAULT NULL COMMENT '父节点ID',
  `name` varchar(111) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `iconCls` varchar(255) DEFAULT 'am-icon-file',
  `seq` int(11) DEFAULT '1',
  `type` int(1) DEFAULT '2' COMMENT '1 菜单 2 权限',
  `modifydate` timestamp NULL DEFAULT NULL,
  `enabled` int(1) DEFAULT '1' COMMENT '是否启用 1：启用  0：禁用',
  `level` int(11) DEFAULT '0',
  `key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- 板块分类信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_category`;
CREATE TABLE `known_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `pid` int(11) DEFAULT '0' COMMENT '父节点ID',
  `name` varchar(30) DEFAULT NULL COMMENT '分类名称',
  `desc` varchar(500) DEFAULT NULL COMMENT '分类描述',
  `rank` int(11) DEFAULT NULL COMMENT '分类排名',
  `show_in_topic` char(1) DEFAULT 'Y' COMMENT '是否是话题分类Y是N不是',
  `show_in_ask` char(1) DEFAULT 'Y' COMMENT '是否是问答分类Y是N不是',
  `show_in_knowledge` char(1) DEFAULT 'Y' COMMENT '是否是知识库分类Y是N不是',
  PRIMARY KEY (`category_id`),
  KEY `category_index_id` (`category_id`),
  KEY `idx_show_in_topic` (`show_in_topic`),
  KEY `idx_show_in_ask` (`show_in_ask`),
  KEY `idx_show_in_knowledge` (`show_in_knowledge`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;


-- ----------------------------
-- 问答信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_ask`;
CREATE TABLE `known_ask` (
  `ask_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问答ID',
  `p_category_id` int(11) DEFAULT NULL COMMENT '组ID',
  `category_id` int(11) DEFAULT NULL COMMENT '类型ID',
  `title` varchar(100) DEFAULT '' COMMENT '标题',
  `content` longtext COMMENT '内容',
  `summary` text COMMENT '内容摘要',
  `user_id` int(11) DEFAULT NULL COMMENT '作者ID',
  `user_icon` varchar(1000) DEFAULT '' COMMENT '作者头像',
  `user_name` varchar(1000) DEFAULT '' COMMENT '作者名字',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论人数',
  `read_count` int(11) DEFAULT '0' COMMENT '阅读人数',
  `collection_count` int(11) DEFAULT '0' COMMENT '收藏人数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢人数',
  `ask_image` mediumtext COMMENT '问答配图',
  `ask_image_thum` mediumtext COMMENT '问答缩列图',
  `mark` int(11) DEFAULT '0' COMMENT '赏分',
  `best_answer_id` int(11) DEFAULT NULL COMMENT '最佳回复id',
  `best_answer_user_id` int(11) DEFAULT NULL COMMENT '最佳答案作者ID',
  `best_answer_user_icon` varchar(1000) DEFAULT '' COMMENT '最佳作者头像',
  `best_answer_user_name` varchar(1000) DEFAULT '' COMMENT '最佳答案作者',
  `solve_type` int(1) DEFAULT '0' COMMENT '0为已解决1为解决',
  PRIMARY KEY (`ask_id`),
  KEY `idx_topic_id` (`ask_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category_id` (`p_category_id`,`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 附件信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_attachment`;
CREATE TABLE `known_attachment` (
  `attachment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '附件ID',
  `article_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `file_name` varchar(1000) DEFAULT '' COMMENT '文件名',
  `file_url` varchar(1000) DEFAULT '' COMMENT '文件地址',
  `download_mark` int(11) DEFAULT NULL COMMENT '下载所需积分',
  `download_count` int(11) DEFAULT '0' COMMENT '下载次数',
  `article_type` char(1) DEFAULT 'T' COMMENT 'T附件属于话题 K属于知识库',
  PRIMARY KEY (`attachment_id`),
  KEY `attachment_id` (`attachment_id`),
  KEY `attachment_articleid` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 附件下载
-- ----------------------------
DROP TABLE IF EXISTS `known_attachment_download`;
CREATE TABLE `known_attachment_download` (
  `attachment_id` int(11) NOT NULL DEFAULT '0' COMMENT '附件ID',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '下载用户ID',
  `user_icon` varchar(1000) DEFAULT '' COMMENT '用户头像',
  `user_name` varchar(1000) DEFAULT '' COMMENT '用户名',
  PRIMARY KEY (`attachment_id`,`user_id`),
  KEY `attachment_download_id` (`attachment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 收藏信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_collection`;
CREATE TABLE `known_collection` (
  `article_id` int(11) NOT NULL DEFAULT '0' COMMENT '文章ID',
  `article_type` char(1) NOT NULL COMMENT '文章类型',
  `article_user_id` int(11) DEFAULT NULL COMMENT '文章作者',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `title` varchar(1000) DEFAULT '' COMMENT '文章标题',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`article_id`,`article_type`,`user_id`),
  KEY `collection_index_articleid` (`article_id`),
  KEY `collection_index_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 文章点赞表
-- ----------------------------
DROP TABLE IF EXISTS `known_like`;
CREATE TABLE `known_like` (
  `article_id` int(11) NOT NULL DEFAULT '0' COMMENT '文章ID',
  `article_type` char(1) NOT NULL COMMENT '文章类型',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `title` varchar(500) DEFAULT NULL  COMMENT '文章标题',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`article_id`,`article_type`,`user_id`),
  KEY `like_index_articleid` (`article_id`),
  KEY `like_index_userid` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 评论信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_comment`;
CREATE TABLE `known_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` int(11) DEFAULT NULL COMMENT '父节点ID',
  `article_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `content` longtext COMMENT '评论内容',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '评论时间',
  `source_from` char(1) DEFAULT 'P' COMMENT 'P代表PC端发出',
  `article_type` char(255) DEFAULT 'T' COMMENT '文章类型',
  `user_name` varchar(200) DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(1000) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`),
  KEY `topic_comment_index_id` (`id`),
  KEY `topic_comment_index_pid` (`pid`),
  KEY `topic_comment_index_topicid` (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 知识库信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_knowledge`;
CREATE TABLE `known_knowledge` (
  `knowledge_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
  `p_category_id` int(11) DEFAULT NULL COMMENT '组ID',
  `category_id` int(11) DEFAULT NULL COMMENT '类型ID',
  `title` varchar(200) DEFAULT '' COMMENT '标题',
  `content` longtext COMMENT '内容',
  `summary` text COMMENT '内容摘要',
  `user_id` int(11) DEFAULT NULL COMMENT '作者ID',
  `user_icon` varchar(1000) DEFAULT NULL COMMENT '作者头像',
  `user_name` varchar(200) DEFAULT NULL COMMENT '作者名字',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论人数',
  `read_count` int(11) DEFAULT '0' COMMENT '阅读人数',
  `collection_count` int(11) DEFAULT '0' COMMENT '收藏人数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢人数',
  `knowledge_image` mediumtext COMMENT '知识库图片' ,
  `knowledge_image_thum` mediumtext COMMENT '知识库缩列图',
  `status` int(1) DEFAULT '0' COMMENT '0-未审核 1-审核',
  PRIMARY KEY (`knowledge_id`),
  KEY `idx_knowledge_id` (`knowledge_id`),
  KEY `idx_category_id` (`p_category_id`,`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=384 DEFAULT CHARSET=utf8;


-- ----------------------------
-- 消息发送表
-- ----------------------------
DROP TABLE IF EXISTS `known_message`;
CREATE TABLE `known_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `received_user_id` int(11) DEFAULT NULL COMMENT '接收人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(1) DEFAULT '0' COMMENT '0-未读 1-已读',
  `description` varchar(500) DEFAULT NULL COMMENT '信息详情',
  `url` varchar(500) DEFAULT NULL COMMENT 'url地址',
  PRIMARY KEY (`id`),
  KEY `idx_id` (`id`),
  KEY `idx_received_userid` (`received_user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8;




-- ----------------------------
-- 说说信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_shuoshuo`;
CREATE TABLE `known_shuoshuo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_icon` varchar(1000) DEFAULT '' COMMENT '头像',
  `user_name` varchar(100) DEFAULT '' COMMENT '用户名',
  `image_url` text COMMENT '配图URL',
  `image_url_small` varchar(600) DEFAULT NULL COMMENT '表情URL',
  `music_url` varchar(300) DEFAULT NULL COMMENT '音乐URL',
  `content` text COMMENT '说说内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论次数',
  `like_count` int(11) DEFAULT '0' COMMENT '点赞次数',
  PRIMARY KEY (`id`),
  KEY `shuoshuo_index_userid` (`user_id`),
  KEY `shuoshuo_index_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3433 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 说说评论表
-- ----------------------------
DROP TABLE IF EXISTS `known_shuoshuo_comment`;
CREATE TABLE `known_shuoshuo_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `shuoshuo_id` int(11) DEFAULT NULL COMMENT '说说ID',
  `content` varchar(1000) DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '评论时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_icon` varchar(1000) DEFAULT '' COMMENT '头像',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`),
  KEY `index_shuoshuo_id` (`shuoshuo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 说说点赞表
-- ----------------------------
DROP TABLE IF EXISTS `known_shuoshuo_like`;
CREATE TABLE `known_shuoshuo_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `shuoshuo_id` int(11) NOT NULL COMMENT '说说ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_icon` varchar(1000) DEFAULT '' COMMENT '头像',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `create_time` datetime DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`,`shuoshuo_id`,`user_id`),
  KEY `index_shuoshuo_id` (`shuoshuo_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 话题信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_topic`;
CREATE TABLE `known_topic` (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT COMMENt '话题',
  `topic_type` int(1) DEFAULT '0' COMMENT '0是普通话题 1是投票话题',
  `p_category_id` int(11) DEFAULT NULL COMMENT '组ID',
  `category_id` int(11) DEFAULT NULL COMMENT '类型ID',
  `title` varchar(1000) DEFAULT '' COMMENT '标题',
  `content` longtext COMMENT '内容',
  `summary` text COMMENT '内容摘要',
  `user_id` int(11) DEFAULT NULL COMMENT '作者ID',
  `user_icon` varchar(1000) DEFAULT '' COMMENT '作者头像',
  `user_name` varchar(1000) DEFAULT '' COMMENT '作者名字',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `last_comment_time` datetime DEFAULT NULL COMMENT '最后评论时间',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论人数',
  `read_count` int(11) DEFAULT '0' COMMENT '阅读人数',
  `collection_count` int(11) DEFAULT '0' COMMENT '收藏人数',
  `like_count` int(11) DEFAULT '0' COMMENT '喜欢人数',
  `grade` int(2) DEFAULT '0' COMMENT '0是普通话题 1是置顶话题',
  `essence` int(1) DEFAULT '0' COMMENT '0是非精华 1是精华',
  `topic_image` mediumtext COMMENT '话题配图',
  `topic_image_thum` mediumtext COMMENT '话题缩列图',
  PRIMARY KEY (`topic_id`),
  KEY `topic_index_id` (`topic_id`),
  KEY `topic_index_gid` (`p_category_id`),
  KEY `topic_index_id_gid` (`topic_id`,`p_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 投票话题表
-- ----------------------------
DROP TABLE IF EXISTS `known_topic_vote`;
CREATE TABLE `known_topic_vote` (
  `vote_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `topic_id` int(11) NOT NULL COMMENT '话题ID',
  `vote_type` int(1) NOT NULL COMMENT '1是单选 2是多选',
  `end_date` date NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`vote_id`),
  KEY `idx_topic_id` (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 投票话题详情表
-- ----------------------------
DROP TABLE IF EXISTS `known_topic_vote_detail`;
CREATE TABLE `known_topic_vote_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `vote_id` int(11) DEFAULT NULL COMMENT '投票ID',
  `topic_id` int(11) DEFAULT NULL COMMENT '话题ID',
  `title` varchar(1000) DEFAULT '' COMMENT '话题标题',
  `count` int(11) DEFAULT '0' COMMENT '投票数量',
  PRIMARY KEY (`id`),
  KEY `idx_topic_id` (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 投票用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_topic_vote_user`;
CREATE TABLE `known_topic_vote_user` (
  `vote_detail_id` int(11) NOT NULL COMMENT '话题详情ID',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `vote_date` datetime NOT NULL COMMENT '投票日期',
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 统计信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_statistics`;
CREATE TABLE `known_statistics` (
  `statistics_date` date NOT NULL COMMENT '统日期',
  `signin_count` int(11) DEFAULT '0' COMMENT '签到次数',
  `shuoshuo_count` int(11) DEFAULT '0' COMMENT '说说次数',
  `shuoshuo_comment_count` int(11) DEFAULT '0' COMMENT '说说发表次数',
  `topic_count` int(11) DEFAULT '0' COMMENT '话题次数',
  `topic_comment_count` int(11) DEFAULT '0' COMMENT '话题评论次数',
  `knowledge_count` int(11) DEFAULT '0' COMMENT '知识库次数',
  `knowledge_comment_count` int(11) DEFAULT '0' COMMENT '知识库评论次数',
  `ask_count` int(11) DEFAULT '0' COMMENT '问答次数',
  `ask_comment_count` int(11) DEFAULT '0' COMMENT '问答评论次数',
  `user_count` int(11) DEFAULT '0' COMMENT '用户数量',
  `active_user_count` int(11) DEFAULT '0' COMMENT '活跃用户数量',
  PRIMARY KEY (`statistics_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 任务信息表
-- ----------------------------
DROP TABLE IF EXISTS `known_task`;
CREATE TABLE `known_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_classz` varchar(100) DEFAULT NULL,
  `task_method` varchar(100) DEFAULT NULL,
  `task_time` varchar(50) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `status` int(2) DEFAULT '0' COMMENT '0:初始状态 1:暂停',
  `des` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;