package com.kaurihealth.chatlib.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.kaurihealth.chatlib.R;
import com.kaurihealth.chatlib.utils.JoinTypeMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by wli on 15/9/17.
 * 聊天页面中的文本 item 对应的 holder
 */
public class LCIMChatItemHihtTextHolder extends LCIMCommonViewHolder {

    protected boolean isLeft;

    protected TextView timeView;
    protected TextView hintView;
    private ImageView avatarView;


    public LCIMChatItemHihtTextHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft ? R.layout.lcim_chat_item_left_layout : R.layout.lcim_chat_item_right_layout);
        this.isLeft = isLeft;
        initView();
    }

    public void initView() {
        timeView = (TextView) itemView.findViewById(R.id.chat_left_tv_time);
        hintView = (TextView) itemView.findViewById(R.id.chat_left_tv_hint);
        avatarView = (ImageView) itemView.findViewById(R.id.chat_left_iv_avatar);
    }

    @Override
    public void bindData(Object o) {
        AVIMMessage mMessage = (AVIMMessage) o;
        if (mMessage instanceof JoinTypeMessage) {
            avatarView.setVisibility(View.GONE);
            JoinTypeMessage joinTypeMessage = (JoinTypeMessage) mMessage;
            timeView.setText(millisecsToDateString(new Date().getTime()));
            hintView.setVisibility(View.VISIBLE);
            hintView.setText(joinTypeMessage.getText());
        }
    }

    //TODO 展示更人性一点
    private static String millisecsToDateString(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
        return format.format(new Date(timestamp));
    }
}
