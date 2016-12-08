package com.kaurihealth.kaurihealth.clinical_v.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.CommonAdapter;
import com.kaurihealth.kaurihealth.clinical_v.Interface.DeleteLiteratureCommentListener;
import com.kaurihealth.kaurihealth.clinical_v.activity.DynamicActivity;
import com.kaurihealth.kaurihealth.clinical_v.activity.LiteratureCommentActivity;
import com.kaurihealth.kaurihealth.util.DateConvertUtils;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.TranslationAnim;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.image.PickImage;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/7/4.
 * 备注：热门评论（显示）
 */
public class LiteratureCommentAdapter extends CommonAdapter implements PickImage.SuccessInterface {
    private Activity activity;
    private ListView pullLayout;
    private DynamicActivity activityIntance;

    public LiteratureCommentAdapter(Activity activity, List<LiteratureCommentDisplayBean> list, ListView lvdynamic) {
        super(activity, list);
        this.activity = activity;
        this.pullLayout = lvdynamic;
        activityIntance = (DynamicActivity) activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_literaturecomment_new, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView, position);
        viewHolder.setInfo((LiteratureCommentDisplayBean) list.get(position));
        return convertView;
    }

    /**
     * 设置listview 的高度
     *
     * @param refreshLay
     */
    private void setListViewHeight(ListView refreshLay) {
        int childCount = refreshLay.getChildCount();
        ListView listView = null;
        int countTemp = 0;
        for (; countTemp < childCount; countTemp++) {
            View childAt = refreshLay.getChildAt(countTemp);
            if (childAt instanceof ListView) {
                listView = (ListView) childAt;
                return;
            }
        }
        if (countTemp == childCount) {
            return;
        }
        if (listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        for (int i = 0; i < listAdapter.getCount(); i++) {
            ViewGroup viewParent = (ViewGroup) listAdapter.getView(i, null, listView);
            ListView temp2 = null;
            for (int j = 0; j < viewParent.getChildCount(); j++) {
                View childAt = viewParent.getChildAt(i);
                if (childAt instanceof ListView) {
                    temp2 = (ListView) childAt;
                    return;
                }
            }
            if (temp2 != null) {
                ((PickImage.SuccessInterface) temp2.getAdapter()).success();
            }
        }
//        int totalHeight = 0;
//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0);
//            totalHeight += listItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
    }

    @Override
    public void success() {
        setListViewHeight(pullLayout);
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }


    class ViewHolder {
        @Bind(R.id.iv_userAvartar)
        CircleImageView ivUserAvartar;
        @Bind(R.id.tv_userFullName)
        TextView tvUserFullName;
        @Bind(R.id.tv_literatureCommentContent)
        TextView tvLiteratureCommentContent;
        @Bind(R.id.tv_createTime)
        TextView tvCreateTime;
        @Bind(R.id.tv_delete)
        TextView tvDelete;
        @Bind(R.id.tv_browse)
        TextView tvBrowse;
        @Bind(R.id.lv_LiteratureReply)
        ListView lvLiteratureReply;

        private List<LiteratureReplyDisplayBean> iteratureReplyDisplayBeanList;
        private LiteratureReplyAdapter literatureReplyAdapter;
        private DeleteLiteratureCommentListener listener;


        ViewHolder(View view, int position) {
            ButterKnife.bind(this, view);
            init(position, listener);
        }

        /**
         * 点击回复
         *
         * @param position
         * @param listener
         */
        private void init(final int position, final DeleteLiteratureCommentListener listener) {
            tvBrowse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LiteratureCommentDisplayBean bean = (LiteratureCommentDisplayBean) list.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("LiteratureCommentDisplayBean", bean);
                    TranslationAnim.zlStartActivityForResult(activity, LiteratureCommentActivity.class, bundle, 0);
                }
            });

            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("是否要删除评论？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //接口回调（一）
//                                    listener.DeleteSuccess((((LiteratureCommentDisplayBean)list.get(position)).literatureCommentId),position);
                                    //activity对象获取（二）
                                    activityIntance.DeleteSuccess((((LiteratureCommentDisplayBean) list.get(position)).literatureCommentId), position);
                                }
                            }).show();


                }
            });
        }

        private void setInfo(LiteratureCommentDisplayBean literatureCommentDisplayBean) {
            if (literatureCommentDisplayBean != null) {

                if (CheckUtils.checkUrlNotNull(literatureCommentDisplayBean.userAvartar)) {
                    ImageUrlUtils.picassoBySmallUrlCircle(context, literatureCommentDisplayBean.userAvartar, ivUserAvartar);
                } else {
                    ivUserAvartar.setImageResource(R.mipmap.ic_circle_head_green);
                }
            }
            //不是自己评论的不可以删除
            tvDelete.setVisibility(literatureCommentDisplayBean.userId != LocalData.getLocalData().getMyself().userId ? View.GONE : View.VISIBLE);
            tvUserFullName.setText(literatureCommentDisplayBean.userFullName);//用户名
            tvLiteratureCommentContent.setText(literatureCommentDisplayBean.literatureCommentContent);//评论内容
            tvCreateTime.setText(DateConvertUtils.getWeekOfDate(null, literatureCommentDisplayBean.createTime));
//            tvDelete.setVisibility(View.INVISIBLE);

            iteratureReplyDisplayBeanList = new ArrayList<>();
            literatureReplyAdapter = new LiteratureReplyAdapter(context, literatureCommentDisplayBean.userId,
                    literatureCommentDisplayBean.userFullName, iteratureReplyDisplayBeanList, lvLiteratureReply);
            lvLiteratureReply.setAdapter(literatureReplyAdapter);
//            literatureReplyAdapter.notifyDataSetChanged();

            //从v层拿取数据
            activityIntance.LoadLiteratureReplyByLiteratureCommentId(literatureCommentDisplayBean.literatureCommentId, iteratureReplyDisplayBeanList, literatureReplyAdapter);

//            List<LiteratureReplyDisplayBean> literatureReplyDisplayBeen = activityIntance.getCurrentLiteratureReplyDisplayBeen();
//            iteratureReplyDisplayBeanList.clear();
//            iteratureReplyDisplayBeanList.addAll(literatureReplyDisplayBeen);
//            literatureReplyAdapter.notifyDataSetChanged();
        }

    }
}
