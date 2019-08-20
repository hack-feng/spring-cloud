package com.maple.common.minio.util;

import cn.hutool.core.util.StrUtil;
import com.google.api.client.util.Value;
import com.maple.common.minio.vo.MinioItem;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Minio 工具类
 *
 * @author zhua
 * @date 2019/8/15
 */
@Slf4j
@Component
@AllArgsConstructor
public class MinioClientUtils {

    @Value("${minio.retry_num:3}")
    private int RETRY_NUM;
    private MinioClient minioClient;

    public boolean createBucket(String bucketName) {
        boolean isCreated;
        try {
            if(!minioClient.bucketExists(bucketName)){
                minioClient.makeBucket(bucketName);
            }
            isCreated = true;
        } catch (Exception e) {
            isCreated = false;
            log.error("createBucketPublic", e);
            e.printStackTrace();
        }
        return isCreated;
    }

    @SneakyThrows
    public List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    @SneakyThrows
    public Optional<Bucket> getBucket(String bucketName) {
        return minioClient.listBuckets().stream().filter( b -> b.name().equals(bucketName)).findFirst();
    }

    @SneakyThrows
    public void removeBucket(String bucketName) {
        minioClient.removeBucket(bucketName);
    }

    @SneakyThrows
    public void saveObject(String bucketName, String objectName, InputStream stream, long size, String contentType) {
        minioClient.putObject(bucketName, objectName, stream, size, null, null, contentType);
    }

    @SneakyThrows
    public ObjectStat getObjectInfo(String bucketName, String objectName) {
        return minioClient.statObject(bucketName, objectName);
    }

    @SneakyThrows
    public void removeObject(String bucketName, String objectName ) {
        minioClient.removeObject(bucketName, objectName);
    }

    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        return minioClient.presignedGetObject(bucketName, objectName, expires);
    }

    public String uploadJpegFile(String bucketName, String minioPath, String jpgFilePath) {
        return uploadFile(bucketName, minioPath, jpgFilePath, MediaType.IMAGE_JPEG_VALUE);
    }

    public String uploadJpegStream(String bucketName, String minioPath, InputStream inputStream) {
        return uploadStream(bucketName, minioPath, inputStream, MediaType.IMAGE_JPEG_VALUE);
    }

    public String uploadStream(String bucketName, String minioFilePath, InputStream inputStream, String mediaType) {
        log.info("uploadStream for bucketName={} minioFilePath={} inputStream.getclass={}, mediaType={}", bucketName,
                minioFilePath, inputStream.getClass(), mediaType);
        if (StrUtil.isBlank(mediaType)) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        try {
            putObjectWithRetry(bucketName, minioFilePath, inputStream, mediaType);
            return minioClient.getObjectUrl(bucketName, minioFilePath);
        } catch (Exception e) {
            log.error("uploadStream occur error:", e);
            throw new RuntimeException(e);
        }
    }

    public String uploadFile(String bucketName, String minioFilePath, String localFile, String mediaType) {
        log.info("uploadFile for bucketName={} minioFilePath={} localFile={}, mediaType={}", bucketName,
                minioFilePath, localFile, mediaType);
        if (StrUtil.isBlank(mediaType)) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        try {
            putObjectWithRetry(bucketName, minioFilePath, localFile, mediaType);
            return minioClient.getObjectUrl(bucketName, minioFilePath);
        } catch (Exception e) {
            log.error("uploadFile occur error:", e);
            throw new RuntimeException(e);
        }
    }

    public List<MinioItem> listFilesSwap(String bucketName, String prefix, boolean recursive) {
        log.info("list files for bucketName={} prefix={} recursive={}", bucketName, prefix, recursive);
        return swapResultToEntityList(minioClient.listObjects(bucketName, prefix, recursive));
    }

    public Iterable<Result<Item>> listFiles(String bucketName, String prefix, boolean recursive) {
        log.info("list files for bucketName={} prefix={} recursive={}", bucketName, prefix, recursive);
        return minioClient.listObjects(bucketName, prefix, recursive);
    }


    public List<MinioItem> listFilesByBucketNameSwap(String bucketName) {
        log.info("listFilesByBucketName for bucketName={}", bucketName);
        return swapResultToEntityList(minioClient.listObjects(bucketName, null, true));
    }

    public Iterable<Result<Item>> listFilesByBucketName(String bucketName) {
        log.info("listFilesByBucketName for bucketName={}", bucketName);
        return minioClient.listObjects(bucketName, null, true);
    }

    public Iterable<Result<Item>> listFilesByBucketAndPrefix(String bucketName, String prefix) {
        log.info("listFilesByBucketAndPrefix for bucketName={} and prefix={}", bucketName, prefix);
        return minioClient.listObjects(bucketName, prefix, true);
    }

    public List<MinioItem> listFilesByBucketAndPrefixSwap(String bucketName, String prefix) {
        log.info("listFilesByBucketAndPrefix for bucketName={} and prefix={}", bucketName, prefix);
        return swapResultToEntityList(minioClient.listObjects(bucketName, prefix, true));
    }

    private MinioItem swapResultToEntity(Result<Item> result) {
        MinioItem minioEntity = new MinioItem();
        try {
            if (result.get() != null) {
                Item item = result.get();
                minioEntity.setObjectName(item.objectName());
                minioEntity.setDir(item.isDir());
                minioEntity.setEtag(item.etag());
                minioEntity.setLastModified(item.lastModified());
                minioEntity.setSize(item.size());
                minioEntity.setStorageClass(item.storageClass());
            }
        } catch (Exception e) {
            log.error("UrlUtils error, e={}", e.getMessage());
        }
        return minioEntity;
    }

    private List<MinioItem> swapResultToEntityList(Iterable<Result<Item>> results) {
        List<MinioItem> minioEntities = new ArrayList<>();
        for (Result<Item> result : results) {
            minioEntities.add(swapResultToEntity(result));
        }
        return minioEntities;
    }

    public void putObjectWithRetry(String bucketName, String objectName, InputStream stream, String contentType) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, NoResponseException, InvalidBucketNameException, XmlPullParserException, InternalException {
        int current = 0;
        boolean isSuccess = false;
        while (!isSuccess && current < RETRY_NUM) {
            try {
                Long size = new Long(stream.available());
                minioClient.putObject(bucketName, objectName, stream, size, null, null, contentType);
                isSuccess = true;
            } catch (ErrorResponseException e) {
                log.warn("[minio] putObject stream, ErrorResponseException occur for time =" + current, e);
                current++;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        if (current == RETRY_NUM) {
            log.error("[minio] putObject, backetName={}, objectName={}, failed finally!");
        }
    }

    public void putObjectWithRetry(String bucketName, String objectName, String fileName, String contentType) throws InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, IOException, InvalidKeyException, NoResponseException, XmlPullParserException, ErrorResponseException, InternalException, InvalidArgumentException, InsufficientDataException {
        int current = 0;
        boolean isSuccess = false;
        while (!isSuccess && current < RETRY_NUM) {
            try {
                minioClient.putObject(bucketName, objectName, fileName, null, null ,null,  contentType);
                isSuccess = true;
            } catch (ErrorResponseException e) {
                current++;
                log.debug("[minio] putObject file, ErrorResponseException occur!");
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        if (current == RETRY_NUM) {
            log.error("[minio] putObject, backetName={}, objectName={}, failed finally!");
        }
    }

    /**
     * 获取文件流操作
     * @param bucketName
     * @param objectName
     * @return
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        return minioClient.getObject(bucketName, objectName);
    }

}
