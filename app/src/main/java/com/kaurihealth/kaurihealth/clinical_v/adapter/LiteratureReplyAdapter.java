package com.kaurihealth.kaurihealth.clinical_v.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.CommonAdapter;
import com.kaurihealth.utilslib.image.PickImage;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/7/5.
 * 备注：回复
 */
public class LiteratureReplyAdapter extends CommonAdapter implements PickImage.SuccessInterface {

    private int userId;
    private String userFullName;

    private ListView listView;

    public LiteratureReplyAdapter(Context context, int id, String name, List<LiteratureReplyDisplayBean> list, ListView lvLiteratureReply) {
        super(context, list);
        userFullName = name;
        userId = id;
        listView = lvLiteratureReply;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_iteraturereply, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.setInfo((LiteratureReplyDisplayBean) list.get(position));
        return convertView;
    }

    @Override
    public void success() {
        setListViewHeight(listView);
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.tv_userName)
        TextView tvUserName;//用户name
        @Bind(R.id.tv_comment)
        TextView tvComment;//
        @Bind(R.id.tv_userFullName)
        TextView tvUserFullName;
        @Bind(R.id.tv_literatureReplyComment)
        TextView tvLiteratureReplyComment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(LiteratureReplyDisplayBean literatureReplyDisplayBean) {
            tvUserName.setText(literatureReplyDisplayBean.userFullName);
            tvUserFullName.setText(userFullName + ":");
            tvLiteratureReplyComment.setText(literatureReplyDisplayBean.literatureReplyComment);
            if (literatureReplyDisplayBean.userId == userId) {
                tvUserName.setText(userFullName);
                tvUserFullName.setText(":");
            }
        }
    }

    private void setListViewHeight(ListView listView) {
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
//        setListViewHeight(listView);
    }
}
