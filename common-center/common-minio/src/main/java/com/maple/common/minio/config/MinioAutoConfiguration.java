package com.maple.common.minio.config;

import cn.hutool.core.util.StrUtil;
import com.maple.common.minio.MinioProperties;
import io.minio.MinioClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * minio 配置
 *
 * @author zhua
 * @date 2019/8/16
 */
@Slf4j
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({ MinioProperties.class })
public class MinioAutoConfiguration {

    private MinioProperties minioProperties;

    @Bean
    @ConditionalOnExpression("!'${minio}'.isEmpty()")
    public MinioClient minioClient() {
        MinioClient minioClient = null;
        try {
            if (StrUtil.isNotEmpty(minioProperties.getUrl()) && StrUtil.isNotEmpty(minioProperties.getAccessKey()) && StrUtil.isNotEmpty(minioProperties.getSecretKey())) {
                minioClient = new MinioClient(minioProperties.getUrl(), minioProperties.getAccessKey(), minioProperties.getSecretKey(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("restClient.close occur error", e);
        }
        return minioClient;
    }
}
