package com.kaurihealth.kaurihealth.patient_v;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.DoctorTeamAdapter;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.kaurihealth.doctor_v.DoctorInfoActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorFragmentRefreshEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorJumpEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorTeamJumpEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientFragmentEvent;
import com.kaurihealth.kaurihealth.eventbus.VerificationJumpEvent;
import com.kaurihealth.kaurihealth.home_v.VerificationActivity;
import com.kaurihealth.mvplib.patient_p.DoctorTeamPresenter;
import com.kaurihealth.mvplib.patient_p.IDoctorTeamView;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


/**
 *
 */
public class DoctorTeamFragment extends ChildBaseFragment implements IDoctorTeamView {

    @Inject
    DoctorTeamPresenter<IDoctorTeamView> mPresenter;
    //列表图
    @Bind(R.id.gv_content)
    GridView mGvContent;

    @Bind(R.id.tv_note)   //空记录时候的提示文字
    TextView tvNote;
    //留言
    @Nullable
    @Bind(R.id.btn_conversation)
    Button mBtnConversation;
    // “添加”按钮
    @Nullable
    @Bind(R.id.btn_add)
    Button mBtnAdd;

    //包裹GridView控件
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    private ArrayList<DoctorPatientRelationshipBean> mBeanList;
    private DoctorTeamAdapter adapter;
    private int patientId;

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_doctor_team;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);  //view和presenter建立联系

        mBeanList = new ArrayList<>();//empty data

        adapter = new DoctorTeamAdapter(getContext(), mBeanList);
        mGvContent.setAdapter(adapter);
    }

    @Override
    protected void initDelayedData() {
        mSwipeRefresh.setColorSchemeColors(             //进度条的颜色
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mSwipeRefresh.setScrollUpChild(mGvContent);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);  //指定了下拉刷新的临界值
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onSubscribe());
        patientId = getActivity().getIntent().getIntExtra("patientId", -1);
        //注册事件
        EventBus.getDefault().register(this);
        //进入DoctorTeam先得知自己的协作医生有谁
        mPresenter.showCooperationDoctor();   //已经同意的协作医生


        mPresenter.onSubscribe();   //患者的医疗团队数据查看
    }


    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className
     */

    //跳转界面
    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.DoctorInfoActivity:
                skipTo(DoctorInfoActivity.class);
                break;
            case Global.Jump.VerificationActivity:
                skipTo(VerificationActivity.class);
                break;
            default:
                break;
        }

    }

    @Override
    protected void childLazyLoadingData() {
        mPresenter.onSubscribe();
    }


    //IDoctorTeamView 根据医患id加载
    @Override
    public int getPatientRelationshipId() {
        return patientId;
    }

    //Fragment 生命周期
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        if (mBeanList != null) {   //清空集合
            mBeanList.clear();
        }
        //TODO   removeStickyEvent(?.calss)
        removeStickyEvent(DoctorJumpEvent.class);
        removeStickyEvent(DoctorFragmentRefreshEvent.class);
        removeStickyEvent(VerificationJumpEvent.class);
        //注销事件
        EventBus.getDefault().unregister(this);
    }

    /**
     * ----------------------------订阅事件----------------------------------
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(DoctorTeamJumpEvent event) {
        switchPageUI(event.getMessage());  //DoctorInfoActivity界面跳转
      /*  try {
            Intent intent = new Intent();
            //intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
            intent.setPackage(getContext().getPackageName());
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(LCIMConstants.PEER_ID, event.getKauriHealthId());
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
        }*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(VerificationJumpEvent event) {
                switchPageUI(event.getMessage());
        }




    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void  eventBusMain(PatientFragmentEvent event){
        mPresenter.loadingRemoteData(true);
    }

    @Override
    public void lazyLoadingDataSuccess(List<?> list) {
        noDoctorDataState(list.isEmpty());  //返回数据是否为空？ 视图显示由服务器返回数据决定

        if (mBeanList.size() > 0) mBeanList.clear();
        mBeanList.addAll((Collection<? extends DoctorPatientRelationshipBean>) list);  //服务器返回数据用本地变量维持
        adapter.notifyDataSetChanged();
        //TODO 拿到数据，下拉刷新结束
        mSwipeRefresh.setRefreshing(false);
    }

    private void noDoctorDataState(boolean empty) {
        tvNote.setVisibility(empty ? View.VISIBLE : View.GONE);
        mGvContent.setVisibility(empty ? View.GONE : View.VISIBLE);
    }
}
