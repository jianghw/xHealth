package com.kaurihealth.kaurihealth.home.util;

/**
 * Created by 张磊 on 2016/7/26.
 * 介绍：
 */
public interface ISearch {
    void search(String content);

    void showHistory();

    void showHint(String hint);
    void showRecyclerView();
    void addDoctorFriendSuccess(int doctorID);
}
