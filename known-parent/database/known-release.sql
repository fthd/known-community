/*
 Navicat Premium Data Transfer

 Source Server         : Milky
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : '127'.0.0.1:3306
 Source Schema         : known

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 26/05/2019 17:48:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for known_ask
-- ----------------------------
DROP TABLE IF EXISTS `known_ask`;
CREATE TABLE `known_ask`  (
  `ask_id` varchar(64) NOT NULL COMMENT '问答ID',
  `p_category_id` varchar(64) NULL DEFAULT NULL COMMENT '组ID',
  `category_id` varchar(64) NULL DEFAULT NULL COMMENT '类型ID',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `summary` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容摘要',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '作者ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '作者头像',
  `user_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '作者名字',
  `create_time` timestamp(0) DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `comment_count` int(11) NULL DEFAULT 0 COMMENT '评论人数',
  `read_count` int(11) NULL DEFAULT 0 COMMENT '阅读人数',
  `collection_count` int(11) NULL DEFAULT 0 COMMENT '收藏人数',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '喜欢人数',
  `ask_image` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '问答配图',
  `ask_image_thum` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '问答缩列图',
  `mark` int(11) NULL DEFAULT 0 COMMENT '赏分',
  `best_answer_id` varchar(64) NULL DEFAULT NULL COMMENT '最佳回复id',
  `best_answer_user_id` varchar(64) NULL DEFAULT NULL COMMENT '最佳答案作者ID',
  `best_answer_user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最佳作者头像',
  `best_answer_user_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '最佳答案作者',
  `solve_type` int(1) NULL DEFAULT 0 COMMENT '0为已解决1为解决',
  PRIMARY KEY (`ask_id`) USING BTREE,
  INDEX `idx_topic_id`(`ask_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_category_id`(`p_category_id`, `category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_attachment
-- ----------------------------
DROP TABLE IF EXISTS `known_attachment`;
CREATE TABLE `known_attachment`  (
  `attachment_id` varchar(64) NOT NULL COMMENT '附件ID',
  `article_id` varchar(64) NULL DEFAULT NULL COMMENT '文章ID',
  `file_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文件名',
  `file_url` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文件地址',
  `download_mark` varchar(64) NULL DEFAULT NULL COMMENT '下载所需积分',
  `download_count` varchar(64) NULL DEFAULT 0 COMMENT '下载次数',
  `article_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'T' COMMENT 'T附件属于话题 K属于知识库',
  PRIMARY KEY (`attachment_id`) USING BTREE,
  INDEX `attachment_id`(`attachment_id`) USING BTREE,
  INDEX `attachment_articleid`(`article_id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_attachment_download
-- ----------------------------
DROP TABLE IF EXISTS `known_attachment_download`;
CREATE TABLE `known_attachment_download`  (
  `attachment_id` varchar(64) NOT NULL COMMENT '附件ID',
  `user_id` varchar(64) NOT NULL  COMMENT '下载用户ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `user_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名',
  PRIMARY KEY (`attachment_id`, `user_id`) USING BTREE,
  INDEX `attachment_download_id`(`attachment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_category
-- ----------------------------
DROP TABLE IF EXISTS `known_category`;
CREATE TABLE `known_category`  (
  `category_id` varchar(64) NOT NULL COMMENT '分类ID',
  `pid` varchar(64) NULL DEFAULT NULL COMMENT '父节点ID',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类描述',
  `rank` varchar(64) NULL DEFAULT NULL COMMENT '分类排名',
  `show_in_topic` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y' COMMENT '是否是话题分类Y是N不是',
  `show_in_ask` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y' COMMENT '是否是问答分类Y是N不是',
  `show_in_knowledge` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'Y' COMMENT '是否是知识库分类Y是N不是',
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `category_index_id`(`category_id`) USING BTREE,
  INDEX `idx_show_in_topic`(`show_in_topic`) USING BTREE,
  INDEX `idx_show_in_ask`(`show_in_ask`) USING BTREE,
  INDEX `idx_show_in_knowledge`(`show_in_knowledge`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of known_category
-- ----------------------------
INSERT INTO `known_category` VALUES ('13', '0', '我爱编程', '一级分类', NULL, 'Y', 'Y', 'Y');
INSERT INTO `known_category` VALUES ('14', '13', 'Java', '二级分类', NULL, 'Y', 'Y', 'Y');

-- ----------------------------
-- Table structure for known_collection
-- ----------------------------
DROP TABLE IF EXISTS `known_collection`;
CREATE TABLE `known_collection`  (
  `article_id` varchar(64) NOT NULL COMMENT '文章ID',
  `article_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章类型',
  `article_user_id` varchar(64) NULL DEFAULT NULL COMMENT '文章作者',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文章标题',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`article_id`, `article_type`, `user_id`) USING BTREE,
  INDEX `collection_index_articleid`(`article_id`) USING BTREE,
  INDEX `collection_index_userid`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_comment
-- ----------------------------
DROP TABLE IF EXISTS `known_comment`;
CREATE TABLE `known_comment`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `pid` varchar(64) NULL DEFAULT NULL COMMENT '父节点ID',
  `article_id` varchar(64) NULL DEFAULT NULL COMMENT '文章ID',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '评论内容',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  `source_from` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'P' COMMENT 'P代表PC端发出',
  `article_type` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'T' COMMENT '文章类型',
  `user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `topic_comment_index_id`(`id`) USING BTREE,
  INDEX `topic_comment_index_pid`(`pid`) USING BTREE,
  INDEX `topic_comment_index_topicid`(`article_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `known_knowledge`;
CREATE TABLE `known_knowledge`  (
  `knowledge_id` varchar(64) NOT NULL COMMENT '知识库ID',
  `p_category_id` varchar(64) NULL DEFAULT NULL COMMENT '组ID',
  `category_id` varchar(64) NULL DEFAULT NULL COMMENT '类型ID',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `summary` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容摘要',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '作者ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者头像',
  `user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者名字',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `comment_count` varchar(64) NULL DEFAULT 0 COMMENT '评论人数',
  `read_count` varchar(64) NULL DEFAULT 0 COMMENT '阅读人数',
  `collection_count` varchar(64) NULL DEFAULT 0 COMMENT '收藏人数',
  `like_count` varchar(64) NULL DEFAULT 0 COMMENT '喜欢人数',
  `knowledge_image` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '知识库图片',
  `knowledge_image_thum` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '知识库缩列图',
  `status` int(1) NULL DEFAULT 0 COMMENT '0-未审核 1-审核',
  PRIMARY KEY (`knowledge_id`) USING BTREE,
  INDEX `idx_knowledge_id`(`knowledge_id`) USING BTREE,
  INDEX `idx_category_id`(`p_category_id`, `category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_like
-- ----------------------------
DROP TABLE IF EXISTS `known_like`;
CREATE TABLE `known_like`  (
  `article_id` varchar(64) NOT NULL COMMENT '文章ID',
  `article_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章类型',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`article_id`, `article_type`, `user_id`) USING BTREE,
  INDEX `like_index_articleid`(`article_id`) USING BTREE,
  INDEX `like_index_userid`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_message
-- ----------------------------
DROP TABLE IF EXISTS `known_message`;
CREATE TABLE `known_message`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `received_user_id` varchar(64) NULL DEFAULT NULL COMMENT '接收人ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` int(1) NULL DEFAULT 0 COMMENT '0-未读 1-已读',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '信息详情',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url地址',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_id`(`id`) USING BTREE,
  INDEX `idx_received_userid`(`received_user_id`) USING BTREE,
  INDEX `idx_status`(`status`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_shuoshuo
-- ----------------------------
DROP TABLE IF EXISTS `known_shuoshuo`;
CREATE TABLE `known_shuoshuo`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '用户ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户名',
  `image_url` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '配图URL',
  `image_url_small` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表情URL',
  `music_url` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '音乐URL',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '说说内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `comment_count` int(11) NULL DEFAULT 0 COMMENT '评论次数',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '点赞次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `shuoshuo_index_userid`(`user_id`) USING BTREE,
  INDEX `shuoshuo_index_id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_shuoshuo_comment
-- ----------------------------
DROP TABLE IF EXISTS `known_shuoshuo_comment`;
CREATE TABLE `known_shuoshuo_comment`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `shuoshuo_id` varchar(64) NULL DEFAULT NULL COMMENT '说说ID',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '用户ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_shuoshuo_id`(`shuoshuo_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_shuoshuo_like
-- ----------------------------
DROP TABLE IF EXISTS `known_shuoshuo_like`;
CREATE TABLE `known_shuoshuo_like`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `shuoshuo_id` varchar(64) NOT NULL COMMENT '说说ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`, `shuoshuo_id`, `user_id`) USING BTREE,
  INDEX `index_shuoshuo_id`(`shuoshuo_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_sign_in
-- ----------------------------
DROP TABLE IF EXISTS `known_sign_in`;
CREATE TABLE `known_sign_in`  (
  `userid` varchar(64) NOT NULL COMMENT '用户ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `sign_date` date NOT NULL COMMENT '签到日期',
  `sign_time` datetime(0) NULL DEFAULT NULL COMMENT '签到具体时间',
  UNIQUE INDEX `idx_userid_signdate`(`userid`, `sign_date`) USING BTREE,
  INDEX `signin_index_userid`(`userid`) USING BTREE,
  INDEX `signin_index_signdate`(`sign_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for known_statistics
-- ----------------------------
DROP TABLE IF EXISTS `known_statistics`;
CREATE TABLE `known_statistics`  (
  `statistics_date` date NOT NULL COMMENT '统计日期',
  `signin_count` int(11) NULL DEFAULT 0 COMMENT '签到次数',
  `shuoshuo_count` int(11) NULL DEFAULT 0 COMMENT '说说次数',
  `shuoshuo_comment_count`int(11) NULL DEFAULT 0 COMMENT '说说发表次数',
  `topic_count` int(11) NULL DEFAULT 0 COMMENT '话题次数',
  `topic_comment_count` int(11) NULL DEFAULT 0 COMMENT '话题评论次数',
  `knowledge_count` int(11) NULL DEFAULT 0 COMMENT '知识库次数',
  `knowledge_comment_count` int(11) NULL DEFAULT 0 COMMENT '知识库评论次数',
  `ask_count` int(11) NULL DEFAULT 0 COMMENT '问答次数',
  `ask_comment_count` int(11) NULL DEFAULT 0 COMMENT '问答评论次数',
  `user_count` int(11) NULL DEFAULT 0 COMMENT '用户数量',
  `active_user_count` int(11) NULL DEFAULT 0 COMMENT '活跃用户数量',
  PRIMARY KEY (`statistics_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_task
-- ----------------------------
DROP TABLE IF EXISTS `known_task`;
CREATE TABLE `known_task`  (
  `id` varchar(64) NOT NULL,
  `task_classz` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `task_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `task_time` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_update_time` datetime(0) NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT 0 COMMENT '0:初始状态 1:暂停',
  `des` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_topic
-- ----------------------------
DROP TABLE IF EXISTS `known_topic`;
CREATE TABLE `known_topic`  (
  `topic_id` varchar(64) NOT NULL COMMENT '话题ID',
  `topic_type` int(1) NULL DEFAULT 0 COMMENT '0是普通话题 1是投票话题',
  `p_category_id` varchar(64) NULL DEFAULT NULL COMMENT '组ID',
  `category_id` varchar(64) NULL DEFAULT NULL COMMENT '类型ID',
  `title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `summary` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容摘要',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '作者ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '作者头像',
  `user_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '作者名字',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `last_comment_time` datetime(0) NULL DEFAULT NULL COMMENT '最后评论时间',
  `comment_count` int(11) NULL DEFAULT 0 COMMENT '评论人数',
  `read_count` int(11) NULL DEFAULT 0 COMMENT '阅读人数',
  `collection_count` int(11) NULL DEFAULT 0 COMMENT '收藏人数',
  `like_count` int(11) NULL DEFAULT 0 COMMENT '喜欢人数',
  `grade` int(2) NULL DEFAULT 0 COMMENT '0是普通话题 1是置顶话题',
  `essence` int(1) NULL DEFAULT 0 COMMENT '0是非精华 1是精华',
  `topic_image` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '话题配图',
  `topic_image_thum` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '话题缩列图',
  PRIMARY KEY (`topic_id`) USING BTREE,
  INDEX `topic_index_id`(`topic_id`) USING BTREE,
  INDEX `topic_index_gid`(`p_category_id`) USING BTREE,
  INDEX `topic_index_id_gid`(`topic_id`, `p_category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_topic_vote
-- ----------------------------
DROP TABLE IF EXISTS `known_topic_vote`;
CREATE TABLE `known_topic_vote`  (
  `vote_id` varchar(64) NOT NULL COMMENT 'ID',
  `topic_id` varchar(64) NOT NULL COMMENT '话题ID',
  `vote_type` int(1) NOT NULL COMMENT '1是单选 2是多选',
  `end_date` date NOT NULL COMMENT '结束时间',
  PRIMARY KEY (`vote_id`) USING BTREE,
  INDEX `idx_topic_id`(`topic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_topic_vote_detail
-- ----------------------------
DROP TABLE IF EXISTS `known_topic_vote_detail`;
CREATE TABLE `known_topic_vote_detail`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `vote_id` varchar(64) NULL DEFAULT NULL COMMENT '投票ID',
  `topic_id` varchar(64) NULL DEFAULT NULL COMMENT '话题ID',
  `title` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '话题标题',
  `count` int(11) NULL DEFAULT 0 COMMENT '投票数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_topic_id`(`topic_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_topic_vote_user
-- ----------------------------
DROP TABLE IF EXISTS `known_topic_vote_user`;
CREATE TABLE `known_topic_vote_user`  (
  `vote_detail_id` varchar(64) NOT NULL COMMENT '话题详情ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `vote_date` datetime(0) NOT NULL COMMENT '投票日期',
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for known_user
-- ----------------------------
DROP TABLE IF EXISTS `known_user`;
CREATE TABLE `known_user`  (
  `userid` varchar(64) NOT NULL COMMENT '用户ID',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `user_icon` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '头像',
  `user_bg` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户背景',
  `age` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `characters` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座右铭',
  `mark` int(11) NULL DEFAULT 0 COMMENT '积分',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `work` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `activation_code` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '激活码',
  `status` int(1) NULL DEFAULT 0 COMMENT '0-未激活 1-已激活',
  `user_page` int(2) NULL DEFAULT 0 COMMENT '用户页面模板',
  PRIMARY KEY (`userid`) USING BTREE,
  INDEX `user_index_username`(`user_name`) USING BTREE,
  INDEX `user_index_email`(`email`) USING BTREE,
  INDEX `user_index_userid`(`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of known_user
-- ----------------------------
INSERT INTO `known_user` VALUES ('10086', '2829266028@qq.com', 'will', '7402d37e1431288b190bdeb1a550d698', '/resources/images/default_usericon/5.jpg', '/resources/images/user_bg/bg8.jpg', NULL, NULL, NULL, 64, NULL, NULL, NULL, '2019-05-24 11:07:34', '2019-05-26 17:40:46', 'AEZZ9X', 1, 1);

-- ----------------------------
-- Table structure for known_user_friend
-- ----------------------------
DROP TABLE IF EXISTS `known_user_friend`;
CREATE TABLE `known_user_friend`  (
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `friend_user_id` varchar(64) NOT NULL COMMENT '好友ID',
  `user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `user_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `friend_user_icon` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友头像',
  `friend_user_name` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友名称',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`, `friend_user_id`) USING BTREE,
  INDEX `friend_index_userid`(`user_id`) USING BTREE,
  INDEX `friend_index_friendid`(`friend_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `login_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '登录时间',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `lat` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '纬度',
  `lng` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '经度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `op_content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作内容',
  `client_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端ID',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `pid` varchar(64) NULL DEFAULT NULL COMMENT '父节点ID',
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `iconCls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'am-icon-file',
  `type` int(1) NULL DEFAULT 2 COMMENT '1 菜单 2 权限',
  `modifydate` timestamp(0) NULL DEFAULT NULL,
  `enabled` int(1) NULL DEFAULT 1 COMMENT '是否启用 1：启用  0：禁用',
  `level` varchar(64) NULL DEFAULT 0,
  `key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_res
-- ----------------------------
INSERT INTO `sys_res` VALUES ('126', NULL, '系统管理', NULL, '', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('127', NULL, '内容管理', NULL, NULL, 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('128', NULL, '控制台', NULL, NULL, 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('129', '126', '资源管理', NULL, '/manage/res/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('130', '126', '角色管理', NULL, '/manage/role/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('131', '126', '用户管理', NULL, '/manage/user/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('132', '127', '说说管理', NULL, '/content/shuoshuo/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('133', '127', '话题管理', NULL, '/content/topic/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('134', '127', '知识库管理', NULL, '/content/knowledge/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('135', '127', '问答管理', NULL, '/content/ask/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('136', '128', '报表管理', NULL, '/console/statistics/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('137', '128', '任务管理', NULL, '/console/task/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('138', '128', '操作日志', NULL, '/console/syslog/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('139', '128', '登录日志', NULL, '/console/loginlog/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('140', '128', 'Druid管理', NULL, '/console/druid/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('141', '128', '用户分布图', NULL, '/console/userlocation/list', 'am-icon-file', 1, NULL, 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('142', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:res:list');
INSERT INTO `sys_res` VALUES ('143', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:res:save');
INSERT INTO `sys_res` VALUES ('144', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:res:delete');
INSERT INTO `sys_res` VALUES ('145', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:role:list');
INSERT INTO `sys_res` VALUES ('146', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:role:save');
INSERT INTO `sys_res` VALUES ('147', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:role:save');
INSERT INTO `sys_res` VALUES ('148', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:role:updateAuthority');
INSERT INTO `sys_res` VALUES ('149', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:role:delete');
INSERT INTO `sys_res` VALUES ('150', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:user:list');
INSERT INTO `sys_res` VALUES ('151', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:user:markChange');
INSERT INTO `sys_res` VALUES ('152', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:user:updateUserRole');
INSERT INTO `sys_res` VALUES ('153', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'manage:user:delete');
INSERT INTO `sys_res` VALUES ('154', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:shuoshuo:list');
INSERT INTO `sys_res` VALUES ('155', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:shuoshuo:delete');
INSERT INTO `sys_res` VALUES ('156', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:topic:list');
INSERT INTO `sys_res` VALUES ('157', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:topic:essence');
INSERT INTO `sys_res` VALUES ('158', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:topic:unessence');
INSERT INTO `sys_res` VALUES ('159', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:topic:stick');
INSERT INTO `sys_res` VALUES ('160', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:topic:unstick');
INSERT INTO `sys_res` VALUES ('161', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:topic:delete');
INSERT INTO `sys_res` VALUES ('162', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:knowledge:list');
INSERT INTO `sys_res` VALUES ('163', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:knowledge:updatestatus');
INSERT INTO `sys_res` VALUES ('164', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:knowledge:delete');
INSERT INTO `sys_res` VALUES ('165', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:ask:list');
INSERT INTO `sys_res` VALUES ('166', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:ask:delete');
INSERT INTO `sys_res` VALUES ('167', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:statistics:list');
INSERT INTO `sys_res` VALUES ('168', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:task:list');
INSERT INTO `sys_res` VALUES ('169', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:task:save');
INSERT INTO `sys_res` VALUES ('170', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:task:save');
INSERT INTO `sys_res` VALUES ('171', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:task:excute');
INSERT INTO `sys_res` VALUES ('172', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:task:pause');
INSERT INTO `sys_res` VALUES ('173', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:task:delete');
INSERT INTO `sys_res` VALUES ('174', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:syslog:list');
INSERT INTO `sys_res` VALUES ('175', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:loginlog:list');
INSERT INTO `sys_res` VALUES ('176', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:druid:list');
INSERT INTO `sys_res` VALUES ('177', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'console:userlocation:list');
INSERT INTO `sys_res` VALUES ('178', '127', '分类管理', '内容管理-二级目录-分类管理', '/content/category/list', 'am-icon-file', 1, '2019-05-26 06:11:13', 1, 0, 'manage:sys:res:list');
INSERT INTO `sys_res` VALUES ('179', NULL, '分类管理页面', '分类管理页面访问', NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:category:list');
INSERT INTO `sys_res` VALUES ('180', NULL, '新增and修改', '分类管理-新增and修改-按钮', '', 'am-icon-file', 2, '2019-05-26 12:39:58', 1, 0, 'content:category:save');
INSERT INTO `sys_res` VALUES ('181', NULL, NULL, NULL, NULL, 'am-icon-file', 2, NULL, 1, 0, 'content:category:delete');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `name` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `des` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `seq` int(11) NULL DEFAULT 1 COMMENT '序列',
  `createdate` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `status` varchar(64) NULL DEFAULT 1 COMMENT '0-禁用  1-启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('5', '超级管理员', '一级菜单-系统管理', 1, '2019-05-26 03:39:41', 1);
INSERT INTO `sys_role` VALUES ('6', '超级管理员', '一级菜单-内容管理', 1, '2019-05-26 03:39:31', 1);
INSERT INTO `sys_role` VALUES ('7', '超级管理员', '一级菜单-控制台', 1, '2019-05-26 03:39:15', 1);
INSERT INTO `sys_role` VALUES ('8', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('9', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('10', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('11', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('12', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('13', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('14', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('15', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('16', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('17', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('18', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('19', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('20', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('21', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('22', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('23', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('24', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('25', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('26', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('27', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('28', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('29', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('30', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('31', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('32', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('33', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('34', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('35', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('36', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('37', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('38', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('39', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('40', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('41', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('42', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('43', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('44', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('45', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('46', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('47', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('48', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('49', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('50', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('51', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('52', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('53', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('54', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('55', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('56', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('57', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('58', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('59', '', NULL, 1, NULL, 1);
INSERT INTO `sys_role` VALUES ('60', '', NULL, 1, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res`  (
  `id` int(11)NOT NULL COMMENT 'ID',
  `role_id` varchar(64) NULL DEFAULT NULL COMMENT '权限表ID',
  `res_id` varchar(64) NULL DEFAULT NULL COMMENT '系统权限详情表ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK_sys_ROLE_RES_RES_ID`(`res_id`) USING BTREE,
  INDEX `FK_sys_ROLE_RES_ROLE_ID`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
INSERT INTO `sys_role_res` VALUES (1, '5', '126');
INSERT INTO `sys_role_res` VALUES (2, '6', '127');
INSERT INTO `sys_role_res` VALUES (3, '7', '128');
INSERT INTO `sys_role_res` VALUES (4, '8', '129');
INSERT INTO `sys_role_res` VALUES (5, '9', '130');
INSERT INTO `sys_role_res` VALUES (6, '10', '131');
INSERT INTO `sys_role_res` VALUES (7, '11', '132');
INSERT INTO `sys_role_res` VALUES (8, '12', '133');
INSERT INTO `sys_role_res` VALUES (9, '13', '134');
INSERT INTO `sys_role_res` VALUES (10, '14', '135');
INSERT INTO `sys_role_res` VALUES (11, '15', '136');
INSERT INTO `sys_role_res` VALUES (12, '16', '137');
INSERT INTO `sys_role_res` VALUES (13, '17', '138');
INSERT INTO `sys_role_res` VALUES (14, '18', '139');
INSERT INTO `sys_role_res` VALUES (15, '19', '140');
INSERT INTO `sys_role_res` VALUES (16, '20', '141');
INSERT INTO `sys_role_res` VALUES (17, '21', '142');
INSERT INTO `sys_role_res` VALUES (18, '22', '143');
INSERT INTO `sys_role_res` VALUES (19, '23', '144');
INSERT INTO `sys_role_res` VALUES (20, '24', '145');
INSERT INTO `sys_role_res` VALUES (21, '25', '146');
INSERT INTO `sys_role_res` VALUES (22, '26', '147');
INSERT INTO `sys_role_res` VALUES (23, '27', '148');
INSERT INTO `sys_role_res` VALUES (24, '28', '149');
INSERT INTO `sys_role_res` VALUES (25, '29', '150');
INSERT INTO `sys_role_res` VALUES (26, '30', '151');
INSERT INTO `sys_role_res` VALUES (27, '31', '152');
INSERT INTO `sys_role_res` VALUES (28, '32', '153');
INSERT INTO `sys_role_res` VALUES (29, '33', '154');
INSERT INTO `sys_role_res` VALUES (30, '34', '155');
INSERT INTO `sys_role_res` VALUES (31, '35', '156');
INSERT INTO `sys_role_res` VALUES (32, '36', '157');
INSERT INTO `sys_role_res` VALUES (33, '37', '158');
INSERT INTO `sys_role_res` VALUES (34, '38', '159');
INSERT INTO `sys_role_res` VALUES (35, '39', '160');
INSERT INTO `sys_role_res` VALUES (36, '40', '161');
INSERT INTO `sys_role_res` VALUES (37, '41', '162');
INSERT INTO `sys_role_res` VALUES (38, '42', '163');
INSERT INTO `sys_role_res` VALUES (39, '43', '164');
INSERT INTO `sys_role_res` VALUES (40, '44', '165');
INSERT INTO `sys_role_res` VALUES (41, '45', '166');
INSERT INTO `sys_role_res` VALUES (42, '46', '167');
INSERT INTO `sys_role_res` VALUES (43, '47', '168');
INSERT INTO `sys_role_res` VALUES (44, '48', '169');
INSERT INTO `sys_role_res` VALUES (45, '49', '170');
INSERT INTO `sys_role_res` VALUES (46, '50', '171');
INSERT INTO `sys_role_res` VALUES (47, '51', '172');
INSERT INTO `sys_role_res` VALUES (48, '52', '173');
INSERT INTO `sys_role_res` VALUES (49, '53', '174');
INSERT INTO `sys_role_res` VALUES (50, '54', '175');
INSERT INTO `sys_role_res` VALUES (51, '55', '176');
INSERT INTO `sys_role_res` VALUES (52, '56', '177');
INSERT INTO `sys_role_res` VALUES (53, '57', '178');
INSERT INTO `sys_role_res` VALUES (54, '58', '179');
INSERT INTO `sys_role_res` VALUES (55, '59', '180');
INSERT INTO `sys_role_res` VALUES (56, '60', '181');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL COMMENT 'ID',
  `user_id` varchar(64) NULL DEFAULT NULL COMMENT '用户表ID',
  `role_id` varchar(64) NULL DEFAULT NULL COMMENT '系统权限表ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_role_index`(`user_id`, `role_id`) USING BTREE,
  INDEX `FK_SYSTME_USER_ROLE_USER_ID`(`user_id`) USING BTREE,
  INDEX `FK_SYSTME_USER_ROLE_ROLE_ID`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, '10086', '5');
INSERT INTO `sys_user_role` VALUES (2, '10086', '6');
INSERT INTO `sys_user_role` VALUES (3, '10086', '7');
INSERT INTO `sys_user_role` VALUES (4, '10086', '8');
INSERT INTO `sys_user_role` VALUES (5, '10086', '9');
INSERT INTO `sys_user_role` VALUES (6, '10086', '10');
INSERT INTO `sys_user_role` VALUES (7, '10086', '11');
INSERT INTO `sys_user_role` VALUES (8, '10086', '12');
INSERT INTO `sys_user_role` VALUES (9, '10086', '13');
INSERT INTO `sys_user_role` VALUES (10, '10086', '14');
INSERT INTO `sys_user_role` VALUES (11, '10086', '15');
INSERT INTO `sys_user_role` VALUES (12, '10086', '16');
INSERT INTO `sys_user_role` VALUES (13, '10086', '17');
INSERT INTO `sys_user_role` VALUES (14, '10086', '18');
INSERT INTO `sys_user_role` VALUES (15, '10086', '19');
INSERT INTO `sys_user_role` VALUES (16, '10086', '20');
INSERT INTO `sys_user_role` VALUES (17, '10086', '21');
INSERT INTO `sys_user_role` VALUES (18, '10086', '22');
INSERT INTO `sys_user_role` VALUES (19, '10086', '23');
INSERT INTO `sys_user_role` VALUES (20, '10086', '24');
INSERT INTO `sys_user_role` VALUES (21, '10086', '25');
INSERT INTO `sys_user_role` VALUES (22, '10086', '26');
INSERT INTO `sys_user_role` VALUES (23, '10086', '27');
INSERT INTO `sys_user_role` VALUES (24, '10086', '28');
INSERT INTO `sys_user_role` VALUES (25, '10086', '29');
INSERT INTO `sys_user_role` VALUES (26, '10086', '30');
INSERT INTO `sys_user_role` VALUES (27, '10086', '31');
INSERT INTO `sys_user_role` VALUES (28, '10086', '32');
INSERT INTO `sys_user_role` VALUES (29, '10086', '33');
INSERT INTO `sys_user_role` VALUES (30, '10086', '34');
INSERT INTO `sys_user_role` VALUES (31, '10086', '35');
INSERT INTO `sys_user_role` VALUES (32, '10086', '36');
INSERT INTO `sys_user_role` VALUES (33, '10086', '37');
INSERT INTO `sys_user_role` VALUES (34, '10086', '38');
INSERT INTO `sys_user_role` VALUES (35, '10086', '39');
INSERT INTO `sys_user_role` VALUES (36, '10086', '40');
INSERT INTO `sys_user_role` VALUES (37, '10086', '41');
INSERT INTO `sys_user_role` VALUES (38, '10086', '42');
INSERT INTO `sys_user_role` VALUES (39, '10086', '43');
INSERT INTO `sys_user_role` VALUES (40, '10086', '44');
INSERT INTO `sys_user_role` VALUES (41, '10086', '45');
INSERT INTO `sys_user_role` VALUES (42, '10086', '46');
INSERT INTO `sys_user_role` VALUES (43, '10086', '47');
INSERT INTO `sys_user_role` VALUES (44, '10086', '48');
INSERT INTO `sys_user_role` VALUES (45, '10086', '49');
INSERT INTO `sys_user_role` VALUES (46, '10086', '50');
INSERT INTO `sys_user_role` VALUES (47, '10086', '51');
INSERT INTO `sys_user_role` VALUES (48, '10086', '52');
INSERT INTO `sys_user_role` VALUES (49, '10086', '53');
INSERT INTO `sys_user_role` VALUES (50, '10086', '54');
INSERT INTO `sys_user_role` VALUES (51, '10086', '55');
INSERT INTO `sys_user_role` VALUES (52, '10086', '56');
INSERT INTO `sys_user_role` VALUES (53, '10086', '57');
INSERT INTO `sys_user_role` VALUES (54, '10086', '58');
INSERT INTO `sys_user_role` VALUES (55, '10086', '59');
INSERT INTO `sys_user_role` VALUES (56, '10086', '60');

SET FOREIGN_KEY_CHECKS = 1;
