package com.kaurihealth.kaurihealth.adapter;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchBaseAdapter extends RecyclerView.Adapter<SearchBaseAdapter.ViewHolder> {

    private final Application context;
    private final List<SearchBooleanResultBean> list;
    private static ItemClickBack mItemClickBack;

    public SearchBaseAdapter(FragmentActivity activity, List<SearchBooleanResultBean> dataContainer) {
        this.context = activity.getApplication();
        this.list = dataContainer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchcommon_iteam, null);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIteam(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
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

        private Context context;
        private String mDoctorID;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context.getApplicationContext();
            ButterKnife.bind(this, itemView);
        }

        public void setIteam(SearchBooleanResultBean bean) {
            SearchResultBean.ResultBean.ItemsBean itemBean = bean.getItemsBean();
            mDoctorID = itemBean.getDoctorid();
            if (CheckUtils.checkUrlNotNull(itemBean.getAvatar())) {
                ImageUrlUtils.picassoBySmallUrlCircle(context, itemBean.getAvatar(), civPhoto);
            } else {
                civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
            }

            tvName.setText(itemBean.getFullname());
            tvHospitalName.setText(itemBean.getHospitalname());
            tvTitle.setText(itemBean.getHospitaltitle());
            tvDepartmentName.setText(itemBean.getDepartmentname());
            setIsFriendState(bean.isAdd());
        }

        private void setIsFriendState(boolean isFriendState) {
            tvAddFriend.setEnabled(!isFriendState);
            tvAddFriend.setText(isFriendState ? "已添加" : "添加");
        }

        //添加“按钮”
        @OnClick({R.id.tvAddFriend})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvAddFriend:
                    mItemClickBack.onItemTextClick(mDoctorID);
                    break;
                default:
                    break;
            }
        }
    }

    public void setItemClickBack(ItemClickBack itemClickBack) {
        mItemClickBack = itemClickBack;
    }

    public interface ItemClickBack {
        void onItemTextClick(String doctorID);
    }
}
