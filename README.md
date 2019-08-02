# spring-cloud
环境介绍：
* JAVA版本：JDK8
* springBoot：2.1.0.RELEASE
* springCloud：Greenwich.RELEASE
* 数据库：mysql-5.7.22（版本在5.7以上即可）
* Maven：
* 页面：Vue.js

项目目的：
* 学习spring cloud的知识，总结java常用技术
* 集成常用的工具或模块，在工作中遇到类似功能，可拿来即用
* 模拟工作常见的场景，寻找更优的解决方案
* 交结志同道合的朋友，有兴趣的朋友都可以加入我们，QQ群：680348385
* 为开源贡献一份绵薄之力


## 项目介绍
### cloud-center(spring-cloud组件中心)
#### spring-cloud-config(配置中心)
* config-master(已启用，本项目的配置中心)
#### spring-cloud-eureka(注册中心)
* eureka-master(已启用，本项目的注册中心)
* eureka-slave(未启用，配置高可用注册中心时使用)
* eureka-backup(未启用，配置高可用注册中心时使用)
#### spring-cloud-zuul(服务网关)
* zuul-demo(已启用，本项目的网关配置)
#### spring-cloud-zipkin(链路跟踪)
* zipkin-master(已启用，本项目的链路跟踪)
#### spring-cloud-admin(spring boot admin 服务监控)
* admin-server(已启用，本项目的服务监控)
  
### common-center(工具中心，暂未开发)
  
### project-center(项目中心)
#### maple-service-api(本项目的实体类)
* user-api(用户模块api)
#### maple-service(本项目的微服务)
* user-service(已启用，用户模块微服务)
* maple-sso(已启用，用户登录微服务)

### logger-center(日志中心，暂未开发)


## 开发规范

### 项目规范
 * 遵循阿里的《JAVA开发手册-华山版》
 * 项目创建放在对应的目录中，例如Eureka放在cloud-center下。
 * 项目命名
### 数据库规范

### 文档规范

### 部署规范