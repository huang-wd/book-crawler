/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : book_crawler

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 05/12/2019 14:06:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lorefree_book_detail
-- ----------------------------
DROP TABLE IF EXISTS `lorefree_book_detail`;
CREATE TABLE "lorefree_book_detail" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  "score" int(11) NOT NULL DEFAULT '0',
  "status" int(11) NOT NULL DEFAULT '0',
  "book_id" int(11) NOT NULL DEFAULT '0',
  "title" varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  "sub_title" varchar(500) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  "collection_count" int(11) NOT NULL DEFAULT '0',
  "source_url" varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY ("id"),
  UNIQUE KEY "idx_book_id" ("book_id") USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for lorefree_book_list
-- ----------------------------
DROP TABLE IF EXISTS `lorefree_book_list`;
CREATE TABLE "lorefree_book_list" (
  "id" int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  "page_num" int(11) NOT NULL DEFAULT '0',
  "status" int(11) NOT NULL DEFAULT '0',
  "source_url" varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '',
  "create_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "update_time" datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY ("id"),
  UNIQUE KEY "idx_page_num" ("page_num") USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
