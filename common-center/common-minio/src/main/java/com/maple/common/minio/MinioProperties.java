package com.maple.common.minio;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * minio 配置
 *
 * @author zhua
 * @date 2019/8/16
 */
@Data
@Configuration
@ConditionalOnExpression("!'${minio}'.isEmpty()")
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String url;
    private String accessKey;
    private String secretKey;
}
