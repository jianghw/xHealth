package com.youyou.zllibrary.util;

import android.os.Bundle;

import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public interface FragmentData<T> {
    void setData(List<T> list);
    void setBundle(Bundle bundle);
}
