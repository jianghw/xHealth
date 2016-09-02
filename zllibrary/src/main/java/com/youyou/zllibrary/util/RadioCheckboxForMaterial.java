package com.youyou.zllibrary.util;

import android.widget.CompoundButton;

import com.rey.material.widget.CheckBox;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 张磊 on 2016/3/21.
 * 介绍：
 */
public class RadioCheckboxForMaterial {
    List<CheckBox> list = new LinkedList<>();

    public RadioCheckboxForMaterial() {
    }

    public void setListener() {
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            list.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        for (int j = 0; j < list.size(); j++) {
                            if (j != finalI) {
                                list.get(j).setChecked(false);
                            }
                        }
                    }
                }
            });
        }
    }


    public void regist(int index, CheckBox checkBox) {
        list.add(index, checkBox);
    }


    public int getSelectedIndx() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null && list.get(i).isChecked()) {
                return i;
            }
        }
        return -1;
    }

    public void select(int index) {
        CheckBox checkBox = list.get(index);
        checkBox.setChecked(true);
    }

    public void clearAll() {
        list.clear();
    }

    public void unregist(int index) {
        if (list.size() >= index && index >= 0) {
            list.remove(index);
        }
    }
}
