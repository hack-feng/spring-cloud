# Spring Cloud Config(统一配置中心)

这里是本项目的配置中心，开始基于github进行配置，后改为基于Mysql数据库配置，并增加前端UI配置界面。

基于github配置教程：https://blog.csdn.net/qq_34988304/article/details/95784724

前端UI界面：https://github.com/zhua-an/adminUI.git

这里介绍本项目的配置和基于Mysql搭建配置中心

## config-master项目搭建

pom文件引入：
~~~ xml
<!-- Spring Cloud ==> 引入config配置中心 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>
~~~

数据库表结构：
~~~ sql
SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=185 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='系统配置-config动态配置';
~~~

ConfigMasterApplication.java文件：
~~~ java
@SpringBootApplication
@EnableConfigServer     //配置中心的服务端
public class ConfigMasterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigMasterApplication.class, args);
    }
}
~~~

application.yml文件：
~~~ yml
server:
  port: 2000

spring:
  profiles:
    active: jdbc
  application:
    name: config-master
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: admin
    password: 123456
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/maple?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&serverTimezone=GMT%2B8
    username: root
    password: 123456
  cloud:
    config:
      label: master
      server:
        jdbc:
          sql: SELECT key1, value1 from system_config_properties where APPLICATION=? and PROFILE=? and LABEL=?
        default-label: master
      profile: dev
~~~

这样config配置中心的server就创建好了，下面介绍一下config的使用：

## Config Click搭建

创建gateway-master click项目,在resources目录下创建bootstrap.yml文件

* bootstrap.yml是Spring Cloud的配置文件，在Spring Cloud项目启动时，会优先加载该配置文件
* application.yml是Spring Boot的配置文件，其加载优先级在bootstrap.yml 之后

bootstrap.yml文件配置：
~~~ yml
spring:
  application:
    name: gateway-master
  cloud:
    config:
      uri: http://127.0.0.1:2000
      profile: dev
      label: master
~~~

在数据库中插入数据：
~~~ sql
INSERT INTO `maple`.`system_config_properties` (`id`, `key1`, `value1`, `application`, `profile`, `label`, `sort`, `create_date`, `modify_date`, `create_id`, `modify_id`, `remark`) VALUES ('1', 'server.port', '5001', 'gateway-master', 'dev', 'master', '1', '2019-08-12 14:56:22', NULL, NULL, NULL, '端口号');
INSERT INTO `maple`.`system_config_properties` (`id`, `key1`, `value1`, `application`, `profile`, `label`, `sort`, `create_date`, `modify_date`, `create_id`, `modify_id`, `remark`) VALUES ('2', 'maple.test', '测试数据', 'gateway-master', 'dev', 'master', '1', '2019-08-15 16:46:22', '2019-08-15 17:43:25', NULL, NULL, 'ceshi');
~~~

创建TestController：
~~~ java
@RestController("test")
public class TestController {

    // 启动时从配置文件中读取key1为maple.test的value1值
    @Value("${maple.test}")
    private String test;
    
    @GetMapping
    public String test(){
        return test;
    }
}
~~~

启动项目时，还需要配置一些文件：

启动时打印：
~~~ console
c.c.c.ConfigServicePropertySourceLocator : Fetching config from server at : http://127.0.0.1:2000
......
o.s.b.web.embedded.netty.NettyWebServer  : Netty started on port(s): 5001

~~~
说明调用config server成功，若调用的端口号为：<b>8888</b>，则bootstrap.yml配置有误。8888端口号为config server默认的端口号。
启动的端口号是数据库中配置的server.port。

启动成功后，访问：http://127.0.0.1:5001/test

访问：http://127.0.0.1:2000/gateway-master/dev 可以查看项目的配置

页面显示返回：<b>测试数据</b>。到这里config配置中心搭建完成。

## 配置文件修改后，不停机加载配置信息

在上一节的gateway-master项目中进行优化。

在bootstrap.xml配置文件中添加：
~~~
# 开启监控接口
management:
  endpoints:
    web:
      exposure:
        include: "*"
        #include: refresh,health,info #打开部分
~~~

在TestController文件中添加注解 @RefreshScop

启动项目后，修改maple.test的值为："修改后的测试数据"，使用postman 调用：http://127.0.0.1:5001/actuator/refresh 接口触发刷新

* spring boot2.0版本之前,刷新调用： http://127.0.0.1:5001/refresh
* spring boot2.0版本之后,刷新调用： http://127.0.0.1:5001/actuator/refresh

重新访问：http://127.0.0.1:5001/test 返回："修改后的测试数据"

## 使用 Spring Cloud Bus消息总线，动态更新配置信息

Spring cloud bus被国内很多都翻译为消息总线，也挺形象的。大家可以将它理解为管理和传播所有分布式项目中的消息既可，其实本质是利用了MQ的广播机制在分布式的系统中传播消息，目前常用的有Kafka和RabbitMQ。利用bus的机制可以做很多的事情，其中配置中心客户端刷新就是典型的应用场景之一。

本项目中使用RabbitMQ。安装并启动RabbitMQ，创建admin/123456用户。

基于上文的项目我们继续优化

分别在server和click项目中添加pom依赖：
~~~

~~~







