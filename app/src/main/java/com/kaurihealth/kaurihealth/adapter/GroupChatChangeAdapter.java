package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/10/24.
 * <p/>
 * Describe:
 */

public class GroupChatChangeAdapter extends CommonAdapter<ContactUserDisplayBean> {

    private List<String> doctorIdList;

    public GroupChatChangeAdapter(Context context, List<ContactUserDisplayBean> list, List<String> doctorIdList) {
        super(context, list);
        this.doctorIdList = doctorIdList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.lv_item_group_chat_change, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position), position);
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.civPhoto)
        CircleImageView civPhoto;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_gender)
        TextView tvGender;
        @Bind(R.id.tv_age)
        TextView tvAge;
        @Bind(R.id.checkbox)
        CheckBox checkbox;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(ContactUserDisplayBean contactUserDisplayBean, int position) {
            if (contactUserDisplayBean != null) {
                if (CheckUtils.checkUrlNotNull(contactUserDisplayBean.getAvatar())) {
                    ImageUrlUtils.picassoByUrlCircle(context, contactUserDisplayBean.getAvatar(), civPhoto);
                } else {
                    civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
                }

                tvName.setText(contactUserDisplayBean.getFullName());
                tvGender.setText(contactUserDisplayBean.getGender());
                int age = DateUtils.calculateAge(contactUserDisplayBean.getDateOfBirth());
                tvAge.setText(age + "岁");


                //防止checkBox混乱的问题
                if (contactUserDisplayBean.isTop()) {
                    checkbox.setChecked(true);
                } else {
                    checkbox.setChecked(false);
                }
                String myKauriHealthId = LocalData.getLocalData().getMyself().getKauriHealthId();
                if (contactUserDisplayBean.getKauriHealthId().equals(myKauriHealthId)) {
                    checkbox.setVisibility(View.GONE);
                } else {
                    checkbox.setVisibility(View.VISIBLE);
                }

                checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                    if (!isChecked) {
                        contactUserDisplayBean.setTop(false);
                    }
                    if (isChecked) {
                        contactUserDisplayBean.setTop(true);
                        if (!(doctorIdList.contains(contactUserDisplayBean.getKauriHealthId()))) {
                            doctorIdList.add(contactUserDisplayBean.getKauriHealthId());
                        }
                    } else if (doctorIdList.contains(contactUserDisplayBean.getKauriHealthId())) {
                        doctorIdList.remove(doctorIdList.indexOf(contactUserDisplayBean.getKauriHealthId()));
                    }
                });
            }
        }
    }
}
