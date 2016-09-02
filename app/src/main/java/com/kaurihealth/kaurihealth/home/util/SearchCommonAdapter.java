package com.kaurihealth.kaurihealth.home.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.home.activity.VerificationActivity;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public class SearchCommonAdapter extends RecyclerView.Adapter<SearchCommonAdapter.ViewHolder> {
    public static final int RequestCode = 20;
    private Context context;
    private List<SearchDoctorDisplayBean> dataContainer;
    private ISearchController iSearchController;
    Activity activity;

    public SearchCommonAdapter(Activity activity, List<SearchDoctorDisplayBean> dataContainer, ISearchController iSearchController) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.dataContainer = dataContainer;
        this.iSearchController = iSearchController;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchcommon_iteam, null);
        ViewHolder viewHolder = new ViewHolder(view, iSearchController, activity);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIteam(dataContainer.get(position));
    }


    @Override
    public int getItemCount() {
        return dataContainer.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.civPhoto)
        CircleImageView civPhoto;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvAge)
        TextView tvTitle;
        @Bind(R.id.tvAddress)
        TextView tvHospitalName;
        @Bind(R.id.tvDepartmentName)
        TextView tvDepartmentName;
        @Bind(R.id.tvAddFriend)
        TextView tvAddFriend;
        private ISearchController iSearchController;
        private SearchDoctorDisplayBean iteam;
        private Activity activity;
        private Context context;

        public ViewHolder(View itemView, ISearchController iSearchController, Activity activity) {
            super(itemView);
            this.activity = activity;
            this.context = activity.getApplicationContext();
            ButterKnife.bind(this, itemView);
            this.iSearchController = iSearchController;
        }

        public void setIteam(SearchDoctorDisplayBean iteam) {
            this.iteam = iteam;
            PicassoImageLoadUtil.loadUrlToImageView(iteam.avatar, civPhoto, context, ImagSizeMode.imageSizeBeens[4]);
            tvName.setText(iteam.fullName);
            tvHospitalName.setText(iteam.hospitalName);
            tvTitle.setText(iteam.hospitalTitle);
            tvDepartmentName.setText(iteam.departmentName);
            setIsFriendState(iSearchController.isDoctorFriend(iteam.doctorId));
        }

        private void setIsFriendState(boolean isFriendState) {
            tvAddFriend.setEnabled(!isFriendState);
            if (isFriendState) {
                tvAddFriend.setText("已添加");
            } else {
                tvAddFriend.setText("添加");
            }
        }

        @OnClick({R.id.tvAddFriend})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvAddFriend:
                    Bundle bundle = new Bundle();
                    bundle.putInt("relatedDoctorId", iteam.doctorId);
                    iSearchController.startActivityForResult(VerificationActivity.class, bundle, RequestCode);
                    break;
            }
        }
    }
}
