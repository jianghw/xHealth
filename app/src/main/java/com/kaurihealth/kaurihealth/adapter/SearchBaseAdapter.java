package com.kaurihealth.kaurihealth.adapter;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
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
    private final ItemClickBack mItemClickBack;


    public SearchBaseAdapter(FragmentActivity activity, List<SearchBooleanResultBean> dataContainer, ItemClickBack itemClickBack) {
        this.context = activity.getApplication();
        this.list = dataContainer;
        this.mItemClickBack = itemClickBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchcommon_item, null);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIteam(list.get(position), mItemClickBack);
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
        private ItemClickBack itemClickBack;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context.getApplicationContext();
            ButterKnife.bind(this, itemView);
        }

        public void setIteam(SearchBooleanResultBean bean, ItemClickBack itemClickBack) {
            this.itemClickBack = itemClickBack;
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
            markDoctor();
        }

        private void markDoctor() {
            try {
                boolean isMe = LocalData.getLocalData().getMyself().getDoctorId() == Integer.valueOf(mDoctorID);
                if (isMe) {
                    tvAddFriend.setEnabled(false);
                    tvAddFriend.setText("本人");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //添加“按钮”
        @OnClick({R.id.tvAddFriend})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvAddFriend:
                    itemClickBack.onItemTextClick(mDoctorID);
                    break;
                default:
                    break;
            }
        }
    }

    public interface ItemClickBack {
        void onItemTextClick(String doctorID);
    }
}
