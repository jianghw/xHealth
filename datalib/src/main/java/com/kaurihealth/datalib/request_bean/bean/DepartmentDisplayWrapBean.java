package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 张磊 on 2016/6/21.
 * 介绍：
 */
public class DepartmentDisplayWrapBean implements Serializable {
    public DepartmentDisplayBean level1;
    public List<DepartmentDisplayBean> level2 = new LinkedList<>();
}
