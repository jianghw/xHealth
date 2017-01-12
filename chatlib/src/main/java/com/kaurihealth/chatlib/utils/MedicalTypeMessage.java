package com.kaurihealth.chatlib.utils;

import com.avos.avoscloud.im.v2.AVIMMessageCreator;
import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;

/**
 * Created by jianghw on 2017/1/3.
 * <p/>
 * Describe: 病历
 */
@AVIMMessageType(type = 1)
public class MedicalTypeMessage extends AVIMTypedMessage {
    @AVIMMessageField(name = "_lctext")
    String text;

    @AVIMMessageField(name = "_lcattrs")
    MainPagePatientDisplayBean attrs;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MainPagePatientDisplayBean getAttrs() {
        return attrs;
    }

    public void setAttrs(MainPagePatientDisplayBean attrs) {
        this.attrs = attrs;
    }

    public static final Creator<MedicalTypeMessage> CREATOR = new AVIMMessageCreator<>(MedicalTypeMessage.class);
}
