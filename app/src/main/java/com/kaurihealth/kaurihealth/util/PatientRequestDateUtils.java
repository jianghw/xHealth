package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by mip on 2016/11/11.
 */

public class PatientRequestDateUtils {
    private ExampleComparator comparator = new ExampleComparator();

    private class ExampleComparator implements Comparator<PatientRequestDisplayBean> {

        @Override
        public int compare(PatientRequestDisplayBean self, PatientRequestDisplayBean other) {
            java.util.Date timeSelf = self.requestDate;
            java.util.Date timeOther = other.requestDate;
            if(timeSelf.before(timeOther)) return 1;
            return -1;

        }
    }
    //数据处理
    public PatientRequestDisplayBean[] handleData(List<PatientRequestDisplayBean> list) {
        PatientRequestDisplayBean[] jsonNodes = new PatientRequestDisplayBean[list.size()];
        list.toArray(jsonNodes);
        Arrays.sort(jsonNodes, comparator);
        return jsonNodes;
    }
}
