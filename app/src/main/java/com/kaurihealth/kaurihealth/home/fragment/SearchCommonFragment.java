package com.kaurihealth.kaurihealth.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.SearchResultBean;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.service.ISearchService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.home.util.ISearch;
import com.kaurihealth.kaurihealth.home.util.ISearchController;
import com.kaurihealth.kaurihealth.home.util.SearchCommonAdapter;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 张磊 on 2016/7/26.
 * 介绍：
 */
public class SearchCommonFragment extends CommonFragment {
    private SearchCommonAdapter adapter;
    private List<SearchDoctorDisplayBean> dataContainer = new LinkedList<>();
    private InitialiseSearchRequestBean searchRequestBean = new InitialiseSearchRequestBean();
    private ISearchController iSearchController;
    private IGetter getter;

    {
        searchRequestBean.hit = 100;
        searchRequestBean.start = 0;

    }

    @Bind(R.id.iteamsHistory)
    RecyclerView iteamsHistory;
    @Bind(R.id.iteamsData)
    RecyclerView iteamsData;
    @Bind(R.id.tvHint)
    TextView tvHint;
    public ISearch search = new ISearch() {
        @Override
        public void search(final String content) {
            if (TextUtils.isEmpty(content)) {
                showHistory();
            } else {
                searchRequestBean.keyword = content;
                Call<SearchResultBean> searchResultBeanCall = searchService.KeywordSearch_out(searchRequestBean);
                final LoadingUtil loadingUtil = LoadingUtil.getInstance(getActivity());
                loadingUtil.show();
                searchResultBeanCall.enqueue(new Callback<SearchResultBean>() {
                    @Override
                    public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                        if (response.isSuccessful()) {
                            dataContainer.clear();
                            try {
                                List<SearchDoctorDisplayBean> items = response.body().result.items;
                                if (items.size() == 0) {
                                    showHint(String.format("搜索不到 %s", content));
                                } else {
                                    showRecyclerView();
                                }
                                for (SearchDoctorDisplayBean searchDoctorDisplayBean:items) {
                                    if (searchDoctorDisplayBean.doctorId !=getter.getMyself().doctorId){
                                        dataContainer.add(searchDoctorDisplayBean);
                                    }
                                }


                                adapter.notifyDataSetChanged();
                            } catch (NullPointerException ex) {
                                Bugtags.sendException(ex);
                                showToast(LoadingStatu.GetDataError.value);
                            }
                        } else {
                            showToast(LoadingStatu.GetDataError.value);
                        }
                        loadingUtil.dismiss();
                    }

                    @Override
                    public void onFailure(Call<SearchResultBean> call, Throwable t) {
                        loadingUtil.dismiss();
                        showToast(LoadingStatu.NetError.value);
                    }
                });
            }
        }

        @Override
        public void showHistory() {
            iteamsHistory.setVisibility(View.VISIBLE);
            iteamsData.setVisibility(View.GONE);
            tvHint.setVisibility(View.GONE);
        }

        @Override
        public void showHint(String hint) {
            tvHint.setVisibility(View.VISIBLE);
            tvHint.setText(hint);
            iteamsHistory.setVisibility(View.GONE);
            iteamsData.setVisibility(View.GONE);
        }

        @Override
        public void showRecyclerView() {
            iteamsHistory.setVisibility(View.GONE);
            iteamsData.setVisibility(View.VISIBLE);
            tvHint.setVisibility(View.GONE);
        }

        @Override
        public void addDoctorFriendSuccess(int doctorID) {
            iSearchController.addDoctorFriend(doctorID);
            if (adapter!=null) {
                adapter.notifyDataSetChanged();
            }
        }
    };
    private ISearchService searchService;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchcommon, null);
        ButterKnife.bind(this, view);
        initService();
        initViews();
        return view;
    }

    private void initViews() {
        getter = Getter.getInstance(getContext());
        adapter = new SearchCommonAdapter(getActivity(), dataContainer, iSearchController);
        iteamsData.setAdapter(adapter);
        iteamsData.setLayoutManager(new LinearLayoutManager(getContext()));
//        iteamsData.addItemDecoration(new VerticalSpaceItemDecoration(3));
    }

    private void initService() {
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix ,getContext());
        searchService = serviceFactory.getSearchService();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setScope(String scope) {
        searchRequestBean.scope = scope;
    }

    public void setISearchController(ISearchController iSearchController) {
        this.iSearchController = iSearchController;
    }
}
