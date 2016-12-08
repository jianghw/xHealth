package com.kaurihealth.datalib.request_bean.bean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/13.
 * 备注：
 */
public class ImageBean {
    private String documentUrl;
    private String fileName;
    private String displayName;
    private String documentFormat;

    public ImageBean(String documentUrl, String fileName, String displayName, String documentFormat) {
        this.documentUrl = documentUrl;
        this.fileName = fileName;
        this.displayName = displayName;
        this.documentFormat = documentFormat;
    }

    public ImageBean() {
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }
}
