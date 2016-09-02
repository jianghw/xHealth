package com.kaurihealth.mvplib.main_p;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface IMessageView extends IMvpView {

    void loadingIndicator(boolean flag);

    void AllConversationsError(String message);

    void AllConversationsSuccess(List<AVIMConversation> list);
}
