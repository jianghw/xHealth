package com.kaurihealth.datalib.response_bean;

import java.util.List;

/**
 * Created by Nick on 17/05/2016.
 */
public class SearchResultBean {


    /**
     * status : OK
     * requestId : 0
     * result : {"searchTime":0,"total":6,"num":6,"viewTotal":6,"items":[{"doctorId":516,"fullName":"刘1","gender":1,"departmentName":"内科系统","hospitalTitle":"副主任医师","hospitalName":"天津市和平区中医医院","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/2e44ee5fb7a8473ea0cb26f16f5af43920160901175312/Icon/7783563c0f264539ac7aa046beaabc32.png"},{"doctorId":505,"fullName":"张一","gender":0,"departmentName":"普通内科","hospitalTitle":"主任医师","hospitalName":"天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/c693c0065b2e4b8883a932c2f6ca170d20160819114348/Icon/571ea69c06474716b91c338347bea73e.png"},{"doctorId":509,"fullName":"张一一","gender":0,"departmentName":"胃肠外科","hospitalTitle":"副主任医师","hospitalName":"天津华博医院1","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/432a5b9f76cd44b7b88091a04e71b63a20160819133721/file/2504e00ef3d44f219443f08365083521.jpg"},{"doctorId":518,"fullName":"小哥哥","gender":1,"departmentName":"脑内儿科","hospitalTitle":"副主任医师","hospitalName":"上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1","avatar":""},{"doctorId":532,"fullName":"张c","gender":0,"departmentName":"神经内科","hospitalTitle":"副主任医师","hospitalName":"1231456789","avatar":""},{"doctorId":533,"fullName":"张79","gender":0,"departmentName":"血液科","hospitalTitle":"副主任医师","hospitalName":"123456789","avatar":""}],"facet":null}
     * errors : null
     * tracer : none
     */

    private String status;
    private String requestId;
    /**
     * searchTime : 0.0
     * total : 6
     * num : 6
     * viewTotal : 6
     * items : [{"doctorId":516,"fullName":"刘1","gender":1,"departmentName":"内科系统","hospitalTitle":"副主任医师","hospitalName":"天津市和平区中医医院","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/2e44ee5fb7a8473ea0cb26f16f5af43920160901175312/Icon/7783563c0f264539ac7aa046beaabc32.png"},{"doctorId":505,"fullName":"张一","gender":0,"departmentName":"普通内科","hospitalTitle":"主任医师","hospitalName":"天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院天津市天津华博医院1第一分院","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/c693c0065b2e4b8883a932c2f6ca170d20160819114348/Icon/571ea69c06474716b91c338347bea73e.png"},{"doctorId":509,"fullName":"张一一","gender":0,"departmentName":"胃肠外科","hospitalTitle":"副主任医师","hospitalName":"天津华博医院1","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/432a5b9f76cd44b7b88091a04e71b63a20160819133721/file/2504e00ef3d44f219443f08365083521.jpg"},{"doctorId":518,"fullName":"小哥哥","gender":1,"departmentName":"脑内儿科","hospitalTitle":"副主任医师","hospitalName":"上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1上海仁J医院1","avatar":""},{"doctorId":532,"fullName":"张c","gender":0,"departmentName":"神经内科","hospitalTitle":"副主任医师","hospitalName":"1231456789","avatar":""},{"doctorId":533,"fullName":"张79","gender":0,"departmentName":"血液科","hospitalTitle":"副主任医师","hospitalName":"123456789","avatar":""}]
     * facet : null
     */

    private ResultBean result;
    private List<SearchErrorsBean> errors;
    private String tracer;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<SearchErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<SearchErrorsBean> errors) {
        this.errors = errors;
    }

    public String getTracer() {
        return tracer;
    }

    public void setTracer(String tracer) {
        this.tracer = tracer;
    }

    public static class ResultBean {
        private double searchTime;
        private int total;
        private int num;
        private int viewTotal;
        private Object facet;
        /**
         * doctorId : 516
         * fullName : 刘1
         * gender : 1
         * departmentName : 内科系统
         * hospitalTitle : 副主任医师
         * hospitalName : 天津市和平区中医医院
         * avatar : http://kaurihealthrecordimagetest.kaurihealth.com/2e44ee5fb7a8473ea0cb26f16f5af43920160901175312/Icon/7783563c0f264539ac7aa046beaabc32.png
         */

        private List<ItemsBean> items;

        public double getSearchTime() {
            return searchTime;
        }

        public void setSearchTime(double searchTime) {
            this.searchTime = searchTime;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getViewTotal() {
            return viewTotal;
        }

        public void setViewTotal(int viewTotal) {
            this.viewTotal = viewTotal;
        }

        public Object getFacet() {
            return facet;
        }

        public void setFacet(Object facet) {
            this.facet = facet;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private int doctorId;
            private String fullName;
            private int gender;
            private String departmentName;
            private String hospitalTitle;
            private String hospitalName;
            private String avatar;
            private String relationshipStatus;

            public String getRelationshipStatus() {
                return relationshipStatus;
            }

            public void setRelationshipStatus(String relationshipStatus) {
                this.relationshipStatus = relationshipStatus;
            }

            public int getDoctorId() {
                return doctorId;
            }

            public void setDoctorId(int doctorId) {
                this.doctorId = doctorId;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
            }

            public String getHospitalTitle() {
                return hospitalTitle;
            }

            public void setHospitalTitle(String hospitalTitle) {
                this.hospitalTitle = hospitalTitle;
            }

            public String getHospitalName() {
                return hospitalName;
            }

            public void setHospitalName(String hospitalName) {
                this.hospitalName = hospitalName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }
    }
}