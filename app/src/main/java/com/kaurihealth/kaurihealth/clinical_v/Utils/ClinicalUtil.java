package com.kaurihealth.kaurihealth.clinical_v.Utils;


import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/20.
 * 备注：
 */
public class ClinicalUtil {
    /**
     * 根據mCategoryName獲取到ArrayList<MedicalLiteratureDisPlayBean>
     *
     * @param list
     * @param mCategoryName
     * @return
     */
    private compatorForMyClinicalUtils myClinicalUtils;

    public ArrayList<MedicalLiteratureDisPlayBean> getMedicalLiteratureDisPlayBeanArrayList(List<MedicalLiteratureDisPlayBean> list, String mCategoryName) {
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList_new = new ArrayList<>();
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList_two = new ArrayList<>();
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList_three = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String medicalLiteratureCategory = list.get(i).medicalLiteratureCategory;
            if (medicalLiteratureCategory.equals(mCategoryName)) {
                medicalLiteratureDisPlayBeanList.add(list.get(i));
            }
        }
        //置顶跟已发布设置
        int count = 0;
        for (int i = 0; i < medicalLiteratureDisPlayBeanList.size(); i++) {
            if (medicalLiteratureDisPlayBeanList.get(i).medicalLiteratureState == 0 && medicalLiteratureDisPlayBeanList.size() > 0) {
                medicalLiteratureDisPlayBeanList_new.add(count, medicalLiteratureDisPlayBeanList.get(i));
                ++count;
            }
        }
        //置顶排序
        myClinicalUtils = new compatorForMyClinicalUtils();
        MedicalLiteratureDisPlayBean[] literatureDisPlayBeen_one = myClinicalUtils.handleData(medicalLiteratureDisPlayBeanList_new);
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeen_array = Arrays.asList(literatureDisPlayBeen_one);
        medicalLiteratureDisPlayBeanList_new.clear();
        medicalLiteratureDisPlayBeanList_new.addAll(medicalLiteratureDisPlayBeen_array);

        for (int n = 0; n < medicalLiteratureDisPlayBeanList.size(); n++) {
            if (medicalLiteratureDisPlayBeanList.get(n).medicalLiteratureState == 2 && medicalLiteratureDisPlayBeanList.size() > 0) {
                medicalLiteratureDisPlayBeanList_two.add(medicalLiteratureDisPlayBeanList.get(n));
            }
        }
        //已发布排序
        myClinicalUtils = new compatorForMyClinicalUtils();
        MedicalLiteratureDisPlayBean[] literatureDisPlayBeen_two = myClinicalUtils.handleData(medicalLiteratureDisPlayBeanList_two);
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeen_array_new = Arrays.asList(literatureDisPlayBeen_two);
        medicalLiteratureDisPlayBeanList_three.addAll(medicalLiteratureDisPlayBeen_array_new);

        for (int n = 0; n < medicalLiteratureDisPlayBeanList_three.size(); n++) {
            if (medicalLiteratureDisPlayBeanList_three.size() > 0) {
                medicalLiteratureDisPlayBeanList_new.add(count, medicalLiteratureDisPlayBeanList_three.get(n));
                ++count;
            }
        }

