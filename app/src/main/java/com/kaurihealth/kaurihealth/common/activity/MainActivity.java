package com.kaurihealth.kaurihealth.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.chat.MessageFragment;
import com.kaurihealth.kaurihealth.clinical.util.IClinicalFragmentGetData;
import com.kaurihealth.kaurihealth.common.adapter.PatientAdapter;
import com.kaurihealth.kaurihealth.home.activity.AcceptReasonActivity;
import com.kaurihealth.kaurihealth.home.fragment.HomeFragment;
import com.kaurihealth.kaurihealth.mine.Interface.ResponseListener;
import com.kaurihealth.kaurihealth.mine.activity.PersoninfoNewActivity;
import com.kaurihealth.kaurihealth.mine.fragment.MineFragment;
import com.kaurihealth.kaurihealth.mine.util.NewPersonInfoUtil;
import com.kaurihealth.kaurihealth.mine.util.PersonInfoUtil;
import com.kaurihealth.kaurihealth.patientwithdoctor.fragment.FriendFragment;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.GetNewestVersion;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.kaurihealth.kaurihealth.util.Putter;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.updata.NetLastVersionAbstract;
import com.youyou.zllibrary.util.CommonFragmentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends CommonFragmentActivity {

    public static final int ToSearch = 8;
    public static final int ToOpenAccount = 9;
    public static final int ToAcceptReason = 10;
    public static final String PositionKey = "PositionKey";

    @Bind(R.id.rbtn_home_main)
    RadioButton mRbtnHomeMain;
    @Bind(R.id.rbtn_message_main)
    RadioButton mRbtnMessageMain;
    @Bind(R.id.iv_dot)
    ImageView mIvDot;
    @Bind(R.id.rbtn_friend_main)
    RadioButton mRbtnFriendMain;
    @Bind(R.id.rbtn_clinical_main)
    RadioButton mRbtnClinicalMain;
    @Bind(R.id.rbtn_mine_main)
    RadioButton mRbtnMineMain;
    @Bind(R.id.vp_content_main)
    ViewPager mVpContentMain;

    private IDoctorService doctorService;
    private MainFragmentAdapter mainFragmentAdapter;
    private PersonInfoUtil personInfoUtil;
    HomeFragment homeFragment = new HomeFragment();
    MessageFragment messageFragment = new MessageFragment();
    FriendFragment friendFragment = new FriendFragment();
    MineFragment mineFragment = new MineFragment();
//    ClinicalFragment clinicalFragment = new ClinicalFragment();

    List<Fragment> fragments = new ArrayList<>();

    private IPutter iPutter;
    private IClinicalFragmentGetData clinicalFragmentGetDate;


    SuccessInterfaceM hasMesCome = new SuccessInterfaceM<Boolean>() {
        @Override
        public void success(Boolean hasNewMes) {
            if (hasNewMes) {
                mIvDot.setVisibility(View.VISIBLE);
            } else {
                mIvDot.setVisibility(View.GONE);
            }
        }
    };


    {
        fragments.add(homeFragment);
        fragments.add(messageFragment);
        fragments.add(friendFragment);
//        fragments.add(clinicalFragment);
        fragments.add(mineFragment);
//        clinicalFragmentGetDate = clinicalFragment.getData;
        messageFragment.setMessageComeListener(hasMesCome);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }




    private void initViewPager() {
        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments);
        mVpContentMain.setAdapter(mainFragmentAdapter);
    }

    Handler handler = new Handler();

    @Override
    public void init() {
        super.init();
        iPutter = new Putter(getApplicationContext());
        friendFragment.getDataInterface(getApplicationContext()).getData();//初始化朋友界面的数据
        messageFragment.getDataInterface().getData();//初始化消息界面

        personInfoUtil = PersonInfoUtil.getInstance(this, Config.Role.Doctor);

        NetLastVersionAbstract versionCheck = new GetNewestVersion(handler, this, Url.LoadAndroidVersionWithDoctor);
        versionCheck.startWithDownloadManager();

        initViewPager();
        initBodyAndBottom();

        mVpContentMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setBottomSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        personInfoUtil = PersonInfoUtil.getInstance(this, Config.Role.Doctor);
        NewPersonInfoUtil newPersonInfoUtil = new NewPersonInfoUtil(this, Config.Role.Doctor);
        newPersonInfoUtil.getPersonInfoFromRetrofit(personinfolistener);
        newPersonInfoUtil.getndDepartmentFromRetrofit(jobTitleListener);
        getDoctor();
        clinicalFragmentGetDate.getData();
    }





    private void getDoctor() {
//        doctorService = new ServiceFactory(Url.prefix, getApplicationContext()).getDoctorService();
//        Call<DoctorDisplayBean> doctorDisplayBeanCall = doctorService.LoadDoctorDetail_out();
//        doctorDisplayBeanCall.enqueue(new Callback<DoctorDisplayBean>() {
//            @Override
//            public void onResponse(Call<DoctorDisplayBean> call, Response<DoctorDisplayBean> response) {
//                if (response.isSuccessful()) {
//                    DoctorDisplayBean bean = response.body();
//                    int priceID = 0;
//                    for (ProductDisplayBean iteam : bean.products) {
//                        if (iteam.price == null) {
//                            iteam.price = new PriceDisplayBean();
//                            iteam.price.unitPrice = 0;
//                            iteam.price.isAvailable = false;
//                            iteam.price.description = iteam.description;
//                            iteam.price.doctorId = response.body().doctorId;
//                            iteam.price.priceId = priceID;
//                            iteam.price.productId = iteam.productId;
//                            priceID++;
//                        }
//                    }
//                    iPutter.setMyself(bean);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DoctorDisplayBean> call, Throwable t) {
//                showToast(LoadingStatu.NetError.value);
//            }
//        });
    }





    ResponseListener<PersonInfoBean> personinfolistener = new ResponseListener<PersonInfoBean>() {
        @Override
        public void erorr(String erorr) {
            showToast(erorr);
        }

        @Override
        public void success(PersonInfoBean response) {
            iPutter.setPersonInfo(response);
        }
    };



    ResponseListener<List<DepartmentDisplayBean>> jobTitleListener = new ResponseListener<List<DepartmentDisplayBean>>() {
        @Override
        public void erorr(String erorr) {
            showToast(erorr);
        }

        @Override
        public void success(List<DepartmentDisplayBean> response) {
            if (response == null) {
                return;
            }
            DepartmentDisplayBean[] departmentDisplayBean = response.toArray(new DepartmentDisplayBean[response.size()]);
            iPutter.setDepartment(departmentDisplayBean);
            handleDepartment(response);

        }
    };




    //对科室信息进行处理
    private void handleDepartment(List<DepartmentDisplayBean> response) {
        List<DepartmentDisplayBean> container = new LinkedList<>();
        container.addAll(response);
        Map<Integer, DepartmentDisplayWrapBean> map = new HashMap<>();
        Iterator<DepartmentDisplayBean> iterator = container.iterator();
        while (iterator.hasNext()) {
            DepartmentDisplayBean next = iterator.next();
            if (next.parent == null) {
                DepartmentDisplayWrapBean bean = new DepartmentDisplayWrapBean();
                bean.level1 = next;
                map.put(next.departmentId, bean);
            }
        }
        iterator = container.iterator();
        while (iterator.hasNext()) {
            DepartmentDisplayBean next = iterator.next();
            DepartmentDisplayWrapBean bean = map.get(next.parent);
            if (bean != null) {
                bean.level2.add(next);
            }
        }
        Iterator<Map.Entry<Integer, DepartmentDisplayWrapBean>> iterator1 = map.entrySet().iterator();
        List<DepartmentDisplayWrapBean> result = new LinkedList<>();
        while (iterator1.hasNext()) {
            result.add(iterator1.next().getValue());
        }
        DepartmentDisplayWrapBean[] departmentDisplayWrapBeens = new DepartmentDisplayWrapBean[result.size()];
        result.toArray(departmentDisplayWrapBeens);
        iPutter.setDepartment(departmentDisplayWrapBeens);
    }



    private void initBodyAndBottom() {
        setBottomSelect(0);
        setBodySelect(0);
    }

    //设置底部的选择
    private void setBottomSelect(int index) {
        switch (index) {
            //            case 0:
            //                rbtnHomeMain.setChecked(true);
            //                rbtnMessageMain.setChecked(false);
            //                break;
            //            case 1:
            //                rbtnHomeMain.setChecked(false);
            //                rbtnMessageMain.setChecked(true);
            //                rbtnFriendMain.setChecked(false);
            //                break;
            //            case 2:
            //                rbtnFriendMain.setChecked(true);
            //                rbtnMessageMain.setChecked(false);
            //                break;
            //            case 3:
            //                rbtnClinicalMain.setChecked(true);
            //                break;
            //            case 4:
            //                rbtnMineMain.setChecked(true);
            //                break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        ExitApplication.getInstance().exit();
        return super.onKeyDown(keyCode, event);
    }

    //设置body的选择
    private void setBodySelect(int index) {
        mVpContentMain.setCurrentItem(index);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        homeFragment.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ToSearch:
                if (resultCode == PatientAdapter.ReturnToMain) {
                    setBodySelect(2);
                }
                break;
            case ToAcceptReason:
                if (resultCode == AcceptReasonActivity.AcceptReason) {
                    setBodySelect(2);
                }
                break;
            case ToOpenAccount:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        int position = data.getIntExtra(PositionKey, -1);
                        if (position != -1 && position >= 0 && position < fragments.size()) {
                            setBodySelect(position);
                        }
                    }
                }
                break;
            case PersoninfoNewActivity.PersoninfoNewActivityCode:
                if (resultCode == RESULT_OK) {
                    getDoctor();
                }
                break;

        }
    }

    @OnClick({R.id.rbtn_home_main, R.id.rbtn_message_main, R.id.rbtn_friend_main, R.id.rbtn_clinical_main, R.id.rbtn_mine_main, R.id.rlayBottom})
    public void onClick(View view) {
        switch (view.getId()) {
            //            case R.id.rbtn_home_main:
            //                rbtnHomeMain.setChecked(true);
            //                rbtnMessageMain.setChecked(false);
            //                setBodySelect(0);
            //                break;
            //            case R.id.rbtn_message_main:
            //            case R.id.rlayBottom:
            //                rbtnMessageMain.setChecked(true);
            //                rbtnMessageMain.setChecked(false);
            //                setBodySelect(1);
            //                break;
            //            case R.id.rbtn_friend_main:
            //                rbtnFriendMain.setChecked(true);
            //                rbtnMessageMain.setChecked(false);
            //                setBodySelect(2);
            //                break;
            //            case R.id.rbtn_clinical_main:
            //                rbtnClinicalMain.setChecked(true);
            //                rbtnMessageMain.setChecked(false);
            //                setBodySelect(3);
            //                break;
            //            case R.id.rbtn_mine_main:
            //                rbtnMineMain.setChecked(true);
            //                rbtnMessageMain.setChecked(false);
            //                setBodySelect(4);
            //                break;
        }
    }

    public void selectPatientAndDoctor() {
        setBodySelect(2);
    }

}
