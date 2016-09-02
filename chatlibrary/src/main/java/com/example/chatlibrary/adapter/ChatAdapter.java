package com.example.chatlibrary.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.chatlibrary.R;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.squareup.picasso.Picasso;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 张磊 on 2016/5/11.
 * 介绍：
 */
public class ChatAdapter extends CommonAdapter<AVIMMessage> {

    private final int TypeSelf = 1;
    private final int TypeOther = 2;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.SIMPLIFIED_CHINESE);
    private String urlSelf;
    private String urlOther;
    private String selfKauriHealthId;

    public ChatAdapter(Context context, List<AVIMMessage> list, String urlSelf, String urlOther, String selfKauriHealthId) {
        super(context, list);
        this.selfKauriHealthId = selfKauriHealthId;
        this.urlSelf = urlSelf;
        this.urlOther = urlOther;
        logUtil = LogFactory.getSimpleLog(getClass().getName());
    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        int type = getItemViewType(position);
        viewHolder = new ViewHolder();
        switch (type) {
            case TypeSelf:
                convertView = LayoutInflater.from(context).inflate(R.layout.chatiteam_self, null);
                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
                viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.circleiv_photo);
                if (!TextUtils.isEmpty(urlSelf)) {
                    Picasso.with(context).load(urlSelf + "@200w_200h.jpg").resize(200, 200).placeholder(R.mipmap.photo_holder).error(R.mipmap.ic_patient_error).centerInside().into(viewHolder.ivPhoto);
                }
                break;
            case TypeOther:
                convertView = LayoutInflater.from(context).inflate(R.layout.chatiteam_other, null);
                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
                viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.circleiv_photo);
                if (!TextUtils.isEmpty(urlOther)) {
                    Picasso.with(context).load(urlOther + "@200w_200h.jpg").resize(200, 200).placeholder(R.mipmap.photo_holder).error(R.mipmap.ic_patient_error).centerInside().into(viewHolder.ivPhoto);
                }
                break;
        }
        AVIMMessage iteam = list.get(position);
        calendar.setTime(new Date(iteam.getTimestamp()));
        String time = formatter.format(calendar.getTime());
        String content = null;
        if (iteam instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage) iteam).getText();
        }
        if (content != null) {
            viewHolder.tvContent.setText(content);
        }
        viewHolder.tvTime.setText(time);
        return convertView;
    }

    public static class ViewHolder {
        public TextView tvTime;
        public TextView tvContent;
        public ImageView ivPhoto;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getFrom().equals(selfKauriHealthId)) {
            logUtil.i("自己的view");
            return TypeSelf;
        } else {
            logUtil.i("别人的view");
            return TypeOther;
        }
    }
}
