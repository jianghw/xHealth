package com.kaurihealth.datalib.request_bean.bean;

import com.kaurihealth.datalib.response_bean.SicknessesBean;

import java.util.List;

/**
 * 描述：
 * 修订日期:
 */
public class LoadAllSicknessItem {
    private String title;
    private List<SicknessesBean> list;

    public LoadAllSicknessItem(String title, List<SicknessesBean> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SicknessesBean> getChildList() {
        return list;
    }

    public void setChildList(List<SicknessesBean> list) {
        this.list = list;
    }
}
