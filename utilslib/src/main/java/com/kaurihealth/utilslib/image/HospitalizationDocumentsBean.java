package com.kaurihealth.utilslib.image;

/**
 * Created by jianghw on 2016/12/23.
 * <p/>
 * Describe:住院图像（附件）的参数
 */
public class HospitalizationDocumentsBean {
    private int hospitalizationDocumentId;
    private int hospitalizationId;
    private String documentUrl;
    private String documentFormat;
    private String fileName;
    private String comment;
    private boolean isDeleted;
    private String displayName;

    public int getHospitalizationDocumentId() {
        return hospitalizationDocumentId;
    }

    public void setHospitalizationDocumentId(int hospitalizationDocumentId) {
        this.hospitalizationDocumentId = hospitalizationDocumentId;
    }

    public int getHospitalizationId() {
        return hospitalizationId;
    }

    public void setHospitalizationId(int hospitalizationId) {
        this.hospitalizationId = hospitalizationId;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
