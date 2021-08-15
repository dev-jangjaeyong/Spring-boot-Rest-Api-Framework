package com.atonm.kblease.api.file.fileStorageUtil.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@ConfigurationProperties(prefix = "file")
public class FileStorageUtilProperties {
    private String uploadDir;
    private String innerUploadDir;
    private String outerUploadDir;
    private String imageUrl;
    private String imageBackupDir;
    private String carWatermarkFilePath;
    private String passWatermarkFilePath;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getInnerUploadDir() {
        return innerUploadDir;
    }

    public void setInnerUploadDir(String innerUploadDir) {
        this.innerUploadDir = innerUploadDir;
    }

    public String getOuterUploadDir() {
        return outerUploadDir;
    }

    public void setOuterUploadDir(String outerUploadDir) {
        this.outerUploadDir = outerUploadDir;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageBackupDir ( String imageBackupDir ) {
        this.imageBackupDir =   imageBackupDir;
    }

    public String getImageBackupDir () {
        return imageBackupDir;
    }

    public String getCarWatermarkFilePath() {
        return carWatermarkFilePath;
    }

    public void setCarWatermarkFilePath(String carWatermarkFilePath) {
        this.carWatermarkFilePath = carWatermarkFilePath;
    }

    public String getPassWatermarkFilePath() {
        return passWatermarkFilePath;
    }

    public void setPassWatermarkFilePath(String passWatermarkFilePath) {
        this.passWatermarkFilePath = passWatermarkFilePath;
    }

}   // END class
