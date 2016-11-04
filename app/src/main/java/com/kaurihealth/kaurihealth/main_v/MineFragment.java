package com.kaurihealth.kaurihealth.main_v;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.eventbus.MineFragmentEvent;
import com.kaurihealth.kaurihealth.mine_v.SettingActivity;
import com.kaurihealth.kaurihealth.mine_v.account.MyAccountActivity;
import com.kaurihealth.kaurihealth.mine_v.personal.DoctorDetailsActivity;
import com.kaurihealth.kaurihealth.mine_v.service.ServiceSettingActivity;
import com.kaurihealth.mvplib.main_p.IMineView;
import com.kaurihealth.mvplib.main_p.MinePresenter;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的  母页
 * 描述: 我的Fragment
 */
public class MineFragment extends BaseFragment implements IMineView {
    @Inject
    MinePresenter<IMineView> mPresenter;

    @Bind(R.id.right_top)
    ImageView rightTop;
    @Bind(R.id.civ_photo)
    CircleImageView civPhoto;
    @Bind(R.id.tv_welcome)
    TextView tvWelcome;
    @Bind(R.id.tv_percentage)
    TextView tvPercentage;
    @Bind(R.id.pb_bar)
    ProgressBar pbBar;
    @Bind(R.id.tv_textButton)
    TextView tvTextButton;
    @Bind(R.id.tv_moreinfo)
    TextView tvMoreinfo;
    @Bind(R.id.lay_complete)
    LinearLayout layComplete;
    @Bind(R.id.buttonLayout)
    LinearLayout buttonLayout;
    @Bind(R.id.lay_service_set)
    LinearLayout layServiceSet;
    @Bind(R.id.lay_setting)
    LinearLayout laySetting;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initDelayedData() {
        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(getContext())
        );
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);
        mSwipeRefresh.setOnRefreshListener(() -> mPresenter.loadDoctorDetail(true));
    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.tv_welcome, R.id.civ_photo, R.id.tv_percentage, R.id.pb_bar,
            R.id.tv_textButton, R.id.tv_moreinfo, R.id.lay_service_set, R.id.lay_account, R.id.lay_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_welcome:
            case R.id.civ_photo:
            case R.id.tv_percentage:
            case R.id.pb_bar:
            case R.id.tv_textButton:
            case R.id.tv_moreinfo:
                skipTo(DoctorDetailsActivity.class);
                break;
            case R.id.lay_service_set://服务
                skipTo(ServiceSettingActivity.class);
                break;
            case R.id.lay_setting:
                skipTo(SettingActivity.class);
                break;
            case R.id.lay_account:
                skipTo(MyAccountActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(MineFragmentEvent event) {
        loadDoctorDataSucceed(LocalData.getLocalData().getMyself());
    }

    @Override
    public void loadDoctorDataSucceed(DoctorDisplayBean doctorDisplayBean) {
        if (doctorDisplayBean != null) {
            String fullName = doctorDisplayBean.getFullName();
            tvWelcome.setText(String.format(getResources().getString(R.string.mine_hello), fullName));
            int registPercentage = (int) doctorDisplayBean.getRegistPercentage();
            tvPercentage.setText(String.format(getResources().getString(R.string.mine_regist_percentage), String.valueOf(registPercentage)) + "%");
            pbBar.setProgress(registPercentage);

            if (CheckUtils.checkUrlNotNull(doctorDisplayBean.getAvatar())) {
                ImageUrlUtils.picassoByUrlCircle(getActivity(), doctorDisplayBean.getAvatar(), civPhoto);

                blur(CheckUtils.checkUrlNotNull(doctorDisplayBean.getAvatar()), doctorDisplayBean.getAvatar());
            }
        }
    }

    /**
     * 高斯模糊处理
     */
    private void blur(boolean hasChanged, String avatar) {
        if (!hasChanged) {
            rightTop.setImageDrawable(civPhoto.getDrawable());
        } else {
            rightTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    rightTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mPresenter.gaussianBlur(avatar);
                }
            });
        }
    }

    @Override
    public void gaussianBlur(Bitmap bitmap) {
        if (rightTop == null) return;
        rightTop.setScaleType(ImageView.ScaleType.CENTER_CROP);
        rightTop.setImageBitmap(bitmap);
    }
}
