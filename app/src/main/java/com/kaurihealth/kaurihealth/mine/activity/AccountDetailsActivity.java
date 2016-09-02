package com.kaurihealth.kaurihealth.mine.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.datalib.service.ICreditTransactionService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.Interface.IControl;
import com.kaurihealth.kaurihealth.mine.adapter.FragmentPageAdapter;
import com.kaurihealth.kaurihealth.mine.bean.FragmentBean;
import com.kaurihealth.kaurihealth.mine.fragment.GetMoneyOutFragment;
import com.kaurihealth.kaurihealth.mine.fragment.ComeMoneyFragment;
import com.kaurihealth.kaurihealth.mine.util.IGetAmountDetail;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDetailsActivity extends CommonActivity {
    public IControl controller = new IControl() {
        @Override
        public void getData() {
            getAmountDetail();
        }
    };

    private IGetAmountDetail<List<CreditTransactionDisplayBean>> getMoneyListener;
    private IGetAmountDetail<List<CreditTransactionDisplayBean>> comeMoneyListener;
    @Bind(R.id.tablay)
    TabLayout tablay;
    @Bind(R.id.vp_content)
    ViewPager vpContent;
    private String[] titles = {"收取明细", "提现明细"};
    ComeMoneyFragment fragmentPut = new ComeMoneyFragment();
    GetMoneyOutFragment fragmentGet = new GetMoneyOutFragment();
    List<FragmentBean> fragmentBeanList = new LinkedList<>();
    FragmentBean fragmentBeanGet = new FragmentBean();
    FragmentBean fragmentBeanPut = new FragmentBean();
    private Call<List<CreditTransactionDisplayBean>> amountDetailCall;
    private ICreditTransactionService creditTransactions;

    {
        fragmentBeanGet.fragment = fragmentGet;
        fragmentBeanGet.title = "提现明细";
        fragmentBeanPut.fragment = fragmentPut;
        fragmentBeanPut.title = "收取明细";
        fragmentBeanList.add(fragmentBeanPut);
        fragmentBeanList.add(fragmentBeanGet);
        getMoneyListener = fragmentGet.getListener();
        comeMoneyListener = fragmentPut.getListener();
        fragmentGet.setController(controller);
        fragmentPut.setController(controller);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
        setBack(R.id.iv_back);
        ButterKnife.bind(this);
        initUi();
        initGetDataService();
        controller.getData();
    }

    //初始化界面
    private void initUi() {
        FragmentPageAdapter fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentBeanList);
        vpContent.setAdapter(fragmentPageAdapter);
        tablay.setupWithViewPager(vpContent);
    }

    //初始化获取数据逻辑
    private void initGetDataService() {
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        creditTransactions = serviceFactory.getCreditTransactions();

    }


    //获取数据
    private void getAmountDetail() {
        amountDetailCall = creditTransactions.CreditTransactions();
        amountDetailCall.enqueue(new Callback<List<CreditTransactionDisplayBean>>() {
            @Override
            public void onResponse(Call<List<CreditTransactionDisplayBean>> call, Response<List<CreditTransactionDisplayBean>> response) {
                if (response.isSuccessful()) {
                    classify(response.body());
                }
                getMoneyListener.complete();
                comeMoneyListener.complete();
            }

            @Override
            public void onFailure(Call<List<CreditTransactionDisplayBean>> call, Throwable t) {
                getMoneyListener.complete();
                comeMoneyListener.complete();
            }
        });
    }

    //对数据进行分类
    private void classify(List<CreditTransactionDisplayBean> datas) {
        List<CreditTransactionDisplayBean> getMoneyList = new LinkedList<>();
        List<CreditTransactionDisplayBean> comeMoneyList = new LinkedList<>();
        for (CreditTransactionDisplayBean iteam : datas) {
            switch (iteam.type) {
                case "提现":
                    getMoneyList.add(iteam);
                    break;
                case "到款":
                    comeMoneyList.add(iteam);
                    break;
                case "提现失败":
                    break;
            }
        }
        getMoneyListener.success(getMoneyList);
        comeMoneyListener.success(comeMoneyList);
    }
}
