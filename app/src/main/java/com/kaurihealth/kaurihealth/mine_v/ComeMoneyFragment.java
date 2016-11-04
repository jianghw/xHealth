package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.Interface.IControl;
import com.kaurihealth.kaurihealth.adapter.MoneyComeAdapter;
import com.kaurihealth.kaurihealth.mine.util.IGetAmountDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComeMoneyFragment  extends Fragment {
    @Bind(R.id.tvMoney)
    TextView tvMoney;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.rfreshLay)
    SwipeRefreshLayout rfreshLay;
    private IControl iControl;
    MoneyComeAdapter adapter;
    private CompatorForAccountDetailUtils compatorForAccountDetailUtils;
    List<CreditTransactionDisplayBean> dataContainer = new ArrayList<>();
    IGetAmountDetail<List<CreditTransactionDisplayBean>> listener = new IGetAmountDetail<List<CreditTransactionDisplayBean>>() {


        @Override
        public void success(List<CreditTransactionDisplayBean> creditTransactionDisplayBeen) {
            dataContainer.clear();

            //将数据进行排序处理
            CreditTransactionDisplayBean[] creditTransactionDisplayBeen_array  = compatorForAccountDetailUtils.handleData(creditTransactionDisplayBeen);
            List<CreditTransactionDisplayBean> creditTransactionDisplayBeen_list = Arrays.asList(creditTransactionDisplayBeen_array);

            dataContainer.addAll(creditTransactionDisplayBeen_list);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            if (tvMoney != null) {
                double totalMoney = calculateTotalMoney(creditTransactionDisplayBeen);
                tvMoney.setText(String.format("%.2f", totalMoney));
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.come_money, null);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initUi() {
        compatorForAccountDetailUtils = new CompatorForAccountDetailUtils();
        adapter = new MoneyComeAdapter(getActivity(), dataContainer);
        lvContent.setAdapter(adapter);
        rfreshLay.setSize(SwipeRefreshLayout.DEFAULT);
        rfreshLay.setProgressBackgroundColor(R.color.linelogin);
        rfreshLay.setOnRefreshListener(() -> {
            if (iControl != null) {
                iControl.getData();
            }
        });
    }

    public IGetAmountDetail<List<CreditTransactionDisplayBean>> getListener() {
        return listener;
    }

    public void setController(IControl controller) {
        this.iControl = controller;
    }

    private double calculateTotalMoney(List<CreditTransactionDisplayBean> list) {
        double totalMoney = 0;
        for (CreditTransactionDisplayBean item : list) {
            totalMoney += item.amount;
        }
        return totalMoney;
    }

}
