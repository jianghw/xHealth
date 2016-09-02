package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * Created by Nick on 17/05/2016.
 */
public class SearchResultBean {
    /**
     * status : OK
     * requestId : null
     * result : {"searchTime":0.006988,"total":3,"num":3,"viewTotal":3,"items":[{"doctorId":1598,"fullName":"zhanglei","gender":0,"departmentName":"","hospitalTitle":"","hospitalName":"","avatar":""},{"doctorId":624,"fullName":"zhanglei","gender":1,"departmentName":"内科系统","hospitalTitle":"住院医师","hospitalName":"上海仁J医院","avatar":""},{"doctorId":506,"fullName":"zhanglei","gender":0,"departmentName":"心血管内科","hospitalTitle":"住院医师","hospitalName":"XX医学院附属RJ医院","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/1d5da89e54074f2da1e882d163d7a150/file/0a963012212f43a489b1c0d84d6c77d2.jpg"}],"facet":[]}
     * errors : [{"code":"6016","message":"Hit in query over limit."}]
     * tracer :
     */

    public String status;
    private Object requestId;
    /**
     * searchTime : 0.006988
     * total : 3
     * num : 3
     * viewTotal : 3
     * items : [{"doctorId":1598,"fullName":"zhanglei","gender":0,"departmentName":"","hospitalTitle":"","hospitalName":"","avatar":""},{"doctorId":624,"fullName":"zhanglei","gender":1,"departmentName":"内科系统","hospitalTitle":"住院医师","hospitalName":"上海仁J医院","avatar":""},{"doctorId":506,"fullName":"zhanglei","gender":0,"departmentName":"心血管内科","hospitalTitle":"住院医师","hospitalName":"XX医学院附属RJ医院","avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/1d5da89e54074f2da1e882d163d7a150/file/0a963012212f43a489b1c0d84d6c77d2.jpg"}]
     * facet : []
     */

    public ResultBean result;
    private String tracer;
    /**
     * code : 6016
     * message : Hit in query over limit.
     */

    private List<ErrorsBean> errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRequestId() {
        return requestId;
    }

    public void setRequestId(Object requestId) {
        this.requestId = requestId;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getTracer() {
        return tracer;
    }

    public void setTracer(String tracer) {
        this.tracer = tracer;
    }

    public List<ErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorsBean> errors) {
        this.errors = errors;
    }

    public static class ResultBean {
        private double searchTime;
        private int total;
        private int num;
        private int viewTotal;
        /**
         * doctorId : 1598
         * fullName : zhanglei
         * gender : 0
         * departmentName :
         * hospitalTitle :
         * hospitalName :
         * avatar :
         */

        public List<SearchDoctorDisplayBean> items;
        private List<?> facet;

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

        public List<SearchDoctorDisplayBean> getItems() {
            return items;
        }

        public void setItems(List<SearchDoctorDisplayBean> items) {
            this.items = items;
        }

        public List<?> getFacet() {
            return facet;
        }

        public void setFacet(List<?> facet) {
            this.facet = facet;
        }

    }

    public static class ErrorsBean {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }


//    /// <summary>
//    /// 状态
//    /// </summary>
//    public String status;
//
//    /// <summary>
//    /// 请求ID
//    /// </summary>
//    public String requestId;
//
//    /// <summary>
//    /// 搜索所需的参数
//    /// </summary>
//    public SearchResultStatusBean result;
//
//    /// <summary>
//    /// 错误
//    /// </summary>
//    public List<SearchErrorBean> Errors;
//
//    /// <summary>
//    /// 曳光弹
//    /// </summary>
//    public String Tracer;
}