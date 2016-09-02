package com.kaurihealth.kaurihealth.search_v;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.common.adapter.PatientAdapter;
import com.kaurihealth.kaurihealth.util.Interface.ISkip;
import com.kaurihealth.mvplib.search_p.BlankPatientPresenter;
import com.kaurihealth.mvplib.search_p.IBlankPatientView;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Garnet_Wu on 2016/8/23.
 * 首页--> 右上角搜索-->患者标签搜索
 */
public class BlankPatientFragment extends BaseFragment implements IBlankPatientView{
    @Bind(R.id.history_recordes_fragment_blank_patient)
    RelativeLayout history_recordes_fragment_blank_patient;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    private List<PatientDisplayBean> sblist = new ArrayList<>();
    private PatientAdapter blankPatientAdapter;
    ISkip skip;

    @Inject
    BlankPatientPresenter<IBlankPatientView> mPresenter;


    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_blank_patient;
    }

    @Override
    protected void initPresenterAndData() {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initPagerView();

    }

    //初始化界面对象
    private void initPagerView() {
        //将list放入ListView并显示
        blankPatientAdapter = new PatientAdapter(this.getContext(), sblist);
        blankPatientAdapter.setSkip(skip);

        lvContent.setAdapter(blankPatientAdapter);
        blankPatientAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initDelayedView() {
        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(),R.color.colorPrimary),
                ContextCompat.getColor(getActivity(),R.color.colorAccent),
                ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark)
        );
        mSwipeRefresh.setScrollUpChild(lvContent);
        mSwipeRefresh.setDistanceToTriggerSync(300);
        mSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                       // mPresenter.doSomething()...
                    }
                }
        );

        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                PatientDisplayBean patientDisplayBean = (PatientDisplayBean) blankPatientAdapter.getItem(position);
                //mPresenter.doSomething2()...

            }
        });
    }

    @Override
    protected void lazyLoadingData() {  //懒加载
        //去调用SearchActivity里面的数据请求方法..
        mPresenter.onSubscribe();

    }

    //跳转界面
    @Override
    public void switchPageUI(String className) {

    }

    public void setSkip(ISkip skip) {
        this.skip = skip;
    }


    //设置数据
    @Override
    public void setData(List<PatientDisplayBean> patientDisplayBeanList) {
        //去除以前的数据
        sblist.clear();
        //添加新的数据
        sblist.addAll(patientDisplayBeanList);
        //动态跟新listview
        blankPatientAdapter.notifyDataSetChanged();
        //如果从网上拿到数据就将历史纪录布局设为不可见
        history_recordes_fragment_blank_patient.setVisibility(View.GONE);
    }

    //清除数据
    @Override
    public void clear() {
        sblist.clear();
        if (blankPatientAdapter != null) {
            blankPatientAdapter.notifyDataSetChanged();
        }
    }

    //清除历史搜索按钮的点击事件
    @OnClick(R.id.fragment_blank_patient_clear_record)
    public void clearRecord() {
        sblist.clear();
        blankPatientAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消订阅
        mPresenter.unSubscribe();
    }
}
