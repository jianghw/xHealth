package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by jianghw on 2016/8/24.
 * <p>
 * 描述：
 */
public interface ILongHealthyStandardPresenter<V> extends IMvpPresenter<V> {
    /**
     * 弹出框数据
     *
     * @param list
     */
    void dialogDateByList(List<LongTermMonitoringDisplayBean> list);

    /**
     * 处理出需要显示的数据
     *
     * @param string
     * @param beanList
     */
    void needToDisplayData(String string, List<LongTermMonitoringDisplayBean> beanList);

    String getTypeToBlood(String type);

    /**
     * 过滤规则
     *
     * @param bean
     * @param type
     * @return
     */
    boolean typeFilteringRule(LongTermMonitoringDisplayBean bean, String type);

    /**
     * 分组添加数据
     *
     * @param list
     * @param nestedList
     * @param type
     */
    void addGroupBloodData(List<LongTermMonitoringDisplayBean> list, List<List<LongTermMonitoringDisplayBean>> nestedList, String type);

    /**
     * 删除项目
     *
     * @param bean
     */
    void onDeleteProgram(List<Integer> bean);
}
