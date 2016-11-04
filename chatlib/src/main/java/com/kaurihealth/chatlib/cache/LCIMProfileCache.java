package com.kaurihealth.chatlib.cache;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.utilslib.CheckUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by wli on 16/2/25.
 * 用户信息缓存
 * 流程：
 * 1、如果内存中有则从内存中获取
 * 2、如果内存中没有则从 db 中获取
 * 3、如果 db 中没有则通过调用开发者设置的回调 LCChatProfileProvider.fetchProfiles 来获取
 * 同时获取到的数据会缓存到内存与 db
 */
public class LCIMProfileCache {

    private static final String USER_NAME = "user_name";
    private static final String USER_AVATAR = "user_avatar";
    private static final String USER_ID = "user_id";
    private static final String USER_TYPE = "user_type";
    private static final String USER_GENDER = "user_gender";
    private static final String USER_DATA = "user_data";
    private static final String USER_TOP = "user_top";

    private ArrayMap<String, ContactUserDisplayBean> contactUserMap;//缓存好友列表
    private LCIMLocalStorage profileDBHelper;

    private LCIMProfileCache() {
        contactUserMap = new ArrayMap<>();
    }

    public static LCIMProfileCache getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final LCIMProfileCache INSTANCE = new LCIMProfileCache();
    }

    public ArrayMap<String, ContactUserDisplayBean> getContactUserMap() {
        return contactUserMap;
    }

    /**
     * 因为只有在第一次的时候需要设置 Context 以及 clientId，所以单独拎出一个函数主动调用初始化
     * 避免 getInstance 传入过多参数
     *
     * @param clientId 用户kauriHealthId
     */
    public synchronized void initDB(Context context, String clientId) {
        profileDBHelper = new LCIMLocalStorage(context, clientId, "ProfileCache");
    }

    /**
     * 根据 id 获取用户信息  list-->get(0)
     * 先从缓存中获取，若没有再调用用户回调获取
     *
     * @param id       发消息的人的krihid 他人
     * @param callback
     */
    public synchronized void getCachedUser(final String id, final AVCallback<ContactUserDisplayBean> callback) {
        getCachedUsers(Arrays.asList(id), new AVCallback<List<ContactUserDisplayBean>>() {
            @Override
            protected void internalDone0(List<ContactUserDisplayBean> lcimUserProfiles, AVException e) {
                ContactUserDisplayBean LCChatKitUser = (null != lcimUserProfiles && !lcimUserProfiles.isEmpty() ? lcimUserProfiles.get(0) : null);
                callback.internalDone(LCChatKitUser, e);
            }
        });
    }

    /**
     * 获取多个用户的信息
     * 先从缓存中获取，若没有再调用用户回调获取
     *
     * @param idList
     * @param callback
     */
    public synchronized void getCachedUsers(List<String> idList, final AVCallback<List<ContactUserDisplayBean>> callback) {
        if (null != callback) {
            if (null == idList || idList.isEmpty()) {
                callback.internalDone(null, new AVException(new Throwable("idList is empty!")));
            } else {
                final List<ContactUserDisplayBean> cachedList = new ArrayList<>();
                final List<String> unCachedIdList = new ArrayList<>();
//添加对话人的id
                for (String id : idList) {
                    if (contactUserMap.containsKey(id)) {//userMap中的表明已经存数据库
                        cachedList.add(contactUserMap.get(id));
                    } else {
                        unCachedIdList.add(id);//没有缓存的Id列表
                    }
                }

                if (unCachedIdList.isEmpty()) {
                    callback.internalDone(cachedList, null);
                } else if (null != profileDBHelper) {
                    //查询数据库
                    profileDBHelper.getData(idList, new AVCallback<List<String>>() {
                        @Override
                        protected void internalDone0(List<String> strings, AVException e) {
                            if (null != strings && !strings.isEmpty() && strings.size() == unCachedIdList.size()) {
                                List<ContactUserDisplayBean> profileList = new ArrayList<>();
                                for (String data : strings) {
                                    ContactUserDisplayBean userProfile = getUserProfileFromJson(data);
                                    contactUserMap.put(userProfile.getKauriHealthId(), userProfile);
                                    profileList.add(userProfile);
                                }
                                callback.internalDone(profileList, null);
                            } else {
                                getProfilesFromProvider(unCachedIdList, cachedList, callback);
                            }
                        }
                    });
                } else {
                    getProfilesFromProvider(unCachedIdList, cachedList, callback);
                }
            }
        }
    }

    /**
     * 根据没有缓存的id 更新列表
     */
    private void getProfilesFromProvider(List<String> idList, final List<ContactUserDisplayBean> profileList, final AVCallback<List<ContactUserDisplayBean>> callback) {
        LCChatProfileProvider profileProvider = LCChatKit.getInstance().getProfileProvider();
        if (null != profileProvider) {
            profileProvider.fetchProfiles(idList, new LCChatProfilesCallBack() {
                @Override
                public void done(List<ContactUserDisplayBean> userList, Exception e) {
                    if (null != userList) {
                        for (ContactUserDisplayBean userProfile : userList) {
                            cacheUser(userProfile);
                        }
                    }
                    profileList.addAll(userList);
                    callback.internalDone(profileList, null != e ? new AVException(e) : null);
                }
            });
        } else {
            callback.internalDone(null, new AVException(new Throwable("please setProfileProvider first!")));
        }
    }

    /**
     * 根据 id 获取用户名
     */
    public void getUserName(String id, final AVCallback<String> callback) {
        getCachedUser(id, new AVCallback<ContactUserDisplayBean>() {
            @Override
            protected void internalDone0(ContactUserDisplayBean userProfile, AVException e) {
                String userName = (null != userProfile ? userProfile.getFullName() : null);
                callback.internalDone(userName, e);
            }
        });
    }

    /**
     * 根据 id 获取bean
     */
    public void getUserBean(String id, final AVCallback<ContactUserDisplayBean> callback) {
        getCachedUser(id, new AVCallback<ContactUserDisplayBean>() {
            @Override
            protected void internalDone0(ContactUserDisplayBean userProfile, AVException e) {
                String userName = (null != userProfile ? userProfile.getFullName() : null);
                callback.internalDone(userProfile, e);
            }
        });
    }

    /**
     * 根据 id 获取用户头像
     */
    public void getUserAvatar(String id, final AVCallback<String> callback) {
        getCachedUser(id, new AVCallback<ContactUserDisplayBean>() {
            @Override
            protected void internalDone0(ContactUserDisplayBean userProfile, AVException e) {
                if (userProfile == null) return;
                String avatarUrl = (CheckUtils.checkUrlNotNull(userProfile.getAvatar()) ? userProfile.getAvatar() : null);
                callback.internalDone(avatarUrl, e);
            }
        });
    }

    /**
     * 内存中是否包相关 LCChatKitUser 的信息
     *
     * @param id
     * @return
     */
    public synchronized boolean hasCachedUser(String id) {
        return contactUserMap.containsKey(id);
    }

    /**
     * 缓存 LCChatKitUser 信息，更新缓存同时也更新 db
     * 如果开发者 LCChatKitUser 信息变化，可以通过调用此方法刷新缓存
     *
     * @param userProfile
     */
    public synchronized void cacheUser(ContactUserDisplayBean userProfile) {
        if (null != userProfile && null != profileDBHelper) {
            contactUserMap.put(userProfile.getKauriHealthId(), userProfile);
            profileDBHelper.insertData(userProfile.getKauriHealthId(), getStringFormUserProfile(userProfile));
        }
    }

    /**
     * 从 db 中的 String 解析出 ContactUserDisplayBean
     */
    private ContactUserDisplayBean getUserProfileFromJson(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        String userId = jsonObject.getString(USER_ID);
        String userName = jsonObject.getString(USER_NAME);
        String userAvatar = jsonObject.getString(USER_AVATAR);
        int userType = jsonObject.getInteger(USER_TYPE);
        String gender = jsonObject.getString(USER_GENDER);
        Date dateOfBirth = jsonObject.getDate(USER_DATA);
        boolean istop = jsonObject.getBoolean(USER_TOP);
        return new ContactUserDisplayBean(userId, gender, userType, userAvatar,userName, dateOfBirth, istop);
    }

    /**
     * ContactUserDisplayBean 转换成 json String
     */
    private String getStringFormUserProfile(ContactUserDisplayBean userProfile) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(USER_NAME, userProfile.getFullName());
        jsonObject.put(USER_AVATAR, userProfile.getAvatar());
        jsonObject.put(USER_ID, userProfile.getKauriHealthId());
        jsonObject.put(USER_GENDER, userProfile.getGender());
        jsonObject.put(USER_TYPE, userProfile.getUserType());
        jsonObject.put(USER_DATA, userProfile.getDateOfBirth());
        jsonObject.put(USER_TOP, userProfile.isTop());
        return jsonObject.toJSONString();
    }
}
