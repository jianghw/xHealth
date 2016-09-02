package com.kaurihealth.kaurihealth.clinical.adapter;

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
import android.widget.Toast;

import com.example.commonlibrary.widget.CircleImageView;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.ILiteratureCommentService;
import com.kaurihealth.datalib.service.ILiteratureReplyService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.kaurihealth.kaurihealth.clinical_v.activity.LiteratureCommentActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.AnimUtil.TranslationAnim;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/7/4.
 * 备注：热门评论（显示）
 */
public class LiteratureCommentAdapter extends CommonAdapter implements SuccessInterface {
    private Activity activity;
    private ListView pullLayout;
    private TextView tvCommentCount;
    private ILiteratureCommentService mliteratureCommentService;

    public LiteratureCommentAdapter(Activity activity, List<LiteratureCommentDisplayBean> list, ListView lvdynamic,TextView tvCommentCount) {
        super(activity, list);
        this.activity = activity;
        this.pullLayout = lvdynamic;
        this.tvCommentCount = tvCommentCount;
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
                ((SuccessInterface) temp2.getAdapter()).success();
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

        private IGetter getter;
        private ImagSizeMode.ImageSizeBean imageSizeBean;
        private ILiteratureReplyService literatureReplyService;
        private List<LiteratureReplyDisplayBean> iteratureReplyDisplayBeanList;
        private LiteratureReplyAdapter literatureReplyAdapter;

        private String accessToken;

        ViewHolder(View view, int position) {
            ButterKnife.bind(this, view);
            getter = Getter.getInstance(context);
            accessToken = getter.getToken();
            literatureReplyService = new ServiceFactory(Url.prefix,context).getLiteratureReplyService();
            imageSizeBean = ImagSizeMode.imageSizeBeens[4];
            init(position);
        }

        /**
         * 点击回复
         *
         * @param position
         */
        private void init(final int position) {
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
                                    mliteratureCommentService = new ServiceFactory(Url.prefix,context).getILiteratureCommentService();
                                    Call<ResponseDisplayBean> beanCall= mliteratureCommentService.DeleteLiteratureComment((((LiteratureCommentDisplayBean)list.get(position)).literatureCommentId));
                                    beanCall.enqueue(new Callback<ResponseDisplayBean>() {
                                        @Override
                                        public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                                            if (response.isSuccessful()){
                                                if (response.body().isIsSucess()){
                                                    list.remove(position);
                                                    tvCommentCount.setText((Integer.parseInt(tvCommentCount.getText().toString()))-1 +"");
                                                    notifyDataSetChanged();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                                            Toast.makeText(context,"删除失败！",Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                }
                            }).show();


                }
            });
        }

        private void setInfo(LiteratureCommentDisplayBean literatureCommentDisplayBean) {
            if (literatureCommentDisplayBean != null) {
                PicassoImageLoadUtil.loadUrlToImageView(literatureCommentDisplayBean.userAvartar, ivUserAvartar, context, imageSizeBean);
            }
            tvUserFullName.setText(literatureCommentDisplayBean.userFullName);
            tvLiteratureCommentContent.setText(literatureCommentDisplayBean.literatureCommentContent);
            tvCreateTime.setText(new ClinicalUtil().getTimeByTimeFormat(literatureCommentDisplayBean.createTime, "MM-dd HH:mm"));
//            tvDelete.setVisibility(View.INVISIBLE);

            iteratureReplyDisplayBeanList = new ArrayList<>();
            literatureReplyAdapter = new LiteratureReplyAdapter(context, literatureCommentDisplayBean.userId, literatureCommentDisplayBean.userFullName, iteratureReplyDisplayBeanList, lvLiteratureReply);
            lvLiteratureReply.setAdapter(literatureReplyAdapter);
//            literatureReplyAdapter.notifyDataSetChanged();

            Call<List<LiteratureReplyDisplayBean>> listCall = literatureReplyService.LoadLiteratureReplyByLiteratureCommentId(literatureCommentDisplayBean.literatureCommentId);
            listCall.enqueue(new Callback<List<LiteratureReplyDisplayBean>>() {
                @Override
                public void onResponse(Call<List<LiteratureReplyDisplayBean>> call, Response<List<LiteratureReplyDisplayBean>> response) {
                    if (response.isSuccessful()) {
                        iteratureReplyDisplayBeanList.clear();
                        iteratureReplyDisplayBeanList.addAll(response.body());
                        literatureReplyAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<LiteratureReplyDisplayBean>> call, Throwable t) {
                    iteratureReplyDisplayBeanList.clear();
                }
            });
        }

    }
}
