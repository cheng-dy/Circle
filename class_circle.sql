/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : class_circle

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 09/04/2018 19:23:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `school` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `className` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`code`) USING BTREE,
  INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('a', 'a', 1);
INSERT INTO `class` VALUES ('浙江工业大学', '软工1501', 2);
INSERT INTO `class` VALUES ('Stanford', 'one', 3);
INSERT INTO `class` VALUES ('zjut', 'two', 4);
INSERT INTO `class` VALUES ('zjut', 'one', 5);
INSERT INTO `class` VALUES ('ari', 'one', 6);
INSERT INTO `class` VALUES ('rge', '1', 7);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `commentID` bigint(20) NOT NULL AUTO_INCREMENT,
  `dynamicID` bigint(20) NOT NULL,
  `time` datetime(0) NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userID` bigint(20) NOT NULL,
  PRIMARY KEY (`commentID`) USING BTREE,
  INDEX `comment_ibfk_1`(`dynamicID`) USING BTREE,
  INDEX `comment_ibfk_2`(`userID`) USING BTREE,
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`dynamicID`) REFERENCES `dynamic` (`dynamicID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (4, 3, '2017-12-27 00:17:05', 'thanks', 4);
INSERT INTO `comment` VALUES (5, 3, '2017-12-27 00:26:56', 'thanks', 4);
INSERT INTO `comment` VALUES (6, 3, '2017-12-27 16:49:29', 'we', 4);
INSERT INTO `comment` VALUES (7, 3, '2017-12-27 16:52:29', 'I', 4);
INSERT INTO `comment` VALUES (8, 1, '2017-12-27 20:47:46', 'the good', 4);
INSERT INTO `comment` VALUES (9, 1, '2017-12-27 20:50:42', 'ran v the same time as my husband will the good thing about', 4);
INSERT INTO `comment` VALUES (10, 1, '2017-12-27 20:51:12', 'thanks', 4);
INSERT INTO `comment` VALUES (11, 3, '2017-12-27 20:51:49', 'It\'s free Jude x', 4);
INSERT INTO `comment` VALUES (12, 1, '2017-12-27 20:52:03', 'guy aww Hyde he dhd', 4);
INSERT INTO `comment` VALUES (14, 1, '2017-12-27 20:53:07', 'odshss', 4);
INSERT INTO `comment` VALUES (15, 3, '2017-12-27 20:54:41', 'you are doing great', 4);
INSERT INTO `comment` VALUES (16, 1, '2017-12-27 20:55:08', 'ydanx jdcmd', 4);
INSERT INTO `comment` VALUES (17, 1, '2017-12-28 14:41:54', 'I\'m going back', 4);
INSERT INTO `comment` VALUES (18, 3, '2017-12-29 19:27:48', 'gskdbcdbcf', 4);
INSERT INTO `comment` VALUES (19, 3, '2017-12-29 19:28:03', 'we', 4);
INSERT INTO `comment` VALUES (20, 6, '2018-01-01 14:53:30', 'thanks', 6);
INSERT INTO `comment` VALUES (21, 5, '2018-01-03 15:25:05', 'thanks', 9);
INSERT INTO `comment` VALUES (22, 13, '2018-01-05 15:34:15', 'good job', 4);
INSERT INTO `comment` VALUES (23, 13, '2018-01-05 15:41:25', 'test', 4);
INSERT INTO `comment` VALUES (24, 13, '2018-01-05 16:41:18', 'we', 4);
INSERT INTO `comment` VALUES (25, 13, '2018-01-05 17:39:28', 'done', 4);
INSERT INTO `comment` VALUES (26, 14, '2018-01-06 19:14:40', 'good job', 4);
INSERT INTO `comment` VALUES (28, 14, '2018-01-08 11:49:17', 'thanks', 1);
INSERT INTO `comment` VALUES (29, 10, '2018-01-09 18:31:30', 'thanks', 4);
INSERT INTO `comment` VALUES (30, 10, '2018-01-09 19:38:54', 'we', 19);

