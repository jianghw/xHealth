package com.kaurihealth.kaurihealth.casehistory_v;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.kaurihealth.doctor_new.PatientSearchActivity;
import com.kaurihealth.kaurihealth.open_account_v.OpenAnAccountActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.casehistory_p.CaseHistoryPresenter;
import com.kaurihealth.mvplib.casehistory_p.ICaseHistoryView;
import com.kaurihealth.utilslib.TranslationAnim;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 病历关系 母页
 * 描述:病历Fragment
 */
public class CaseHistoryFragment extends BaseFragment
        implements ICaseHistoryView,PopupWindow.OnDismissListener, View.OnClickListener {

    @Bind(R.id.rbtn_all)
    RadioButton mRbtnAll;
    @Bind(R.id.rbtn_processed)
    RadioButton mRbtnProcessed;
    @Bind(R.id.rl_title)
    RelativeLayout mActionBar;

    @Bind(R.id.vp_relation)
    ViewPager mVpRelation;
    @Bind(R.id.tv_add)
    ImageView addText;
    @Bind(R.id.tv_clear)
    TextView tv_clear;

    @Inject
    CaseHistoryPresenter<ICaseHistoryView> mPresenter;

    List<Fragment> mFragmentList = new ArrayList<>();
    private int mCurItem = 0;
    private PopupWindow popupWindow;

    public static CaseHistoryFragment newInstance() {
        return new CaseHistoryFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_case_history;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initChildFragment();
        initViewPager();
    }

    @Override
    protected void initDelayedData() {
        initCurPager(mCurItem);
    }

    @Override
    protected void lazyLoadingData() {

    }

    private void initChildFragment() {
        if (mFragmentList.size() > 0) mFragmentList.clear();
        AllMedicalRecordsFragment allMedicalRecordsFragment = AllMedicalRecordsFragment.newInstance();
        ProcessedRecordsFragment processedRecordsFragment = ProcessedRecordsFragment.newInstance();
        mFragmentList.add(allMedicalRecordsFragment);
        mFragmentList.add(processedRecordsFragment);
    }

    private void initViewPager() {
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getChildFragmentManager(), mFragmentList);
        mVpRelation.setAdapter(mainFragmentAdapter);
        mVpRelation.setOffscreenPageLimit(mFragmentList.size() - 1);
        mVpRelation.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initCurPager(position);
                mCurItem = position;
                setAddView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 清除设置
     */
    private void setAddView(int position) {
        addText.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        tv_clear.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
    }

    private void initCurPager(int mCurItem) {
        switch (mCurItem) {
            case 0:
                mVpRelation.setCurrentItem(mCurItem);
                mRbtnAll.setChecked(true);
                break;
            case 1:
                mVpRelation.setCurrentItem(mCurItem);
                mRbtnProcessed.setChecked(true);
                break;
            default:
                break;
        }
    }



    /**
     * 点击全部
     */
    @OnClick(R.id.rbtn_all)
    public void allMedicalRecords() {
        mVpRelation.setCurrentItem(0);
    }

    /**
     * 点击待处理
     */
    @OnClick(R.id.rbtn_processed)
    public void processedMedicalRecords() {
        mVpRelation.setCurrentItem(1);
    }

    /**
     * 添加患者
     */
    @OnClick(R.id.tv_add)
    public void addPatient() {
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.view_popup_patient, null);
        PopupWindow popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);
        LinearLayout addPatient = (LinearLayout) popupView.findViewById(R.id.ly_add_patient);
        LinearLayout searchPatient = (LinearLayout) popupView.findViewById(R.id.ly_search_patient);
        addPatient.setOnClickListener(new View.OnClickListener() {//患者开户
            @Override
            public void onClick(View v) {
                skipTo(OpenAnAccountActivity.class);
            }
        });
        searchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipTo(PatientSearchActivity.class);
            }
        });
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_view_popup_patient));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(mActionBar, Gravity.RIGHT | Gravity.TOP, 8, 100);
    }

    /**
     * 清除待处理病历
     */
    @OnClick(R.id.tv_clear)
    public void onTvClear(){
        openPopupWindow(getView());
    }

    protected void skipToBundle(Class<? extends Activity> className, Bundle bundle) {
        TranslationAnim.zlStartActivity(getActivity(), className, bundle);
    }

    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getActivity() != null) {
            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (isVisibleToUser) {
                // 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof ChildBaseFragment) {
                            ChildBaseFragment childBaseFragment = (ChildBaseFragment) childFragment;
                            if (childBaseFragment.isWaitingShowToUser()) {
                                childBaseFragment.setWaitingShowToUser(false);
                                childFragment.setUserVisibleHint(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void openPopupWindow(View v) {
        //防止重复按按钮
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //设置PopupWindow的View
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_clear_data, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        //设置背景,这个没什么效果，不添加会报错
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //设置动画
        popupWindow.setAnimationStyle(R.style.PopupWindow);
        //设置位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 20);
        //设置消失监听
        popupWindow.setOnDismissListener(this);
        //设置PopupWindow的View点击事件
        setOnPopupViewClick(view);
        //设置背景色
        setBackgroundAlpha(0.5f);
    }


    private TextView tv_cancel,tv_clear_data;

    private void setOnPopupViewClick(View view) {
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_clear_data = (TextView) view.findViewById(R.id.tv_clear_data);

        tv_cancel.setOnClickListener(this);
        tv_clear_data.setOnClickListener(this);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;
        if (alpha == 1) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_clear_data:
                popupWindow.dismiss();
                //TODO  做清除病历的逻辑
                showToast("清除已处理病历");
                break;
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
        }
    }

    @Override
    public void switchPageUI(String className) {

    }
}
