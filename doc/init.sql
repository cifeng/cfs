-- 数据字典表
DROP TABLE `system_dictionaries`;
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


-- 数据字典表
DROP TABLE `system_user`;
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



