package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.TabLayoutPageAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.home_v.search.FragmentBean;
import com.kaurihealth.mvplib.mine_p.AccountDetailsPresenter;
import com.kaurihealth.mvplib.mine_p.IAccountDetailsView;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by mip on 2016/9/18.
 */
public class AccountDetailsActivity extends BaseActivity implements IAccountDetailsView {

    @Bind(R.id.tablay)
    TabLayout tablay;
    @Bind(R.id.vp_content)
    ViewPager vpContent;
    List<FragmentBean> fragmentBeanList = new LinkedList<>();
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
        createCurrentFragment();
    }

    private void createCurrentFragment() {
        if (fragmentBeanList.size() > 0) fragmentBeanList.clear();
        GetMoneyOutFragment fragmentGet = GetMoneyOutFragment.newInstance();
        ComeMoneyFragment fragmentPut = ComeMoneyFragment.newInstance();
        FragmentBean fragmentBeanGet = new FragmentBean(fragmentPut, "收取明细");
        FragmentBean fragmentBeanPut = new FragmentBean(fragmentGet, "提现明细");
        fragmentBeanList.add(fragmentBeanGet);
        fragmentBeanList.add(fragmentBeanPut);
    }


    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        TabLayoutPageAdapter tabLayoutPageAdapter = new TabLayoutPageAdapter(getSupportFragmentManager(), fragmentBeanList);
        vpContent.setAdapter(tabLayoutPageAdapter);
        tablay.setupWithViewPager(vpContent);
    }

    @Override
    public void switchPageUI(String className) {

    }


}
