package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.util.DateConvertUtils;
import com.kaurihealth.mvplib.mine_p.ComeMoneyFragmentPresenter;
import com.kaurihealth.mvplib.mine_p.IComeMoneyFragmentView;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class ComeMoneyFragment extends BaseFragment implements IComeMoneyFragmentView {
    @Bind(R.id.tvMoney)
    TextView tvMoney;
    @Bind(R.id.lay_out)
    LinearLayout lay_out;
    View view;

    @Inject
    ComeMoneyFragmentPresenter<IComeMoneyFragmentView> mPresenter;

    private CompatorForAccountDetailUtils compatorForAccountDetailUtils;


    public static ComeMoneyFragment newInstance() {
        return new ComeMoneyFragment();
    }


    @Override
    protected int getFragmentLayoutID() {
        return R.layout.come_money;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        compatorForAccountDetailUtils = new CompatorForAccountDetailUtils();
        mPresenter.onSubscribe();
    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();
    }


    private double calculateTotalMoney(List<CreditTransactionDisplayBean> list) {
        double totalMoney = 0;
        for (CreditTransactionDisplayBean item : list) {
            totalMoney += item.amount;
        }
        return totalMoney;
    }

    @Override
    public void switchPageUI(String className) {

    }

    /**
     * 得到过滤后的bean
     */
    @Override
    public void getBean(List<CreditTransactionDisplayBean> creditTransactionDisplayBeen) {
        if (lay_out.getChildCount() != 0) {
            lay_out.removeAllViews();
        }
        //将数据进行排序处理
        CreditTransactionDisplayBean[] creditTransactionDisplayBeen_array = compatorForAccountDetailUtils.handleData(creditTransactionDisplayBeen);
        List<CreditTransactionDisplayBean> creditTransactionDisplayBeen_list = Arrays.asList(creditTransactionDisplayBeen_array);

        // 为更改ui换LinearLayout
        for (CreditTransactionDisplayBean bean : creditTransactionDisplayBeen_list) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.come_money_detail, null);
            TextView tvAge = (TextView) view.findViewById(R.id.tvAge);
            TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
            TextView tvMoney = (TextView) view.findViewById(R.id.tvMoney);
            View line = view.findViewById(R.id.v_line);
            tvAge.setText("   " + bean.order.patientRequest.requestType);
            tvDate.setText(DateConvertUtils.getWeekOfDate(bean.order.patientRequest.endDate, null));
            tvMoney.setText(String.format("+%.2f元", bean.amount));
            //去掉最后那条分割线
            if (creditTransactionDisplayBeen_list.get(creditTransactionDisplayBeen_list.size() - 1).equals(bean)) {
                line.setVisibility(View.GONE);
            }
            lay_out.addView(view);
        }

        if (tvMoney != null) {
            double totalMoney = calculateTotalMoney(creditTransactionDisplayBeen);
            tvMoney.setText(String.format("%.2f", totalMoney));
        }
    }
}
