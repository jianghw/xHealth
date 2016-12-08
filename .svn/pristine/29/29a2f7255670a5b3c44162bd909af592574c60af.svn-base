package com.kaurihealth.chatlib.cache;

import com.kaurihealth.chatlib.utils.UserCacheUtils;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wli on 15/12/4.
 * 用户体系的接口，开发者需要实现此接口来接入 LCChatKit
 */
public class LeanchatUserProvider implements LCChatProfileProvider {

    @Override
    public void fetchProfiles(List<String> list, final LCChatProfilesCallBack callBack) {
        UserCacheUtils.fetchUsers(list, new UserCacheUtils.CacheUserCallback() {
            @Override
            public void done(List<ContactUserDisplayBean> userList, Exception e) {
                callBack.done(userList, e);
            }
        });
    }

    private static List<LCChatKitUser> getThirdPartUsers(List<ContactUserDisplayBean> contactUserDisplayBeen) {
        List<LCChatKitUser> thirdPartUsers = new ArrayList<>();
        for (ContactUserDisplayBean user : contactUserDisplayBeen) {
            thirdPartUsers.add(getThirdPartUser(user));
        }
        return thirdPartUsers;
    }


    private static LCChatKitUser getThirdPartUser(ContactUserDisplayBean contactUserDisplayBean) {
        return new LCChatKitUser(
                contactUserDisplayBean.getKauriHealthId(),
                contactUserDisplayBean.getFullName(),
                contactUserDisplayBean.getAvatar()
        );
    }
}
