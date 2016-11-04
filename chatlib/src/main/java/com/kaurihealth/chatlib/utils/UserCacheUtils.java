package com.kaurihealth.chatlib.utils;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.kaurihealth.datalib.remote.RetrofitFactory;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.service.IContacListService;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wli on 15/9/30.
 * TODO
 * 1、本地存储
 * 2、避免内存、外存占用过多
 */
public class UserCacheUtils {
    private static ArrayMap<String, ContactUserDisplayBean> userMap;

    static {
        userMap = new ArrayMap<>();
    }

    public static ContactUserDisplayBean getCachedUser(String objectId) {
        return userMap.get(objectId);
    }

    public static boolean hasCachedUser(String objectId) {
        return userMap.containsKey(objectId);
    }

    public static void cacheUsers(List<ContactUserDisplayBean> users) {
        if (null != users) {
            for (ContactUserDisplayBean user : users) {
                cacheUser(user);
            }
        }
    }

    public static void cacheUser(ContactUserDisplayBean user) {
        if (null != user && !TextUtils.isEmpty(user.getKauriHealthId())) {
            userMap.put(user.getKauriHealthId(), user);
        }
    }

    public static void fetchUsers(List<String> ids) {
        fetchUsers(ids, null);
    }

    public static void fetchUsers(final List<String> ids, final CacheUserCallback cacheUserCallback) {
        Set<String> uncachedIds = new HashSet<>();
        for (String id : ids) {
            if (!userMap.containsKey(id)) {
                uncachedIds.add(id);
            }
        }

        if (uncachedIds.isEmpty()) {//缓存中都有时,直接返回
            if (null != cacheUserCallback) {
                cacheUserCallback.done(getUsersFromCache(ids), null);
                return;
            }
        }
        LogUtils.e("RetrofitFactory=============="+1);
//网络获取关系列表
        RetrofitFactory.getInstance().createRetrofit(Global.Factory.DEFAULT_TOKEN)
                .create(IContacListService.class)
                .loadContactListByDoctorId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<ContactUserDisplayBean>>() {
                            @Override
                            public void call(List<ContactUserDisplayBean> been) {
                                for (ContactUserDisplayBean userDisplayBean : been) {
                                    userMap.put(userDisplayBean.getKauriHealthId(), userDisplayBean);
                                }
                                if (null != cacheUserCallback) {
                                    cacheUserCallback.done(getUsersFromCache(ids), null);
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                cacheUserCallback.done(null, new Exception(throwable));
                            }
                        });

//        List<ContactUserDisplayBean> queryAll = LocalData.getLocalData().getQueryAll(ContactUserDisplayBean.class);
//        if (queryAll != null && queryAll.size() > 0) {
//            for (ContactUserDisplayBean userDisplayBean : queryAll) {
//                userMap.put(userDisplayBean.getKauriHealthId(), userDisplayBean);
//            }
//        }

//        AVQuery<ContactUserDisplayBean> q = ContactUserDisplayBean.getQuery(ContactUserDisplayBean.class);
//        q.whereContainedIn("objectId", uncachedIds);
//        q.setLimit(1000);
//        q.setCachePolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
//        q.findInBackground(new FindCallback<ContactUserDisplayBean>() {
//            @Override
//            public void done(List<ContactUserDisplayBean> list, AVException e) {
//                if (null == e) {
//                    for (ContactUserDisplayBean user : list) {
//                        userMap.put(user.getObjectId(), user);
//                    }
//                }
//                if (null != cacheUserCallback) {
//                    cacheUserCallback.done(getUsersFromCache(ids), e);
//                }
//            }
//        });
    }

    /**
     * 从内存map中获取
     *
     * @param ids
     * @return
     */
    private static List<ContactUserDisplayBean> getUsersFromCache(List<String> ids) {
        List<ContactUserDisplayBean> userList = new ArrayList<>();
        for (String id : ids) {
            if (userMap.containsKey(id)) {
                userList.add(userMap.get(id));
            }
        }
        return userList;
    }

    public static abstract class CacheUserCallback {
        public abstract void done(List<ContactUserDisplayBean> userList, Exception e);
    }
}
