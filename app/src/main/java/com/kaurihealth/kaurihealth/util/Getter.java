package com.kaurihealth.kaurihealth.util;

import android.content.Context;

import com.example.commonlibrary.widget.GlobalStore.StoreConfig;
import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.request_bean.bean.ProductDisplayBean;
import com.kaurihealth.kaurihealth.mine.util.PersoninfoConfig;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.youyou.zllibrary.util.SpUtil;

import java.util.List;

/**
 * Created by 张磊 on 2016/5/26.
 * 介绍：
 */
public class Getter implements IGetter {

    private final SpUtil spUtil;

    private Getter(Context context) {
        spUtil = SpUtil.getInstance(context);
    }

    private static IGetter getter;

    public static IGetter getInstance(Context context) {
        if (getter == null) {
            getter = new Getter(context);
        }
        return getter;
    }

    @Override
    public String getToken() {
        try {
            TokenBean tokenBean = spUtil.get(StoreConfig.TokenBeanKey, TokenBean.class);
            return tokenBean.accessToken;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAccount() {
        try {
            TokenBean tokenBean = spUtil.get(StoreConfig.TokenBeanKey, TokenBean.class);
            return tokenBean.user.userName;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getKauriHealthId() {
        try {
            TokenBean tokenBean = spUtil.get(StoreConfig.TokenBeanKey, TokenBean.class);
            return tokenBean.user.kauriHealthId;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getFullName() {
        try {
            PersonInfoBean personInfoBean = spUtil.get(StoreConfig.PersoninfoKey, PersonInfoBean.class);
            return personInfoBean.fullName;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    @Override
    public DepartmentDisplayBean[] getDepartments() {
        try {
            return spUtil.get(StoreConfig.DepartmentsKey, DepartmentDisplayBean[].class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DepartmentDisplayWrapBean[] getDepartmentDisplayWrapBean() {
        try {
            return spUtil.get(StoreConfig.DepartmentsNewKey, DepartmentDisplayWrapBean[].class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAvatar() {
        try {
            PersonInfoBean personInfoBean = spUtil.get(StoreConfig.PersoninfoKey, PersonInfoBean.class);
            return personInfoBean.avatar;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PersonInfoBean getPersonInfo() {
        try {
            PersonInfoBean personInfoBean = spUtil.get(StoreConfig.PersoninfoKey, PersonInfoBean.class);
            return personInfoBean;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getRefreshToken() {
        try {
            return getTokenBean().refreshToken;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getUserId() {
        try {
            return getTokenBean().user.userId;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return -100;
        }
    }

    @Override
    public DoctorDisplayBean getMyself() {
        try {
            return spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProductDisplayBean getRemoteProduct() {
        List<ProductDisplayBean> products = getMyself().products;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).productName.equals(PersoninfoConfig.RemoteDoctor)) {
                return products.get(i);
            }
        }
        return null;
    }

    @Override
    public ProductDisplayBean getExclusiveProduct() {
        List<ProductDisplayBean> products = getMyself().products;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).productName.equals(PersoninfoConfig.ExclusiveDoctor)) {
                return products.get(i);
            }
        }
        return null;
    }

    @Override
    public double getTotalCredit() {
        try {
            return spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class).totalCredit;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double getAvailableCredit() {
        try {
            return spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class).availableCredit;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public TokenBean getTokenBean() throws IllegalAccessException {
        TokenBean tokenBean = spUtil.get(StoreConfig.TokenBeanKey, TokenBean.class);
        return tokenBean;
    }

    @Override
    public double getRegistPercentage() {
        try {
            DoctorDisplayBean doctorDisplayBean = spUtil.get(StoreConfig.MyselfKey, DoctorDisplayBean.class);
            return doctorDisplayBean.registPercentage;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public DepartmentDisplayBean getDepartment() throws NoValueException {
        DoctorDisplayBean myself = getter.getMyself();
        if (myself != null && myself.doctorInformations != null) {
            DepartmentDisplayBean department = myself.doctorInformations.department;
            if (department == null) {
                throw new NoValueException();
            } else {
                return department;
            }
        }
        throw new NoValueException();
    }

    @Override
    public String getHospitalName() {
        DoctorDisplayBean myself = getMyself();
        if (myself != null && myself.doctorInformations != null) {
            return myself.doctorInformations.hospitalName;
        }
        return null;
    }

    public String getNationalIdentity() {
        DoctorDisplayBean myself = getMyself();
        return myself.nationalIdentity;
    }
}
