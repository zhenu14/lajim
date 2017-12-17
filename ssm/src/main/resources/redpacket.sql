/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : redpacket

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-12-18 00:15:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_red_packet
-- ----------------------------
DROP TABLE IF EXISTS `t_red_packet`;
CREATE TABLE `t_red_packet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `send_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `total` int(11) NOT NULL,
  `unit_amount` decimal(12,0) NOT NULL,
  `stock` int(11) NOT NULL,
  `version` int(12) NOT NULL DEFAULT '0',
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index_stock` (`stock`),
  KEY `index_version` (`version`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_red_packet
-- ----------------------------
INSERT INTO `t_red_packet` VALUES ('1', '1', '200000', '2017-12-17 20:17:29', '20000', '10', '4993', '0', '20万呢');
INSERT INTO `t_red_packet` VALUES ('2', '1', '200000', '2017-12-17 21:24:36', '20000', '10', '2817', '17183', '20万呢');
INSERT INTO `t_red_packet` VALUES ('3', '1', '200000', '2017-12-17 22:51:44', '20000', '10', '20000', '0', '20万呢');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rolename` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'role_name_1', 'note');

-- ----------------------------
-- Table structure for t_user_red_packet
-- ----------------------------
DROP TABLE IF EXISTS `t_user_red_packet`;
CREATE TABLE `t_user_red_packet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `red_packet_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `grab_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `note` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42878 DEFAULT CHARSET=gb2312;

-- ----------------------------
-- Records of t_user_red_packet
-- ----------------------------
