# Eurek注册中心使用
在本项目中使用eureka作为SpringCloud的注册中心，注册预留端口号：1111

注册中心地址：http://localhost:1111/eureka/

本项目配置为单机eureka，如果需要配置高可用eureka集群

请参考：https://blog.csdn.net/qq_34988304/article/details/88997703

## eureka server详情：

pom.xml文件引入
~~~
<!-- 引入Eureka Server，用于注册Eureka服务 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
~~~

~~~ 
# 项目端口号
server:
  port: 1111

# 实例名称
spring:
  application:
    name: eureka-master

# eureka的配置信息
eureka:
  instance:
    # 是否使用ip地址
    preferIpAddress: true
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
  client:
    # false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    # false表示不向注册中心注册自己。
    register-with-eureka: false
    service-url:
      defaultZone: http://localhost:1111/eureka/
~~~
在EurekaMasterApplication.java中添加@EnableEurekaServer注解，用于开启eureka注册中心

## eureka click配置：
pom.xml配置文件：
~~~
<!-- Spring Cloud ==> 开启eureka客户端注册需要 -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
~~~
application.yml文件：
~~~
eureka:
  instance:
    # 在信息列表时显示主机名称
    instance-id: eureka-click
    # 是否使用IP地址注册
    prefer-ip-address: true
    leaseRenewalIntervalInSeconds: 10
    health-check-url-path: /actuator/health
  client:
    registryFetchIntervalSeconds: 5
    service-url:
      defaultZone: http://localhost:1111/eureka/
~~~
Application.java文件添加@EnableDiscoveryClient或@EnableEurekaClient注解，用于开启eureka click。
* 在Spring Cloud Edgware及以上版本，只需要添加相关依赖，即可自动注册。
* 如不想将服务注册到Eureka Server上，只需设置@EnableDiscoveryClient(auto-Register = false)即可
* @EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现
* @EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用

## 多网卡环境下的IP选择
对于多网卡的服务器，各个微服务注册到Eureka Server上的IP要如何指定？
指定IP在某些场景很有用，例如某台服务器有eth0、eth1、eth2三块网卡，但是只有eth1可以被其他服务器访问；如果eureka click将eth0或eth2注册到eureka server上，其他微服务就将无法通过这个IP调用该微服务的接口。
Spring Cloud提供了按需选择IP的能力。

* 忽略指定名称的网卡，例如
~~~
spring:
  cloud:
    inetutils:
      ignored-interfaces:
        - docker0
        - veth.*
eureka:
  instance:
    prefer-ip-address: ture
~~~
这样就可以忽略docker0网卡以及以veth开头的网卡

* 使用正则表达式，指定使用的网络地址，例如
~~~
spring:
  cloud:
    inetutils:
      preferredNetworks:
        - 192.168
        - 10.0
eureka:
  instance:
    prefer-ip-address: ture
~~~

* 只使用站点本地地址，例如
~~~
spring:
  cloud:
    inetutils:
      useOnlySiteLocalInterfaces： true
eureka:
  instance:
    prefer-ip-address: ture
~~~
这样就可以强制使用站点本地地址

* 手动指定IP地址。在某些极端的场景下，可以手动指定注册到Eureka Server的微服务IP，例如
~~~
eureka:
  instance:
    prefer-ip-address: ture
    ip-address: 127.0.0.1
~~~