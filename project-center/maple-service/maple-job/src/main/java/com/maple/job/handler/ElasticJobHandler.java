package com.maple.job.handler;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhua
 * @date 2019/9/20
 */
@Component
public class ElasticJobHandler {
    @Autowired
    private ZookeeperRegistryCenter regCenter;
    @Resource
    private JobEventConfiguration jobEventConfiguration;
    @Resource
    private ElasticJobListener elasticJobListener;

    /**
     * @Description 任务配置
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build()
                , jobClass.getCanonicalName())
        ).overwrite(true).build();
    }

    public void addJob(final SimpleJob simpleJob,
                       final String cron,
                       final Integer shardingTotalCount,
                       final String shardingItemParameters)
            throws IllegalAccessException, InstantiationException {
        LiteJobConfiguration jobConfig =
                getLiteJobConfiguration(simpleJob.getClass(), cron, shardingTotalCount, shardingItemParameters);

        new SpringJobScheduler(simpleJob, regCenter, jobConfig, jobEventConfiguration, elasticJobListener).init();
    }

    /**
     * 动态添加
     *
     * @param jobClass
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     */
    public void addSimpleJobScheduler(final Class<? extends SimpleJob> jobClass,
                                      final String cron,
                                      final int shardingTotalCount,
                                      final String shardingItemParameters) {
        JobCoreConfiguration coreConfig = JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters).build();
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName());
        JobScheduler jobScheduler = new JobScheduler(regCenter, LiteJobConfiguration.newBuilder(simpleJobConfig).build());
        jobScheduler.init();
    }
}