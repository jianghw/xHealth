package com.kaurihealth.kaurihealth.search_v;
/**
 * 首页面搜索：医院
 */

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.common.adapter.DoctorAdapter;
import com.kaurihealth.mvplib.search_p.BlankHospitalPresenter;
import com.kaurihealth.mvplib.search_p.IBlankHospitalView;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Garnet_Wu on 2016/8/23.
 */
public class BlankHospitalFragment extends BaseFragment  implements  IBlankHospitalView{
    @Bind(R.id.history_recordes_fragment_blank_doctor_all)
    RelativeLayout history_recordes_fragment_blank_doctor_all;
    @Bind(R.id.lv_content)
    ListView lvContent;

    //下拉刷新控件    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;


    private List<SearchDoctorDisplayBean> sblist = new ArrayList<>();

    private DoctorAdapter doctorAdapter;

    @Inject
    BlankHospitalPresenter<IBlankHospitalView>  mPresenter;
    private SearchDoctorDisplayBean searchDoctorDisplayBean;

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_blank_doctor;
    }

    @Override
    protected void initPresenterAndData() {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initPagerView();
    }

    //初始化界面对象
    private void initPagerView() {
        doctorAdapter = new DoctorAdapter(this.getContext(), sblist);
        lvContent.setAdapter(doctorAdapter);
        doctorAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initDelayedView() {
        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mSwipeRefresh.setScrollUpChild(lvContent);
        mSwipeRefresh.setDistanceToTriggerSync(300);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mPresenter.doSomething()...;
            }
        });

        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                searchDoctorDisplayBean = (SearchDoctorDisplayBean) doctorAdapter.getItem(position);
                //mPresenter.doSomething2()....
            }
        });




    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();

    }

    //界面跳转UI
    @Override
    public void switchPageUI(String className) {

    }

    //IBlankHospitalView
    @Override
    public void setData(List<SearchDoctorDisplayBean> searchBeanslist) {
        //去除以前的数据
        sblist.clear();
        //添加新的数据
        sblist.addAll(searchBeanslist);
        //动态跟新listview
        doctorAdapter.notifyDataSetChanged();
        history_recordes_fragment_blank_doctor_all.setVisibility(View.GONE);

    }

    //IBlankHospitalView
    @Override
        public void clear() {
        sblist.clear();
        if (doctorAdapter != null) {
            doctorAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

    }

    // 清除历史搜索按钮对的点击事件
    @OnClick(R.id.fragment_blank_doctor_clear_record)
    public void clearRecord(){
        clear();
    }
}





