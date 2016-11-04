package com.kaurihealth.datalib.response_bean;

import java.util.List;

/**
 * Created by Nick on 17/05/2016.
 */
public class SearchResultBean {
    /**
     * status : OK
     * request_id : 147374591917779943157718
     * result : {"searchtime":0.007033,"total":5,"num":5,"viewtotal":5,"items":[{"doctorid":"25278","hospitaltitle":"","departmentid":"0","hospitalname":"","departmentname":"","gender":"0","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"25279","hospitaltitle":"","departmentid":"0","hospitalname":"","departmentname":"","gender":"0","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"1598","hospitaltitle":"","departmentid":"","hospitalname":"","departmentname":"","gender":"0","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"624","hospitaltitle":"住院医师","departmentid":"1","hospitalname":"上海仁J医院","departmentname":"内科系统","gender":"1","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"2007","hospitaltitle":"主治医师","departmentid":"2","hospitalname":"XX大学附属眼耳鼻喉科医院","departmentname":"普通内科","gender":"0","fullname":"zhangdhbshhfvsgdvhdvhdhdbdhgdjdbdhdhvdhdbdhxvdhdvdhdvd","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"}],"facet":[]}
     * errors : []
     * tracer :
     */

    private String status;
    private String request_id;
    /**
     * searchtime : 0.007033
     * total : 5
     * num : 5
     * viewtotal : 5
     * items : [{"doctorid":"25278","hospitaltitle":"","departmentid":"0","hospitalname":"","departmentname":"","gender":"0","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"25279","hospitaltitle":"","departmentid":"0","hospitalname":"","departmentname":"","gender":"0","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"1598","hospitaltitle":"","departmentid":"","hospitalname":"","departmentname":"","gender":"0","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"624","hospitaltitle":"住院医师","departmentid":"1","hospitalname":"上海仁J医院","departmentname":"内科系统","gender":"1","fullname":"zhanglei","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"},{"doctorid":"2007","hospitaltitle":"主治医师","departmentid":"2","hospitalname":"XX大学附属眼耳鼻喉科医院","departmentname":"普通内科","gender":"0","fullname":"zhangdhbshhfvsgdvhdvhdhdbdhgdjdbdhdhvdhdbdhxvdhdvdhdvd","lastname":"zhang","avatar":"","index_name":"KauriHealthOpenSearchTest"}]
     * facet : []
     */

    private ResultBean result;
    private String tracer;
    private List<SearchErrorsBean> errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
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

    public List<SearchErrorsBean> getErrors() {
        return errors;
    }

    public void setErrors(List<SearchErrorsBean> errors) {
        this.errors = errors;
    }

    public static class ResultBean {
        private double searchtime;
        private int total;
        private int num;
        private int viewtotal;
        /**
         * doctorid : 25278
         * hospitaltitle :
         * departmentid : 0
         * hospitalname :
         * departmentname :
         * gender : 0
         * fullname : zhanglei
         * lastname : zhang
         * avatar :
         * index_name : KauriHealthOpenSearchTest
         */

        private List<ItemsBean> items;
        private List<?> facet;

        public double getSearchtime() {
            return searchtime;
        }

        public void setSearchtime(double searchtime) {
            this.searchtime = searchtime;
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

        public int getViewtotal() {
            return viewtotal;
        }

        public void setViewtotal(int viewtotal) {
            this.viewtotal = viewtotal;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public List<?> getFacet() {
            return facet;
        }

        public void setFacet(List<?> facet) {
            this.facet = facet;
        }

        public static class ItemsBean {
            private String doctorid;
            private String hospitaltitle;
            private String departmentid;
            private String hospitalname;
            private String departmentname;
            private String gender;
            private String fullname;
            private String lastname;
            private String avatar;
            private String index_name;

            public String getDoctorid() {
                return doctorid;
            }

            public void setDoctorid(String doctorid) {
                this.doctorid = doctorid;
            }

            public String getHospitaltitle() {
                return hospitaltitle;
            }

            public void setHospitaltitle(String hospitaltitle) {
                this.hospitaltitle = hospitaltitle;
            }

            public String getDepartmentid() {
                return departmentid;
            }

            public void setDepartmentid(String departmentid) {
                this.departmentid = departmentid;
            }

            public String getHospitalname() {
                return hospitalname;
            }

            public void setHospitalname(String hospitalname) {
                this.hospitalname = hospitalname;
            }

            public String getDepartmentname() {
                return departmentname;
            }

            public void setDepartmentname(String departmentname) {
                this.departmentname = departmentname;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getFullname() {
                return fullname;
            }

            public void setFullname(String fullname) {
                this.fullname = fullname;
            }

            public String getLastname() {
                return lastname;
            }

            public void setLastname(String lastname) {
                this.lastname = lastname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getIndex_name() {
                return index_name;
            }

            public void setIndex_name(String index_name) {
                this.index_name = index_name;
            }
        }
    }

}