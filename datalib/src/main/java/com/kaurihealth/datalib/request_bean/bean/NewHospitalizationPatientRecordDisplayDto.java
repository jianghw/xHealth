package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * Created by KauriHealth on 2017/1/5.
 */

public class NewHospitalizationPatientRecordDisplayDto {

    /**
     * patientId : 1
     * comment : sample string 2
     * recordDate : 2017-01-05T16:12:31+08:00
     * doctor : sample string 4
     * hospital : sample string 5
     * departmentId : 6
     * subject : sample string 7
     * hospitalization : [{"hospitalizationType":"sample string 1","createdDate":"2017-01-05T16:12:31+08:00","doctor":"sample string 3","hospital":"sample string 4","departmentId":5,"hospitalizationDocuments":[{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6},{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6}],"comment":"sample string 6","hospitalNumber":"sample string 7"},{"hospitalizationType":"sample string 1","createdDate":"2017-01-05T16:12:31+08:00","doctor":"sample string 3","hospital":"sample string 4","departmentId":5,"hospitalizationDocuments":[{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6},{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6}],"comment":"sample string 6","hospitalNumber":"sample string 7"}]
     * status : sample string 8
     */

    private int patientId;
    private String comment;
    private String recordDate;
    private String doctor;
    private String hospital;
    private int departmentId;
    private String subject;
    private String status;
    /**
     * hospitalizationType : sample string 1
     * createdDate : 2017-01-05T16:12:31+08:00
     * doctor : sample string 3
     * hospital : sample string 4
     * departmentId : 5
     * hospitalizationDocuments : [{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6},{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6}]
     * comment : sample string 6
     * hospitalNumber : sample string 7
     */

    private List<HospitalizationBean> hospitalization;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HospitalizationBean> getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(List<HospitalizationBean> hospitalization) {
        this.hospitalization = hospitalization;
    }

    public static class HospitalizationBean {
        private String hospitalizationType;
        private String createdDate;
        private String doctor;
        private String hospital;
        private int departmentId;
        private String comment;
        private String hospitalNumber;
        /**
         * fileName : sample string 1
         * comment : sample string 2
         * documentFormat : sample string 3
         * documentUrl : sample string 4
         * displayName : sample string 5
         * hospitalizationId : 6
         */

        private List<HospitalizationDocumentsBean> hospitalizationDocuments;

        public String getHospitalizationType() {
            return hospitalizationType;
        }

        public void setHospitalizationType(String hospitalizationType) {
            this.hospitalizationType = hospitalizationType;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getDoctor() {
            return doctor;
        }

        public void setDoctor(String doctor) {
            this.doctor = doctor;
        }

        public String getHospital() {
            return hospital;
        }

        public void setHospital(String hospital) {
            this.hospital = hospital;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getHospitalNumber() {
            return hospitalNumber;
        }

        public void setHospitalNumber(String hospitalNumber) {
            this.hospitalNumber = hospitalNumber;
        }

        public List<HospitalizationDocumentsBean> getHospitalizationDocuments() {
            return hospitalizationDocuments;
        }

        public void setHospitalizationDocuments(List<HospitalizationDocumentsBean> hospitalizationDocuments) {
            this.hospitalizationDocuments = hospitalizationDocuments;
        }

        public static class HospitalizationDocumentsBean {
            private String fileName;
            private String comment;
            private String documentFormat;
            private String documentUrl;
            private String displayName;
            private int hospitalizationId;

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

            public String getDocumentFormat() {
                return documentFormat;
            }

            public void setDocumentFormat(String documentFormat) {
                this.documentFormat = documentFormat;
            }

            public String getDocumentUrl() {
                return documentUrl;
            }

            public void setDocumentUrl(String documentUrl) {
                this.documentUrl = documentUrl;
            }

            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            public int getHospitalizationId() {
                return hospitalizationId;
            }

            public void setHospitalizationId(int hospitalizationId) {
                this.hospitalizationId = hospitalizationId;
            }
        }
    }
}
