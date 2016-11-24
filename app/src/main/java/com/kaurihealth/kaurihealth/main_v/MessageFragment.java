package com.kaurihealth.kaurihealth.main_v;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.cache.GroupMessageBean;
import com.kaurihealth.chatlib.cache.LCIMConversationItem;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.chatlib.cache.RelationMessageBean;
import com.kaurihealth.chatlib.custom.LCChatMessageInterface;
import com.kaurihealth.chatlib.event.LCIMIMTypeMessageEvent;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.NotifyIsReadDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.conversation_v.ConversationGroupItemActivity;
import com.kaurihealth.kaurihealth.conversation_v.ConversationItemActivity;
import com.kaurihealth.kaurihealth.conversation_v.SystemMessageActivity;
import com.kaurihealth.mvplib.main_p.IMessageView;
import com.kaurihealth.mvplib.main_p.MessagePresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

/**
 * 消息  母页
 * 描述: 消息 Fragment
 */
public class MessageFragment extends BaseFragment implements IMessageView {
    @Inject
    MessagePresenter<IMessageView> mPresenter;

    @Bind(R.id.iv_head_patient)
    ImageView mIvHeadPatient;
    @Bind(R.id.tv_name_patient)
    TextView mTvNamePatient;
    @Bind(R.id.tv_time_patient)
    TextView mTvTimePatient;
    @Bind(R.id.img_unread_patient)
    ImageView mImgUnreadPatient;
    @Bind(R.id.tv_message_patient)
    TextView mTvMessagePatient;
    @Bind(R.id.iv_head_doctor)
    ImageView mIvHeadDoctor;
    @Bind(R.id.tv_name_doctor)
    TextView mTvNameDoctor;
    @Bind(R.id.tv_time_doctor)
    TextView mTvTimeDoctor;
    @Bind(R.id.img_unread_doctor)
    ImageView mImgUnreadDoctor;
    @Bind(R.id.tv_message_doctor)
    TextView mTvMessageDoctor;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    @Bind(R.id.rl_patient)
    RelativeLayout mRlPatient;
    @Bind(R.id.rl_doctor)
    RelativeLayout mRlDoctor;

    @Bind(R.id.tv_time_system)
    TextView mTvTimeSystem;
    @Bind(R.id.img_unread_system)
    ImageView mImgUnreadSystem;
    @Bind(R.id.tv_message_system)
    TextView mTvMessageSystem;
    @Bind(R.id.rl_system)
    RelativeLayout mRlSystem;

    @Bind(R.id.iv_head_group)
    ImageView mIvHeadGroup;
    @Bind(R.id.tv_time_group)
    TextView mTvTimeGroup;
    @Bind(R.id.img_unread_group)
    ImageView mImgUnreadGroup;
    @Bind(R.id.tv_message_group)
    TextView mTvMessageGroup;
    @Bind(R.id.rl_group)
    RelativeLayout mRlGroup;


    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    static class SwipeRefreshHandler extends Handler {
        private WeakReference<MessageFragment> weakReference = null;

        SwipeRefreshHandler(MessageFragment fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MessageFragment fragment = weakReference.get();
            if (null == weakReference || null == fragment) return;
            fragment.loadingIndicator(false);
        }
    }

