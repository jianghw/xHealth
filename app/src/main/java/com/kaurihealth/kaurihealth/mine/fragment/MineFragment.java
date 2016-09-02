package com.kaurihealth.kaurihealth.mine.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.activity.MyAccountActivity;
import com.kaurihealth.kaurihealth.mine.activity.PersoninfoNewActivity;
import com.kaurihealth.kaurihealth.mine.activity.ServiceSettingActivity;
import com.kaurihealth.kaurihealth.mine.activity.SettingActivity;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.youyou.zllibrary.util.CommonFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.blurry.Blurry;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 * 介绍：
 * 主界面中我的界面
 */
public class MineFragment extends CommonFragment {

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
    private IGetter getter;
    PersonInfoBean personInfoBean;
    private Handler handler;
    private int registPercentage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void init() {
        super.init();
        getter = Getter.getInstance(getActivity().getApplicationContext());
        handler = new Handler();
    }

    @Override
    public void onResume() {
        super.onResume();
        personInfoBean = getter.getPersonInfo();
        String name = personInfoBean.fullName;
        tvWelcome.setText("你好！" + name);
        registPercentage = (int) getter.getRegistPercentage();
        tv_percentage.setText("资料完成度" + registPercentage + "%");
        pb_bar.setProgress(registPercentage);
//        PicassoImageLoadUtil.loadUrlToImageView(personInfoBean.avatar, civPhoto, getContext());
        if (!TextUtils.isEmpty(personInfoBean.avatar)) {

            Picasso.with(getContext()).load(personInfoBean.avatar + ImagSizeMode.imageSizeBeens[3].size).into(civPhoto, new Callback() {
                @Override
                public void onSuccess() {
                    blur(true);
                }

                @Override
                public void onError() {
                    blur(false);
                }
            });
        } else {
            blur(false);
        }
    }

    /**
     * 高斯模糊处理
     */
    private void blur(boolean hasChanged) {
        if (hasChanged) {
            rightTop.setImageDrawable(civPhoto.getDrawable());
        }
        rightTop.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rightTop.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Blurry.with(getContext())
                        .radius(10)
                        .sampling(1)
                        .color(Color.argb(0, 0, 0, 0))
                        .async()
                        .capture(rightTop)
                        .into(rightTop);
            }
        });
    }


    /**
     * 完成个人信息
     */
    @OnClick({R.id.tv_welcome, R.id.civ_photo, R.id.tv_percentage, R.id.pb_bar, R.id.tv_textButton, R.id.tv_moreinfo, R.id.lay_servicesetting, R.id.lay_myaccount, R.id.lay_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_welcome:
            case R.id.civ_photo:
            case R.id.tv_percentage:
            case R.id.pb_bar:
            case R.id.tv_textButton:
            case R.id.tv_moreinfo:
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", personInfoBean);
                skipToForResult(PersoninfoNewActivity.class, bundle, PersoninfoNewActivity.PersoninfoNewActivityCode);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        registPercentage = (int) getter.getRegistPercentage();
    }
}
