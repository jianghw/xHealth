package com.kaurihealth.kaurihealth.util;

import android.content.Context;

import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.GlobalStore.StoreConfig;
import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.mine.util.PersoninfoConfig;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.youyou.zllibrary.util.SpUtil;

import java.util.List;

/**
 * Created by 张磊 on 2016/5/26.
 * 介绍：
 */
public class Putter implements IPutter {

    private final SpUtil spUtil;

    public Putter(Context context) {
        spUtil = SpUtil.getInstance(context);
    }

    @Override
    public void setTokenBean(TokenBean tokenBean) {
        spUtil.put(StoreConfig.TokenBeanKey, tokenBean);
    }

    @Override
    public void setMyself(DoctorDisplayBean doctorDisplayBean) {
        spUtil.put(StoreConfig.MyselfKey, doctorDisplayBean);
    }

    @Override
    public void setDepartment(DepartmentDisplayBean[] departments) {
        spUtil.put(StoreConfig.DepartmentsKey, departments);
    }

    @Override
    public void setDepartment(DepartmentDisplayWrapBean[] departments) {
        spUtil.put(StoreConfig.DepartmentsNewKey, departments);
    }

    @Override
    public void setPersonInfo(PersonInfoBean personInfo) {
        spUtil.put(StoreConfig.PersoninfoKey, personInfo);
    }

    @Override
    public void setToken(String token) {
        try {
            TokenBean tokenBean = spUtil.get(StoreConfig.TokenBeanKey, TokenBean.class);
            tokenBean.accessToken = token;
            setTokenBean(tokenBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
    }

    @Override
    public void setName(String firstName, String lastName) {
        try {
            PersonInfoBean personInfoBean = spUtil.get(StoreConfig.PersoninfoKey, PersonInfoBean.class);
            personInfoBean.firstName = firstName;
            personInfoBean.lastName = lastName;
            personInfoBean.fullName = lastName + firstName;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
    }

    @Override
    public void setExclusiveProduct(boolean isAvailable, double unitPrice) {
        try {
            DoctorDisplayBean doctorDisplayBean = spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class);
            List<ProductDisplayBean> products = doctorDisplayBean.products;
            for (ProductDisplayBean iteam : products) {
                if (iteam.productName.equals(PersoninfoConfig.ExclusiveDoctor)) {
                    iteam.price.isAvailable = isAvailable;
                    iteam.price.unitPrice = unitPrice;
                    setMyself(doctorDisplayBean);
                    return;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
    }

    @Override
    public void setRemoteProduct(boolean isAvailable, double unitPrice) {
        try {
            DoctorDisplayBean doctorDisplayBean = spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class);
            List<ProductDisplayBean> products = doctorDisplayBean.products;
            for (ProductDisplayBean iteam : products) {
                if (iteam.productName.equals(PersoninfoConfig.RemoteDoctor)) {
                    iteam.price.isAvailable = isAvailable;
                    iteam.price.unitPrice = unitPrice;
                    setMyself(doctorDisplayBean);
                    return;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
    }

    @Override
    public void setRegistPercentage(String registPercentage) {
        DoctorDisplayBean doctorDisplayBean = null;
        try {
            doctorDisplayBean = spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class);
            doctorDisplayBean.registPercentage = Double.parseDouble(registPercentage);
            setMyself(doctorDisplayBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
    }

    @Override
    public void reduceTotalCredit(double amount) {
        DoctorDisplayBean doctorDisplayBean = null;
        try {
            doctorDisplayBean = spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class);
            doctorDisplayBean.totalCredit = doctorDisplayBean.totalCredit-amount;
            setMyself(doctorDisplayBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
    }

    @Override
    public void reduceAvailableCredit(double amount) {
        DoctorDisplayBean doctorDisplayBean = null;
        try {
            doctorDisplayBean = spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class);
            doctorDisplayBean.availableCredit = doctorDisplayBean.availableCredit-amount;
            setMyself(doctorDisplayBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
    }



}
