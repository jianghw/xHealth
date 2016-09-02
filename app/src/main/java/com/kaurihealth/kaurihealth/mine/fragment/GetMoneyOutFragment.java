package com.kaurihealth.kaurihealth.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.home.util.CompatorForAccountDetailUtils;
import com.kaurihealth.kaurihealth.mine.Interface.IControl;
import com.kaurihealth.kaurihealth.mine.adapter.GetMoneyOutAdapter;
import com.kaurihealth.kaurihealth.mine.util.IGetAmountDetail;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/7/18.
 * 介绍：
 */
public class GetMoneyOutFragment extends CommonFragment {
    @Bind(R.id.tvMoney)
    TextView tvMoney;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.rfreshLay)
    SwipeRefreshLayout rfreshLay;
    private IControl iControl;
    private CompatorForAccountDetailUtils compatorForAccountDetailUtils;
    List<CreditTransactionDisplayBean> dataContainer = new LinkedList<>();
    IGetAmountDetail<List<CreditTransactionDisplayBean>> listener = new IGetAmountDetail<List<CreditTransactionDisplayBean>>() {


        @Override
        public void success(List<CreditTransactionDisplayBean> creditTransactionDisplayBeen) {
            dataContainer.clear();

            //将数据进行排序处理
            CreditTransactionDisplayBean[] creditTransactionDisplayBeen_array  =compatorForAccountDetailUtils.handleData(creditTransactionDisplayBeen);
            List<CreditTransactionDisplayBean> creditTransactionDisplayBeen_list = Arrays.asList(creditTransactionDisplayBeen_array);

            dataContainer.addAll(creditTransactionDisplayBeen_list);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            if (tvMoney != null) {
                tvMoney.setText(String.format("%.2f", calculateTotalMoney(creditTransactionDisplayBeen)));
            }
        }

        @Override
        public void complete() {
            if (rfreshLay != null) {
                if (rfreshLay.isRefreshing()) {
                    rfreshLay.setRefreshing(false);
                }
            }
        }
    };
    private GetMoneyOutAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.get_money, null);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        compatorForAccountDetailUtils = new CompatorForAccountDetailUtils();
        rfreshLay.setSize(SwipeRefreshLayout.DEFAULT);
        rfreshLay.setColorSchemeResources(R.color.holo_blue_light_new
                , R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);
        rfreshLay.setProgressBackgroundColor(R.color.linelogin);
        adapter = new GetMoneyOutAdapter(getContext(), dataContainer);
        lvContent.setAdapter(adapter);
        rfreshLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iControl.getData();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public IGetAmountDetail<List<CreditTransactionDisplayBean>> getListener() {
        return listener;
    }

    public void setController(IControl controller) {
        this.iControl = controller;
    }

    private double calculateTotalMoney(List<CreditTransactionDisplayBean> list) {
        double totalMoney = 0;
        for (CreditTransactionDisplayBean iteam : list) {
            totalMoney += iteam.amount;
        }
        return Math.abs(totalMoney);
    }
}
