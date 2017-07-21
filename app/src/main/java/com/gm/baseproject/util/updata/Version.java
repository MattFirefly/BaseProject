package com.gm.baseproject.util.updata;

/**
 * Created by zhanggangmin on 16/8/1.
 */
public class Version {

    /**
     * id : 20
     * code : android
     * type : client
     * version : 1.0.0
     * checkCode : test1
     * downloadTimes : 0
     * status : YES
     * uploadTime : 2016-07-29 12:07:15
     * filFileKey : 39
     * updateContent : test
     * fileName : test.zip
     * userName : null
     * fileId : 123
     * downloadUrl : http://192.168.1.115/sc-file/api/file/download/test.zip/123
     * codeName : 安卓叉车端
     */

    private int id;
    private String code;
    private String type;
    private String version;
    private String checkCode;
    private int downloadTimes;
    private String status;
    private String uploadTime;
    private int filFileKey;
    private String updateContent;
    private String fileName;
    private Object userName;
    private String fileId;
    private String downloadUrl;
    private String codeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public int getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(int downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public int getFilFileKey() {
        return filFileKey;
    }

    public void setFilFileKey(int filFileKey) {
        this.filFileKey = filFileKey;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
