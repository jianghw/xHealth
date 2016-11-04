package com.kaurihealth.kaurihealth.main_v;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.kaurihealth.chatlib.adapter.LCIMCommonListAdapter;
import com.kaurihealth.chatlib.cache.LCIMConversationItem;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.chatlib.custom.LCChatMessageInterface;
import com.kaurihealth.chatlib.event.LCIMConversationItemLongClickEvent;
import com.kaurihealth.chatlib.event.LCIMIMTypeMessageEvent;
import com.kaurihealth.chatlib.utils.LCIMLogUtils;
import com.kaurihealth.chatlib.viewholder.LCIMConversationItemHolder;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.mvplib.main_p.IMessageView;
import com.kaurihealth.mvplib.main_p.MessagePresenter;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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

    protected LCIMCommonListAdapter<AVIMConversation> itemAdapter;

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

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new LCIM_DividerItemDecoration(getActivity()));
//        recyclerView.setAdapter(itemAdapter);

        itemAdapter = new LCIMCommonListAdapter<>(LCIMConversationItemHolder.class);
    }

    @Override
    protected void initDelayedData() {
        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getContext())
        );
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadingRemoteData(true));

        EventBus.getDefault().register(this);
    }

    @Override
    protected void lazyLoadingData() {
        loadContactListByDoctorIdSucceed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadContactListByDoctorIdSucceed();
    }

    @OnClick({R.id.rl_patient, R.id.rl_doctor})
    public void onClickItem() {
        Bundle bundle=new Bundle();
        switch (getId()) {
            case R.id.rl_patient:
                bundle.putString(Global.Bundle.CONVER_ITEM_BUNDLE,Global.Bundle.CONVER_ITEM_PATIENT);
                break;
            case R.id.rl_doctor:
                bundle.putString(Global.Bundle.CONVER_ITEM_BUNDLE,Global.Bundle.CONVER_ITEM_DOCTOR);
                break;
            default:
                break;
        }
        skipToBundle(ConversationItemActivity.class,bundle);
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

    /**
     * 是否需要 获取关系列表
     */
//    private void updateConversationList() {
//        List<String> convIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();
//        AVIMClient client = LCChatKit.getInstance().getClient();
//        String currentUserId = LCChatKit.getInstance().getCurrentUserId();
////本地有的
//    }
    @Override
    public void switchPageUI(String className) {
//TODO
        Bundle bundle=new Bundle();
        bundle.putString(Global.Bundle.CONVER_ITEM_BUNDLE,Global.Bundle.CONVER_ITEM_DOCTOR);
        skipToBundle(ConversationItemActivity.class,bundle);
    }

    /**
     * 刷新页面
     */
    @Override
    public void loadContactListByDoctorIdSucceed() {
        if (itemAdapter == null) return;
        noMessage();
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
                                   if (strings != null && strings.size() > 0) {
                                       LCIMProfileCache.getInstance().getCachedUser(strings.get(0), new AVCallback<ContactUserDisplayBean>() {
                                           @Override
                                           protected void internalDone0(ContactUserDisplayBean bean, AVException e) {
                                               if (bean != null) filterReadedMessage();
                                           }
                                       });
                                   } else {
                                       filterReadedMessage();
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
     * 1、过滤掉已经读过的消息
     */
    private void filterReadedMessage() {
        List<LCIMConversationItem> converList = LCIMConversationItemCache.getInstance().getConversationList();
        LogUtils.jsonDate(converList);
        Observable.from(converList)
                .filter(new Func1<LCIMConversationItem, Boolean>() {
                    @Override
                    public Boolean call(LCIMConversationItem item) {
                        return item.unreadCount != 0;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<LCIMConversationItem>>() {
                            @Override
                            public void call(List<LCIMConversationItem> items) {
                                if (items != null && items.size() > 0) {
                                    handlerConversationItem(items, 0);
                                    handlerConversationItem(items, 1);
                                } else {
                                    noMessage();
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
     * 过滤出各类型中第一个没读的bean
     * 1、conversationId
     * 2、ContactUserDisplayBean
     */
    private void handlerConversationItem(List<LCIMConversationItem> items, int position) {
        AVIMClient client = LCChatKit.getInstance().getClient();
        String currentUserId = LCChatKit.getInstance().getCurrentUserId();
        Observable.from(items)
                .map(new Func1<LCIMConversationItem, String>() {
                    @Override
                    public String call(LCIMConversationItem item) {
                        return item.conversationId;
                    }
                })
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
                                .takeWhile(new Func1<ContactUserDisplayBean, Boolean>() {
                                    @Override
                                    public Boolean call(ContactUserDisplayBean bean) {
                                        return bean.getUserType() == position;//医生0，患者1
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
                                if (map != null && map.size() == 1) {
                                    for (Map.Entry<String, ContactUserDisplayBean> entry : map.entrySet()) {
                                        displayMessageData(entry, client, position);
                                    }
                                } else {
                                    if (position == 0) {
                                        noDoctorMessage();
                                    } else if (position == 1) {
                                        noPatientMessage();
                                    }
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

    private void displayMessageData(Map.Entry<String, ContactUserDisplayBean> entry, AVIMClient client, int position) {
        AVIMConversation conversation = client.getConversation(entry.getKey());
        ContactUserDisplayBean userDisplayBean = entry.getValue();
        conversation.queryMessages(1, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (null != e) {
                    LCIMLogUtils.logException(e);
                }
                if (null != list && !list.isEmpty()) {
                    long time = list.get(0).getTimestamp();
                    CharSequence lastMessage = getMessageShorthand(getContext(), list.get(0));
                    int num = LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
                    setTvMessage(position, userDisplayBean, lastMessage, num, time);
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

    private void setTvMessage(int position, ContactUserDisplayBean bean, CharSequence message, int num, long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
        if (position == 0) {
            setTvMessageDoctor(bean.getFullName() + ":" + String.valueOf(message));
            mTvTimeDoctor.setVisibility(View.VISIBLE);
            setTvTimeDoctor(format.format(date));
            mImgUnreadDoctor.setVisibility(num > 0 ? View.VISIBLE : View.INVISIBLE);
        } else if (position == 1) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
