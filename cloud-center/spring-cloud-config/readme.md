# Spring Cloud Config(统一配置中心)

这里是本项目的配置中心，开始基于git进行配置，后改为基于Mysql数据库配置，并增加前端UI配置界面。

基于git配置教程：https://blog.csdn.net/qq_34988304/article/details/95784724

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
* spring.profiles.active为spring读取的配置文件名，从数据库中读取，必须为jdbc
* spring.datasource配置了数据库相关的信息
* spring.cloud.config.label读取的配置的分支，这个需要在数据库中数据对应
* spring.cloud.config.server.jdbc.sql为查询数据库的sql语句，该语句的字段必须与数据库的表字段一致

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

启动项目后，修改maple.test的值为："修改后的测试数据"，使用postman POST调用：http://127.0.0.1:5001/actuator/refresh 接口触发刷新

* spring boot2.0版本之前,刷新调用： http://127.0.0.1:5001/refresh
* spring boot2.0版本之后,刷新调用： http://127.0.0.1:5001/actuator/refresh

重新访问：http://127.0.0.1:5001/test 返回："修改后的测试数据"

## 使用 Spring Cloud Bus消息总线，动态更新配置信息

Spring cloud bus被国内很多都翻译为消息总线，也挺形象的。大家可以将它理解为管理和传播所有分布式项目中的消息既可，其实本质是利用了MQ的广播机制在分布式的系统中传播消息，目前常用的有Kafka和RabbitMQ。利用bus的机制可以做很多的事情，其中配置中心客户端刷新就是典型的应用场景之一。

本项目中使用RabbitMQ。安装并启动RabbitMQ，创建admin/123456用户。

基于上文的项目我们继续优化，将项目注册到Eureka注册中心

分别在server和click项目中添加pom依赖：
~~~
<!-- Spring Cloud ==> 引入bus的消息总线-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
~~~

修改 config-master的application.yml 配置文件：
~~~
# 开启监控接口
management:
  endpoints:
    web:
      exposure:
        include: "*"
        # include: refresh,health,info #打开部分
~~~

分别在server和click项目中添加rabbitMQ配置：
~~~
spring:
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: admin
    password: 123456
~~~

然后依次启动eureka，config-master，gateway-click项目

POST调用：http://127.0.0.1:2000/actuator/refresh-bus刷新配置文件，该刷新将会刷新所有调用config-master统一配置中心的项目。

/actuator/bus-refresh接口可以指定服务，即使用”destination”参数，比如 “/actuator/bus-refresh?destination=customers:**” 即刷新服务名为customers的所有服务。

使用范围：该/actuator/bus-refresh端点清除@RefreshScope缓存和重新绑定 @ConfigurationProperties

* spring boot2.0版本之前,刷新调用： http://127.0.0.1:5001/bus/refresh
* spring boot2.0版本之后,刷新调用： http://127.0.0.1:5001/actuator/bus-refresh

* 刷新所有的服务的配置信息：http://127.0.0.1:5001/actuator/bus-refresh
* 刷新某个服务名称下的所有的配置信息：http://127.0.0.1:5001/actuator/bus-refresh/customers:**
* 刷新某个服务的的配置信息：http://127.0.0.1:5001/actuator/bus-refresh/customers:9000

customers:9000 解释：应用程序的每个实例都有一个服务ID，其值可以设置为 spring.cloud.bus.id，其值应该是以冒号分隔的标识符列表，从最不具体到最具体。默认值是从环境构造为spring.application.name和 server.port（或spring.application.index，如果设置）的组合。
