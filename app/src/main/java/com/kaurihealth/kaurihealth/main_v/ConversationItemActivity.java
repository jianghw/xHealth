package com.kaurihealth.kaurihealth.main_v;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.chatlib.custom.LCChatMessageInterface;
import com.kaurihealth.chatlib.event.LCIMConversationItemLongClickEvent;
import com.kaurihealth.chatlib.event.LCIMIMTypeMessageEvent;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.request_bean.bean.ConversationItemBean;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.ConversationItemSwipeAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnItemClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by jianghw on 2016/11/2.
 * <p/>
 * Describe: 医生或者患者 历史消息
 */

public class ConversationItemActivity extends BaseActivity {

    @Bind(R.id.rv_conversation)
    ListView mRvConversation;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    protected ConversationItemSwipeAdapter itemAdapter;
    private int mUserType;
    List<ConversationItemBean<ContactUserDisplayBean>> beanList = new ArrayList<>();

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_conversation_items;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        Bundle bundle = getBundle();

        if (bundle != null) {
            String titleType = bundle.getString(Global.Bundle.CONVER_ITEM_BUNDLE);
            initNewBackBtn(titleType.equals(Global.Bundle.CONVER_ITEM_DOCTOR) ? "医生消息" : "患者消息");
            mUserType = titleType.equals(Global.Bundle.CONVER_ITEM_DOCTOR) ? 0 : 1;
        }

