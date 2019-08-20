/*
Navicat MySQL Data Transfer

Source Server         : maple
Source Server Version : 50722
Source Host           : 127.0.0.1:3306
Source Database       : maple

Target Server Type    : MYSQL
Date: 2019-08-20 17:35:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `base_oauth_client_details`;
CREATE TABLE `base_oauth_client_details` (
  `client_id` varchar(32) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='终端信息表';

-- ----------------------------
-- Records of base_oauth_client_details
-- ----------------------------
INSERT INTO `base_oauth_client_details` VALUES ('maple', null, 'maple', 'server', 'password,refresh_token,authorization_code,client_credentials', 'http://localhost:3000/aa', null, null, null, null, 'true');

-- ----------------------------
-- Table structure for base_resources
-- ----------------------------
DROP TABLE IF EXISTS `base_resources`;
CREATE TABLE `base_resources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父级id',
  `res_name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `res_code` varchar(255) DEFAULT NULL COMMENT '按钮代码',
  `res_desc` varchar(255) DEFAULT NULL COMMENT '资源描述',
  `res_type` int(2) DEFAULT NULL COMMENT '资源类型 1：菜单，2：权限',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `sort_num` int(11) DEFAULT NULL COMMENT '序号',
  `path` varchar(255) DEFAULT NULL COMMENT '路由',
  `component` varchar(255) DEFAULT NULL COMMENT 'vue路径',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(1) DEFAULT '0' COMMENT '是否删除',
  `status` int(1) DEFAULT '0' COMMENT '状态（0：正常   1：停用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='基础信息-资源表';

-- ----------------------------
-- Records of base_resources
-- ----------------------------
INSERT INTO `base_resources` VALUES ('1', '-1', '基础管理', null, '基础管理', '1', 'icon-quanxianguanli', '1', '/mgr', 'Main', '2019-08-05 15:49:21', null, '0', '0');
INSERT INTO `base_resources` VALUES ('2', '1', '权限管理', null, '权限管理', '1', 'icon-caidanguanli', '1', 'menu', 'view/mgr/menu/menu', '2019-08-05 15:59:58', null, '0', '0');
INSERT INTO `base_resources` VALUES ('3', '1', '系统管理', null, '系统管理', '1', 'icon-quanxianguanli', '1', 'system/microservice', 'view/mgr/system/microservice/service', '2019-08-05 15:49:21', '2019-08-10 11:38:51', '0', '0');
INSERT INTO `base_resources` VALUES ('4', '2', '新增', 'res_add', '新增', '2', null, '1', null, null, '2019-08-09 11:01:58', null, '0', '0');
INSERT INTO `base_resources` VALUES ('5', '2', '编辑', 'res_edit', '编辑', '2', null, '2', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('6', '2', '删除', 'res_del', '删除', '2', null, '3', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('7', '1', '用户管理', null, '用户管理', '1', null, '2', 'user', 'view/mgr/user/user', '2019-08-09 13:45:16', '2019-08-10 11:38:19', '0', '0');
INSERT INTO `base_resources` VALUES ('9', '7', '新增', 'user_add', '新增', '2', null, '1', null, null, '2019-08-09 11:01:58', null, '0', '0');
INSERT INTO `base_resources` VALUES ('10', '7', '编辑', 'user_edit', '编辑', '2', null, '2', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('11', '7', '删除', 'user_del', '删除', '2', null, '3', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('12', '3', '新增服务', 'service_add', '新增服务', '2', null, '1', null, null, '2019-08-12 16:16:34', '2019-08-12 16:18:46', '0', '0');
INSERT INTO `base_resources` VALUES ('13', '3', '修改服务', 'service_edit', '修改服务', '2', 'icon-quanxianguanli', '2', null, null, '2019-08-12 16:16:57', '2019-08-12 16:18:54', '0', '0');
INSERT INTO `base_resources` VALUES ('14', '3', '删除服务', 'service_del', '删除服务', '2', 'icon-quanxianguanli', '3', null, null, '2019-08-12 16:17:18', '2019-08-12 16:19:28', '0', '0');
INSERT INTO `base_resources` VALUES ('15', '1', '角色管理', null, '角色管理', '1', null, '3', 'role', 'view/mgr/role/role', null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('16', '15', '新增', 'role_add', null, '2', null, '1', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('17', '15', '修改', 'role_edit', null, '2', null, '2', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('18', '15', '删除', 'role_del', null, '2', null, '3', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('19', '15', '授权', 'role_perm', null, '2', null, '4', null, null, null, null, '0', '0');
INSERT INTO `base_resources` VALUES ('20', '3', '配置管理', 'service_config', 'config配置管理', '2', null, '4', null, null, '2019-08-13 10:25:32', '2019-08-13 13:08:31', '0', '0');
INSERT INTO `base_resources` VALUES ('21', '3', '路由管理', 'service_route', 'gateway动态路由管理', '2', null, '5', null, null, '2019-08-13 13:09:08', '2019-08-13 13:09:21', '0', '0');
INSERT INTO `base_resources` VALUES ('22', '20', '新增配置', 'config_add', null, '2', null, '1', null, null, '2019-08-14 14:53:13', '2019-08-14 14:53:13', '0', '0');
INSERT INTO `base_resources` VALUES ('23', '20', '修改配置', 'config_edit', null, '2', null, '2', null, null, '2019-08-14 14:53:39', '2019-08-14 14:53:39', '0', '0');
INSERT INTO `base_resources` VALUES ('24', '20', '删除配置', 'config_del', null, '2', null, '3', null, null, '2019-08-14 14:54:12', '2019-08-14 14:54:12', '0', '0');
INSERT INTO `base_resources` VALUES ('26', '-1', '监控中心', null, null, '1', null, '2', '/monitor', 'Main', '2019-08-18 10:14:15', '2019-08-18 10:17:13', '0', '0');
INSERT INTO `base_resources` VALUES ('27', '26', '文件系统', null, null, '1', null, '2', 'minio', 'http://127.0.0.1:9000/minio/', '2019-08-18 10:14:59', '2019-08-18 10:18:38', '0', '0');
INSERT INTO `base_resources` VALUES ('28', '26', '系统监控', null, null, '1', null, '1', 'admin', 'view/monitor/admin/admin', '2019-08-18 10:18:32', '2019-08-18 10:18:32', '0', '0');

-- ----------------------------
-- Table structure for base_role
-- ----------------------------
DROP TABLE IF EXISTS `base_role`;
CREATE TABLE `base_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色代码',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `is_delete` int(1) DEFAULT NULL COMMENT '是否删除（0：否 1：是）',
  `status` int(1) DEFAULT NULL COMMENT '是否启用（0：正常， 1：停用）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='基础信息-用户角色表';

-- ----------------------------
-- Records of base_role
-- ----------------------------
INSERT INTO `base_role` VALUES ('1', 'ROLE_ADMIN', '管理员', '2019-07-08 16:41:34', '2019-08-14 15:36:51', '0', '0', '管理员');

-- ----------------------------
-- Table structure for base_role_res
-- ----------------------------
DROP TABLE IF EXISTS `base_role_res`;
CREATE TABLE `base_role_res` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `res_id` int(11) NOT NULL COMMENT '资源id',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=321 DEFAULT CHARSET=utf8 COMMENT='基础信息-角色和资源关联表';

-- ----------------------------
-- Records of base_role_res
-- ----------------------------
INSERT INTO `base_role_res` VALUES ('295', '1', '1', null);
INSERT INTO `base_role_res` VALUES ('296', '1', '2', null);
INSERT INTO `base_role_res` VALUES ('297', '1', '4', null);
INSERT INTO `base_role_res` VALUES ('298', '1', '5', null);
INSERT INTO `base_role_res` VALUES ('299', '1', '6', null);
INSERT INTO `base_role_res` VALUES ('300', '1', '3', null);
INSERT INTO `base_role_res` VALUES ('301', '1', '12', null);
INSERT INTO `base_role_res` VALUES ('302', '1', '13', null);
INSERT INTO `base_role_res` VALUES ('303', '1', '14', null);
INSERT INTO `base_role_res` VALUES ('304', '1', '20', null);
INSERT INTO `base_role_res` VALUES ('305', '1', '22', null);
INSERT INTO `base_role_res` VALUES ('306', '1', '23', null);
INSERT INTO `base_role_res` VALUES ('307', '1', '24', null);
INSERT INTO `base_role_res` VALUES ('308', '1', '21', null);
INSERT INTO `base_role_res` VALUES ('309', '1', '7', null);
INSERT INTO `base_role_res` VALUES ('310', '1', '9', null);
INSERT INTO `base_role_res` VALUES ('311', '1', '10', null);
INSERT INTO `base_role_res` VALUES ('312', '1', '11', null);
INSERT INTO `base_role_res` VALUES ('313', '1', '15', null);
INSERT INTO `base_role_res` VALUES ('314', '1', '16', null);
INSERT INTO `base_role_res` VALUES ('315', '1', '17', null);
INSERT INTO `base_role_res` VALUES ('316', '1', '18', null);
INSERT INTO `base_role_res` VALUES ('317', '1', '19', null);
INSERT INTO `base_role_res` VALUES ('318', '1', '26', null);
INSERT INTO `base_role_res` VALUES ('319', '1', '28', null);
INSERT INTO `base_role_res` VALUES ('320', '1', '27', null);

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(10) DEFAULT NULL COMMENT '昵称',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(32) DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email_status` int(1) DEFAULT '0' COMMENT '是否绑定邮箱(0：否；1：是）',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `qq_status` int(1) DEFAULT '0' COMMENT '是否绑定QQ（0：否；1：是）',
  `qq_link` varchar(12) DEFAULT NULL COMMENT 'QQ链接',
  `wx_status` int(1) DEFAULT '0' COMMENT '是否绑定微信（0：否； 1：是）',
  `wx_link` varchar(64) DEFAULT NULL COMMENT '微信链接',
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `is_delete` int(1) DEFAULT '0' COMMENT '是否删除',
  `is_lock` int(1) DEFAULT '0' COMMENT '是否锁定',
  `content` text COMMENT '个性签名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='基础信息-用户信息';

-- ----------------------------
-- Records of base_user
-- ----------------------------
INSERT INTO `base_user` VALUES ('1', '笑小枫', 'maple', '123456', null, '18300000000', '0', null, '0', null, '0', null, '2019-06-16 11:06:15', null, '0', '0', null);
INSERT INTO `base_user` VALUES ('2', '笑小枫2', 'maple2', '123456', null, '18300000000', '0', '123@126.com', '0', '', '0', '', '2019-06-16 11:06:04', '2019-08-14 17:29:29', '1', '0', null);

-- ----------------------------
-- Table structure for base_user_role
-- ----------------------------
DROP TABLE IF EXISTS `base_user_role`;
CREATE TABLE `base_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='基础信息-用户和角色关联表';

-- ----------------------------
-- Records of base_user_role
-- ----------------------------
INSERT INTO `base_user_role` VALUES ('1', '1', '1', '2019-07-08 16:43:05');

-- ----------------------------
-- Table structure for log_system_log
-- ----------------------------
DROP TABLE IF EXISTS `log_system_log`;
CREATE TABLE `log_system_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `params` text COMMENT '日志参数',
  `results` text COMMENT '返回结果',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `request_ip` varchar(255) DEFAULT NULL COMMENT '请求ip',
  `all_method_name` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `method_name` varchar(255) DEFAULT NULL COMMENT '请求方法名称',
  `operation_type` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `success` varchar(10) DEFAULT NULL COMMENT '是否成功（0：否 1：是）',
  `resp_time` varchar(20) DEFAULT NULL COMMENT '响应时间',
  `error_msg` text COMMENT '错误信息',
  `operate_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `operate_name` varchar(20) DEFAULT NULL COMMENT '操作人姓名',
  `log_type` varchar(10) DEFAULT NULL COMMENT '日志类型',
  `log_desc` varchar(255) DEFAULT NULL COMMENT '日志描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=490 DEFAULT CHARSET=utf8 COMMENT='日志信息-系统日志表';

-- ----------------------------
-- Table structure for system_config_properties
-- ----------------------------
DROP TABLE IF EXISTS `system_config_properties`;
CREATE TABLE `system_config_properties` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `key1` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '配置中key的值',
  `value1` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '配置中value的值',
  `application` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '应用名',
  `profile` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '环境',
  `label` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '读取的分支',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=264 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统配置-config动态配置';

-- ----------------------------
-- Records of system_config_properties
-- ----------------------------
INSERT INTO `system_config_properties` VALUES ('1', 'server.port', '5001', 'gateway-master', 'dev', 'master', '1', '2019-08-12 14:56:22', null, null, null, '端口号');
INSERT INTO `system_config_properties` VALUES ('2', 'spring.zipkin.base-url', 'http://localhost:3101', 'gateway-master', 'dev', 'master', '2', '2019-08-12 14:56:22', null, null, null, 'zipkin地址');
INSERT INTO `system_config_properties` VALUES ('3', 'spring.rabbitmq.host', '127.0.0.1', 'gateway-master', 'dev', 'master', '3', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('4', 'spring.rabbitmq.port', '5672', 'gateway-master', 'dev', 'master', '4', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('5', 'spring.rabbitmq.username', 'admin', 'gateway-master', 'dev', 'master', '5', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('6', 'spring.rabbitmq.password', '123456', 'gateway-master', 'dev', 'master', '6', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('7', 'spring.redis.host', '127.0.0.1', 'gateway-master', 'dev', 'master', '7', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('8', 'spring.redis.port', '6379', 'gateway-master', 'dev', 'master', '8', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('9', 'spring.redis.password', '123456', 'gateway-master', 'dev', 'master', '9', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('10', 'spring.redis.timeout', '60s', 'gateway-master', 'dev', 'master', '10', '2019-08-12 14:56:22', null, null, null, '数据库连接超时时间，2.0 中该参数的类型为Duration，这里在配置的时候需要指明单位');
INSERT INTO `system_config_properties` VALUES ('11', 'spring.redis.lettuce.pool.max-active', '8', 'gateway-master', 'dev', 'master', '11', '2019-08-12 14:56:22', null, null, null, '连接池最大连接数（使用负值表示没有限制） 默认 8');
INSERT INTO `system_config_properties` VALUES ('12', 'spring.redis.max-wait', '10s', 'gateway-master', 'dev', 'master', '12', '2019-08-12 14:56:22', null, null, null, '连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1');
INSERT INTO `system_config_properties` VALUES ('13', 'spring.redis.max-idle', '8', 'gateway-master', 'dev', 'master', '13', '2019-08-12 14:56:22', null, null, null, '连接池中的最大空闲连接 默认 8');
INSERT INTO `system_config_properties` VALUES ('14', 'spring.redis.min-idle', '0', 'gateway-master', 'dev', 'master', '14', '2019-08-12 14:56:22', null, null, null, '连接池中的最小空闲连接 默认 0');
INSERT INTO `system_config_properties` VALUES ('15', 'eureka.instance.leaseRenewalIntervalInSeconds', '10', 'gateway-master', 'dev', 'master', '15', '2019-08-12 14:56:22', null, null, null, '心跳时间，即服务续约间隔时间（缺省为30s）');
INSERT INTO `system_config_properties` VALUES ('16', 'eureka.instance.health-check-url-path', '/actuator/health', 'gateway-master', 'dev', 'master', '16', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('17', 'eureka.client.registryFetchIntervalSeconds', '5', 'gateway-master', 'dev', 'master', '17', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('18', 'eureka.client.service-url.defaultZone', 'http://127.0.0.1:1111/eureka/', 'gateway-master', 'dev', 'master', '18', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('19', 'eureka.instance.preferIpAddress', 'true', 'gateway-master', 'dev', 'master', '19', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('20', 'eureka.instance.instance-id', '${eureka.instance.ip-address}:${server.port}', 'gateway-master', 'dev', 'master', '20', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('21', 'eureka.instance.ip-address', '127.0.0.1', 'gateway-master', 'dev', 'master', '20', '2019-08-12 14:56:22', null, null, null, '');
INSERT INTO `system_config_properties` VALUES ('22', 'server.port', '8001', 'user-service', 'dev', 'master', '1', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('23', 'eureka.instance.leaseRenewalIntervalInSeconds', '10', 'user-service', 'dev', 'master', '2', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('24', 'eureka.instance.health-check-url-path', '/actuator/health', 'user-service', 'dev', 'master', '3', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('25', 'eureka.client.registryFetchIntervalSeconds', '5', 'user-service', 'dev', 'master', '4', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('26', 'eureka.client.service-url.defaultZone', 'http://127.0.0.1:1111/eureka/', 'user-service', 'dev', 'master', '5', '2019-08-12 14:56:22', '2019-08-15 16:35:30', null, null, null);
INSERT INTO `system_config_properties` VALUES ('27', 'eureka.instance.preferIpAddress', 'true', 'user-service', 'dev', 'master', '6', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('28', '\r\neureka.instance.instance-id', '${eureka.instance.ip-address}:${server.port}', 'user-service', 'dev', 'master', '7', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('29', 'eureka.instance.ip-address', '127.0.0.1', 'user-service', 'dev', 'master', '8', '2019-08-12 14:56:22', null, null, null, '');
INSERT INTO `system_config_properties` VALUES ('30', 'spring.zipkin.base-url', 'http://localhost:3101', 'user-service', 'dev', 'master', '9', '2019-08-12 14:56:22', null, null, null, 'zipkin地址');
INSERT INTO `system_config_properties` VALUES ('31', 'spring.rabbitmq.host', '127.0.0.1', 'user-service', 'dev', 'master', '10', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('32', 'spring.rabbitmq.port', '5672', 'user-service', 'dev', 'master', '11', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('33', 'spring.rabbitmq.username', 'admin', 'user-service', 'dev', 'master', '12', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('34', 'spring.rabbitmq.password', '123456', 'user-service', 'dev', 'master', '13', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('35', 'spring.datasource.driver-class-name', 'com.mysql.jdbc.Driver', 'user-service', 'dev', 'master', '14', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('36', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/maple?serverTimezone=GMT%2B8', 'user-service', 'dev', 'master', '15', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('37', 'spring.datasource.username', 'root', 'user-service', 'dev', 'master', '16', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('38', 'spring.datasource.password', '123456', 'user-service', 'dev', 'master', '17', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('39', 'spring.datasource.type', 'com.alibaba.druid.pool.DruidDataSource', 'user-service', 'dev', 'master', '18', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('40', 'spring.datasource.initialSize', '5', 'user-service', 'dev', 'master', '19', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('41', 'spring.datasource.minIdle', '5', 'user-service', 'dev', 'master', '20', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('42', 'spring.datasource.maxWait', '60000', 'user-service', 'dev', 'master', '21', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('43', 'spring.datasource.timeBetweenEvictionRunsMillis', '60000', 'user-service', 'dev', 'master', '22', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('44', 'spring.datasource.minEvictableIdleTimeMillis', '300000', 'user-service', 'dev', 'master', '23', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('45', 'spring.datasource.validationQuery', 'SELECT \'1\'', 'user-service', 'dev', 'master', '24', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('46', 'spring.datasource.testWhileIdle', 'true', 'user-service', 'dev', 'master', '25', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('47', 'spring.datasource.testOnBorrow', 'false', 'user-service', 'dev', 'master', '26', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('48', 'spring.datasource.testOnReturn', 'false', 'user-service', 'dev', 'master', '27', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('49', 'spring.datasource.poolPreparedStatements', 'true', 'user-service', 'dev', 'master', '28', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('50', 'spring.datasource.filters', 'stat,wall,log4j', 'user-service', 'dev', 'master', '29', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('51', 'spring.datasource.useGlobalDataSourceStat', 'true', 'user-service', 'dev', 'master', '30', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('52', 'mybatis-plus.type-aliases-package', 'com.maple.userapi.bean', 'user-service', 'dev', 'master', '31', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('53', 'mybatis-plus.mapper-locations', 'classpath:mapper/*.xml', 'user-service', 'dev', 'master', '32', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('54', 'management.endpoints.web.exposure.include', '*', 'user-service', 'dev', 'master', '33', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('55', 'management.endpoint.health.show-details', 'ALWAYS', 'user-service', 'dev', 'master', '34', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('56', 'security.oauth2.client.client-id', 'maple', 'user-service', 'dev', 'master', '35', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('57', 'security.oauth2.client.client-secret', 'maple', 'user-service', 'dev', 'master', '36', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('58', 'security.oauth2.client.scope', 'server', 'user-service', 'dev', 'master', '37', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('59', 'security.oauth2.resource.token-info-uri', 'http://127.0.0.1:3000/oauth/check_token', 'user-service', 'dev', 'master', '38', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('128', 'server.port', '3000', 'user-auth', 'dev', 'master', '0', '2019-08-12 14:56:22', null, null, null, '服务端口号');
INSERT INTO `system_config_properties` VALUES ('129', 'eureka.instance.preferIpAddress', 'true', 'user-auth', 'dev', 'master', '1', '2019-08-12 14:56:22', null, null, null, '是否将IP注册到EurekaServer');
INSERT INTO `system_config_properties` VALUES ('130', 'eureka.instance.leaseRenewalIntervalInSeconds', '10', 'user-auth', 'dev', 'master', '2', '2019-08-12 14:56:22', null, null, null, '心跳时间，即服务续约间隔时间（缺省为30s）');
INSERT INTO `system_config_properties` VALUES ('131', 'eureka.instance.leaseExpirationDurationInSeconds', '30', 'user-auth', 'dev', 'master', '3', '2019-08-12 14:56:22', null, null, null, '发呆时间，即服务续约到期时间（缺省为90s）');
INSERT INTO `system_config_properties` VALUES ('132', 'eureka.instance.health-check-url-path', '/actuator/health', 'user-auth', 'dev', 'master', '4', '2019-08-12 14:56:22', null, null, null, 'eureka实例的健康监控');
INSERT INTO `system_config_properties` VALUES ('133', 'eureka.client.registryFetchIntervalSeconds', '10', 'user-auth', 'dev', 'master', '5', '2019-08-12 14:56:22', null, null, null, '间隔多久去拉取服务注册信息，默认为30秒');
INSERT INTO `system_config_properties` VALUES ('134', 'eureka.client.service-url.defaultZone', 'http://127.0.0.1:1111/eureka/', 'user-auth', 'dev', 'master', '6', '2019-08-12 14:56:22', null, null, null, '配置注册中心');
INSERT INTO `system_config_properties` VALUES ('135', 'spring.zipkin.base-url', 'http://localhost:3101', 'user-auth', 'dev', 'master', '7', '2019-08-12 14:56:22', null, null, null, 'zipkin链路跟踪');
INSERT INTO `system_config_properties` VALUES ('136', 'spring.datasource.driver-class-name', 'com.mysql.cj.jdbc.Driver', 'user-auth', 'dev', 'master', '8', '2019-08-12 14:56:22', null, null, null, '数据库数据库驱动');
INSERT INTO `system_config_properties` VALUES ('137', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/maple?serverTimezone=GMT%2B8', 'user-auth', 'dev', 'master', '9', '2019-08-12 14:56:22', null, null, null, '数据库数据库地址');
INSERT INTO `system_config_properties` VALUES ('139', 'spring.datasource.username', 'root', 'user-auth', 'dev', 'master', '11', '2019-08-12 14:56:22', null, null, null, '数据库用户名');
INSERT INTO `system_config_properties` VALUES ('140', 'spring.datasource.password', '123456', 'user-auth', 'dev', 'master', '12', '2019-08-12 14:56:22', null, null, null, '数据库密码');
INSERT INTO `system_config_properties` VALUES ('141', 'spring.datasource.type', 'com.alibaba.druid.pool.DruidDataSource', 'user-auth', 'dev', 'master', '13', '2019-08-12 14:56:22', null, null, null, '数据库连接池');
INSERT INTO `system_config_properties` VALUES ('142', 'spring.datasource.initialSize', '5', 'user-auth', 'dev', 'master', '14', '2019-08-12 14:56:22', null, null, null, '数据库连接池初始化大小');
INSERT INTO `system_config_properties` VALUES ('143', 'spring.datasource.minIdle', '5', 'user-auth', 'dev', 'master', '15', '2019-08-12 14:56:22', null, null, null, '数据库连接池最小');
INSERT INTO `system_config_properties` VALUES ('144', 'spring.datasource.maxActive', '30', 'user-auth', 'dev', 'master', '16', '2019-08-12 14:56:22', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('145', 'spring.datasource.maxActive', '60000', 'user-auth', 'dev', 'master', '17', '2019-08-12 14:56:22', null, null, null, '数据库连接池获取连接等待超时的时间');
INSERT INTO `system_config_properties` VALUES ('146', 'spring.datasource.timeBetweenEvictionRunsMillis', '6000', 'user-auth', 'dev', 'master', '18', '2019-08-12 14:56:22', null, null, null, '数据库连接池间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒');
INSERT INTO `system_config_properties` VALUES ('147', 'spring.datasource.minEvictableIdleTimeMillis', '300000', 'user-auth', 'dev', 'master', '19', '2019-08-12 14:56:22', null, null, null, '数据库连接池配置一个连接在池中最小生存的时间，单位是毫秒');
INSERT INTO `system_config_properties` VALUES ('148', 'spring.datasource.validationQuery', 'SELECT \'x\'', 'user-auth', 'dev', 'master', '20', '2019-08-12 14:56:22', null, null, null, '数据库连接池校验SQL');
INSERT INTO `system_config_properties` VALUES ('149', 'spring.datasource.testWhileIdle', 'true', 'user-auth', 'dev', 'master', '21', '2019-08-12 14:56:22', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('150', 'spring.datasource.testOnBorrow', 'false', 'user-auth', 'dev', 'master', '22', '2019-08-12 14:56:22', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('151', 'spring.datasource.testOnReturn', 'false', 'user-auth', 'dev', 'master', '23', '2019-08-12 14:56:22', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('152', 'spring.datasource.poolPreparedStatements', 'true', 'user-auth', 'dev', 'master', '24', '2019-08-12 14:56:22', null, null, null, '数据库连接池打开PSCache');
INSERT INTO `system_config_properties` VALUES ('153', 'spring.datasource.maxPoolPreparedStatementPerConnectionSize', '20', 'user-auth', 'dev', 'master', '25', '2019-08-12 14:56:22', null, null, null, '数据库连接池指定每个连接上PSCache的大小');
INSERT INTO `system_config_properties` VALUES ('154', 'spring.datasource.filters', 'stat,wall,log4j', 'user-auth', 'dev', 'master', '26', '2019-08-12 14:56:22', null, null, null, '数据库连接池配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙');
INSERT INTO `system_config_properties` VALUES ('155', 'spring.datasource.connectionProperties', 'druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000', 'user-auth', 'dev', 'master', '27', '2019-08-12 14:56:22', null, null, null, '数据库连接池通过connectProperties属性来打开mergeSql功能；慢SQL记录');
INSERT INTO `system_config_properties` VALUES ('156', 'spring.datasource.useGlobalDataSourceStat', 'true', 'user-auth', 'dev', 'master', '28', '2019-08-12 14:56:22', null, null, null, '数据库连接池合并多个DruidDataSource的监控数据');
INSERT INTO `system_config_properties` VALUES ('157', 'spring.rabbitmq.host', '127.0.0.1', 'user-auth', 'dev', 'master', '29', '2019-08-12 14:56:22', null, null, null, 'rabbitmq地址');
INSERT INTO `system_config_properties` VALUES ('158', 'spring.rabbitmq.port', '5672', 'user-auth', 'dev', 'master', '30', '2019-08-12 14:56:22', null, null, null, 'rabbitmq端口号');
INSERT INTO `system_config_properties` VALUES ('159', 'spring.rabbitmq.username', 'admin', 'user-auth', 'dev', 'master', '31', '2019-08-12 14:56:22', null, null, null, 'rabbitmq用户名');
INSERT INTO `system_config_properties` VALUES ('160', 'spring.rabbitmq.password', '123456', 'user-auth', 'dev', 'master', '32', '2019-08-12 14:56:22', null, null, null, 'rabbitmq密码');
INSERT INTO `system_config_properties` VALUES ('161', 'spring.redis.database', '1', 'user-auth', 'dev', 'master', '33', '2019-08-12 14:56:22', null, null, null, 'redis数据库索引（默认为0）');
INSERT INTO `system_config_properties` VALUES ('162', 'spring.redis.host', '127.0.0.1', 'user-auth', 'dev', 'master', '34', '2019-08-12 14:56:22', null, null, null, 'redis地址');
INSERT INTO `system_config_properties` VALUES ('163', 'spring.redis.port', '6379', 'user-auth', 'dev', 'master', '35', '2019-08-12 14:56:22', null, null, null, 'redis端口号');
INSERT INTO `system_config_properties` VALUES ('164', 'spring.redis.password', '123456', 'user-auth', 'dev', 'master', '36', '2019-08-12 14:56:22', null, null, null, 'redis密码');
INSERT INTO `system_config_properties` VALUES ('165', 'spring.redis.timeout', '5000ms', 'user-auth', 'dev', 'master', '37', '2019-08-12 14:56:22', null, null, null, 'redis连接超时时间（毫秒）默认是2000ms');
INSERT INTO `system_config_properties` VALUES ('166', 'mybatis-plus.mapper-locations', 'classpath:/mapper/*Mapper.xml', 'user-auth', 'dev', 'master', '37', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('167', 'mybatis-plus.global-config.banner', 'false', 'user-auth', 'dev', 'master', '32', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('168', 'mybatis-plus.global-config.db-config.id-type', 'AUTO', 'user-auth', 'dev', 'master', '33', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('169', 'mybatis-plus.global-config.db-config.field-strategy', 'IGNORED', 'user-auth', 'dev', 'master', '34', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('170', 'mybatis-plus.global-config.db-config.table-underline', 'true', 'user-auth', 'dev', 'master', '35', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('171', 'mybatis-plus.global-config.db-config.logic-delete-value', '1', 'user-auth', 'dev', 'master', '36', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('172', 'mybatis-plus.global-config.db-config.logic-not-delete-value', '0', 'user-auth', 'dev', 'master', '37', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('173', 'mybatis-plus.configuration.map-underscore-to-camel-case', 'true', 'user-auth', 'dev', 'master', '37', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('174', '\r\neureka.instance.instance-id', '${eureka.instance.ip-address}:${server.port}', 'user-auth', 'dev', 'master', '2', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('175', 'eureka.instance.ip-address', '127.0.0.1', 'user-auth', 'dev', 'master', '3', '2019-08-12 14:56:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('180', 'maple.test', '1234', 'user-service', 'dev', 'master', '39', '2019-08-15 16:40:22', '2019-08-15 16:44:48', null, null, 'ceshi');
INSERT INTO `system_config_properties` VALUES ('181', 'maple.test', '12345', 'gateway-master', 'dev', 'master', '1', '2019-08-15 16:46:22', '2019-08-16 02:25:25', null, null, 'ceshi');
INSERT INTO `system_config_properties` VALUES ('185', 'server.port', '5002', 'cloud-manage', 'dev', 'master', '0', '2019-08-16 13:35:06', null, null, null, '服务端口号');
INSERT INTO `system_config_properties` VALUES ('186', 'eureka.instance.preferIpAddress', 'true', 'cloud-manage', 'dev', 'master', '1', '2019-08-16 13:35:06', null, null, null, '是否将IP注册到EurekaServer');
INSERT INTO `system_config_properties` VALUES ('187', 'eureka.instance.leaseRenewalIntervalInSeconds', '10', 'cloud-manage', 'dev', 'master', '2', '2019-08-16 13:35:06', null, null, null, '心跳时间，即服务续约间隔时间（缺省为30s）');
INSERT INTO `system_config_properties` VALUES ('188', 'eureka.instance.leaseExpirationDurationInSeconds', '30', 'cloud-manage', 'dev', 'master', '3', '2019-08-16 13:35:06', null, null, null, '发呆时间，即服务续约到期时间（缺省为90s）');
INSERT INTO `system_config_properties` VALUES ('189', 'eureka.instance.health-check-url-path', '/actuator/health', 'cloud-manage', 'dev', 'master', '4', '2019-08-16 13:35:06', null, null, null, 'eureka实例的健康监控');
INSERT INTO `system_config_properties` VALUES ('190', 'eureka.client.registryFetchIntervalSeconds', '10', 'cloud-manage', 'dev', 'master', '5', '2019-08-16 13:35:06', null, null, null, '间隔多久去拉取服务注册信息，默认为30秒');
INSERT INTO `system_config_properties` VALUES ('191', 'eureka.client.service-url.defaultZone', 'http://127.0.0.1:1111/eureka/', 'cloud-manage', 'dev', 'master', '6', '2019-08-16 13:35:06', null, null, null, '配置注册中心');
INSERT INTO `system_config_properties` VALUES ('192', 'spring.zipkin.base-url', 'http://localhost:3101', 'cloud-manage', 'dev', 'master', '7', '2019-08-16 13:35:06', null, null, null, 'zipkin链路跟踪');
INSERT INTO `system_config_properties` VALUES ('193', 'spring.datasource.driver-class-name', 'com.mysql.cj.jdbc.Driver', 'cloud-manage', 'dev', 'master', '8', '2019-08-16 13:35:06', null, null, null, '数据库数据库驱动');
INSERT INTO `system_config_properties` VALUES ('194', 'spring.datasource.url', 'jdbc:mysql://127.0.0.1:3306/maple?useUnicode=true&characterEncoding=utf8&useSSL=false', 'cloud-manage', 'dev', 'master', '9', '2019-08-16 13:35:06', null, null, null, '数据库数据库地址');
INSERT INTO `system_config_properties` VALUES ('195', 'spring.datasource.username', 'root', 'cloud-manage', 'dev', 'master', '10', '2019-08-16 13:35:06', null, null, null, '数据库用户名');
INSERT INTO `system_config_properties` VALUES ('196', 'spring.datasource.password', '123456', 'cloud-manage', 'dev', 'master', '11', '2019-08-16 13:35:06', null, null, null, '数据库密码');
INSERT INTO `system_config_properties` VALUES ('197', 'spring.datasource.type', 'com.alibaba.druid.pool.DruidDataSource', 'cloud-manage', 'dev', 'master', '12', '2019-08-16 13:35:06', null, null, null, '数据库连接池');
INSERT INTO `system_config_properties` VALUES ('198', 'spring.datasource.initialSize', '5', 'cloud-manage', 'dev', 'master', '13', '2019-08-16 13:35:06', null, null, null, '数据库连接池初始化大小');
INSERT INTO `system_config_properties` VALUES ('199', 'spring.datasource.minIdle', '5', 'cloud-manage', 'dev', 'master', '14', '2019-08-16 13:35:06', null, null, null, '数据库连接池最小');
INSERT INTO `system_config_properties` VALUES ('200', 'spring.datasource.maxActive', '30', 'cloud-manage', 'dev', 'master', '15', '2019-08-16 13:35:06', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('201', 'spring.datasource.maxActive', '60000', 'cloud-manage', 'dev', 'master', '16', '2019-08-16 13:35:06', null, null, null, '数据库连接池获取连接等待超时的时间');
INSERT INTO `system_config_properties` VALUES ('202', 'spring.datasource.timeBetweenEvictionRunsMillis', '6000', 'cloud-manage', 'dev', 'master', '17', '2019-08-16 13:35:06', null, null, null, '数据库连接池间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒');
INSERT INTO `system_config_properties` VALUES ('203', 'spring.datasource.minEvictableIdleTimeMillis', '300000', 'cloud-manage', 'dev', 'master', '18', '2019-08-16 13:35:06', null, null, null, '数据库连接池配置一个连接在池中最小生存的时间，单位是毫秒');
INSERT INTO `system_config_properties` VALUES ('204', 'spring.datasource.validationQuery', 'SELECT \'x\'', 'cloud-manage', 'dev', 'master', '19', '2019-08-16 13:35:06', null, null, null, '数据库连接池校验SQL');
INSERT INTO `system_config_properties` VALUES ('205', 'spring.datasource.testWhileIdle', 'true', 'cloud-manage', 'dev', 'master', '20', '2019-08-16 13:35:06', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('206', 'spring.datasource.testOnBorrow', 'false', 'cloud-manage', 'dev', 'master', '21', '2019-08-16 13:35:06', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('207', 'spring.datasource.testOnReturn', 'false', 'cloud-manage', 'dev', 'master', '22', '2019-08-16 13:35:06', null, null, null, '数据库连接池最大');
INSERT INTO `system_config_properties` VALUES ('208', 'spring.datasource.poolPreparedStatements', 'true', 'cloud-manage', 'dev', 'master', '23', '2019-08-16 13:35:06', null, null, null, '数据库连接池打开PSCache');
INSERT INTO `system_config_properties` VALUES ('209', 'spring.datasource.maxPoolPreparedStatementPerConnectionSize', '20', 'cloud-manage', 'dev', 'master', '24', '2019-08-16 13:35:06', null, null, null, '数据库连接池指定每个连接上PSCache的大小');
INSERT INTO `system_config_properties` VALUES ('210', 'spring.datasource.filters', 'stat,wall,log4j', 'cloud-manage', 'dev', 'master', '25', '2019-08-16 13:35:06', null, null, null, '数据库连接池配置监控统计拦截的filters，去掉后监控界面sql无法统计，\'wall\'用于防火墙');
INSERT INTO `system_config_properties` VALUES ('211', 'spring.datasource.connectionProperties', 'druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000', 'cloud-manage', 'dev', 'master', '26', '2019-08-16 13:35:06', null, null, null, '数据库连接池通过connectProperties属性来打开mergeSql功能；慢SQL记录');
INSERT INTO `system_config_properties` VALUES ('212', 'spring.datasource.useGlobalDataSourceStat', 'true', 'cloud-manage', 'dev', 'master', '27', '2019-08-16 13:35:06', null, null, null, '数据库连接池合并多个DruidDataSource的监控数据');
INSERT INTO `system_config_properties` VALUES ('213', 'spring.rabbitmq.host', '127.0.0.1', 'cloud-manage', 'dev', 'master', '28', '2019-08-16 13:35:06', null, null, null, 'rabbitmq地址');
INSERT INTO `system_config_properties` VALUES ('214', 'spring.rabbitmq.port', '5672', 'cloud-manage', 'dev', 'master', '29', '2019-08-16 13:35:06', null, null, null, 'rabbitmq端口号');
INSERT INTO `system_config_properties` VALUES ('215', 'spring.rabbitmq.username', 'admin', 'cloud-manage', 'dev', 'master', '30', '2019-08-16 13:35:06', null, null, null, 'rabbitmq用户名');
INSERT INTO `system_config_properties` VALUES ('216', 'spring.rabbitmq.password', '123456', 'cloud-manage', 'dev', 'master', '31', '2019-08-16 13:35:06', null, null, null, 'rabbitmq密码');
INSERT INTO `system_config_properties` VALUES ('217', 'spring.redis.database', '0', 'cloud-manage', 'dev', 'master', '32', '2019-08-16 13:35:06', null, null, null, 'redis数据库索引（默认为0）');
INSERT INTO `system_config_properties` VALUES ('218', 'spring.redis.host', '127.0.0.1', 'cloud-manage', 'dev', 'master', '33', '2019-08-16 13:35:06', null, null, null, 'redis地址');
INSERT INTO `system_config_properties` VALUES ('219', 'spring.redis.port', '6379', 'cloud-manage', 'dev', 'master', '34', '2019-08-16 13:35:06', null, null, null, 'redis端口号');
INSERT INTO `system_config_properties` VALUES ('220', 'spring.redis.password', '123456', 'cloud-manage', 'dev', 'master', '35', '2019-08-16 13:35:06', null, null, null, 'redis密码');
INSERT INTO `system_config_properties` VALUES ('221', 'spring.redis.timeout', '5000ms', 'cloud-manage', 'dev', 'master', '36', '2019-08-16 13:35:06', null, null, null, 'redis连接超时时间（毫秒）默认是2000ms');
INSERT INTO `system_config_properties` VALUES ('222', 'management.endpoints.web.exposure.include', '*', 'cloud-manage', 'dev', 'master', '37', '2019-08-16 13:35:06', null, null, null, 'Actuator暴露的端点');
INSERT INTO `system_config_properties` VALUES ('223', 'management.endpoint.health.show-details', 'ALWAYS', 'cloud-manage', 'dev', 'master', '38', '2019-08-16 13:35:06', null, null, null, 'Actuator的健康检查');
INSERT INTO `system_config_properties` VALUES ('224', 'minio.url', 'http://127.0.0.1:9000', 'user-service', 'dev', 'master', '40', null, null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('225', 'minio.url', null, 'user-service', null, null, '0', null, null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('226', 'minio.accessKey', 'maple', 'user-service', 'dev', 'master', '41', null, null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('227', 'minio.secretKey', 'maple123456', 'user-service', 'dev', 'master', '42', null, null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('228', 'security.encode.key', '1234567812345678', 'user-service', 'dev', 'master', '43', null, null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('229', 'server.port', '6666', 'admin-service', 'dev', 'master', '0', '2019-08-20 01:01:59', null, null, null, '服务端口号');
INSERT INTO `system_config_properties` VALUES ('230', 'eureka.instance.preferIpAddress', 'true', 'admin-service', 'dev', 'master', '1', '2019-08-20 01:01:59', null, null, null, '是否将IP注册到EurekaServer');
INSERT INTO `system_config_properties` VALUES ('231', 'eureka.instance.leaseRenewalIntervalInSeconds', '10', 'admin-service', 'dev', 'master', '2', '2019-08-20 01:01:59', null, null, null, '心跳时间，即服务续约间隔时间（缺省为30s）');
INSERT INTO `system_config_properties` VALUES ('232', 'eureka.instance.leaseExpirationDurationInSeconds', '30', 'admin-service', 'dev', 'master', '3', '2019-08-20 01:01:59', null, null, null, '发呆时间，即服务续约到期时间（缺省为90s）');
INSERT INTO `system_config_properties` VALUES ('233', 'eureka.instance.health-check-url-path', '/actuator/health', 'admin-service', 'dev', 'master', '4', '2019-08-20 01:01:59', null, null, null, 'eureka实例的健康监控');
INSERT INTO `system_config_properties` VALUES ('234', 'eureka.client.registryFetchIntervalSeconds', '10', 'admin-service', 'dev', 'master', '5', '2019-08-20 01:01:59', null, null, null, '间隔多久去拉取服务注册信息，默认为30秒');
INSERT INTO `system_config_properties` VALUES ('235', 'eureka.client.service-url.defaultZone', 'http://127.0.0.1:1111/eureka/', 'admin-service', 'dev', 'master', '6', '2019-08-20 01:01:59', null, null, null, '配置注册中心');
INSERT INTO `system_config_properties` VALUES ('236', 'spring.zipkin.base-url', 'http://localhost:3101', 'admin-service', 'dev', 'master', '7', '2019-08-20 01:01:59', null, null, null, 'zipkin链路跟踪');
INSERT INTO `system_config_properties` VALUES ('237', 'spring.rabbitmq.host', '127.0.0.1', 'admin-service', 'dev', 'master', '8', '2019-08-20 01:01:59', null, null, null, 'rabbitmq地址');
INSERT INTO `system_config_properties` VALUES ('238', 'spring.rabbitmq.port', '5672', 'admin-service', 'dev', 'master', '9', '2019-08-20 01:01:59', null, null, null, 'rabbitmq端口号');
INSERT INTO `system_config_properties` VALUES ('239', 'spring.rabbitmq.username', 'admin', 'admin-service', 'dev', 'master', '10', '2019-08-20 01:01:59', null, null, null, 'rabbitmq用户名');
INSERT INTO `system_config_properties` VALUES ('240', 'spring.rabbitmq.password', '123456', 'admin-service', 'dev', 'master', '11', '2019-08-20 01:01:59', null, null, null, 'rabbitmq密码');
INSERT INTO `system_config_properties` VALUES ('241', 'management.endpoints.web.exposure.include', '*', 'admin-service', 'dev', 'master', '12', '2019-08-20 01:01:59', null, null, null, 'Actuator暴露的端点');
INSERT INTO `system_config_properties` VALUES ('242', 'management.endpoint.health.show-details', 'ALWAYS', 'admin-service', 'dev', 'master', '13', '2019-08-20 01:01:59', null, null, null, 'Actuator的健康检查');
INSERT INTO `system_config_properties` VALUES ('243', 'spring.security.user.name', 'admin', 'admin-service', 'dev', 'master', '14', '2019-08-20 01:03:11', null, null, null, '');
INSERT INTO `system_config_properties` VALUES ('244', 'spring.security.user.password', '123456', 'admin-service', 'dev', 'master', '15', '2019-08-20 01:04:00', null, null, null, '验证密码');
INSERT INTO `system_config_properties` VALUES ('246', 'eureka.instance.preferIpAddress', 'true', 'zipkin-master', 'dev', 'master', '1', '2019-08-20 01:12:22', null, null, null, '是否将IP注册到EurekaServer');
INSERT INTO `system_config_properties` VALUES ('247', 'eureka.instance.leaseRenewalIntervalInSeconds', '10', 'zipkin-master', 'dev', 'master', '2', '2019-08-20 01:12:22', null, null, null, '心跳时间，即服务续约间隔时间（缺省为30s）');
INSERT INTO `system_config_properties` VALUES ('248', 'eureka.instance.leaseExpirationDurationInSeconds', '30', 'zipkin-master', 'dev', 'master', '3', '2019-08-20 01:12:22', null, null, null, '发呆时间，即服务续约到期时间（缺省为90s）');
INSERT INTO `system_config_properties` VALUES ('249', 'eureka.instance.health-check-url-path', '/actuator/health', 'zipkin-master', 'dev', 'master', '4', '2019-08-20 01:12:22', null, null, null, 'eureka实例的健康监控');
INSERT INTO `system_config_properties` VALUES ('250', 'eureka.client.registryFetchIntervalSeconds', '10', 'zipkin-master', 'dev', 'master', '5', '2019-08-20 01:12:22', null, null, null, '间隔多久去拉取服务注册信息，默认为30秒');
INSERT INTO `system_config_properties` VALUES ('251', 'eureka.client.service-url.defaultZone', 'http://127.0.0.1:1111/eureka/', 'zipkin-master', 'dev', 'master', '6', '2019-08-20 01:12:22', null, null, null, '配置注册中心');
INSERT INTO `system_config_properties` VALUES ('253', 'spring.rabbitmq.host', '127.0.0.1', 'zipkin-master', 'dev', 'master', '8', '2019-08-20 01:12:22', null, null, null, 'rabbitmq地址');
INSERT INTO `system_config_properties` VALUES ('254', 'spring.rabbitmq.port', '5672', 'zipkin-master', 'dev', 'master', '9', '2019-08-20 01:12:22', null, null, null, 'rabbitmq端口号');
INSERT INTO `system_config_properties` VALUES ('255', 'spring.rabbitmq.username', 'admin', 'zipkin-master', 'dev', 'master', '10', '2019-08-20 01:12:22', null, null, null, 'rabbitmq用户名');
INSERT INTO `system_config_properties` VALUES ('256', 'spring.rabbitmq.password', '123456', 'zipkin-master', 'dev', 'master', '11', '2019-08-20 01:12:22', null, null, null, 'rabbitmq密码');
INSERT INTO `system_config_properties` VALUES ('257', 'management.endpoints.web.exposure.include', '*', 'zipkin-master', 'dev', 'master', '12', '2019-08-20 01:12:22', null, null, null, 'Actuator暴露的端点');
INSERT INTO `system_config_properties` VALUES ('258', 'management.endpoint.health.show-details', 'ALWAYS', 'zipkin-master', 'dev', 'master', '13', '2019-08-20 01:12:22', null, null, null, 'Actuator的健康检查');
INSERT INTO `system_config_properties` VALUES ('259', 'management.metrics.web.server.auto-time-requests', 'false', 'zipkin-master', 'dev', 'master', '14', '2019-08-20 01:12:22', null, null, null, '去除控制台异常');
INSERT INTO `system_config_properties` VALUES ('260', 'server.use-forward-headers', 'true', 'zipkin-master', 'dev', 'master', '15', '2019-08-20 01:12:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('261', 'server.compression.enabled', 'true', 'zipkin-master', 'dev', 'master', '16', '2019-08-20 01:12:22', null, null, null, null);
INSERT INTO `system_config_properties` VALUES ('262', 'server.compression.mime-types', 'application/json,application/javascript,text/css,image/svg', 'zipkin-master', 'dev', 'master', '17', '2019-08-20 01:12:22', null, null, null, null);

-- ----------------------------
-- Table structure for system_gateway_define
-- ----------------------------
DROP TABLE IF EXISTS `system_gateway_define`;
CREATE TABLE `system_gateway_define` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uri` varchar(255) DEFAULT NULL COMMENT '对应routes的uri',
  `predicates` varchar(255) DEFAULT NULL COMMENT '对应routes的predicates判断匹配条件',
  `filters` varchar(255) DEFAULT NULL COMMENT '对应routes的filters',
  `route_id` varchar(255) DEFAULT NULL COMMENT 'Eureka注册的服务名,对应routes的- id',
  `description` varchar(255) DEFAULT NULL COMMENT '路由描述',
  `orders` int(6) DEFAULT '0' COMMENT '路由排序',
  `status` int(1) DEFAULT '0' COMMENT '状态（0：启用 ，1：停用）',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_EUREKAID` (`route_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统配置-gateway动态路由配置';

-- ----------------------------
-- Records of system_gateway_define
-- ----------------------------
INSERT INTO `system_gateway_define` VALUES ('2', 'lb://USER-SERVICE', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/user/**\"}}]', '[{\"name\":\"StripPrefix\",\"args\":{\"_genkey_user\":\"1\"}},{\"name\":\"RequestRateLimiter\",\"args\":{\"redis-rate-limiter.replenishRate\":\"10\",\"redis-rate-limiter.burstCapacity\":\"20\"}}]', 'user-service', '这是一个带限流的路由', '0', '0', null);
INSERT INTO `system_gateway_define` VALUES ('4', 'lb://USER-AUTH', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/auth/**\"}}]', '[{\"name\":\"StripPrefix\",\"args\":{\"_genkey_auth\":\"1\"}},{\"name\":\"RequestRateLimiter\",\"args\":{\"redis-rate-limiter.replenishRate\":\"10\",\"redis-rate-limiter.burstCapacity\":\"20\"}}]', 'user-auth', '这是user-auth微服务一个带限流的路由', '0', '0', '2019-08-06 14:10:33');
INSERT INTO `system_gateway_define` VALUES ('5', 'lb://CLOUD-MANAGE', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/manage/**\"}}]', '[{\"name\":\"StripPrefix\",\"args\":{\"_genkey_manage\":\"1\"}},{\"name\":\"RequestRateLimiter\",\"args\":{\"redis-rate-limiter.replenishRate\":\"10\",\"redis-rate-limiter.burstCapacity\":\"20\"}}]', 'cloud-manage', '这是cloud-manage微服务一个带限流的路由', '0', '0', '2019-08-08 17:14:15');

-- ----------------------------
-- Table structure for system_info
-- ----------------------------
DROP TABLE IF EXISTS `system_info`;
CREATE TABLE `system_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `host` varchar(16) DEFAULT NULL COMMENT '连接的url',
  `port` varchar(8) DEFAULT NULL COMMENT '端口号',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(32) DEFAULT NULL COMMENT '密码',
  `data_name` varchar(255) DEFAULT NULL COMMENT '数据库名称',
  `info_type` varchar(20) DEFAULT NULL COMMENT '信息类型（mysql ; redis；rabbitmq）',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `status` int(2) DEFAULT NULL COMMENT '状态（0：未启用 1：启用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统配置-系统的基础数据配置';

-- ----------------------------
-- Records of system_info
-- ----------------------------
INSERT INTO `system_info` VALUES ('1', '127.0.0.1', '3306', 'root', '123456', 'maple', 'mysql', '2019-08-12 10:06:08', null, null, null, null, '1');
INSERT INTO `system_info` VALUES ('2', '127.0.0.1', '5672', 'admin', '123456', null, 'rabbitmq', '2019-08-12 10:06:08', null, null, null, '', '1');
INSERT INTO `system_info` VALUES ('3', '127.0.0.1', '6379', null, '123456', '0', 'redis', '2019-08-12 10:06:08', null, null, null, '', '1');

-- ----------------------------
-- Table structure for system_microservices
-- ----------------------------
DROP TABLE IF EXISTS `system_microservices`;
CREATE TABLE `system_microservices` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `service_name` varchar(255) DEFAULT NULL COMMENT '微服务名称',
  `service_ip` varchar(255) DEFAULT NULL COMMENT '微服务部署机器ip',
  `service_port` varchar(20) DEFAULT NULL COMMENT '微服务端口号',
  `is_start` int(2) DEFAULT NULL COMMENT '是否启动',
  `status` int(2) DEFAULT NULL COMMENT '使用状态（0：未使用  1：已使用）',
  `is_create_config` int(2) DEFAULT NULL COMMENT '是否默认创建config配置信息',
  `is_use_mysql` int(2) DEFAULT NULL COMMENT '是否使用数据库',
  `is_use_redis` int(2) DEFAULT NULL COMMENT '是否使用redis',
  `is_use_rabbitmq` int(2) DEFAULT NULL COMMENT '是否使用Rabbitmq',
  `mysql_info` int(11) DEFAULT NULL COMMENT 'mysql信息',
  `redis_info` int(11) DEFAULT NULL COMMENT 'redis信息',
  `rabbitmq_info` int(11) DEFAULT NULL COMMENT 'rabbitmq信息',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_date` datetime DEFAULT NULL COMMENT '修改时间',
  `create_id` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='系统配置-微服务备案';

-- ----------------------------
-- Records of system_microservices
-- ----------------------------
INSERT INTO `system_microservices` VALUES ('1', 'gateway-master', '127.0.0.1', '5001', '1', '1', '1', '1', '1', '1', '1', '3', '2', '2019-08-08 16:33:12', null, null, null, null);
INSERT INTO `system_microservices` VALUES ('2', 'user-auth', '127.0.0.1', '3000', '1', '1', '1', '1', '1', '1', '1', '3', '2', '2019-08-12 17:46:27', null, null, null, '授权中心user-auth微服务');
INSERT INTO `system_microservices` VALUES ('6', 'user-service', '127.0.0.1', '8001', null, null, '0', '1', '1', '1', null, null, null, '2019-08-15 14:15:18', null, null, null, 'user-service微服务');
INSERT INTO `system_microservices` VALUES ('7', 'config-master', '127.0.0.1', '2000', null, null, '0', '1', '0', '1', null, null, null, '2019-08-15 16:28:19', null, null, null, '');
INSERT INTO `system_microservices` VALUES ('8', 'cloud-manage', '127.0.0.1', '5002', null, null, '1', '1', '1', '1', null, null, null, '2019-08-16 13:35:06', null, null, null, '系统管理 cloud-manage 服务');
INSERT INTO `system_microservices` VALUES ('9', 'admin-service', '127.0.0.1', '6666', null, null, '1', '0', '0', '1', null, null, null, '2019-08-20 01:01:59', null, null, null, '');
INSERT INTO `system_microservices` VALUES ('10', 'zipkin-master', '127.0.0.1', '3101', null, null, '1', '0', '0', '1', null, null, null, '2019-08-20 01:12:22', null, null, null, '链路跟踪服务');