        return (ArrayList<MedicalLiteratureDisPlayBean>) medicalLiteratureDisPlayBeanList_new;
    }

    /**
     * 获取到拼接好的url
     * 釦l
     *
     * @param UrlString
     * @param id
     * @return
     */
    public String getUrlString(String UrlString, int id) {
        return UrlString + "index?id=" + id;
    }


    /**
     * 判断字符串是否超过指定长度，超过长度显示。。。
     *
     * @param name
     * @return
     */
    public String getLengthString(String name, int cunt) {
        if (name.length() > cunt) {
            return name.substring(0, cunt) + "...";
        }
        return name;
    }

    /**
     * 将后台Time(String) 转变成指定Time(String),转变失败则返回当前系统时间
     *
     * @param creatTime
     * @param timeFormat
     * @return
     */
    public String getTimeByTimeFormat(String creatTime, String timeFormat) {
        SimpleDateFormat formatStrToData = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat formatDateToStr = new SimpleDateFormat(timeFormat);
        String format = formatDateToStr.format(Calendar.getInstance().getTime());
        try {
            Date parse = formatStrToData.parse(creatTime);
            format = formatDateToStr.format(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }


    /**
     * 判断str是否为，空，0, 0.0   是返回true
     *
     * @param str
     * @return
     */
    public boolean getToBoolean(String str) {
        if (str.length() == 0) {
            return true;
        }
        if (0 == Double.parseDouble(str)) {
            return true;
        }
        return false;
    }

    /**
     * 判斷str是否為0.0  是則返回“”
     *
     * @param dou
     * @return
     */
    public String getDoubleIsString(Double dou) {
        if (String.valueOf(dou).equals("0.0")) {
            return "";
        }
        return String.valueOf(dou);
    }

    /**
     * 获取List中已发布的MedicalLiteratureDisPlayBean
     *
     * @param list
     * @return
     */
    public List<MedicalLiteratureDisPlayBean> getListMedicalLiteratureDisPlayBean(List<MedicalLiteratureDisPlayBean> list) {
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList_new = new ArrayList<>();
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList_two = new ArrayList<>();
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList_three = new ArrayList<>();
        for (MedicalLiteratureDisPlayBean item : list) {
            int medicalLiteratureState = item.medicalLiteratureState;
            if (medicalLiteratureState == 2 || medicalLiteratureState == 0) {
                medicalLiteratureDisPlayBeanList.add(item);
            }
        }

        //置顶跟已发布设置
        int count = 0;
        for (int i = 0; i < medicalLiteratureDisPlayBeanList.size(); i++) {
            if (medicalLiteratureDisPlayBeanList.get(i).medicalLiteratureState == 0 && medicalLiteratureDisPlayBeanList.size() > 0) {
                medicalLiteratureDisPlayBeanList_new.add(count, medicalLiteratureDisPlayBeanList.get(i));
                ++count;
            }
        }
        //置顶排序
        myClinicalUtils = new compatorForMyClinicalUtils();
        MedicalLiteratureDisPlayBean[] literatureDisPlayBeen_two_one = myClinicalUtils.handleData(medicalLiteratureDisPlayBeanList_new);
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeen_array_new_one = Arrays.asList(literatureDisPlayBeen_two_one);
        medicalLiteratureDisPlayBeanList_new.clear();
        medicalLiteratureDisPlayBeanList_new.addAll(medicalLiteratureDisPlayBeen_array_new_one);

        for (int n = 0; n < medicalLiteratureDisPlayBeanList.size(); n++) {
            if (medicalLiteratureDisPlayBeanList.get(n).medicalLiteratureState == 2 && medicalLiteratureDisPlayBeanList.size() > 0) {
                medicalLiteratureDisPlayBeanList_two.add(medicalLiteratureDisPlayBeanList.get(n));
            }
        }
        //已发布排序
        myClinicalUtils = new compatorForMyClinicalUtils();
        MedicalLiteratureDisPlayBean[] literatureDisPlayBeen_two = myClinicalUtils.handleData(medicalLiteratureDisPlayBeanList_two);
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeen_array_new = Arrays.asList(literatureDisPlayBeen_two);
        medicalLiteratureDisPlayBeanList_three.addAll(medicalLiteratureDisPlayBeen_array_new);

        for (int n = 0; n < medicalLiteratureDisPlayBeanList_three.size(); n++) {
            if (medicalLiteratureDisPlayBeanList_three.size() > 0) {
                medicalLiteratureDisPlayBeanList_new.add(count, medicalLiteratureDisPlayBeanList_three.get(n));
                ++count;
            }
        }

        return medicalLiteratureDisPlayBeanList_new;
    }

    /**
     * 获取List中用户已发布的MedicalLiteratureDisPlayBean
     *
     * @param list
     * @param id
     * @return
     */
    public List<MedicalLiteratureDisPlayBean> getListMedicalLiteratureDisPlayBeanById(List<MedicalLiteratureDisPlayBean> list, int id) {
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
        for (MedicalLiteratureDisPlayBean item : list) {
            int medicalLiteratureState = item.medicalLiteratureState;
            int userId = item.userId;
            if ((medicalLiteratureState == 2 || medicalLiteratureState == 0) && userId == id) {
                medicalLiteratureDisPlayBeanList.add(item);
            }
        }
        return medicalLiteratureDisPlayBeanList;
    }

    /**
     * 获取List中用户已发布的MedicalLiteratureDisPlayBean  (关键字搜索)
     *
     * @param list
     * @param
     * @return
     */
    public List<MedicalLiteratureDisPlayBean> getListMedicalLiteratureDisPlayBeanBytitleImage(List<MedicalLiteratureDisPlayBean> list, String str) {
        List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeanList = new ArrayList<>();
        for (MedicalLiteratureDisPlayBean item : list) {
            int medicalLiteratureState = item.medicalLiteratureState;
            String medicalLiteratureTitle = item.medicalLiteratureTitle;
            if ((medicalLiteratureState == 2 || medicalLiteratureState == 0) && medicalLiteratureTitle.indexOf(str) != -1) {
                medicalLiteratureDisPlayBeanList.add(item);
            }
        }
        return medicalLiteratureDisPlayBeanList;
    }

//    /**
//     * 通过将时间
//     *
//     * @param str
//     * @return
//     */
//    public String getTime(String str) {
//        String time = "";
//
//        return time;
//    }
//
//    /**
//     * 判断2个时间相差多少天、多少小时、多少分<br>
//     * <br>
//     *
//     * @param pBeginTime 请假开始时间<br>
//     * @param pEndTime   请假结束时间<br>
//     * @return String 计算结果<br>
//     * @Exception 发生异常<br>
//     */
//    public static String TimeDiff(String pBeginTime, String pEndTime) throws Exception {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//        Long beginL = format.parse(pBeginTime).getTime();
//        Long endL = format.parse(pEndTime).getTime();
//        Long day = (endL - beginL) / 86400000;
//        Long hour = ((endL - beginL) % 86400000) / 3600000;
//        Long min = ((endL - beginL) % 86400000 % 3600000) / 60000;
//        return ("实际请假" + day + "小时" + hour + "分钟" + min);
//    }
}
