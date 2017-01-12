package com.kaurihealth.chatlib.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.kaurihealth.chatlib.R;
import com.kaurihealth.chatlib.utils.MedicalTypeMessage;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.utilslib.CheckUtils;

import java.util.List;


/**
 * Created by wli on 15/9/17.
 * 聊天页面中的病历 名片 item 对应的 holder
 */
public class LCIMChatItemSickCardHolder extends LCIMChatItemHolder {

    private RelativeLayout cardLay;
    private TextView nameTv;
    private TextView genderTv;
    private TextView ageTv;
    private TextView sicknessesTv;
    private TextView dateTv;
    private TextView typeTv;

    public LCIMChatItemSickCardHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft);
    }

    @Override
    public void initView() {
        super.initView();
        conventLayout.addView(View.inflate(getContext(), R.layout.lcim_chat_item_sick_card_layout, null));
        cardLay = (RelativeLayout) itemView.findViewById(R.id.lay_card);
        nameTv = (TextView) itemView.findViewById(R.id.tv_patient_name);
        genderTv = (TextView) itemView.findViewById(R.id.tv_gender);
        ageTv = (TextView) itemView.findViewById(R.id.tv_patient_age);
        sicknessesTv = (TextView) itemView.findViewById(R.id.tv_sicknesses);
        dateTv = (TextView) itemView.findViewById(R.id.tv_recordDate);
        typeTv = (TextView) itemView.findViewById(R.id.tv_recordType);
        if (isLeft) {
            cardLay.setBackgroundResource(R.drawable.other_send);
        } else {
            cardLay.setBackgroundResource(R.drawable.my_self);
        }
    }

    @Override
    public void bindData(Object o) {
        super.bindData(o);

        AVIMMessage message = (AVIMMessage) o;
        if (message instanceof MedicalTypeMessage) {
            MedicalTypeMessage medicalTypeMessage = (MedicalTypeMessage) message;
            MainPagePatientDisplayBean bean = medicalTypeMessage.getAttrs();
            if(bean!=null){
                nameTv.setText(CheckUtils.checkTextContent(bean.getPatientFullName()));
                genderTv.setText(CheckUtils.checkTextContent(bean.getGender()));
                ageTv.setText(CheckUtils.checkTextContent(bean.age));
                List<SicknessesBean> sickList = bean.getSicknesses();
                StringBuilder stringBuilder = new StringBuilder();
                if (sickList != null && !sickList.isEmpty()) {
                    for (SicknessesBean sicknessesBean : sickList) {
                        stringBuilder.append(sicknessesBean.getSicknessName());
                        stringBuilder.append("/");
                    }
                } else {
                    stringBuilder.append("暂无记录");
                    stringBuilder.append("/");
                }
                String disease = stringBuilder.toString();
                String newDis = disease.substring(0, disease.length() - 1);
                sicknessesTv.setText(CheckUtils.checkTextContent(newDis));

                dateTv.setText(CheckUtils.checkTextContent(bean.getPatientFullName()));
                typeTv.setText(CheckUtils.checkTextContent(bean.getRecordType()));
            }
        }
    }
}