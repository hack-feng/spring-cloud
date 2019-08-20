package com.maple.common.minio.vo;

import io.minio.ObjectStat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * minio 配置
 *
 * @author zhua
 * @date 2019/8/16
 */
@Data
public class MinioObject {

    private final String bucketName;
    private final String name;
    private final Date createdTime;
    private final long length;
    private final String etag;
    private final String contentType;
    private final Map<String, List<String>> httpHeaders;

    public MinioObject(ObjectStat os) {
        this.bucketName = os.bucketName();
        this.name = os.name();
        this.createdTime = os.createdTime();
        this.length = os.length();
        this.etag = os.etag();
        this.contentType = os.contentType();
        this.httpHeaders = os.httpHeaders();
    }
}
