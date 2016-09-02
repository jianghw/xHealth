package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/13.
 * 备注：对象类
 */
public class Category {

    private String mCategoryName;
    private List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanItem = new ArrayList<MedicalLiteratureDisPlayBean>();

    public Category(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public List<MedicalLiteratureDisPlayBean> getMedicalLiteratureDisPlayBeanItem() {
        return medicalLiteratureDisPlayBeanItem;
    }

    public void addItem(MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean) {
        medicalLiteratureDisPlayBeanItem.add(medicalLiteratureDisPlayBean);
    }

    /**
     * 获取Item内容
     *
     * @param pPostion
     * @return
     */
    public Object getItem(int pPostion) {
        if (pPostion == 0) {
            return mCategoryName;
        } else {
            return medicalLiteratureDisPlayBeanItem.get(pPostion - 1);
        }
    }

    /**
     * 当前类别Item总数,category也需要占用一个Item
     *
     * @return
     */
    public int getItemCount() {
        return medicalLiteratureDisPlayBeanItem.size() + 1;
    }

    /**
     * 判断List<MedicalLiteratureDisPlayBean>里面MedicalLiteratureDisPlayBean是否超过4个
     *
     * @return
     */
    public boolean getItemBoolean() {
        if (medicalLiteratureDisPlayBeanItem == null) {
            return true;
        }
        if (medicalLiteratureDisPlayBeanItem.size() <= 3) {
            return true;
        }
        return false;
    }
}
