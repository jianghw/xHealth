package com.kaurihealth.kaurihealth.home.util.Interface;


import com.kaurihealth.kaurihealth.common.util.MedicalHistoryMode;

/**
 * Created by 张磊 on 2016/6/17.
 * 介绍：
 */
public interface IEditMedicaHistory {
    /**
     * 将上个界面传递过来的值展示到界面上
     */
    void loadDataToUi();

    /**
     * 提交修改好的数据到服务器
     */
    void commitDataToServer();

    /**
     * 提交到服务器成功的话 调用该方法
     */
    void commitDataSuccess();

    /**
     * 设置当前界面某些控件可以进行编辑
     */
    void setEditable();

    /**
     * 设置当前界面某些控件可以进行编辑
     */
    void setUnEditable();

    /**
     * 判断是不是同一个医生
     *
     * @return
     */
    boolean isSameDoctor();

    /**
     * 设置不是同一个医生时候的Ui
     */
    void setNotSameDoctorUi();

    /**
     * 设置是同一个医生时候的Ui
     */
    void setIsSameDoctorUi();

    /***
     * 获取当前的模式
     *
     * @return
     */
    MedicalHistoryMode getMode();

    /**
     * 设置当前的模式
     *
     * @param mode
     */
    void setMode(MedicalHistoryMode mode);
}
