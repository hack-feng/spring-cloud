package com.maple.cloud.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.cloud.manage.mapper.ConfigPropertiesMapper;
import com.maple.cloud.manage.mapper.InfoMapper;
import com.maple.cloud.manage.mapper.MicroservicesMapper;
import com.maple.cloud.manage.service.IMicroservicesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.ConfigProperties;
import com.maple.system.api.bean.Info;
import com.maple.system.api.bean.Microservices;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Service
public class MicroservicesServiceImpl extends ServiceImpl<MicroservicesMapper, Microservices> implements IMicroservicesService {

    @Autowired
    private MicroservicesMapper microservicesMapper;
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private ConfigPropertiesMapper configPropertiesMapper;

    @Override
    public IPage<Microservices> getList() {
        Page<Microservices> page = new Page<>();
        IPage<Microservices> list = microservicesMapper.selectPage(page, new QueryWrapper<>());
        return list;
    }

    @Override
    @Transactional
    public R add(Microservices microservices) {
        // 判断微服务名称或者端口号是否存在，存在不可使用
        int micCount = microservicesMapper.selectCount(
                new QueryWrapper<Microservices>()
                        .eq("service_name", microservices.getServiceName())
                        .or()
                        .eq("service_port", microservices.getServicePort()));
        if(micCount > 0){
            return R.failed("服务名称或者端口号已经存在，不可重复使用");
        }
        // 是否自动创建文件
        if(microservices.getIsCreateConfig() == 1){
            List<ConfigProperties> configList = new ArrayList<>();
            ConfigProperties config = new ConfigProperties();
            config.setApplication(microservices.getServiceName());
            config.setCreateDate(new Date());
            config.setCreateId(microservices.getCreateId());
            config.setLabel("master");
            config.setProfile("dev");

            configList.add(createBaseConfig("服务端口号", config, "server.port", microservices.getServicePort(), configList.size()));
            configList.add(createBaseConfig("是否将IP注册到EurekaServer", config, "eureka.instance.preferIpAddress", "true", configList.size()));
            configList.add(createBaseConfig("心跳时间，即服务续约间隔时间（缺省为30s）", config, "eureka.instance.leaseRenewalIntervalInSeconds", "10", configList.size()));
            configList.add(createBaseConfig("发呆时间，即服务续约到期时间（缺省为90s）", config, "eureka.instance.leaseExpirationDurationInSeconds", "30", configList.size()));
            configList.add(createBaseConfig("eureka实例的健康监控", config, "eureka.instance.health-check-url-path", "/actuator/health", configList.size()));
            configList.add(createBaseConfig("间隔多久去拉取服务注册信息，默认为30秒", config, "eureka.client.registryFetchIntervalSeconds", "10", configList.size()));
            configList.add(createBaseConfig("配置注册中心", config, "eureka.client.service-url.defaultZone", "http://127.0.0.1:1111/eureka/", configList.size()));
            configList.add(createBaseConfig("zipkin链路跟踪", config, "spring.zipkin.base-url", "http://localhost:3101", configList.size()));
            if(microservices.getIsUseMysql() == 1){
                int mysqlId = microservices.getMysqlInfo();
                Info info = infoMapper.selectById(mysqlId);
                String url = "jdbc:mysql://"+info.getHost()+":"+info.getPort()+"/"+info.getDataName()+"?useUnicode=true&characterEncoding=utf8&useSSL=false";
                configList.add(createBaseConfig("数据库数据库驱动", config, "spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver", configList.size()));
                configList.add(createBaseConfig("数据库数据库地址", config, "spring.datasource.url", url, configList.size()));
                configList.add(createBaseConfig("数据库用户名", config, "spring.datasource.username", info.getUserName(), configList.size()));
                configList.add(createBaseConfig("数据库密码", config, "spring.datasource.password", info.getPassWord(), configList.size()));
                configList.add(createBaseConfig("数据库连接池", config, "spring.datasource.type", "com.alibaba.druid.pool.DruidDataSource", configList.size()));
                configList.add(createBaseConfig("数据库连接池初始化大小", config, "spring.datasource.initialSize", "5", configList.size()));
                configList.add(createBaseConfig("数据库连接池最小", config, "spring.datasource.minIdle", "5", configList.size()));
                configList.add(createBaseConfig("数据库连接池最大", config, "spring.datasource.maxActive", "30", configList.size()));
                configList.add(createBaseConfig("数据库连接池获取连接等待超时的时间", config, "spring.datasource.maxActive", "60000", configList.size()));
                configList.add(createBaseConfig("数据库连接池间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒", config, "spring.datasource.timeBetweenEvictionRunsMillis", "6000", configList.size()));
                configList.add(createBaseConfig("数据库连接池配置一个连接在池中最小生存的时间，单位是毫秒", config, "spring.datasource.minEvictableIdleTimeMillis", "300000", configList.size()));
                configList.add(createBaseConfig("数据库连接池校验SQL", config, "spring.datasource.validationQuery", "SELECT 'x'", configList.size()));
                configList.add(createBaseConfig("数据库连接池最大", config, "spring.datasource.testWhileIdle", "true", configList.size()));
                configList.add(createBaseConfig("数据库连接池最大", config, "spring.datasource.testOnBorrow", "false", configList.size()));
                configList.add(createBaseConfig("数据库连接池最大", config, "spring.datasource.testOnReturn", "false", configList.size()));
                configList.add(createBaseConfig("数据库连接池打开PSCache", config, "spring.datasource.poolPreparedStatements", "true", configList.size()));
                configList.add(createBaseConfig("数据库连接池指定每个连接上PSCache的大小", config, "spring.datasource.maxPoolPreparedStatementPerConnectionSize", "20", configList.size()));
                configList.add(createBaseConfig("数据库连接池配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙", config, "spring.datasource.filters", "stat,wall,log4j", configList.size()));
                configList.add(createBaseConfig("数据库连接池通过connectProperties属性来打开mergeSql功能；慢SQL记录", config, "spring.datasource.connectionProperties", "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000", configList.size()));
                configList.add(createBaseConfig("数据库连接池合并多个DruidDataSource的监控数据", config, "spring.datasource.useGlobalDataSourceStat", "true", configList.size()));

            }
            if(microservices.getIsUseRabbitmq() == 1){
                int rabbitmqId = microservices.getRabbitmqInfo();
                Info info = infoMapper.selectById(rabbitmqId);
                configList.add(createBaseConfig("rabbitmq地址", config, "spring.rabbitmq.host", info.getHost(), configList.size()));
                configList.add(createBaseConfig("rabbitmq端口号", config, "spring.rabbitmq.port", info.getPort(), configList.size()));
                configList.add(createBaseConfig("rabbitmq用户名", config, "spring.rabbitmq.username", info.getUserName(), configList.size()));
                configList.add(createBaseConfig("rabbitmq密码", config, "spring.rabbitmq.password", info.getPassWord(), configList.size()));
            }
            if(microservices.getIsUseRedis() == 1){
                int redisId = microservices.getRedisInfo();
                Info info = infoMapper.selectById(redisId);
                configList.add(createBaseConfig("redis数据库索引（默认为0）", config, "spring.redis.database", "0", configList.size()));
                configList.add(createBaseConfig("redis地址", config, "spring.redis.host", info.getHost(), configList.size()));
                configList.add(createBaseConfig("redis端口号", config, "spring.redis.port", info.getPort(), configList.size()));
                configList.add(createBaseConfig("redis密码", config, "spring.redis.password", info.getPassWord(), configList.size()));
                configList.add(createBaseConfig("redis连接超时时间（毫秒）默认是2000ms", config, "spring.redis.timeout", "5000ms", configList.size()));
            }

            // 批量插入config的配置信息
            try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(ReflectionKit.getSuperClassGenericType(ConfigPropertiesServiceImpl.class, 1))) {
                int i = 0;
                String sqlStatement = SqlHelper.table(ReflectionKit.getSuperClassGenericType(ConfigPropertiesServiceImpl.class, 1)).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
                for (ConfigProperties anEntityList : configList) {
                    batchSqlSession.insert(sqlStatement, anEntityList);
                    if (i >= 1 && i % 30 == 0) {
                        batchSqlSession.flushStatements();
                    }
                    i++;
                }
                batchSqlSession.flushStatements();
            } catch (Throwable e) {
                throw ExceptionUtils.mpe("Error: Cannot execute saveBatch Method. Cause", e);
            }
        }
        int count = microservicesMapper.insert(microservices);
        if(count > 0){
            return R.ok();
        }
        return R.failed("添加为微服务失败，请重试");
    }


    private ConfigProperties createBaseConfig(String remark, ConfigProperties configFlag, String key, String value, int sort) {
        ConfigProperties config = new ConfigProperties();
        BeanUtils.copyProperties(configFlag, config);
        config.setKey1(key);
        config.setValue1(value);
        config.setSort(sort);
        config.setRemark(remark);
        return config;
    }


}
