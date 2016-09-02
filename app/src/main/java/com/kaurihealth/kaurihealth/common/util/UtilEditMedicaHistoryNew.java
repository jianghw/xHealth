package com.kaurihealth.kaurihealth.common.util;

import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.home.util.Interface.IEditMedicaHistoryNew;

/**
 * Created by 张磊 on 2016/6/20.
 * 介绍：
 */
public class UtilEditMedicaHistoryNew {
    IEditMedicaHistoryNew iEditMedicaHistoryNew;
    private TextView controlTv;

    public UtilEditMedicaHistoryNew(IEditMedicaHistoryNew iEditMedicaHistoryNew) {
        this.iEditMedicaHistoryNew = iEditMedicaHistoryNew;
    }

    public void start() {
        controlTv = iEditMedicaHistoryNew.getControlTv();
        iEditMedicaHistoryNew.displayImages();
        iEditMedicaHistoryNew.setImageListener();
        iEditMedicaHistoryNew.setOnIteamLongClickListener();
        iEditMedicaHistoryNew.enableIteamLongClick(false);
        iEditMedicaHistoryNew.initUiWhenUnAbleEdit();
        if (iEditMedicaHistoryNew.isAbleEdit()) {
            iEditMedicaHistoryNew.initUiWhenIsAbleEdit();
            iEditMedicaHistoryNew.setReadMode();
        } else {
            return;
        }
        controlTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (iEditMedicaHistoryNew.getMode()) {
                    case Edit:
                        iEditMedicaHistoryNew.commitDataToServer(iEditMedicaHistoryNew.getType(), iEditMedicaHistoryNew.getController().getBean());
                        break;
                    case Read:
                        iEditMedicaHistoryNew.setMode(MedicalHistoryMode.Edit);
                        iEditMedicaHistoryNew.enableIteamLongClick(true);
                        break;
                }
            }
        });
    }
}
