package com.example.chatlibrary;

import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.example.chatlibrary.constant.LeanCloud;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by jianghw on 2016/8/19.
 * <p/>
 * 描述：创建实时聊天工厂类
 */
public class ChatFactory {

    private AVIMClient avimClient;
    private String mAccountID;

    private static class SingletonHolder {
        private static final ChatFactory INSTANCE = new ChatFactory();
    }

    public static ChatFactory instance() {
        return SingletonHolder.INSTANCE;
    }

    public ChatFactory() {
    }

    /**
     * 初始化LeanCloud
     *
     * @param context
     */
    public static void init(Context context, String environment) {
        switch (environment) {
            case "Develop":
            case "Test":
                AVOSCloud.initialize(context, LeanCloud.Config.APP_ID_DEBUG, LeanCloud.Config.APP_KEY_DEBUG);
                break;
            case "Preview":
                AVOSCloud.initialize(context, LeanCloud.Config.APP_ID_PREVIEW, LeanCloud.Config.APP_KEY_PREVIEW);
                break;
            default:
                AVOSCloud.initialize(context, LeanCloud.Config.APP_ID_PREVIEW, LeanCloud.Config.APP_KEY_PREVIEW);
                break;
        }
    }

    public void createAVIMClient(String accountID, AVIMClientCallback avimClientCallback) {
        mAccountID = accountID;
        avimClient = AVIMClient.getInstance(accountID);
        avimClient.open(avimClientCallback);
    }

    public AVIMClient getClient() {
        if (avimClient == null)
            throw new IllegalStateException("Please call AVImClientManager.open first");
        return avimClient;
    }

    public String getClientId() {
        if (TextUtils.isEmpty(mAccountID)) {
            throw new IllegalStateException("Please call AVImClientManager.open first");
        }
        return mAccountID;
    }

    public <T> void createObservable(Observable<T> observable) {
        Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                getAllConversation(subscriber);
            }
        });
    }

    /**
     * 获得所有的对话
     * 通过 AVIMClient.getQuery() 这样得到的 AVIMConversationQuery 实例默认是查询全部对话的
     *
     * @param subscriber
     * @param <T>
     */
    public <T> void getAllConversation(final Subscriber<T> subscriber) {
        AVIMConversationQuery conversationQuery = getClient().getQuery();
        conversationQuery.setQueryPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        conversationQuery.limit(1000);
        conversationQuery.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) {
                    subscriber.onNext((T) list);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(e.getCause());
                }
            }
        });
    }
}
