/*
 Navicat Premium Data Transfer

 Source Server         : 47.57.188.97(新南宫IM)
 Source Server Type    : MySQL
 Source Server Version : 50650 (5.6.50-log)
 Source Host           : 47.57.188.97:3306
 Source Schema         : boxim

 Target Server Type    : MySQL
 Target Server Version : 50650 (5.6.50-log)
 File Encoding         : 65001

 Date: 23/10/2024 22:26:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for im_friend
-- ----------------------------
DROP TABLE IF EXISTS `im_friend`;
CREATE TABLE `im_friend`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `friend_id` bigint(20) NOT NULL COMMENT '好友id',
  `friend_nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '好友昵称',
  `friend_head_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '好友头像',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remark_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_friend_id`(`friend_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '好友' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of im_friend
-- ----------------------------
INSERT INTO `im_friend` VALUES (14, 1, 3, 'xmdw', '', '2024-08-20 01:33:51', NULL);
INSERT INTO `im_friend` VALUES (15, 3, 1, '充值客服账号1', '', '2024-08-20 01:33:51', NULL);
INSERT INTO `im_friend` VALUES (16, 2, 3, 'xmdw', '', '2024-08-20 01:33:51', NULL);
INSERT INTO `im_friend` VALUES (17, 3, 2, '充值客服账号2', '', '2024-08-20 01:33:51', NULL);
INSERT INTO `im_friend` VALUES (18, 1, 4, '啊啊啊啊', '', '2024-08-20 12:47:01', NULL);
INSERT INTO `im_friend` VALUES (19, 4, 1, '充值客服账号1', '', '2024-08-20 12:47:01', NULL);
INSERT INTO `im_friend` VALUES (20, 2, 4, '啊啊啊啊', '', '2024-08-20 12:47:01', NULL);
INSERT INTO `im_friend` VALUES (21, 4, 2, '充值客服账号2', '', '2024-08-20 12:47:01', NULL);
INSERT INTO `im_friend` VALUES (22, 1, 5, '顺风顺水', '', '2024-08-20 17:42:35', NULL);
INSERT INTO `im_friend` VALUES (23, 5, 1, '充值客服账号1', '', '2024-08-20 17:42:35', NULL);
INSERT INTO `im_friend` VALUES (24, 2, 5, '顺风顺水', '', '2024-08-20 17:42:35', NULL);
INSERT INTO `im_friend` VALUES (25, 5, 2, '充值客服账号2', '', '2024-08-20 17:42:35', NULL);
INSERT INTO `im_friend` VALUES (26, 1, 6, '1111', '', '2024-08-20 21:03:15', NULL);
INSERT INTO `im_friend` VALUES (27, 6, 1, '充值客服账号1', '', '2024-08-20 21:03:15', NULL);
INSERT INTO `im_friend` VALUES (28, 2, 6, '1111', '', '2024-08-20 21:03:15', NULL);
INSERT INTO `im_friend` VALUES (29, 6, 2, '充值客服账号2', '', '2024-08-20 21:03:15', NULL);
INSERT INTO `im_friend` VALUES (30, 1, 7, 'wtsn', '', '2024-08-24 00:07:02', NULL);
INSERT INTO `im_friend` VALUES (31, 7, 1, '充值客服账号1', '', '2024-08-24 00:07:02', NULL);
INSERT INTO `im_friend` VALUES (32, 2, 7, 'wtsn', '', '2024-08-24 00:07:02', NULL);
INSERT INTO `im_friend` VALUES (33, 7, 2, '充值客服账号2', '', '2024-08-24 00:07:02', NULL);
INSERT INTO `im_friend` VALUES (38, 1, 11, 'xmdw🍎', '', '2024-08-24 00:28:10', NULL);
INSERT INTO `im_friend` VALUES (39, 11, 1, '充值客服账号1', '', '2024-08-24 00:28:10', NULL);
INSERT INTO `im_friend` VALUES (40, 2, 11, 'xmdw🍎', '', '2024-08-24 00:28:10', NULL);
INSERT INTO `im_friend` VALUES (41, 11, 2, '充值客服账号2', '', '2024-08-24 00:28:10', NULL);
INSERT INTO `im_friend` VALUES (42, 1, 12, 'temp_20240901203833', '', '2024-09-01 20:38:33', NULL);
INSERT INTO `im_friend` VALUES (43, 12, 1, '充值客服账号1', '', '2024-09-01 20:38:33', NULL);
INSERT INTO `im_friend` VALUES (44, 2, 12, 'temp_20240901203833', '', '2024-09-01 20:38:33', NULL);
INSERT INTO `im_friend` VALUES (45, 12, 2, '充值客服账号2', '', '2024-09-01 20:38:33', NULL);
INSERT INTO `im_friend` VALUES (46, 1, 13, '啊贵', '', '2024-10-12 16:21:49', NULL);
INSERT INTO `im_friend` VALUES (47, 13, 1, '充值客服账号1', '', '2024-10-12 16:21:49', NULL);
INSERT INTO `im_friend` VALUES (48, 2, 13, '啊贵', '', '2024-10-12 16:21:49', NULL);
INSERT INTO `im_friend` VALUES (49, 13, 2, '充值客服账号2', '', '2024-10-12 16:21:49', NULL);
INSERT INTO `im_friend` VALUES (50, 1, 14, '马上开始', '', '2024-10-13 10:41:17', NULL);
INSERT INTO `im_friend` VALUES (51, 14, 1, '充值客服账号1', '', '2024-10-13 10:41:17', NULL);
INSERT INTO `im_friend` VALUES (52, 2, 14, '马上开始', '', '2024-10-13 10:41:17', NULL);
INSERT INTO `im_friend` VALUES (53, 14, 2, '充值客服账号2', '', '2024-10-13 10:41:17', NULL);
INSERT INTO `im_friend` VALUES (54, 1, 15, '※儚謃※', '', '2024-10-13 13:53:42', NULL);
INSERT INTO `im_friend` VALUES (55, 15, 1, '充值客服账号1', '', '2024-10-13 13:53:42', NULL);
INSERT INTO `im_friend` VALUES (56, 2, 15, '※儚謃※', '', '2024-10-13 13:53:42', NULL);
INSERT INTO `im_friend` VALUES (57, 15, 2, '充值客服账号2', '', '2024-10-13 13:53:42', NULL);
INSERT INTO `im_friend` VALUES (58, 1, 16, '器皿', '', '2024-10-23 20:17:56', NULL);
INSERT INTO `im_friend` VALUES (59, 16, 1, '充值客服账号1', '', '2024-10-23 20:17:56', NULL);
INSERT INTO `im_friend` VALUES (60, 2, 16, '器皿', '', '2024-10-23 20:17:56', NULL);
INSERT INTO `im_friend` VALUES (61, 16, 2, '充值客服账号2', '', '2024-10-23 20:17:56', NULL);

-- ----------------------------
-- Table structure for im_group
-- ----------------------------
DROP TABLE IF EXISTS `im_group`;
CREATE TABLE `im_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群名字',
  `owner_id` bigint(20) NOT NULL COMMENT '群主id',
  `head_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '群头像',
  `head_image_thumb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '群头像缩略图',
  `notice` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '群公告',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '群备注',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '是否已删除',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of im_group
-- ----------------------------

-- ----------------------------
-- Table structure for im_group_member
-- ----------------------------
DROP TABLE IF EXISTS `im_group_member`;
CREATE TABLE `im_group_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(20) NOT NULL COMMENT '群id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `alias_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '组内显示名称',
  `head_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `quit` tinyint(1) NULL DEFAULT 0 COMMENT '是否已退出',
  `quit_time` datetime NULL DEFAULT NULL COMMENT '退出时间',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群成员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of im_group_member
-- ----------------------------

-- ----------------------------
-- Table structure for im_group_message
-- ----------------------------
DROP TABLE IF EXISTS `im_group_message`;
CREATE TABLE `im_group_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `group_id` bigint(20) NOT NULL COMMENT '群id',
  `send_id` bigint(20) NOT NULL COMMENT '发送用户id',
  `send_nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '发送用户昵称',
  `recv_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '接收用户id,逗号分隔，为空表示发给所有成员',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '发送内容',
  `at_user_ids` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '被@的用户id列表，逗号分隔',
  `receipt` tinyint(4) NULL DEFAULT 0 COMMENT '是否回执消息',
  `receipt_ok` tinyint(4) NULL DEFAULT 0 COMMENT '回执消息是否完成',
  `type` tinyint(1) NOT NULL COMMENT '消息类型 0:文字 1:图片 2:文件 3:语音 4:视频 10:系统提示',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '状态 0:未发出 1:已送达  2:撤回 3:已读',
  `send_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of im_group_message
-- ----------------------------

-- ----------------------------
-- Table structure for im_private_message
-- ----------------------------
DROP TABLE IF EXISTS `im_private_message`;
CREATE TABLE `im_private_message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `send_id` bigint(20) NOT NULL COMMENT '发送用户id',
  `recv_id` bigint(20) NOT NULL COMMENT '接收用户id',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '发送内容',
  `type` tinyint(1) NOT NULL COMMENT '消息类型 0:文字 1:图片 2:文件 3:语音 10:系统提示',
  `status` tinyint(1) NOT NULL COMMENT '状态 0:未读 1:已读 2:撤回',
  `send_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_send_recv_id`(`send_id`, `recv_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '私聊消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of im_private_message
-- ----------------------------
INSERT INTO `im_private_message` VALUES (20, 4, 1, '#憨笑;', 0, 3, '2024-08-20 12:47:05');
INSERT INTO `im_private_message` VALUES (21, 6, 1, '哈喽', 0, 3, '2024-08-23 23:05:28');
INSERT INTO `im_private_message` VALUES (22, 1, 6, 'hi', 0, 3, '2024-08-23 23:05:55');
INSERT INTO `im_private_message` VALUES (23, 10, 1, 'hi', 0, 3, '2024-08-24 00:26:44');
INSERT INTO `im_private_message` VALUES (24, 11, 1, '微信1111', 0, 3, '2024-08-24 00:29:47');
INSERT INTO `im_private_message` VALUES (25, 11, 1, '#憨笑;#媚眼;', 0, 3, '2024-08-24 00:29:53');
INSERT INTO `im_private_message` VALUES (26, 1, 11, '{\"originUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724430595360.jpeg\",\"thumbUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724430595360.jpeg\"}', 1, 3, '2024-08-24 00:29:55');
INSERT INTO `im_private_message` VALUES (27, 11, 1, '{\"originUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724430604780.png\",\"thumbUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724430605846.png\"}', 1, 3, '2024-08-24 00:30:06');
INSERT INTO `im_private_message` VALUES (28, 1, 11, '照片发下', 0, 3, '2024-08-24 00:30:07');
INSERT INTO `im_private_message` VALUES (29, 1, 11, 'OK', 0, 3, '2024-08-24 00:30:09');
INSERT INTO `im_private_message` VALUES (30, 1, 11, '那就都正常了', 0, 3, '2024-08-24 00:30:15');
INSERT INTO `im_private_message` VALUES (31, 11, 1, '嗯嗯', 0, 3, '2024-08-24 00:30:19');
INSERT INTO `im_private_message` VALUES (32, 1, 11, '辛苦了', 0, 3, '2024-08-24 00:30:23');
INSERT INTO `im_private_message` VALUES (33, 11, 1, '没事的，我在看下其他的', 0, 3, '2024-08-24 00:30:36');
INSERT INTO `im_private_message` VALUES (34, 1, 11, '好的', 0, 3, '2024-08-24 00:30:40');
INSERT INTO `im_private_message` VALUES (35, 1, 11, '有啥问题 及时说哈', 0, 3, '2024-08-24 00:30:44');
INSERT INTO `im_private_message` VALUES (36, 11, 1, '好', 0, 3, '2024-08-24 00:30:48');
INSERT INTO `im_private_message` VALUES (37, 6, 1, '#爱心;', 0, 3, '2024-08-24 17:41:34');
INSERT INTO `im_private_message` VALUES (38, 6, 1, '{\"originUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724492504489.png\",\"thumbUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724492505574.png\"}', 1, 3, '2024-08-24 17:41:46');
INSERT INTO `im_private_message` VALUES (39, 11, 1, '{\"originUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724494653305.png\",\"thumbUrl\":\"http://8.223.52.102:9090/box-im/image/20240824/1724494654146.png\"}', 1, 3, '2024-08-24 18:17:34');
INSERT INTO `im_private_message` VALUES (40, 11, 1, '11', 0, 3, '2024-08-24 18:17:40');
INSERT INTO `im_private_message` VALUES (41, 11, 1, '#憨笑;', 0, 3, '2024-08-24 18:17:44');
INSERT INTO `im_private_message` VALUES (42, 4, 1, '#憨笑;', 0, 3, '2024-09-01 19:53:43');
INSERT INTO `im_private_message` VALUES (43, 6, 1, '111', 0, 3, '2024-10-05 20:05:24');
INSERT INTO `im_private_message` VALUES (44, 1, 6, '{\"originUrl\":\"http://47.57.188.97:9090/box-im/image/20241005/1728129935771.jpeg\",\"thumbUrl\":\"http://47.57.188.97:9090/box-im/image/20241005/1728129935771.jpeg\"}', 1, 3, '2024-10-05 20:05:36');
INSERT INTO `im_private_message` VALUES (45, 1, 6, '4654', 0, 3, '2024-10-05 20:05:40');
INSERT INTO `im_private_message` VALUES (46, 6, 1, '123', 0, 3, '2024-10-12 10:31:48');
INSERT INTO `im_private_message` VALUES (47, 1, 6, '{\"originUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241013/1728784153876.jpeg\",\"thumbUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241013/1728784153876.jpeg\"}', 1, 3, '2024-10-13 09:49:14');
INSERT INTO `im_private_message` VALUES (48, 6, 1, '你好', 0, 3, '2024-10-13 10:02:15');
INSERT INTO `im_private_message` VALUES (49, 6, 1, '{\"originUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241013/1728784953229.jpg\",\"thumbUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241013/1728784954058.jpg\"}', 1, 3, '2024-10-13 10:02:34');
INSERT INTO `im_private_message` VALUES (50, 6, 1, '154', 0, 3, '2024-10-13 10:12:03');
INSERT INTO `im_private_message` VALUES (51, 1, 6, '{\"originUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241013/1728785554567.jpeg\",\"thumbUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241013/1728785554567.jpeg\"}', 1, 3, '2024-10-13 10:12:35');
INSERT INTO `im_private_message` VALUES (52, 4, 1, '#憨笑;', 0, 3, '2024-10-13 10:41:19');
INSERT INTO `im_private_message` VALUES (53, 14, 1, '#憨笑;#媚眼;', 0, 3, '2024-10-13 10:41:22');
INSERT INTO `im_private_message` VALUES (54, 15, 1, '156', 0, 3, '2024-10-19 22:24:56');
INSERT INTO `im_private_message` VALUES (55, 1, 6, '123', 0, 3, '2024-10-19 22:28:12');
INSERT INTO `im_private_message` VALUES (56, 6, 1, '12312', 0, 3, '2024-10-19 22:28:24');
INSERT INTO `im_private_message` VALUES (57, 4, 1, '1', 0, 3, '2024-10-20 20:59:19');
INSERT INTO `im_private_message` VALUES (58, 14, 1, '1', 0, 3, '2024-10-20 21:14:58');
INSERT INTO `im_private_message` VALUES (59, 7, 1, '{\"originUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241022/1729527989477.jpg\",\"thumbUrl\":\"https://ngbt.qdcspy.shop/file/box-im/image/20241022/1729527989597.jpg\"}', 1, 3, '2024-10-22 00:26:30');
INSERT INTO `im_private_message` VALUES (60, 14, 1, '111', 0, 3, '2024-10-22 13:52:05');
INSERT INTO `im_private_message` VALUES (61, 4, 1, '1', 0, 3, '2024-10-22 13:54:53');
INSERT INTO `im_private_message` VALUES (62, 13, 1, '1', 0, 3, '2024-10-23 14:32:21');
INSERT INTO `im_private_message` VALUES (63, 1, 13, '#快乐;', 0, 3, '2024-10-23 14:32:42');
INSERT INTO `im_private_message` VALUES (64, 13, 1, '1', 0, 3, '2024-10-23 14:32:51');
INSERT INTO `im_private_message` VALUES (65, 13, 1, '1', 0, 3, '2024-10-23 14:33:01');

-- ----------------------------
-- Table structure for im_user
-- ----------------------------
DROP TABLE IF EXISTS `im_user`;
CREATE TABLE `im_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `head_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像',
  `head_image_thumb` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户头像缩略图',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码(明文)',
  `sex` tinyint(1) NULL DEFAULT 0 COMMENT '性别 0:男 1:女',
  `type` smallint(6) NULL DEFAULT 1 COMMENT '用户类型 1:客服用户 2:普通用户',
  `signature` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '个性签名',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `created_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `third_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '第三方用户uuid',
  `remark_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `user_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户注册IP',
  `user_ip_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户注册IP归属地',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_user_name`(`user_name`) USING BTREE,
  INDEX `idx_nick_name`(`nick_name`(191)) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of im_user
-- ----------------------------
INSERT INTO `im_user` VALUES (1, 'kefu1', '充值客服账号1', '', '', '$2a$10$Zx.3MGTvRiwF/SVSPaNc1u91rpFpNaCl2xau5h.gOcH7H5jrH74Py', 0, 1, '', NULL, '2024-04-09 20:18:54', NULL, NULL, NULL, '福建省:高砂3');
INSERT INTO `im_user` VALUES (2, 'kefu2', '充值客服账号2', '', '', '$2a$10$Zx.3MGTvRiwF/SVSPaNc1u91rpFpNaCl2xau5h.gOcH7H5jrH74Py', 0, 1, '', NULL, '2024-04-09 20:37:07', NULL, NULL, NULL, '福建省:高砂2');
INSERT INTO `im_user` VALUES (3, '微信用户_20240818222543007', 'xmdw', '', '', '$2a$10$MKavClPwy2RLFY6asolaqeSAqPc9xvwER3p0nFudWI6ZZJrMnNp/m', 0, 2, '', NULL, '2024-08-20 01:33:51', 'sharkUser6', NULL, '127.0.0.1', 'null:null');
INSERT INTO `im_user` VALUES (4, '微信用户_20240820123342959', '啊啊啊啊', '', '', '$2a$10$WAuqIXddIVNbdUzwJiV4RulINTSYjBpCeSA7iFyO48REqzqqqMpBi', 0, 2, '', NULL, '2024-08-20 12:47:01', 'sharkUser9', NULL, '127.0.0.1', 'null:null');
INSERT INTO `im_user` VALUES (5, '微信用户_20240820174210453', '顺风顺水', '', '', '$2a$10$JmusQVDQVRnxQv62skw1nedA0PN0tB1FUCAr8XACHCTAZwielwBGK', 0, 2, '', NULL, '2024-08-20 17:42:35', 'sharkUser12', NULL, '127.0.0.1', 'null:null');
INSERT INTO `im_user` VALUES (6, '1111', '1111', '', '', '$2a$10$c5QZshbl2dLpiWDmP0dXnunbOOHriPG3KVM.qjAoOlIHJg80tXrni', 0, 2, '', NULL, '2024-08-20 21:03:15', 'sharkUser7', NULL, '127.0.0.1', 'null:null');
INSERT INTO `im_user` VALUES (7, '微信用户_20240821141530393', 'wtsn', '', '', '$2a$10$7KcWS92HXjZa38AuWAerVejSgno3gzH1qiB26MnS84LPAyvCZltk2', 0, 2, '', NULL, '2024-08-24 00:07:02', 'sharkUser18', NULL, '127.0.0.1', 'null:null');
INSERT INTO `im_user` VALUES (11, '微信用户_20240820202646739', 'xmdw🍎', '', '', '$2a$10$plcbctAtlfqNSiu.TfwpoeJ8q/0RY4PGGIXUzGPSMQM6Mts93AUVG', 0, 2, '', NULL, '2024-08-24 00:28:10', 'sharkUser17', NULL, '127.0.0.1', 'null:null');
INSERT INTO `im_user` VALUES (12, 'U20240901203833', 'temp_20240901203833', '', '', '$2a$10$1HCjRr3SQxrun6IHY2JELOkDE6vxSk7GKhRC7B/LxzqTWTG8Y0E5u', 0, 2, '', NULL, '2024-09-01 20:38:33', '', NULL, '', NULL);
INSERT INTO `im_user` VALUES (13, '微信用户_20241012162054174', '啊贵', '', '', '$2a$10$AJcsNdwA0rzKZ44mMZXYEuJOwvcL5cmNDs6e271CmmMpPjagcEGn2', 0, 2, '', NULL, '2024-10-12 16:21:49', 'sharkUser33', NULL, '182.239.92.91', '中西區:Queen\'s Terrace');
INSERT INTO `im_user` VALUES (14, '微信用户_20240820123840673', '马上开始', '', '', '$2a$10$Ek7eyRWdDYKizsPsfzlBNOlgMgeD0DihojHwTgsP5vpsDhWiklR.e', 0, 2, '', NULL, '2024-10-13 10:41:17', 'sharkUser10', NULL, '182.239.114.126', '葵青區:葵涌');
INSERT INTO `im_user` VALUES (15, '微信用户_20240928082609176', '※儚謃※', '', '', '$2a$10$Hz/fcMV1E3HEYUnH.z6QQuSq0vg3OCW8a3oT.dC/z86uLMtWWDAVS', 0, 2, '', NULL, '2024-10-13 13:53:42', 'sharkUser28', NULL, '183.250.118.51', '福建省:厦门市');
INSERT INTO `im_user` VALUES (16, '微信用户_20241023145554258', '器皿', '', '', '$2a$10$t4Shn5CbADlv8JG8ia2nfO43L/ViZ0Ezlg0BmDW3ydIjeGyk02mgW', 0, 2, '', NULL, '2024-10-23 20:17:56', 'sharkUser38', NULL, '211.97.130.79', '福建省:厦门市');

SET FOREIGN_KEY_CHECKS = 1;
