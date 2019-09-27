# spring-cloud
环境介绍：
* JAVA版本：JDK8
* springBoot：2.1.0.RELEASE
* springCloud：Greenwich.RELEASE
* 数据库：mysql-5.7.22（版本在5.7以上即可）
* Maven：
* 页面：Vue.js

vue前端页面地址：https://github.com/zhua-an/adminUI.git

项目目的：
* 学习spring cloud的知识，总结java常用技术
* 集成常用的工具或模块，在工作中遇到类似功能，可拿来即用
* 模拟工作常见的场景，寻找更优的解决方案
* 交结志同道合的朋友，有兴趣的朋友都可以加入我们，QQ群：680348385
* 为开源贡献自己的一份绵薄之力


## 项目介绍

### 项目使用端口号、数据库、MQ等资源清单

数据库配置文件：maple.sql

| 项目名称 | 端口号 | mysql | redis | rabbitMQ | 必须启动 | 所属模块 | 开发情况 |
| ------- | ----- | ----- | ----- | -------- | ------- | --------| ------- |
| eureka-master  | 1111 | N | N | N | Y | 注册中心 | √ |
| config-master  | 2000 | Y | N | Y | Y | 配置中心 | √ |
| gateway-master | 5001 | N | Y | Y | Y | 路由转发 | √ |
| zipkin-master  | 3101 | N | N | Y | N | 链路跟踪 | √ |
| admin-server   | 6666 | N | N | Y | N | 可视化监控 | √ |
| user-auth      | 3000 | Y | Y | Y | Y | 统一授权中心 | √ |
| cloud-manage   | 5002 | Y | Y | Y | N | 系统配置服务 | 进行中 |
| user-service   | 8001 | Y | Y | Y | Y | 用户服务 | 进行中 |

### Mysql、Redis、RabbitMQ信息
|    类型   |    地址   | 端口号 | 用户名 |  密码  | 数据库名称 |
| -------- | --------- | ----- | ----- | ------ | -------- |
| Mysql    | 127.0.0.1 |  3306 |  root | 123456 |   maple  |
| Redis    | 127.0.0.1 |  6379 |       | 123456 |          |
| RabbitMQ | 127.0.0.1 |  5672 | admin | 123456 |          |

### cloud-center(spring-cloud组件中心)
#### spring-cloud-config(配置中心)
* config-master(已启用，本项目的配置中心)

config使用手册：https://github.com/hack-feng/spring-cloud/blob/master/cloud-center/spring-cloud-config/readme.md

#### spring-cloud-eureka(注册中心)
* eureka-master(已启用，本项目的注册中心)
* eureka-slave(未启用，配置高可用注册中心时使用)
* eureka-backup(未启用，配置高可用注册中心时使用)

eureka使用手册：https://github.com/hack-feng/spring-cloud/blob/master/cloud-center/spring-cloud-eureka/readme.md

#### spring-cloud-gateway(服务网关)
* gateway-master(已启用，本项目的网关配置)

gateway手册：https://github.com/hack-feng/spring-cloud/blob/master/cloud-center/spring-cloud-gateway/gateway-master/readme.md

#### spring-cloud-zipkin(链路跟踪)
* zipkin-master(已启用，本项目的链路跟踪)

#### spring-cloud-admin(spring boot admin 服务监控)
* admin-server(已启用，本项目的服务监控)
  
### common-center(工具中心)
* common-core(系统通用工具)
* common-generate（mybatis-plus 代码生成工具）
* common-minio（文件处理工具）
* common-security（授权认证工具）
  
### project-center(项目中心)
#### maple-service-api(本项目的实体类)
* user-api(用户模块api)
#### maple-service(本项目的微服务)
* user-service(已启用，用户模块微服务)
* user-auth(已启用，用户授权微服务)

服务授权搭建手册：https://github.com/hack-feng/spring-cloud/tree/master/project-center/maple-service/user-auth

* cloud-manage(已启用，系统配置管理)

### logger-center(日志中心，已完成)
使用ELK统一日志管理
* ElasticSearch
* Logstash
* Kibana

搭建教程：https://blog.csdn.net/qq_34988304/article/details/100058049
## 开发规范

### 项目规范
 * 遵循阿里的《JAVA开发手册-华山版》
 * 项目创建放在对应的目录中，例如Eureka放在cloud-center下。
 * 项目命名规范
### 数据库规范

### 文档规范
