package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.mine.Interface.IControl;
import com.kaurihealth.kaurihealth.adapter.TabLayoutPageAdapter;
import com.kaurihealth.kaurihealth.home_v.search.FragmentBean;
import com.kaurihealth.kaurihealth.mine.util.IGetAmountDetail;
import com.kaurihealth.mvplib.mine_p.AccountDetailsPresenter;
import com.kaurihealth.mvplib.mine_p.IAccountDetailsView;
import com.kaurihealth.utilslib.CheckUtils;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/9/18.
 */
public class AccountDetailsActivity extends BaseActivity implements IAccountDetailsView{

    public IControl controller = new IControl() {
        @Override
        public void getData() {
            mPresenter.onSubscribe();
        }
    };

    private IGetAmountDetail<List<CreditTransactionDisplayBean>> getMoneyListener;
    private IGetAmountDetail<List<CreditTransactionDisplayBean>> comeMoneyListener;
    @Bind(R.id.tablay)
    TabLayout tablay;
    @Bind(R.id.vp_content)
    ViewPager vpContent;
    ComeMoneyFragment fragmentPut = new ComeMoneyFragment();
    GetMoneyOutFragment fragmentGet = new GetMoneyOutFragment();
    List<FragmentBean> fragmentBeanList = new LinkedList<>();
    FragmentBean fragmentBeanGet = new FragmentBean();
    FragmentBean fragmentBeanPut = new FragmentBean();

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

    @Inject
    AccountDetailsPresenter<IAccountDetailsView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.account_details;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }


    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        TabLayoutPageAdapter tabLayoutPageAdapter = new TabLayoutPageAdapter(getSupportFragmentManager(), fragmentBeanList);
        vpContent.setAdapter(tabLayoutPageAdapter);
        tablay.setupWithViewPager(vpContent);
        mPresenter.onSubscribe();
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 得到数据
     * @param bean
     */
    @Override
    public void getAmountDetail(List<CreditTransactionDisplayBean> bean) {
        List<CreditTransactionDisplayBean> displayBeen= CheckUtils.checkNotNull(bean,"bean is null");
        classify(displayBeen);
        getMoneyListener.complete();
        comeMoneyListener.complete();
    }

    //对数据进行分类
    private void classify(List<CreditTransactionDisplayBean> data) {
        List<CreditTransactionDisplayBean> getMoneyList = new LinkedList<>();
        List<CreditTransactionDisplayBean> comeMoneyList = new LinkedList<>();

        for (CreditTransactionDisplayBean item : data) {
            switch (item.type) {
                case "提现":
                    getMoneyList.add(item);
                    break;
                case "到款":
                    comeMoneyList.add(item);
                    break;
                case "提现失败":
                    break;
            }
        }
        getMoneyListener.success(getMoneyList);
        comeMoneyListener.success(comeMoneyList);
    }
}
