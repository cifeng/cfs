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