package com.kaurihealth.kaurihealth.patient_v.family_members;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBeanWrapper;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.FamilyMemberAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.FamilyMemberEvent;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.patient_p.FamilyMembersPresenter;
import com.kaurihealth.mvplib.patient_p.IFamilyMembersView;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Garnet_Wu on 2016/11/10.
 * 描述 : 患者信息 -->  家庭成员界面 (请求刷新数据Api)
 */
public class FamilyMembersActivity extends BaseActivity implements IFamilyMembersView, FamilyMemberAdapter.ItemClickBack {
    @Inject
    FamilyMembersPresenter<IFamilyMembersView> mPresenter;

    //下拉刷新控件
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;
    //没有数据的提示文字
    @Bind(R.id.tv_note)
    TextView tvNote;
    //数据列表
    @Bind(R.id.lv_content)
    ListView mLvContent;
    //添加
    @Bind(R.id.tv_more)
    TextView tv_more;


    private ArrayList<FamilyMemberBeanWrapper> mBeanList;
    private int mPatientId;
    private FamilyMemberAdapter adapter;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_family_member;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getApplication())
        );
        mSwipeRefresh.setScrollUpChild(mLvContent);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.onSubscribe());

    }

    @Override
    protected void initDelayedData() {

        initNewBackBtn("家庭成员");
        tv_more.setVisibility(View.VISIBLE);
        tv_more.setText("添加");
        mBeanList = new ArrayList<>();
        adapter = new FamilyMemberAdapter(getApplicationContext(), mBeanList, this);
        mLvContent.setAdapter(adapter);

        EventBus.getDefault().register(this);
        mPresenter.onSubscribe();
    }


    //接受PatientInfoActivity传值的patientId
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(FamilyMemberEvent event) {
        int patientId = event.getPatientId();
        this.mPatientId = patientId;

    }

    /*点击添加按钮 ,跳转到医生为添加家庭成员界面*/
    @OnClick(R.id.tv_more)
    public void onClick(View view) {
        if (OnClickUtils.onNoDoubleClick()) return;
        switch (view.getId()) {
            case R.id.tv_more:
                skipTo(AddFamilyMemberActivity.class);   //跳转到添加家庭成员界面
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override

    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className
     */

    @Override
    public void switchPageUI(String className) {

    }

    //请求成功,数据通过View层显示
    @Override
    public void lazyLoadingDataSuccess(List<FamilyMemberBeanWrapper> wrapLists) {
        tvNote.setVisibility(wrapLists != null ? !wrapLists.isEmpty() ? View.GONE : View.VISIBLE : View.VISIBLE);   //提示信息的显示
        if (mBeanList.size() > 0) mBeanList.clear();
        mBeanList.addAll(wrapLists);
        adapter.notifyDataSetChanged();
    }


    //IFamilyMembersView
    @Override
    public int getPatientId() {
        return mPatientId;
    }


    //停止refreshLayout刷新
    @Override
    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }

    //点击"添加患者" 按钮
    @Override
    public void onItemAddClick(int doctorID) {
        mPresenter.insertNewRelationshipByDoctor(doctorID);
    }

    //点击 "添加患者" 成功/ 失败 提示
    @Override
    public void insertPatientSucceed(DoctorPatientRelationshipBean bean) {
        if (bean != null) {
            showToast("添加患者成功");
            //再次去刷新数据 ,控件发生改变
            mPresenter.onSubscribe();

        } else {
            showToast("添加患者失败");
        }
    }


}
