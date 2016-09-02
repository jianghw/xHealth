package com.kaurihealth.kaurihealth.home.util.Interface;

import android.app.Activity;
import android.widget.GridView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.MedicalRecordType;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.common.util.MedicalHistoryMode;
import com.kaurihealth.kaurihealth.util.Interface.IMedicalRecordController;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;

/**
 * Created by 张磊 on 2016/6/20.
 * 介绍：
 */
public interface IEditMedicaHistoryNew {
    boolean isAbleEdit();

    void setMode(MedicalHistoryMode mode);

    MedicalHistoryMode getMode();

    void commitDataToServer(MedicalRecordType type, PatientRecordDisplayBean bean);

    void commitDataSuccess(PatientRecordDisplayBean backData);

    void commitDataFail(Throwable t);

    void commitDataFail();

    void initUiWhenIsAbleEdit();

    void initUiWhenUnAbleEdit();

    TextView getControlTv();

    IMedicalRecordController getController();

    void setReadMode();

    void setEditMode();


    void displayImages();

    GridView getGridview();

    void setImageListener();

    Activity getActivity();

    void setOnIteamLongClickListener();

    void enableIteamLongClick(boolean enAble);

    MedicalRecordType getType();

    UploadFileUtil getUploadFile();

    /**
     * 检查数据完整
     *
     * @return
     */
    boolean checkDataComplete();
}
