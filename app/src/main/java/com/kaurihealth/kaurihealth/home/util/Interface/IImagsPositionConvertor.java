package com.kaurihealth.kaurihealth.home.util.Interface;

import com.kaurihealth.kaurihealth.util.NoValueException;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public interface IImagsPositionConvertor {
    /**
     * 将在list中的位置转换为Gridview中的位置
     *
     * @param position
     * @return
     */
    int list2Gv(int position);

    /**
     * 将在gridview中位置转换为在list中的位置
     *
     * @param position
     * @return
     * @throws NoValueException
     */
    int gv2list(int position) throws NoValueException;
}
