package com.maple.common.minio.vo;

import io.minio.messages.Owner;
import lombok.Data;

import java.util.Date;

/**
 * minio 配置
 *
 * @author zhua
 * @date 2019/8/16
 */
@Data
public class MinioItem {

    private String objectName;
    private Date lastModified;
    private String etag;
    private long size;
    private String storageClass;
    private Owner owner;
    private boolean isDir;
}
