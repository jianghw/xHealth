package com.kaurihealth.kaurihealth.common;

import com.kaurihealth.kaurihealth.common.Interface.IAddRecord;

/**
 * Created by 张磊 on 2016/7/19.
 * 介绍：
 */
public class AddRecordControl {
    IAddRecord iAddRecord;

    public AddRecordControl(IAddRecord iAddRecord) {
        this.iAddRecord = iAddRecord;
    }

    public AddRecordControl() {
    }

    public void setiAddRecord(IAddRecord iAddRecord) {
        this.iAddRecord = iAddRecord;
    }

    public void startCommitData() {
        if (iAddRecord.isImageComplete()) {
            iAddRecord.commit(iAddRecord.getBean());
        } else {
            iAddRecord.setIsWaite(true);
        }
    }

    public void uploadImageComplete() {
        iAddRecord.setImagsUpLoadCompleted(true);
        if (iAddRecord.isWaite()) {
            iAddRecord.commit(iAddRecord.getBean());
        }
    }
}