        itemAdapter = new ConversationItemSwipeAdapter(getApplication(), beanList);
        mRvConversation.setAdapter(itemAdapter);
    }

    @Override
    protected void initDelayedData() {
        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplicationContext())
        );
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContactListByDoctorIdSucceed();
    }

    @OnItemClick(R.id.rv_conversation)
    public void onItemClick(int position) {
        String conversationId = beanList.get(position).getConversationId();
        AVIMClient client = LCChatKit.getInstance().getClient();
        AVIMConversation conversation = client.getConversation(conversationId);
        try {
            Intent intent = new Intent();
            intent.setPackage(getApplication().getApplicationContext().getPackageName());
            intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(LCIMConstants.CONVERSATION_ID, conversation.getConversationId());
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
        }
    }

    /**
     * 收到对方消息时响应此事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMIMTypeMessageEvent event) {
        loadContactListByDoctorIdSucceed();
    }

    /**
     * 删除会话列表中的某个 item
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMConversationItemLongClickEvent event) {
        if (null != event.conversation) {
            String conversationId = event.conversation.getConversationId();
            LCIMConversationItemCache.getInstance().deleteConversation(conversationId);
            loadContactListByDoctorIdSucceed();
        }
    }

    private void loadContactListByDoctorIdSucceed() {
        onNeedToRefreshUserMap();
    }

    /**
     * 是否需要刷新userbean缓存数据
     * 1、ConversationId-->kauriHearthId
     * 2、缓存是否包含当前kauriHearthId
     * 3、刷新缓存
     */
    private void onNeedToRefreshUserMap() {
        AVIMClient client = LCChatKit.getInstance().getClient();
        String currentUserId = LCChatKit.getInstance().getCurrentUserId();
        List<String> conIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();
        Observable.from(conIdList)
                .map(new Func1<String, List<String>>() {
                    @Override
                    public List<String> call(String convId) {
                        return client.getConversation(convId).getMembers();
                    }
                })
                .filter(new Func1<List<String>, Boolean>() {
                    @Override
                    public Boolean call(List<String> strings) {
                        return strings != null && strings.size() > 0;
                    }
                })
                .map(new Func1<List<String>, String>() {
                    @Override
                    public String call(List<String> strings) {
                        String firstMemeberId = strings.get(0);
                        return strings.get(firstMemeberId.equals(currentUserId) ? 1 : 0);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String kauriId) {
                        return !LCIMProfileCache.getInstance().getContactUserMap().containsKey(kauriId);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                               @Override
                               public void call(List<String> strings) {
                                   LogUtils.e("------------------" + strings.size());
                                   if (strings != null && strings.size() > 0) {
                                       LCIMProfileCache.getInstance().getCachedUser(strings.get(0), new AVCallback<ContactUserDisplayBean>() {
                                           @Override
                                           protected void internalDone0(ContactUserDisplayBean bean, AVException e) {
                                               if (bean != null) filterByTitleTypeMessage();
                                           }
                                       });
                                   } else {
                                       filterByTitleTypeMessage();
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                showToast(throwable.getMessage());
                            }
                        });
    }

    /**
     * 根据类型过滤数据type
     */
    private void filterByTitleTypeMessage() {
        AVIMClient client = LCChatKit.getInstance().getClient();
        String currentUserId = LCChatKit.getInstance().getCurrentUserId();
        List<String> conIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();
        Observable.from(conIdList)
                .flatMap(new Func1<String, Observable<ArrayMap<String, ContactUserDisplayBean>>>() {
                    @Override
                    public Observable<ArrayMap<String, ContactUserDisplayBean>> call(String conversationId) {
                        return Observable.just(conversationId)
                                .map(new Func1<String, List<String>>() {
                                    @Override
                                    public List<String> call(String convId) {
                                        return client.getConversation(convId).getMembers();
                                    }
                                })
                                .filter(new Func1<List<String>, Boolean>() {
                                    @Override
                                    public Boolean call(List<String> strings) {
                                        LogUtils.jsonDate(strings);
                                        return strings != null && strings.size() > 0;
                                    }
                                })
                                .map(new Func1<List<String>, String>() {
                                    @Override
                                    public String call(List<String> strings) {
                                        String firstMemeberId = strings.get(0);
                                        return strings.get(firstMemeberId.equals(currentUserId) ? 1 : 0);
                                    }
                                })
                                .filter(new Func1<String, Boolean>() {
                                    @Override
                                    public Boolean call(String kauriID) {
                                        return LCIMProfileCache.getInstance().getContactUserMap().containsKey(kauriID);
                                    }
                                })
                                .map(new Func1<String, ContactUserDisplayBean>() {
                                    @Override
                                    public ContactUserDisplayBean call(String id) {
                                        return LCIMProfileCache.getInstance().getContactUserMap().get(id);
                                    }
                                })
                                .filter(new Func1<ContactUserDisplayBean, Boolean>() {
                                    @Override
                                    public Boolean call(ContactUserDisplayBean bean) {
                                        return bean.getUserType() == mUserType;//医生0，患者1
                                    }
                                })
                                .map(new Func1<ContactUserDisplayBean, ArrayMap<String, ContactUserDisplayBean>>() {
                                    @Override
                                    public ArrayMap<String, ContactUserDisplayBean> call(ContactUserDisplayBean bean) {
                                        ArrayMap<String, ContactUserDisplayBean> doctorMap = new ArrayMap<>();
                                        doctorMap.put(conversationId, bean);
                                        return doctorMap;
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<ArrayMap<String, ContactUserDisplayBean>>() {
                            @Override
                            public void call(ArrayMap<String, ContactUserDisplayBean> map) {
                                LogUtils.jsonDate(map);
                                if (map != null && map.size() > 0) sortProcessMap(map);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                showToast(throwable.getMessage());
                            }
                        });
    }

    /**
     * map类型转-->ConversationItemBean
     */
    private void sortProcessMap(ArrayMap<String, ContactUserDisplayBean> map) {
        AVIMClient client = LCChatKit.getInstance().getClient();
        LogUtils.e("===============" + 123);
        Observable<ConversationItemBean<ContactUserDisplayBean>> adsdx = Observable.from(map.entrySet())
                .map(new Func1<Map.Entry<String, ContactUserDisplayBean>, AVIMConversation>() {
                    @Override
                    public AVIMConversation call(Map.Entry<String, ContactUserDisplayBean> entry) {
                        return client.getConversation(entry.getKey());
                    }
                })
                .flatMap(new Func1<AVIMConversation, Observable<ConversationItemBean<ContactUserDisplayBean>>>() {
                    @Override
                    public Observable<ConversationItemBean<ContactUserDisplayBean>> call(AVIMConversation conversation) {
                        return Observable.create(
                                new Observable.OnSubscribe<List<AVIMMessage>>() {
                                    @Override
                                    public void call(Subscriber<? super List<AVIMMessage>> subscriber) {
                                        conversation.queryMessages(1, new AVIMMessagesQueryCallback() {
                                            @Override
                                            public void done(List<AVIMMessage> list, AVIMException e) {
                                                LogUtils.e("=========" + list.size());
                                                subscriber.onNext(list);
                                            }
                                        });
                                    }
                                })
                                .filter(new Func1<List<AVIMMessage>, Boolean>() {
                                    @Override
                                    public Boolean call(List<AVIMMessage> messages) {
                                        return null != messages && !messages.isEmpty();
                                    }
                                })
                                .map(new Func1<List<AVIMMessage>, ConversationItemBean<ContactUserDisplayBean>>() {
                                    @Override
                                    public ConversationItemBean<ContactUserDisplayBean> call(List<AVIMMessage> messages) {
                                        long time = messages.get(0).getTimestamp();
                                        CharSequence lastMessage = getMessageShorthand(getApplicationContext(), messages.get(0));
                                        int num = LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
                                        LogUtils.e("" + time);
                                        LogUtils.e("" + lastMessage);
                                        LogUtils.e("" + num);
                                        return new ConversationItemBean<ContactUserDisplayBean>(map.get(conversation.getConversationId()), time, lastMessage, num, conversation.getConversationId());
                                    }
                                });
                    }
                });
        adsdx.toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<ConversationItemBean<ContactUserDisplayBean>>>() {
                            @Override
                            public void call(List<ConversationItemBean<ContactUserDisplayBean>> been) {
                                LogUtils.jsonDate(been);
                                sortProcessList(been);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                showToast(throwable.getMessage());
                            }
                        });


//        Observable.from(map.entrySet())
//                .flatMap(new Func1<Map.Entry<String, ContactUserDisplayBean>, Observable<List<ConversationItemBean<ContactUserDisplayBean>>>>() {
//                    @Override
//                    public Observable<List<ConversationItemBean<ContactUserDisplayBean>>> call(Map.Entry<String, ContactUserDisplayBean> entry) {
//                        return Observable.just(entry)
//                                .map(new Func1<Map.Entry<String, ContactUserDisplayBean>, AVIMConversation>() {
//                                    @Override
//                                    public AVIMConversation call(Map.Entry<String, ContactUserDisplayBean> entry) {
//                                        LogUtils.e("=========" + entry.getKey());
//                                        return client.getConversation(entry.getKey());
//                                    }
//                                })
//                                .flatMap(new Func1<AVIMConversation, Observable<ConversationItemBean<ContactUserDisplayBean>>>() {
//                                    @Override
//                                    public Observable<ConversationItemBean<ContactUserDisplayBean>> call(AVIMConversation conversation) {
//                                        return Observable.create(
//                                                new Observable.OnSubscribe<List<AVIMMessage>>() {
//                                                    @Override
//                                                    public void call(Subscriber<? super List<AVIMMessage>> subscriber) {
//                                                        conversation.queryMessages(1, new AVIMMessagesQueryCallback() {
//                                                            @Override
//                                                            public void done(List<AVIMMessage> list, AVIMException e) {
//                                                                LogUtils.e("=========" + list.size());
//                                                                subscriber.onNext(list);
//                                                            }
//                                                        });
//                                                    }
//                                                })
//                                                .filter(new Func1<List<AVIMMessage>, Boolean>() {
//                                                    @Override
//                                                    public Boolean call(List<AVIMMessage> messages) {
//                                                        return null != messages && !messages.isEmpty();
//                                                    }
//                                                })
//                                                .map(new Func1<List<AVIMMessage>, ConversationItemBean<ContactUserDisplayBean>>() {
//                                                    @Override
//                                                    public ConversationItemBean<ContactUserDisplayBean> call(List<AVIMMessage> messages) {
//                                                        long time = messages.get(0).getTimestamp();
//                                                        CharSequence lastMessage = getMessageShorthand(getApplicationContext(), messages.get(0));
//                                                        int num = LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
//                                                        LogUtils.e("" + time);
//                                                        LogUtils.e("" + lastMessage);
//                                                        LogUtils.e("" + num);
//                                                        return new ConversationItemBean<ContactUserDisplayBean>(entry.getValue(), time, lastMessage, num, entry.getKey());
//                                                    }
//                                                });
//                                    }
//                                })
//                                .toList();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        new Action1<List<ConversationItemBean<ContactUserDisplayBean>>>() {
//                            @Override
//                            public void call(List<ConversationItemBean<ContactUserDisplayBean>> beanList) {
//                                LogUtils.jsonDate(beanList);
//                                sortProcessList(beanList);
//                            }
//                        },
//                        new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                showToast(throwable.getMessage());
//                            }
//                        });


//        for (Map.Entry<String, ContactUserDisplayBean> entry : map.entrySet()) {
//            AVIMConversation conversation = client.getConversation(entry.getKey());
//            ContactUserDisplayBean userDisplayBean = entry.getValue();
//            conversation.queryMessages(1, new AVIMMessagesQueryCallback() {
//                @Override
//                public void done(List<AVIMMessage> list, AVIMException e) {
//                    if (null != e) {
//                        LCIMLogUtils.logException(e);
//                    }
//                    LogUtils.e("===============" + list.size());
//                    if (null != list && !list.isEmpty()) {
//                        long time = list.get(0).getTimestamp();
//                        CharSequence lastMessage = getMessageShorthand(getApplicationContext(), list.get(0));
//                        int num = LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
//                        beanList.add(new ConversationItemBean<ContactUserDisplayBean>(userDisplayBean, time, lastMessage, num, entry.getKey()));
//                    }
//                }
//            });
//        }
        LogUtils.e("===============" + 456);
    }

    /**
     * 时间排序
     */
    private void sortProcessList(List<ConversationItemBean<ContactUserDisplayBean>> list) {
        Observable.from(list)
                .filter(new Func1<ConversationItemBean<ContactUserDisplayBean>, Boolean>() {
                    @Override
                    public Boolean call(ConversationItemBean<ContactUserDisplayBean> bean) {
                        return bean != null;
                    }
                })
                .filter(new Func1<ConversationItemBean<ContactUserDisplayBean>, Boolean>() {
                    @Override
                    public Boolean call(ConversationItemBean<ContactUserDisplayBean> bean) {
                        return bean.getBean().isTop();
                    }
                })
                .toSortedList(new Func2<ConversationItemBean<ContactUserDisplayBean>, ConversationItemBean<ContactUserDisplayBean>, Integer>() {
                    @Override
                    public Integer call(ConversationItemBean<ContactUserDisplayBean> bean, ConversationItemBean<ContactUserDisplayBean> bean2) {
                        long mLong = bean.getTime() - bean2.getTime();
                        return mLong < 0 ? 1 : mLong > 0 ? -1 : 0;
                    }
                })
                .flatMap(new Func1<List<ConversationItemBean<ContactUserDisplayBean>>, Observable<List<ConversationItemBean<ContactUserDisplayBean>>>>() {
                    @Override
                    public Observable<List<ConversationItemBean<ContactUserDisplayBean>>> call(List<ConversationItemBean<ContactUserDisplayBean>> beenList) {
                        return Observable.from(list)
                                .filter(new Func1<ConversationItemBean<ContactUserDisplayBean>, Boolean>() {
                                    @Override
                                    public Boolean call(ConversationItemBean<ContactUserDisplayBean> bean) {
                                        return !bean.getBean().isTop();
                                    }
                                })
                                .toSortedList(new Func2<ConversationItemBean<ContactUserDisplayBean>, ConversationItemBean<ContactUserDisplayBean>, Integer>() {
                                    @Override
                                    public Integer call(ConversationItemBean<ContactUserDisplayBean> bean, ConversationItemBean<ContactUserDisplayBean> bean2) {
                                        long mLong = bean.getTime() - bean2.getTime();
                                        return mLong < 0 ? 1 : mLong > 0 ? -1 : 0;
                                    }
                                })
                                .map(new Func1<List<ConversationItemBean<ContactUserDisplayBean>>, List<ConversationItemBean<ContactUserDisplayBean>>>() {
                                    @Override
                                    public List<ConversationItemBean<ContactUserDisplayBean>> call(List<ConversationItemBean<ContactUserDisplayBean>> been) {
                                        beenList.addAll(beenList.size(), been);
                                        return beenList;
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<ConversationItemBean<ContactUserDisplayBean>>>() {
                            @Override
                            public void call(List<ConversationItemBean<ContactUserDisplayBean>> been) {
                                if (!beanList.isEmpty()) beanList.clear();
                                beanList.addAll(been);
                                itemAdapter.notifyDataSetChanged();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                showToast(throwable.getMessage());
                            }
                        });
    }

    private static CharSequence getMessageShorthand(Context context, AVIMMessage message) {
        if (message instanceof AVIMTypedMessage) {
            AVIMReservedMessageType type = AVIMReservedMessageType.getAVIMReservedMessageType(
                    ((AVIMTypedMessage) message).getMessageType());
            switch (type) {
                case TextMessageType:
                    return ((AVIMTextMessage) message).getText();
                case ImageMessageType:
                    return context.getString(com.kaurihealth.chatlib.R.string.lcim_message_shorthand_image);
                case LocationMessageType:
                    return context.getString(com.kaurihealth.chatlib.R.string.lcim_message_shorthand_location);
                case AudioMessageType:
                    return context.getString(com.kaurihealth.chatlib.R.string.lcim_message_shorthand_audio);
                default:
                    CharSequence shortHand = "";
                    if (message instanceof LCChatMessageInterface) {
                        LCChatMessageInterface messageInterface = (LCChatMessageInterface) message;
                        shortHand = messageInterface.getShorthand();
                    }
                    if (TextUtils.isEmpty(shortHand)) {
                        shortHand = context.getString(com.kaurihealth.chatlib.R.string.lcim_message_shorthand_unknown);
                    }
                    return shortHand;
            }
        } else {
            return message.getContent();
        }
    }
}
