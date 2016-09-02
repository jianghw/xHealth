package com.kaurihealth.kaurihealth.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.service.IPrescriptionService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.adapter.PrescriptionAdapter;
import com.kaurihealth.kaurihealth.home.util.BundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IBundleFactory;
import com.kaurihealth.kaurihealth.home.util.Interface.IGetBundleHealthyRecord;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */

public class PrescriptionActivity extends CommonActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshTool;
    private int patientId;
    private String kauriHealthId;
    @Bind(R.id.tv_operate)
    TextView tvStore;
    private List<PrescriptionBean> list;
    private PrescriptionAdapter prescriptionAdapter;
    public static final int Update = 8;
    private IPrescriptionService prescriptionService;
    private boolean isAble;
    private IGetter getter;
    private IGetBundleHealthyRecord iGetBundleHealthyRecord;
    private Bundle bundle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    /**
     * 添加处方
     */
    @OnClick(R.id.tv_operate)
    public void tv_operate() {
        skipToForResult(AddPrescriptionActivity.class, bundle, 1);
    }

    @Override
    public void init() {
        super.init();
        bundle = getBundle();
        getter = Getter.getInstance(getApplicationContext());
        IBundleFactory iBundleFactory = new BundleFactory();
        iGetBundleHealthyRecord = iBundleFactory.getIGetBundleHealthyRecord(getBundle());
        offCurActivity(getBundle());
        list = new ArrayList<>();
        prescriptionAdapter = new PrescriptionAdapter(this, list);
        lvContent.setAdapter(prescriptionAdapter);
        setBack(R.id.iv_back);
        patientId = iGetBundleHealthyRecord.getPatientId();
        kauriHealthId = iGetBundleHealthyRecord.getKauriHealthId();
        prescriptionService = new ServiceFactory(Url.prefix, getApplicationContext()).getPrescriptionService();
        getData();

        refreshTool.setSize(SwipeRefreshLayout.DEFAULT);
        refreshTool.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        refreshTool.setProgressBackgroundColor(R.color.linelogin);
        refreshTool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    /**
     * 获取所有的处方
     */
    protected void getData() {
        Call<List<PrescriptionBean>> call = prescriptionService.LoadPrescriptionsByPatientId(patientId);
        call.enqueue(new Callback<List<PrescriptionBean>>() {
            @Override
            public void onResponse(Call<List<PrescriptionBean>> call, Response<List<PrescriptionBean>> response) {
                if (refreshTool != null) {
                    refreshTool.setRefreshing(false);
                }
                if (response.isSuccessful()) {
                    List<PrescriptionBean> prescriptionList = response.body();
                    list.clear();
                    list.addAll(prescriptionList);
                    prescriptionAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<PrescriptionBean>> call, Throwable t) {
                showToast(LoadingStatu.NetError.value);
                if (refreshTool != null) {
                    refreshTool.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Update) {
            getData();
        }
    }


    /**
     * 设置当前界面为不可编辑状态
     */
    private void offCurActivity(Bundle bundle) {
        isAble = iGetBundleHealthyRecord.isAble();
        if (!isAble) {
            tvStore.setVisibility(View.GONE);
            isAble = false;
        } else {
            isAble = true;
        }

    }

}

