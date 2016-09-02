package com.kaurihealth.kaurihealth.home.util;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlibrary.widget.CircleImageView;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public class SearchPatientAdapter extends RecyclerView.Adapter<SearchPatientAdapter.ViewHolder> {
    Context context;
    List<PatientDisplayBean> dataContainer;
    private ISearchController iSearchController;
    private IDoctorPatientRelationshipService addDoctorFriendService;
    private Activity activity;

    public SearchPatientAdapter(Activity activity, List<PatientDisplayBean> dataContainer, ISearchController iSearchController) {
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.dataContainer = dataContainer;
        this.iSearchController = iSearchController;
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix, context);
        addDoctorFriendService = serviceFactory.getDoctorPatientRelationshipService();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searchpatient_iteam, null);
        ViewHolder viewHolder = new ViewHolder(view, iSearchController, addDoctorFriendService, context, activity);
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
        TextView tvAge;
        @Bind(R.id.tvAddress)
        TextView tvAddress;
        @Bind(R.id.tvGender)
        TextView tvGender;
        @Bind(R.id.tvAddFriend)
        TextView tvAddFriend;
        private ISearchController iSearchController;
        private PatientDisplayBean iteam;
        private IDoctorPatientRelationshipService addDoctorFriendService;
        private Context context;
        private Activity activity;

        public ViewHolder(View itemView, ISearchController iSearchController, IDoctorPatientRelationshipService addDoctorFriendService, Context context, Activity activity) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
            this.activity = activity;
            this.addDoctorFriendService = addDoctorFriendService;
            this.iSearchController = iSearchController;
        }

        public void setIteam(PatientDisplayBean iteam) {
            this.iteam = iteam;
            PicassoImageLoadUtil.loadUrlToImageView(iteam.getAvatar(), civPhoto, context, ImagSizeMode.imageSizeBeens[4]);
            tvName.setText(iteam.getFullName());
            try {
                tvAddress.setText(iteam.getAddress().addressLine1);
            } catch (NullPointerException ex) {

            }
            tvAge.setText(String.format("%d岁", Utils.calculateAge(iteam.getDateOfBirth())));
            tvGender.setText(iteam.getGender());
            setIsFriendState(iSearchController.isPatientFriend(iteam.getPatientId()));
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
                    final LoadingUtil loadingUtil = LoadingUtil.getInstance(activity);
                    loadingUtil.show();
                    Call<DoctorPatientRelationshipBean> doctorPatientRelationshipBCall = addDoctorFriendService.InsertNewRelationshipByDoctor(iteam.getPatientId());
                    doctorPatientRelationshipBCall.enqueue(new Callback<DoctorPatientRelationshipBean>() {
                        @Override
                        public void onResponse(Call<DoctorPatientRelationshipBean> call, Response<DoctorPatientRelationshipBean> response) {
                            if (response.isSuccessful()) {
                                iSearchController.addPatientFriend(iteam.getPatientId());
                                setIsFriendState(true);
                            } else {
                                showToat(LoadingStatu.GetDataError.value);
                            }
                            loadingUtil.dismiss();
                        }

                        @Override
                        public void onFailure(Call<DoctorPatientRelationshipBean> call, Throwable t) {
                            showToat(LoadingStatu.NetError.value);
                            loadingUtil.dismiss();
                        }
                    });
                    break;
            }
        }

        private void showToat(String mes) {
            Toast.makeText(context, mes, Toast.LENGTH_LONG).show();
        }
    }
}
