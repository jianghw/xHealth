package com.kaurihealth.kaurihealth.main_v;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.mine_v.MyAccountActivity;
import com.kaurihealth.kaurihealth.mine_v.DoctorDetailsActivity;
import com.kaurihealth.kaurihealth.mine_v.ServiceSettingActivity;
import com.kaurihealth.kaurihealth.mine_v.SettingActivity;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.mvplib.main_p.IMineView;
import com.kaurihealth.mvplib.main_p.MinePresenter;
import com.kaurihealth.utilslib.constant.ImageRendition;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


public class MineFragment extends BaseFragment implements IMineView {

    @Bind(R.id.civ_photo)
    CircleImageView civPhoto;
    @Bind(R.id.tv_welcome)
    TextView tvWelcome;
    @Bind(R.id.right_top)
    ImageView rightTop;

    /**
     * 资料完成度
     */
    @Bind(R.id.tv_percentage)
    TextView tv_percentage;
    /**
     * 进度条
     */
    @Bind(R.id.pb_bar)
    ProgressBar pb_bar;

    @Inject
    MinePresenter<IMineView> mPresenter;


    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initPresenterAndData() {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        initViewPager();
    }

    @Override
    protected void initDelayedView() {

    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    @Override
    public void switchPageUI(String className) {

    }

    private void initViewPager() {
        UserBean userBean = LocalData.getLocalData().getTokenBean().getUser();
        tvWelcome.setText("你好！" + userBean.getFullName());
        int registPercentage = (int) userBean.getRegistPercentage();
        tv_percentage.setText("资料完成度" + registPercentage + "%");
        pb_bar.setProgress(registPercentage);

        if (!TextUtils.isEmpty(userBean.getAvatar())) {
            Picasso.with(getContext()).load(userBean.getAvatar() + ImageRendition.Size[3]).into(civPhoto, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        } else {

        }
    }


    @OnClick({R.id.tv_welcome, R.id.civ_photo, R.id.tv_percentage, R.id.pb_bar, R.id.tv_textButton, R.id.tv_moreinfo, R.id.lay_servicesetting, R.id.lay_myaccount, R.id.lay_setting})
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
            case R.id.lay_servicesetting:
                skipTo(ServiceSettingActivity.class);
                break;
            case R.id.lay_setting:
                skipTo(SettingActivity.class);
                break;
            case R.id.lay_myaccount:
                skipTo(MyAccountActivity.class);
                break;
        }
    }

    @Override
    public void showErrorToast(String message) {
        showToast(message);
    }
}
