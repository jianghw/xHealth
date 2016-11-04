package com.kaurihealth.chatlib.cache;


import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;

import java.util.List;

/**
 * Created by wli on 16/2/2.
 */
public interface LCChatProfilesCallBack {
  void done(List<ContactUserDisplayBean> userList, Exception exception);
}