    @Override
    protected void initDelayedData() {
        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getContext())
        );
        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(this::loadConversationItemList);
        noMessage();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadConversationItemList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.rl_patient, R.id.rl_doctor})
    public void onClickItem(RelativeLayout relativeLayout) {
        Bundle bundle = new Bundle();
        switch (relativeLayout.getId()) {
            case R.id.rl_patient:
                bundle.putString(Global.Bundle.CONVER_ITEM_BUNDLE, Global.Bundle.CONVER_ITEM_PATIENT);
                break;
            case R.id.rl_doctor:
                bundle.putString(Global.Bundle.CONVER_ITEM_BUNDLE, Global.Bundle.CONVER_ITEM_DOCTOR);
                break;
            default:
                break;
        }
        skipToBundle(ConversationItemActivity.class, bundle);
    }

    @OnClick(R.id.rl_system)
    public void systemMessageOnClick() {
        switchPageUI(Global.Jump.SystemMessageActivity);
    }

    @OnClick(R.id.rl_group)
    public void groupMessageOnClick() {
        switchPageUI(Global.Jump.ConversationGroupItemActivity);
    }

    /**
     * 收到对方消息时响应此事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMIMTypeMessageEvent event) {
        loadConversationItemList();
    }

    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.SystemMessageActivity:
                skipTo(SystemMessageActivity.class);
                break;
            case Global.Jump.ConversationGroupItemActivity:
                skipTo(ConversationGroupItemActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 系统消息显示
     */
    @Override
    public void isReadNotifySucceed(NotifyIsReadDisplayBean bean) {
        mTvMessageSystem.setText(TextUtils.isEmpty(bean.getSubject())
                ? getString(R.string.no_system_message) : bean.getSubject().equals("null")
                ? getString(R.string.no_system_message) : bean.getSubject());
        mImgUnreadSystem.setVisibility(bean.isIsRead() ? View.GONE : View.VISIBLE);
        mTvTimeSystem.setVisibility(bean.isIsRead() ? View.GONE : View.VISIBLE);
    }

    /**
     * 刷新页面
     */
    @Override
    public void loadConversationItemList() {
        loadingIndicator(true);
        List<LCIMConversationItem> conversationItems = LCIMConversationItemCache.getInstance().getConversationList();
        if (conversationItems == null || conversationItems.size() < 1) {
            loadingIndicator(false);
            noMessage();
        } else {
            isUnreadMessages(conversationItems);
            new SwipeRefreshHandler(MessageFragment.this).sendEmptyMessageDelayed(100, 4000);
        }
    }

    /**
     * 1、未读消息
     * 2、conversation
     * 3、离线刷新处理 过滤size() > 0 过滤本人不在的对话
     * 4、type判断
     * 5、数据分发 0--xx 1--群聊
     * 6、groupby 分段处理
     */
    private void isUnreadMessages(List<LCIMConversationItem> conversationItems) {
        List<AVIMConversation> groupList = new LinkedList<>();
        List<AVIMConversation> otherList = new LinkedList<>();
        AVIMClient client = LCChatKit.getInstance().getClient();

        Observable
                .from(conversationItems)
                .filter(new Func1<LCIMConversationItem, Boolean>() {
                    @Override
                    public Boolean call(LCIMConversationItem item) {
                        return item != null;
                    }
                })
                .filter(new Func1<LCIMConversationItem, Boolean>() {
                    @Override
                    public Boolean call(LCIMConversationItem item) {
                        return item.unreadCount != 0;
                    }
                })
                .map(new Func1<LCIMConversationItem, AVIMConversation>() {
                    @Override
                    public AVIMConversation call(LCIMConversationItem item) {
                        return client != null ? client.getConversation(item.conversationId) : null;
                    }
                })
                .filter(new Func1<AVIMConversation, Boolean>() {
                    @Override
                    public Boolean call(AVIMConversation conversation) {
                        return conversation != null;
                    }
                })
                .flatMap(new Func1<AVIMConversation, Observable<AVIMConversation>>() {
                    @Override
                    public Observable<AVIMConversation> call(AVIMConversation conversation) {
                        return getConversationObservable(conversation);
                    }
                })
                .filter(new Func1<AVIMConversation, Boolean>() {
                    @Override
                    public Boolean call(AVIMConversation conversation) {
                        return conversation.getMembers() != null && conversation.getMembers().size() > 0;
                    }
                })
                .filter(new Func1<AVIMConversation, Boolean>() {
                    @Override
                    public Boolean call(AVIMConversation conversation) {
                        return conversation.getMembers().contains(LocalData.getLocalData().getMyself().getKauriHealthId());
                    }
                })
                .flatMap(new Func1<AVIMConversation, Observable<GroupedObservable<Integer, AVIMConversation>>>() {
                    @Override
                    public Observable<GroupedObservable<Integer, AVIMConversation>> call(AVIMConversation conversation) {
                        return obtainDistributionDataManually(conversation);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupedObservable<Integer, AVIMConversation>>() {
                    @Override
                    public void onCompleted() {
                        treatmentGroupChatUnreadMessages(groupList);
                        treatmentOtherChatUnreadMessages(otherList);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }

                    @Override
                    public void onNext(GroupedObservable<Integer, AVIMConversation> groupedObservable) {
                        groupedObservable.subscribe(new Action1<AVIMConversation>() {
                            @Override
                            public void call(AVIMConversation conversation) {
                                if (groupedObservable.getKey() == 1) {
                                    groupList.add(conversation);
                                } else {
                                    otherList.add(conversation);
                                }
                            }
                        });
                    }
                });
    }

    /**
     * 刷新conversation
     *
     * @param conversation
     * @return
     */
    @NonNull
    private Observable<AVIMConversation> getConversationObservable(AVIMConversation conversation) {
        return Observable.create(new Observable.OnSubscribe<AVIMConversation>() {
            @Override
            public void call(Subscriber<? super AVIMConversation> subscriber) {
                if (conversation.getCreatedAt() == null) {
                    conversation.fetchInfoInBackground(new AVIMConversationCallback() {
                        @Override
                        public void done(AVIMException e) {
                            if (e != null) subscriber.onError(e);
                            subscriber.onNext(conversation);
                            subscriber.onCompleted();
                        }
                    });
                } else {
                    try {
                        subscriber.onNext(conversation);
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }

    /**
     * 1、获取_Conversation对象
     * 2、attr解析
     * 3、分组
     *
     * @param conversation
     * @return
     */
    @NonNull
    private Observable<GroupedObservable<Integer, AVIMConversation>> obtainDistributionDataManually(final AVIMConversation conversation) {
        return Observable
                .create(new Observable.OnSubscribe<AVObject>() {
                    @Override
                    public void call(Subscriber<? super AVObject> subscriber) {
                        AVQuery conversationQuery = new AVQuery("_Conversation");
                        AVQuery avQuery = conversationQuery.whereEqualTo("objectId", conversation.getConversationId());
                        avQuery.getFirstInBackground(new GetCallback<AVObject>() {
                            public void done(AVObject object, AVException e) {
                                if (e != null) subscriber.onError(e);
                                subscriber.onNext(object);
                                subscriber.onCompleted();
                            }
                        });
                    }
                })
                .map(new Func1<AVObject, Integer>() {
                    @Override
                    public Integer call(AVObject object) {
                        if (object.get("attr") != null) {
                            JSONObject attr = object.getJSONObject("attr");
                            if (attr != null) {
                                try {
                                    return attr.getInt(Global.LeanCloud.ATTR_TYPE);
                                } catch (Exception e) {
                                    return 0;
                                }
                            } else {
                                return 0;
                            }
                        }
                        return 0;
                    }
                })
                .groupBy(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer;
                    }
                }, new Func1<Integer, AVIMConversation>() {
                    @Override
                    public AVIMConversation call(Integer integer) {
                        return conversation;
                    }
                });
    }

    /**
     * 群聊处理
     * 1、只要一条  fetchInfoInBackground
     * 2、最后一天消息
     */
    private void treatmentGroupChatUnreadMessages(List<AVIMConversation> conversationList) {
        if (conversationList.size() == 0) {
            noGroupMessage();
        } else {
            Observable
                    .from(conversationList)
                    .filter(new Func1<AVIMConversation, Boolean>() {
                        @Override
                        public Boolean call(AVIMConversation conversation) {
                            return conversation.getLastMessageAt() != null;
                        }
                    })
                    .toSortedList(new Func2<AVIMConversation, AVIMConversation, Integer>() {
                        @Override
                        public Integer call(AVIMConversation conversation, AVIMConversation conversation2) {
                            long time = conversation.getLastMessageAt().getTime() - conversation2.getLastMessageAt().getTime();
                            return time > 0 ? -1 : time < 0 ? 1 : 0;//排序
                        }
                    })
                    .flatMap(new Func1<List<AVIMConversation>, Observable<AVIMConversation>>() {
                        @Override
                        public Observable<AVIMConversation> call(List<AVIMConversation> list) {
                            return Observable.from(list);
                        }
                    })
                    .takeFirst(new Func1<AVIMConversation, Boolean>() {
                        @Override
                        public Boolean call(AVIMConversation conversation) {
                            return conversation != null && conversation.getMembers().contains(LocalData.getLocalData().getMyself().getKauriHealthId());
                        }
                    })
                    .flatMap(new Func1<AVIMConversation, Observable<GroupMessageBean>>() {
                        @Override
                        public Observable<GroupMessageBean> call(AVIMConversation conversation) {
                            return getLastMessageObservable(conversation);
                        }
                    })
                    .filter(new Func1<GroupMessageBean, Boolean>() {
                        @Override
                        public Boolean call(GroupMessageBean bean) {
                            return null != bean.getMessages();
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<GroupMessageBean>() {
                        @Override
                        public void onCompleted() {
                            if (conversationList.isEmpty()) conversationList.clear();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            LogUtils.e(throwable.getMessage());
                        }

                        @Override
                        public void onNext(GroupMessageBean groupMessageBean) {
                            long time = groupMessageBean.getMessages().getTimestamp();
                            CharSequence lastMessage = getMessageShorthand(getContext(), groupMessageBean.getMessages());
                            setGroupMessage(groupMessageBean.getName(), lastMessage, time);
                        }
                    });
        }
    }

    /**
     * 查询最后一条消息事件
     */
    @NonNull
    private Observable<GroupMessageBean> getLastMessageObservable(final AVIMConversation conversation) {
        return Observable.create(new Observable.OnSubscribe<GroupMessageBean>() {
            @Override
            public void call(Subscriber<? super GroupMessageBean> subscriber) {
                String groupName = conversation.getName();
                conversation.queryMessages(1, new AVIMMessagesQueryCallback() {
                    @Override
                    public void done(List<AVIMMessage> list, AVIMException e) {
                        if (null != e) {
                            subscriber.onError(e);
                        }
                        if (null != list && !list.isEmpty()) {
                            subscriber.onNext(new GroupMessageBean(groupName, list.get(0)));
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        });
    }

    /**
     * 医患缓存刷新判断
     * 是否需要刷新userbean缓存数据
     * 1、ConversationId-->kauriHearthId
     * 2、缓存是否包含当前kauriHearthId
     * 3、刷新缓存
     * <p>
     * 注意 fetchInfoInBackground 运用
     */
    private void treatmentOtherChatUnreadMessages(List<AVIMConversation> conversationList) {
        if (conversationList.size() == 0) {
            noDoctorMessage();
            noPatientMessage();
        } else {
            String currentUserId = LCChatKit.getInstance().getCurrentUserId();
            Observable
                    .from(conversationList)
                    .filter(new Func1<AVIMConversation, Boolean>() {
                        @Override
                        public Boolean call(AVIMConversation conversation) {
                            return conversation != null;
                        }
                    })
                    .map(new Func1<AVIMConversation, List<String>>() {
                        @Override
                        public List<String> call(AVIMConversation conversation) {
                            return conversation.getMembers();
                        }
                    })
                    .filter(new Func1<List<String>, Boolean>() {
                        @Override
                        public Boolean call(List<String> strings) {
                            return strings != null && strings.size() == 2;
                        }
                    })
                    .map(new Func1<List<String>, String>() {
                        @Override
                        public String call(List<String> strings) {
                            return strings.get(strings.get(0).equals(currentUserId) ? 1 : 0);
                        }
                    })
                    .filter(new Func1<String, Boolean>() {
                        @Override
                        public Boolean call(String kauriId) {
                            return !LCIMProfileCache.getInstance().hasCachedUser(kauriId);
                        }
                    })
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<String>>() {
                        @Override
                        public void onCompleted() {
                            if (conversationList.isEmpty()) conversationList.clear();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            LogUtils.e(throwable.getMessage());
                        }

                        @Override
                        public void onNext(List<String> membersList) {
                            refreshCharacterInformation(membersList, conversationList);
                        }
                    });
        }
    }

    /**
     * 刷新人物信息
     *
     * @param membersList
     * @param conversationList
     */
    private void refreshCharacterInformation(List<String> membersList, final List<AVIMConversation> conversationList) {
        if (membersList != null && membersList.size() > 0) {
            LCIMProfileCache.getInstance().getCachedUsers(membersList, new AVCallback<List<ContactUserDisplayBean>>() {
                @Override
                protected void internalDone0(List<ContactUserDisplayBean> beanList, AVException e) {
                    if (beanList != null) separationProcessing(conversationList);
                    if (e != null) LogUtils.e(e.getMessage());
                }
            });
        } else {
            separationProcessing(conversationList);
        }
    }

    /**
     * 医患分离处理
     */
    private void separationProcessing(List<AVIMConversation> conversationList) {
        String currentUserId = LCChatKit.getInstance().getCurrentUserId();
        List<RelationMessageBean> doctorList = new LinkedList<>();
        List<RelationMessageBean> patientList = new LinkedList<>();
        Observable
                .from(conversationList)
                .filter(new Func1<AVIMConversation, Boolean>() {
                    @Override
                    public Boolean call(AVIMConversation conversation) {
                        return conversation != null;
                    }
                })
                .flatMap(new Func1<AVIMConversation, Observable<GroupedObservable<String, RelationMessageBean>>>() {
                    @Override
                    public Observable<GroupedObservable<String, RelationMessageBean>> call(AVIMConversation conversation) {
                        return Observable
                                .just(conversation)
                                .map(new Func1<AVIMConversation, List<String>>() {
                                    @Override
                                    public List<String> call(AVIMConversation conversation) {
                                        return conversation.getMembers();
                                    }
                                })
                                .filter(new Func1<List<String>, Boolean>() {
                                    @Override
                                    public Boolean call(List<String> strings) {
                                        return strings != null && strings.size() == 2;
                                    }
                                })
                                .map(new Func1<List<String>, String>() {
                                    @Override
                                    public String call(List<String> strings) {
                                        return strings.get(strings.get(0).equals(currentUserId) ? 1 : 0);
                                    }
                                })
                                .filter(new Func1<String, Boolean>() {
                                    @Override
                                    public Boolean call(String kauriId) {
                                        return LCIMProfileCache.getInstance().hasCachedUser(kauriId);
                                    }
                                })
                                .map(new Func1<String, ContactUserDisplayBean>() {
                                    @Override
                                    public ContactUserDisplayBean call(String id) {
                                        return LCIMProfileCache.getInstance().getContactUserMap().get(id);
                                    }
                                })
                                .groupBy(new Func1<ContactUserDisplayBean, String>() {
                                    @Override
                                    public String call(ContactUserDisplayBean bean) {
                                        return bean.getUserType();//医生0，患者1
                                    }
                                }, new Func1<ContactUserDisplayBean, RelationMessageBean>() {
                                    @Override
                                    public RelationMessageBean call(ContactUserDisplayBean bean) {
                                        return new RelationMessageBean(conversation.getConversationId(), bean);
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GroupedObservable<String, RelationMessageBean>>() {
                    @Override
                    public void onCompleted() {
                        segmentedDisplayData(Global.LeanCloud.USER_TYPE_DOCTOR, doctorList);
                        segmentedDisplayData(Global.LeanCloud.USER_TYPE_PATIENT, patientList);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }

                    @Override
                    public void onNext(GroupedObservable<String, RelationMessageBean> observable) {
                        observable.subscribe(new Action1<RelationMessageBean>() {
                            @Override
                            public void call(RelationMessageBean relationMessageBean) {
                                String mKey = observable.getKey();
                                if (mKey.equals(Global.LeanCloud.USER_TYPE_DOCTOR)) {
                                    doctorList.add(relationMessageBean);
                                } else if (mKey.equals(Global.LeanCloud.USER_TYPE_PATIENT)) {
                                    patientList.add(relationMessageBean);
                                }
                            }
                        });
                    }
                });
    }

    private void segmentedDisplayData(String key, List<RelationMessageBean> relationMessageBeanList) {
        AVIMClient client = LCChatKit.getInstance().getClient();
        if (relationMessageBeanList.size() == 0 && key.equals(Global.LeanCloud.USER_TYPE_DOCTOR)) {
            noDoctorMessage();
        } else if (relationMessageBeanList.size() == 0 && key.equals(Global.LeanCloud.USER_TYPE_PATIENT)) {
            noPatientMessage();
        } else {
            Observable
                    .from(relationMessageBeanList)
                    .filter(new Func1<RelationMessageBean, Boolean>() {
                        @Override
                        public Boolean call(RelationMessageBean bean) {
                            return null != bean;
                        }
                    })
                    .flatMap(new Func1<RelationMessageBean, Observable<RelationMessageBean>>() {
                        @Override
                        public Observable<RelationMessageBean> call(RelationMessageBean relationMessageBean) {
                            return Observable
                                    .just(relationMessageBean)
                                    .map(new Func1<RelationMessageBean, String>() {
                                        @Override
                                        public String call(RelationMessageBean bean) {
                                            return bean.getConversationId();
                                        }
                                    })
                                    .map(new Func1<String, AVIMConversation>() {
                                        @Override
                                        public AVIMConversation call(String conversationId) {
                                            return client != null ? client.getConversation(conversationId) : null;
                                        }
                                    })
                                    .filter(new Func1<AVIMConversation, Boolean>() {
                                        @Override
                                        public Boolean call(AVIMConversation conversation) {
                                            return null != conversation;
                                        }
                                    })
                                    .filter(new Func1<AVIMConversation, Boolean>() {
                                        @Override
                                        public Boolean call(AVIMConversation conversation) {
                                            return conversation.getLastMessageAt() != null;
                                        }
                                    })
                                    .map(new Func1<AVIMConversation, RelationMessageBean>() {
                                        @Override
                                        public RelationMessageBean call(AVIMConversation conversation) {
                                            return relationMessageBean;
                                        }
                                    });
                        }
                    })
                    .toSortedList(new Func2<RelationMessageBean, RelationMessageBean, Integer>() {
                        @Override
                        public Integer call(RelationMessageBean bean, RelationMessageBean bean2) {
                            long time = client.getConversation(bean.getConversationId()).getLastMessageAt().getTime() -
                                    client.getConversation(bean2.getConversationId()).getLastMessageAt().getTime();
                            return time > 0 ? -1 : time < 0 ? 1 : 0;//排序
                        }
                    })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<RelationMessageBean>>() {
                        @Override
                        public void onCompleted() {
                            if (relationMessageBeanList.isEmpty()) relationMessageBeanList.clear();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            LogUtils.e(throwable.getMessage());
                        }

                        @Override
                        public void onNext(List<RelationMessageBean> bean) {
                            if (!bean.isEmpty())
                                displayRelationMessageData(key, bean.get(0), client);
                        }
                    });
        }
    }

    private void displayRelationMessageData(String key, RelationMessageBean relationMessageBean, AVIMClient client) {
        AVIMConversation conversation = client.getConversation(relationMessageBean.getConversationId());
        ContactUserDisplayBean userDisplayBean = relationMessageBean.getBean();
        String groupName = conversation.getName();
        conversation.queryMessages(1, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (null != list && !list.isEmpty()) {
                    long time = list.get(0).getTimestamp();
                    CharSequence lastMessage = getMessageShorthand(getContext(), list.get(0));
                    int num = LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
                    setTvMessage(key, userDisplayBean, lastMessage, num, time);
                }
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

    public void noMessage() {
        noDoctorMessage();
        noPatientMessage();
        noGroupMessage();
    }

    public void noDoctorMessage() {
        setTvMessageDoctor(getString(R.string.no_doctor_message));
        mTvTimeDoctor.setVisibility(View.INVISIBLE);
        mImgUnreadDoctor.setVisibility(View.INVISIBLE);
    }

    public void noPatientMessage() {
        setTvMessagePatient(getString(R.string.no_patient_message));
        mTvTimePatient.setVisibility(View.INVISIBLE);
        mImgUnreadPatient.setVisibility(View.INVISIBLE);
    }

    public void noGroupMessage() {
        setTvMessageGroup(getString(R.string.no_group_message));
        mTvTimeGroup.setVisibility(View.INVISIBLE);
        mImgUnreadGroup.setVisibility(View.INVISIBLE);
    }

    private void setGroupMessage(String name, CharSequence lastMessage, long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
        setTvMessageGroup(name + ":" + String.valueOf(lastMessage));
        mTvTimeGroup.setVisibility(View.VISIBLE);
        setTvTimeGroup(format.format(date));
        mImgUnreadGroup.setVisibility(View.VISIBLE);
    }

    private void setTvMessage(String position, ContactUserDisplayBean bean, CharSequence message, int num, long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
        if (Global.LeanCloud.USER_TYPE_DOCTOR.equals(position)) {
            setTvMessageDoctor(bean.getFullName() + ":" + String.valueOf(message));
            mTvTimeDoctor.setVisibility(View.VISIBLE);
            setTvTimeDoctor(format.format(date));
            mImgUnreadDoctor.setVisibility(num > 0 ? View.VISIBLE : View.INVISIBLE);
        } else if (Global.LeanCloud.USER_TYPE_PATIENT.equals(position)) {
            setTvMessagePatient(bean.getFullName() + ":" + String.valueOf(message));
            mTvTimePatient.setVisibility(View.VISIBLE);
            setTvTimePatient(format.format(date));
            mImgUnreadPatient.setVisibility(num > 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public void setTvTimePatient(String tvTimePatient) {
        mTvTimePatient.setText(tvTimePatient);
    }

    public void setTvMessagePatient(String tvMessagePatient) {
        mTvMessagePatient.setText(tvMessagePatient);
    }

    public void setTvTimeDoctor(String tvTimeDoctor) {
        mTvTimeDoctor.setText(tvTimeDoctor);
    }

    public void setTvMessageDoctor(String tvMessageDoctor) {
        mTvMessageDoctor.setText(tvMessageDoctor);
    }

    public void setTvMessageGroup(String tvMessageGroup) {
        mTvMessageGroup.setText(tvMessageGroup);
    }

    public void setTvTimeGroup(String tvTimeGroup) {
        mTvTimeGroup.setText(tvTimeGroup);
    }

}
