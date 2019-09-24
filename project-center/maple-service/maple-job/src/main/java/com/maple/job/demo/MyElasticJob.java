package com.maple.job.demo;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.elasticjob.lite.annotation.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhua
 * @date 2019/9/23
 */
@Slf4j
@Component
@ElasticSimpleJob(cron = "0/5 * * * * ?", jobName = "test123", shardingTotalCount = 2, jobParameter = "测试参数", shardingItemParameters = "0=A,1=B")
public class MyElasticJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        //打印出任务相关信息，JobParameter用于传递任务的ID
        log.info("任务名：{}, 片数：{}, id={}", shardingContext.getJobName(), shardingContext.getShardingTotalCount(),
                shardingContext.getJobParameter());
        System.out.println(String.format("------Thread ID: %s, 任务分片数: %s, " +
                        "当前分片项: %s.当前参数: %s," +
                        "当前任任务名: %s.当前任务参数: %s"
                ,
                Thread.currentThread().getId(),
                shardingContext.getShardingTotalCount(),
                shardingContext.getShardingItem(),
                shardingContext.getShardingParameter(),
                shardingContext.getJobName(),
                shardingContext.getJobParameter()
        ));
    }
}
