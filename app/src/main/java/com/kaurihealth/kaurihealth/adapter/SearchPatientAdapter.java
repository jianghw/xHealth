package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.AddressDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanPatientBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchPatientAdapter extends RecyclerView.Adapter<SearchPatientAdapter.ViewHolder> {

    private final Context context;
    private final List<SearchBooleanPatientBean> beanList;
    private final ItemClickBack itemClickBack;

    public SearchPatientAdapter(Context context, List<SearchBooleanPatientBean> beanList, ItemClickBack itemClickBack) {
        this.context = context.getApplicationContext();
        this.beanList = beanList;
        this.itemClickBack = itemClickBack;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchpatient_iteam, null);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIteam(beanList.get(position), itemClickBack);
    }


    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.civPhoto)
        CircleImageView civPhoto;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvAge)
        TextView tvAge;
        @Bind(R.id.tvAddress)
        TextView tvAddress;
        @Bind(R.id.tvGender)
        TextView tvGender;
        @Bind(R.id.tvAddFriend)
        TextView tvAddFriend;

        private final Context context;
        private int mPatientID;
        private ItemClickBack mItemClickBack;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
        }

        public void setIteam(SearchBooleanPatientBean iteam, ItemClickBack itemClickBack) {
            this.mItemClickBack = itemClickBack;
            PatientDisplayBean bean = iteam.getItemsBean();
            mPatientID = bean.getPatientId();
            if (bean != null) {
                if (CheckUtils.checkUrlNotNull(bean.getAvatar())) {
                    ImageUrlUtils.picassoBySmallUrlCircle(context, bean.getAvatar(), civPhoto);
                } else {
                    civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
                }
                tvName.setText(bean.getFullName());
                AddressDisplayBean addressBean = bean.getAddress();
                String address;
                if (addressBean == null) {
                    address = "暂无";
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(addressBean.getProvince());
                    sb.append(addressBean.getCity());
                    sb.append(addressBean.getDistrict());
                    if (addressBean.addressLine1 != null) sb.append(addressBean.getAddressLine1());
                    address = sb.toString();
                }
                tvAddress.setText(address);
                tvAge.setText(String.format("%d岁", DateUtils.calculateAge(bean.getDateOfBirth())));
                tvGender.setText(bean.getGender());
                tvAddFriend.setEnabled(!iteam.isAdd());
                tvAddFriend.setText(iteam.isAdd() ? "已添加" : "添加");
            }
        }

        //添加“按钮”
        @OnClick({R.id.tvAddFriend})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvAddFriend:
                    mItemClickBack.onItemTextClick(mPatientID);
                    break;
                default:
                    break;
            }
        }
    }

    public interface ItemClickBack {
        void onItemTextClick(int patientID);
    }

}
