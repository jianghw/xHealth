package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Garnet_Wu on 2016/11/28.
 */

public interface IFeedbackView extends IMvpView {
    String getEditText();
    //Bugtags 提交意见用--> 指明用户
    DoctorDisplayBean getDoctorDisplayBean();

}
