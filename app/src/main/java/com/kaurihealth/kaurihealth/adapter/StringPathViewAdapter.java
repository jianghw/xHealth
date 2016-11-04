package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.io.File;
import java.util.List;

/**
 * Created by 张磊 on 2016/4/18.
 * 介绍：
 */
public class StringPathViewAdapter extends CommonAdapter<String> {
    public StringPathViewAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = (CircleImageView) inflater.inflate(R.layout.url_imageview, null);
        }
        final CircleImageView content = (CircleImageView) convertView.findViewById(R.id.cle_imgeView);
//        content.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                final PopupMenu popupMenu = new PopupMenu(context, view);
//                Menu menu = popupMenu.getMenu();
//                menu.add(Menu.NONE, Menu.FIRST, 0, "删除");
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()) {
//                            case Menu.FIRST:
//                                list.remove(position);
//                                notifyDataSetChanged();
//                                break;
//                        }
//                        return false;
//                    }
//                });
//                popupMenu.show();
//                return true;
//            }
//        });

        if (content.getWidth() <= 0) {
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    if (!(list.get(position).contains("http:"))) {
                        newFileImage(position, content);
                    } else {
                        newUrlImage(position, content);
                    }
                }
            });
        } else {
            if (!(list.get(position).contains("http:"))) {
                newFileImage(position, content);
            } else {
                newUrlImage(position, content);
            }
        }
        return convertView;
    }

    private void newUrlImage(int position, CircleImageView content) {
        ImageUrlUtils.picassoBySmallUrlCircle(context, list.get(position), content);
    }

    private void newFileImage(int position, CircleImageView content) {
        ImageUrlUtils.picassoBySmallFileCircle(context, new File(list.get(position)), content);
    }
}
