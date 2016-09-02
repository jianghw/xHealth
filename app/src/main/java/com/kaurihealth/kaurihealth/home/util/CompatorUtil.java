package com.kaurihealth.kaurihealth.home.util;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/1.
 * 备注： 根据List<JsonNode>中的recordDate进行排序
 * 目的： 将医疗记录根据门诊时间进行排序
 */
public class CompatorUtil {

    private ExampleComparator comparator = new ExampleComparator();

    private class ExampleComparator implements Comparator<PatientRecordDisplayBean> {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        @Override
        public int compare(PatientRecordDisplayBean self, PatientRecordDisplayBean other) {
            try {
                float timeSelf = format.parse(self.recordDate).getTime();
                float timeOther = format.parse(other.recordDate).getTime();
                if (timeSelf - timeOther > 0) {
                    return -1;
                } else if (timeSelf - timeOther < 0) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }


    public PatientRecordDisplayBean[] handleData(List<PatientRecordDisplayBean> list) {
        PatientRecordDisplayBean[] jsonNodes = new PatientRecordDisplayBean[list.size()];
        list.toArray(jsonNodes);
        Arrays.sort(jsonNodes, comparator);
        return jsonNodes;
    }
}
