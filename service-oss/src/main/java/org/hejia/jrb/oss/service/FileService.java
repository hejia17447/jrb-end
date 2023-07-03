package org.hejia.jrb.oss.service;

import java.io.InputStream;

/**
 * 阿里云OSS上传服务
 */
public interface FileService {

    /**
     * 文件上传至阿里云
     * @param inputStream 文件流
     * @param module 上次模式
     * @param fileName 文件名称
     * @return 上传结果
     */
    String upload(InputStream inputStream, String module, String fileName);

    /**
     * 根据路径删除文件
     * @param url 文件地址
     */
    void removeFile(String url);

}
