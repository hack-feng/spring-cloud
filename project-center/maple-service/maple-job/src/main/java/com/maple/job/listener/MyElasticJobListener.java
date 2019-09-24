package com.maple.job.listener;

import cn.hutool.core.date.DateUtil;
import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhua
 * @date 2019/9/23
 */
@Slf4j
public class MyElasticJobListener implements ElasticJobListener {

    private long beginTime = 0;
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        beginTime = System.currentTimeMillis();

        log.info("===>{} JOB BEGIN TIME: {} <===",shardingContexts.getJobName(), DateUtil.now());
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        long endTime = System.currentTimeMillis();
        log.info("===>{} JOB END TIME: {},TOTAL CAST: {} <===",shardingContexts.getJobName(), DateUtil.now(), endTime - beginTime);
    }
}