/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : ai_music_db

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 30/05/2023 20:48:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for music
-- ----------------------------
DROP TABLE IF EXISTS `music`;
CREATE TABLE `music`  (
                          `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `user` bigint unsigned NOT NULL COMMENT '用户ID',
                          `hash` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '文件Hash',
                          `title` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '歌曲标题',
                          `artist` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '原唱歌手',
                          `ai` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'AI翻唱歌手',
                          `status` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'AI处理状态',
                          `created_time` datetime(0) NOT NULL COMMENT '创建时间',
                          `updated_time` datetime(0) NOT NULL COMMENT '修改时间',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `idx_user`(`user`) USING BTREE,
                          UNIQUE INDEX `unique_user_hash`(`user`, `hash`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '歌曲' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