-- ----------------------------
-- Table structure for dynamic
-- ----------------------------
DROP TABLE IF EXISTS `dynamic`;
CREATE TABLE `dynamic`  (
  `dynamicID` bigint(20) NOT NULL AUTO_INCREMENT,
  `userID` bigint(20) NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `imageUrls` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `likeAmount` int(11) NOT NULL,
  PRIMARY KEY (`dynamicID`) USING BTREE,
  INDEX `dynamic_ibfk_1`(`userID`) USING BTREE,
  CONSTRAINT `dynamic_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`userID`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dynamic
-- ----------------------------
INSERT INTO `dynamic` VALUES (1, 9, '2017-12-19 00:00:00', '小戏骨，一开机，哪哪都是戏！', 'http://10.0.2.2:8080/fileUpload/upload/l.png;', 3);
INSERT INTO `dynamic` VALUES (3, 1, '2017-12-26 00:00:00', '周末空闲下来陪两个小调皮看书，哥哥大一点还是懂得要多让着弟弟，感觉好幸福。', 'http://10.0.2.2:8080/fileUpload/upload/d1.png;', 9);
INSERT INTO `dynamic` VALUES (4, 10, '2017-12-31 00:00:00', '好久没有带孩子出来玩过了，放任她在家天天看电视，不如这样出来活动活动，感觉孩子也开心很多', 'http://10.0.2.2:8080/fileUpload/upload/d2.png;', 1);
INSERT INTO `dynamic` VALUES (5, 11, '2017-12-31 00:00:00', '看见她和小区里的比她小的小朋友玩游戏，总是哄着让着小弟弟小妹妹，突然发现孩子长大懂事了', 'http://10.0.2.2:8080/fileUpload/upload/d3.png;', 1);
INSERT INTO `dynamic` VALUES (6, 9, '2018-01-01 14:49:49', '大清早就缠着我要出去玩，小孩子就是有活力啊', 'http://10.0.2.2:8080/fileUpload/upload/l2.png;', 0);
INSERT INTO `dynamic` VALUES (7, 12, '2018-01-03 15:26:15', '最完美的不过就是一家人的团聚和相爱', 'http://10.0.2.2:8080/fileUpload/upload/d4.png;', 0);
INSERT INTO `dynamic` VALUES (8, 13, '2018-01-05 00:00:00', '周末天气好，一家人都去了动物园，孩子看小动物怎么看都不捏，还和大猩猩拍了合照', 'http://10.0.2.2:8080/fileUpload/upload/d5.png;', 1);
INSERT INTO `dynamic` VALUES (9, 14, '2018-01-05 14:42:00', '今天放学回来主动完成了作业，要我奖励他一颗棒棒糖', 'http://10.0.2.2:8080/fileUpload/upload/d6.png;', 0);
INSERT INTO `dynamic` VALUES (10, 9, '2018-01-09 00:00:00', '早上很早就起来了，自己翻了半天衣服，女孩子就是臭美，那么小就想着打扮了', 'http://10.0.2.2:8080/fileUpload/upload/l3.png;', 5);
INSERT INTO `dynamic` VALUES (13, 9, '2018-01-05 00:00:00', '又大一岁了，日子过得太快，总要多去拍点照片来留住回忆，记录你的成长', 'http://10.0.2.2:8080/fileUpload/upload/l41.png;http://10.0.2.2:8080/fileUpload/upload/l51.png;', 6);
INSERT INTO `dynamic` VALUES (14, 4, '2018-01-06 00:00:00', '', 'http://10.0.2.2:8080/fileUpload/upload/td1.png;http://10.0.2.2:8080/fileUpload/upload/td2.png;http://10.0.2.2:8080/fileUpload/upload/td3.png;', 2);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `noticeID` bigint(20) NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `time` datetime(0) NULL,
  `classID` bigint(20) NOT NULL,
  PRIMARY KEY (`noticeID`) USING BTREE,
  INDEX `classID`(`classID`) USING BTREE,
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`classID`) REFERENCES `class` (`code`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '明日下午两点的家长会，请各位家长务必准时到场', '2018-01-01 21:37:59', 1);
INSERT INTO `notice` VALUES (2, '25日早上8点，将举行亲子互动绘画比赛，为了让孩子不再孤独，请各位家长尽量参加', '2018-01-01 21:38:29', 1);
INSERT INTO `notice` VALUES (3, '明天下午两点，诚心邀请各位家长参加学生午餐，并且同孩子共同进餐', '2018-01-01 22:57:35', 1);
INSERT INTO `notice` VALUES (5, 'hello world', '2018-01-05 17:39:45', 1);
INSERT INTO `notice` VALUES (6, 'dhdt', '2018-01-08 11:47:53', 7);
INSERT INTO `notice` VALUES (7, 'gdfsgfddf ', '2018-01-09 19:40:54', 1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `childID` bigint(20) NOT NULL AUTO_INCREMENT,
  `classID` bigint(20) NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sex` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`childID`) USING BTREE,
  INDEX `classID`(`classID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userID` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classID` bigint(20) NULL DEFAULT NULL,
  `phoneNumber` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `characters` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `childName` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `headerImg` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`userID`) USING BTREE,
  INDEX `classID`(`classID`) USING BTREE,
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`classID`) REFERENCES `class` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '李晴川的家长', 1, '123', '123', 'parent', '李晴川', 'http://10.0.2.2:8080/fileUpload/upload/h1.png');
INSERT INTO `user` VALUES (4, '班主任老师', 1, 'b', 'b', 'master', NULL, 'http://10.0.2.2:8080/fileUpload/upload/t2.png');
INSERT INTO `user` VALUES (5, '班主任老师', 2, '15958019273', '123', 'master', NULL, NULL);
INSERT INTO `user` VALUES (6, '班主任老师', 3, '15957123306', '123', 'master', NULL, NULL);
INSERT INTO `user` VALUES (9, '梁一涵的家长', 1, '789', '789', 'parent', '梁一涵', 'http://10.0.2.2:8080/fileUpload/upload/liu.png');
INSERT INTO `user` VALUES (10, '冯孝秋的家长', 1, '123', '345', 'parent', '冯孝秋', 'http://10.0.2.2:8080/fileUpload/upload/h2.png');
INSERT INTO `user` VALUES (11, '赵小斯的家长', 1, '123', '234', 'parent', '赵小斯', 'http://10.0.2.2:8080/fileUpload/upload/h3.png');
INSERT INTO `user` VALUES (12, '李明的家长', 1, '123', '456', 'parent', '李明', 'http://10.0.2.2:8080/fileUpload/upload/h4.png');
INSERT INTO `user` VALUES (13, '陈艺的家长', 1, '123', '567', 'parent', '陈艺', 'http://10.0.2.2:8080/fileUpload/upload/h5.png');
INSERT INTO `user` VALUES (14, '黄小付的家长', 2, '123', '678', 'parent', '黄小付', 'http://10.0.2.2:8080/fileUpload/upload/h6.png');
INSERT INTO `user` VALUES (15, '班主任老师', 6, '1324563', 'fsa', 'master', NULL, NULL);
INSERT INTO `user` VALUES (17, '班主任老师', 7, 'qaz', 'qaz', 'master', NULL, NULL);
INSERT INTO `user` VALUES (18, 's的家长', 1, '11', '11', 'parent', 's', NULL);
INSERT INTO `user` VALUES (19, 'cdy的家长', 1, '963', 'qwe', 'parent', 'cdy', NULL);

-- ----------------------------
-- Triggers structure for table comment
-- ----------------------------
DROP TRIGGER IF EXISTS `T2`;
delimiter ;;
CREATE TRIGGER `T2` BEFORE INSERT ON `comment` FOR EACH ROW BEGIN
SET NEW.time = NOW();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table dynamic
-- ----------------------------
DROP TRIGGER IF EXISTS `T1`;
delimiter ;;
CREATE TRIGGER `T1` BEFORE INSERT ON `dynamic` FOR EACH ROW BEGIN
SET NEW.time = NOW();
END
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table notice
-- ----------------------------
DROP TRIGGER IF EXISTS `T3`;
delimiter ;;
CREATE TRIGGER `T3` BEFORE INSERT ON `notice` FOR EACH ROW BEGIN
SET NEW.time = NOW();
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
