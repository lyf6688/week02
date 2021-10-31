
CREATE TABLE `order_dt`  (
  `id` bigint(20) NOT NULL,
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编码',
  `sku_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品ID',
  `sku_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `created_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `total_money` decimal(20, 0) NULL DEFAULT NULL COMMENT '商品总金额',
  `actual_money` decimal(20, 0) NULL DEFAULT NULL COMMENT '实际金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL,
  `order_no` varchar(32) DEFAULT NULL COMMENT '订单编码',
  `order_status` tinyint(11) DEFAULT NULL COMMENT '订单状态',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `created_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `pay_time` bigint(20) DEFAULT NULL COMMENT '支付时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `delivery_time` bigint(20) DEFAULT NULL COMMENT '发货时间',
  `total_money` decimal(20,0) DEFAULT NULL COMMENT '商品总金额',
  `actual_money` decimal(20,0) DEFAULT NULL COMMENT '实际金额',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `aceeptor` varchar(32) DEFAULT NULL COMMENT '收件人信息',
  `phone` varchar(11) NOT NULL COMMENT '收件人号码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `sku_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品ID',
  `sku_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '商品名称',
  `created_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `total_money` decimal(20,0) DEFAULT NULL COMMENT '商品总金额',
  `stock` int(20) DEFAULT NULL COMMENT '商品库存',
  `status` varchar(255) DEFAULT NULL COMMENT '状态',
  `pic` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `status` tinyint(11) DEFAULT NULL COMMENT '用户状态',
  `created_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `update_time` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `aceeptor` varchar(32) DEFAULT NULL COMMENT '收件人信息',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '收件人号码',
  `user_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
