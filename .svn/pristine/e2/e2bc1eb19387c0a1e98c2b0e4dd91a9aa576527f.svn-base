package com.kaurihealth.kaurihealth.conversation_v;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.doctor_v.DoctorInfoActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientInfoShipBeanEvent;
import com.kaurihealth.kaurihealth.patient_v.PatientInfoActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.IItemDetailsView;
import com.kaurihealth.mvplib.patient_p.ItemDetailsPresenter;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.dialog.PopUpNumberPickerDialog;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;
import com.rey.material.widget.Switch;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jianghw on 2016/11/9.
 * <p/>
 * Describe:对话详情
 */

public class ItemConversationDetailsActivity extends BaseActivity implements IItemDetailsView{

    @Bind(R.id.rl_details)
    RelativeLayout mRlDetail;

    @Bind(R.id.civPhoto)
    CircleImageView mCircleImage;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_gender)
    TextView mGender;
    @Bind(R.id.tv_age)
    TextView mAge;

    @Bind(R.id.switch_push)
    Switch mSwitchPush;
    @Bind(R.id.rl_group_clean)
    RelativeLayout mRlGroupClean;

    private AVIMConversation mConversation;
    private ContactUserDisplayBean contactUserDisplayBean;

    @Inject
    ItemDetailsPresenter<IItemDetailsView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_item_con_details;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);


        initNewBackBtn("对话信息");

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            if (extras.containsKey(LCIMConstants.CONVERSATION_ID_DETAIL_DIALOGUE)) {
                String conversationId = extras.getString(LCIMConstants.CONVERSATION_ID_DETAIL_DIALOGUE);
                AVIMClient client = LCChatKit.getInstance().getClient();
                updateConversation(client != null ? client.getConversation(conversationId) : null);
            } else {
                showToast("memberId or conversationId is needed");
                finish();
            }
        }
    }

    @Override
    protected void initDelayedData() {
//置顶
        mSwitchPush.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                LCIMProfileCache.getInstance().changStickType(contactUserDisplayBean);
            }
        });
//清空会话
        mRlGroupClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] messages = getResources().getStringArray(R.array.message_clean);
                DialogUtils.showStringDialog(ItemConversationDetailsActivity.this, messages, new PopUpNumberPickerDialog.SetClickListener() {
                    @Override
                    public void onClick(int index) {
                        if (index == 0 && mConversation != null) {
                            LCIMConversationItemCache.getInstance().deleteConversation(mConversation.getConversationId());
                            setResult(RESULT_OK);
                        }
                    }
                });
            }
        });

//点击跳转页面 根据contactUserDisplayBean 的type
        mRlDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (contactUserDisplayBean.getUserType().equals("患者")){
                    //患者逻辑
                    mPresenter.loadDoctorPatientRelationshipForPatientId();
                }else {
                    //医生逻辑
                    mPresenter.onSubscribe();
                }
            }
        });

    }

    /**
     * 主动刷新 UI
     */
    protected void updateConversation(AVIMConversation conversation) {
        if (null != conversation) {
            if (conversation.getCreatedAt() == null) {
                conversation.fetchInfoInBackground(new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        mConversation = conversation;
                        imageLoaderByConversation();
                    }
                });
            } else {
                mConversation = conversation;
                imageLoaderByConversation();
            }
        }
    }

    private void imageLoaderByConversation() {
        if (mConversation == null) return;
        String currentUserId = LCChatKit.getInstance().getCurrentUserId();
        Observable.just(mConversation)
                .map(new Func1<AVIMConversation, List<String>>() {
                    @Override
                    public List<String> call(AVIMConversation avimConversation) {
                        return avimConversation.getMembers();
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
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String kauriHealthId) {
                        initDelayView(kauriHealthId);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
    }

    private void initDelayView(String kauriHealthId) {
        mSwitchPush.setChecked(LCIMProfileCache.getInstance().hasCachedUser(kauriHealthId)
                && (LCIMProfileCache.getInstance().getContactUserMap().get(kauriHealthId).isTop()));

        if (LCIMProfileCache.getInstance().hasCachedUser(kauriHealthId)) {
            contactUserDisplayBean = LCIMProfileCache.getInstance().getContactUserMap().get(kauriHealthId);
            ImageUrlUtils.picassoByUrlCircle(this, contactUserDisplayBean.getAvatar(), mCircleImage);
            tvName.setText(contactUserDisplayBean.getFullName());
            mGender.setText(contactUserDisplayBean.getGender());
            int age = DateUtils.calculateAge(contactUserDisplayBean.getDateOfBirth());
            mAge.setText(age + "岁");
        }
    }


    @Override
    public void switchPageUI(String className) {
        if (className.equals("Doctor")){
            skipTo(DoctorInfoActivity.class);
        }else {
            skipTo(PatientInfoActivity.class);
        }
    }

    /**
     * 得到当前的doctorId
     */
    @Override
    public int getDoctorOrPatientId() {
        return contactUserDisplayBean.getPrimaryId();
    }


    /**
     * 返回结果
     * @param o
     * @param mark
     */
    @Override
    public void getResult(Object o,String mark) {
        if (mark.equals("Doctor")){
            EventBus.getDefault().postSticky(new DoctorRelationshipBeanEvent((DoctorRelationshipBean) o,true,mark));//医生
        }else {
            EventBus.getDefault().postSticky(new PatientInfoShipBeanEvent((DoctorPatientRelationshipBean)o,3));//患者
        }
        switchPageUI(mark);
    }

    @Override
    public String getKuarihealthId() {
        return contactUserDisplayBean.getKauriHealthId();
    }
}
