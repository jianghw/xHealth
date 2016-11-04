package com.kaurihealth.kaurihealth.main_v;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.HomeImagePagerAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.eventbus.DoctorFragmentEvent;
import com.kaurihealth.kaurihealth.eventbus.HomeFragmentDoctorEvent;
import com.kaurihealth.kaurihealth.eventbus.HomefragmentPatientEvent;
import com.kaurihealth.kaurihealth.eventbus.OpenAnAccountToHomeFragmentEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientFragmentEvent;
import com.kaurihealth.kaurihealth.home_v.DoctorRequestActivity;
import com.kaurihealth.kaurihealth.open_an_account_v.OpenAnAccountActivity;
import com.kaurihealth.kaurihealth.search_v.SearchActivity;
import com.kaurihealth.mvplib.home_p.HomeFragmentPresenter;
import com.kaurihealth.mvplib.home_p.IHomeFragmentView;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.constant.Global;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/18.
 * <p/>
 * Describe: 首页
 */
public class HomeFragmentNew extends BaseFragment implements IHomeFragmentView {
    @Inject
    HomeFragmentPresenter<IHomeFragmentView> mPresenter;

    @Bind(R.id.tv_patient_request)
    TextView tvPatientRequest;
    @Bind(R.id.tv_doctor_request)
    TextView tvDoctorRequest;
//    @Bind(R.id.tv_doctor_Referrals)//暂时屏蔽
//    TextView tv_doctor_Referrals;
    @Bind(R.id.rbtn_one)
    RadioButton rbtn_one;
    @Bind(R.id.rbtn_two)
    RadioButton rbtn_two;
    @Bind(R.id.Home_vp)
    ViewPager Home_vp;

    private int currentItem = 0; // 当前图片的索引号
    private List<ImageView> imageViews; // 滑动的图片集合
    private ScheduledExecutorService scheduledExecutorService;

    private ImageHandler handler = new ImageHandler(this);

    // 切换当前显示的图片
    static class ImageHandler extends Handler {
        private WeakReference<HomeFragmentNew> weakReference = null;

        ImageHandler(HomeFragmentNew fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HomeFragmentNew fragmentNew = weakReference.get();
            if (null == weakReference || null == fragmentNew) return;
            fragmentNew.setCurrentItem();// 切换当前显示的图片
        }
    }

    private void setCurrentItem() {
        if (Home_vp != null) Home_vp.setCurrentItem(currentItem);
    }

    public static HomeFragmentNew newInstance() {
        return new HomeFragmentNew();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initViewPager();
    }

    @Override
    protected void initDelayedData() {
        EventBus.getDefault().register(this);
    }

    private void initViewPager() {
        int[] imageResId = new int[]{R.mipmap.home_banner, R.mipmap.home_banner, R.mipmap.home_banner, R.mipmap.home_banner,};
        imageViews = new ArrayList<>();
        // 初始化图片资源
        for (int anImageResId : imageResId) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(anImageResId);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        Home_vp.setAdapter(new HomeImagePagerAdapter(imageViews));// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        Home_vp.addOnPageChangeListener(new MyPageChangeListener());
    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.loadPendingDoctorRelationships(true);
        mPresenter.loadPatientRequestsByDoctor(true);
        mPresenter.loadReferralPatientRequests(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当fragment显示出来后，每三秒钟切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 3, TimeUnit.SECONDS);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 当fragment不可见的时候停止切换
        scheduledExecutorService.shutdown();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //有多个事件注意 移除
        removeStickyEvent(HomefragmentPatientEvent.class);
        removeStickyEvent(HomeFragmentDoctorEvent.class);
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    /**
     * ```````````````````点击事件```````````````````
     */
    @OnClick({R.id.home_doctor_RL, R.id.home_patient_RL, R.id.home_register_RL, R.id.home_search_RL})
    public void onClick(View view) {
        if (OnClickUtils.onNoDoubleClick()) return;
        switch (view.getId()) {
            case R.id.home_doctor_RL://医生请求
                skipTo(DoctorRequestActivity.class);
                break;
            case R.id.home_patient_RL://病人请求
                skipTo(PatientRequestActivity.class);
                break;
            //转诊  暂时屏蔽
//            case R.id.home_Referrals_RL://转诊病人
//                skipTo(ReferralPatientRequestActivity.class);
//                break;
            case R.id.home_register_RL://为患者开户
                skipTo(OpenAnAccountActivity.class);
                break;
            case R.id.home_search_RL://搜索
                Bundle bundle=new Bundle();
                bundle.putString(Global.Bundle.SEARCH_BUNDLE,Global.Bundle.SEARCH_DEFAULT);
                skipToBundle(SearchActivity.class,bundle);
                break;
            default:
                break;
        }
    }

    @Override
    public void switchPageUI(String className) {
//TODO 暂时无
    }

    /**
     * 发送图片position
     */
    private class ScrollTask implements Runnable {
        public void run() {
            synchronized (this) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
            }
        }
    }

    /**
     * viewPager监听
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        private int oldPosition = 0;

        public void onPageSelected(int position) {
            currentItem = position;
            if (oldPosition % 2 == 0) {
                rbtn_one.setChecked(true);
                rbtn_two.setChecked(false);
            } else {
                rbtn_one.setChecked(false);
                rbtn_two.setChecked(true);
            }
            oldPosition = position;
        }

        public void onPageScrollStateChanged(int arg0) {

        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }

    /**
     * 获取医生请求个数
     */
    @Override
    public void getDoctorRequestNumber(int number) {
        tvDoctorRequest.setText(String.valueOf(number));
    }

    /**
     * 获取患者请求个数
     */
    @Override
    public void getPatientRequestNumber(int number) {
        tvPatientRequest.setText(String.valueOf(number));
    }

    /**
     * 获取转诊患者请求个数
     */
    @Override
    public void getReferralPatientRequestNumber(int number) {
        //暂时屏蔽
//        tv_doctor_Referrals.setText(number + "");
    }

    /**
     * patient订阅事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HomefragmentPatientEvent event) {
        mPresenter.loadPatientRequestsByDoctor(true);
        EventBus.getDefault().postSticky(new PatientFragmentEvent());
        if (event.getTips().equals("Accept")) {
            MainActivity activity = (MainActivity) getActivity();
            activity.setCurrentPager(2);
        }
    }

    /**
     * doctor订阅事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(HomeFragmentDoctorEvent event) {
        mPresenter.loadPendingDoctorRelationships(true);
        EventBus.getDefault().postSticky(new DoctorFragmentEvent());
        if (event.getTips().equals("Accept")) {
            MainActivity activity = (MainActivity) getActivity();
            activity.setCurrentPager(2);
        }
    }

    /**
     * 为患者开户成功后的订阅事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(OpenAnAccountToHomeFragmentEvent event) {
        EventBus.getDefault().postSticky(new PatientFragmentEvent());//让其刷新
        MainActivity activity = (MainActivity) getActivity();
        activity.setCurrentPager(2);
    }

}
