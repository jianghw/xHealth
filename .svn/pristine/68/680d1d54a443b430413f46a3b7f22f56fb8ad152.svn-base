package com.kaurihealth.chatlib.utils;

import com.avos.avoscloud.im.v2.AVIMMessageField;
import com.avos.avoscloud.im.v2.AVIMMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by jianghw on 2017/1/3.
 * <p/>
 * Describe: 添加人时通知信息
 */
@AVIMMessageType(
        type = 400
)
public class JoinTypeMessage extends AVIMTypedMessage {
    @AVIMMessageField(name = "_lctext")
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
