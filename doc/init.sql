-- 数据字典表
DROP TABLE IF EXISTS `system_dictionaries`;
CREATE TABLE `system_dictionaries` (
  `id` varchar(50) NOT NULL  COMMENT '主键',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `code` varchar(50) DEFAULT NULL COMMENT '字典编码',
  `value` varchar(100) DEFAULT NULL COMMENT '字典值',
  `discription` varchar(500) DEFAULT NULL COMMENT '描述',
  `rank` int(11) DEFAULT NULL COMMENT '排列顺序',
  `valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '上级code值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典表';


-- 用户表
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` varchar(32) NOT NULL COMMENT '用户id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名称，默认同手机号一致',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `card_num` varchar(50) DEFAULT NULL COMMENT '卡号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `balance` varchar(50) DEFAULT NULL COMMENT '余额',
  `frequency` int(11) DEFAULT NULL COMMENT '次数',
  `address` varchar(300) DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `type` int(11) DEFAULT NULL COMMENT '1为普通账户，5为vip账户，100为管理员，101为超级管理员',
  `state` int(11) DEFAULT NULL COMMENT '1为有效，0为失效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `last_time` datetime DEFAULT NULL COMMENT '最后消费时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 菜单表
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `menu_function` int(11) DEFAULT NULL COMMENT '已废弃，菜单还是功能点，菜单为1，功能点为2',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `pname` varchar(50) DEFAULT NULL COMMENT '用来存放父级菜单名称',
  `url` varchar(500) DEFAULT NULL COMMENT '跳转的URL',
  `icon` varchar(200) DEFAULT NULL COMMENT '图标，这块可以存类样式，后续配置一些固定的模板图标',
  `sort_num` int(11) DEFAULT NULL COMMENT '排序使用的字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
-- 菜单数据
INSERT INTO `system_menu` VALUES (1, '系统管理', 1, 0, NULL, NULL, 'icon iconfont icon-gonggeshitu', 99, NULL, '2019-05-08 16:09:27');
INSERT INTO `system_menu` VALUES (2, '菜单管理', 1, 1, NULL, '/menu/querypage', 'icon iconfont icon-Raidobox-weixuan', 1, NULL, '2019-05-08 16:09:01');
INSERT INTO `system_menu` VALUES (3, '会员管理', 1, 0, NULL, NULL, 'icon iconfont icon-jiaosequnti', 1, '2019-05-08 16:10:38', NULL);
INSERT INTO `system_menu` VALUES (4, '用户管理', 1, 3, NULL, '/member/input', 'icon iconfont icon-zhucetianjiahaoyou', 1, '2019-05-08 16:11:55', NULL);


-- 消费表
DROP TABLE IF EXISTS `consume_record`;
CREATE TABLE `consume_record` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户id',
  `balance` varchar(20) DEFAULT NULL COMMENT '消费金额',
  `frequency` int(11) DEFAULT NULL COMMENT '消费次数',
  `last_time` datetime DEFAULT NULL COMMENT '消费时间',
  `discount` varchar(50) DEFAULT NULL COMMENT '折扣',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
