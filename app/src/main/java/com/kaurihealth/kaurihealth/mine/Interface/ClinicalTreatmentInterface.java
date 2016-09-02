package com.kaurihealth.kaurihealth.mine.Interface;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 创建日期:2016/1/22
 * 修订日期:
 */
public interface ClinicalTreatmentInterface {
    /**
     * 获取日期
     * @return
     */
    String getData();

    /**
     * 获取信息内容
     * @return
     */
    String getMessage();

    /**
     * 获取大分类
     * @return
     */
    String getBigCategory();

    /**
     * 获取小分类
     * @return
     */
    String getSmallCategory();
}
