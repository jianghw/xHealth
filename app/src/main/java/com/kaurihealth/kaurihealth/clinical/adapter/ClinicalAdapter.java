package com.kaurihealth.kaurihealth.clinical.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.builder.Category;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.clinical.activity.ClinicalListActivity;
import com.kaurihealth.kaurihealth.clinical.util.ClinicalUtil;
import com.youyou.zllibrary.AnimUtil.TranslationAnim;
import com.youyou.zllibrary.netutil.ImageLoadUtil;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/31.
 * 备注：临床支持公用Adapter
 */
public class ClinicalAdapter extends CommonAdapter<Category> {
    private List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList;

    private static final int TYPE_CATEGORY_ITEM = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<Category> mListData;
    private LayoutInflater mInflater;
    private Activity thisactivity;
    private ClinicalUtil clinicalUtil;


    public ClinicalAdapter(Context context, Activity activity, ArrayList<Category> list, List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayList) {
        super(context, list);
        mListData = list;
        thisactivity = activity;
        medicalLiteratureDisPlayBeanList = medicalLiteratureDisPlayList;
        mInflater = LayoutInflater.from(context);
        clinicalUtil = new ClinicalUtil();
    }

    @Override
    public int getCount() {
        int count = 0;

        if (null != mListData) {
            //  所有分类中item的总和是ListVIew  Item的总个数
            for (Category category : mListData) {
                count += category.getItemCount();
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {

        // 异常情况处理
        if (null == mListData || position < 0 || position > getCount()) {
            return null;
        }

        // 同一分类内，第一个元素的索引值
        int categroyFirstIndex = 0;

        for (Category category : mListData) {
            int size = category.getItemCount();
            // 在当前分类中的索引值
            int categoryIndex = position - categroyFirstIndex;
            // item在当前分类内
            if (categoryIndex < size) {
                return category.getItem(categoryIndex);
            }

            // 索引移动到当前分类结尾，即下一个分类第一个元素索引
            categroyFirstIndex += size;
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        // 异常情况处理
        if (null == mListData || position < 0 || position > getCount()) {
            return TYPE_ITEM;
        }

        int categroyFirstIndex = 0;

        for (Category category : mListData) {
            int size = category.getItemCount();
            // 在当前分类中的索引值
            int categoryIndex = position - categroyFirstIndex;
            if (categoryIndex == 0) {
                return TYPE_CATEGORY_ITEM;
            }

            //categroyFirstIndex=categroyFirstIndex+size;l
            categroyFirstIndex += size;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_CATEGORY_ITEM:
                if (null == convertView) {
                    convertView = mInflater.inflate(R.layout.listview_item_header, null);
                }
                TextView textView = (TextView) convertView.findViewById(R.id.header);
                TextView tv_gendo = (TextView) convertView.findViewById(R.id.tv_gendo);
                tv_gendo.setText(Html.fromHtml("<u>" + "更多" + "</u>"));
                final String itemValue = (String) getItem(position);
                textView.setText(itemValue);
                tv_gendo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("itemValue", itemValue);
                        bundle.putSerializable("medicalLiteratureDisPlayBeanList", (Serializable) clinicalUtil.getMedicalLiteratureDisPlayBeanArrayList(medicalLiteratureDisPlayBeanList, itemValue));
                        skipToForResult(ClinicalListActivity.class, bundle, 0);
                    }
                });
                break;

            case TYPE_ITEM:
                ViewHolder viewHolder = null;
                if (null == convertView) {
                    convertView = mInflater.inflate(R.layout.adapter_clinical, null);
                    viewHolder = new ViewHolder();
                    viewHolder.ivTitleImage = (ImageView) convertView.findViewById(R.id.iv_titleImage);
                    viewHolder.tvBrowse = (TextView) convertView.findViewById(R.id.tv_browse);
                    viewHolder.tvContentBrief = (TextView) convertView.findViewById(R.id.tv_contentBrief);
                    viewHolder.tvCreatTime = (TextView) convertView.findViewById(R.id.tv_creatTime);
                    convertView.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }

                // 绑定数据
                Object item = getItem(position);
                if (item instanceof MedicalLiteratureDisPlayBean) {
                    if (((MedicalLiteratureDisPlayBean) item).titleImage != null) {
                        ImageLoadUtil.displayImg(((MedicalLiteratureDisPlayBean) item).titleImage, viewHolder.ivTitleImage, context);
                    }
                    viewHolder.tvContentBrief.setText(((MedicalLiteratureDisPlayBean) item).medicalLiteratureTitle);
                    viewHolder.tvCreatTime.setText(new ClinicalUtil().getTimeByTimeFormat(((MedicalLiteratureDisPlayBean) item).creatTime, "yyyy-MM-dd HH:mm:ss"));
                    viewHolder.tvBrowse.setText(((MedicalLiteratureDisPlayBean) item).browse + "阅读");
                }

                break;
        }

        return convertView;
    }

    /**
     * 跳转到新的activity 带有值
     *
     * @param className
     * @param bundle
     * @param requestCode
     */
    public void skipToForResult(Class<? extends Activity> className, Bundle bundle, int requestCode) {
        TranslationAnim.zlStartActivityForResult(thisactivity, className, bundle, requestCode);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != TYPE_CATEGORY_ITEM;
    }

    private class ViewHolder {
        //标题图片
        ImageView ivTitleImage;
        //内容摘要
        TextView tvContentBrief;
        //创建时间
        TextView tvCreatTime;
        //浏览量
        TextView tvBrowse;
    }
}
