package com.kaurihealth.kaurihealth.home.util;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 米平 on 2016/8/3.
 */
public class CompatorForAccountDetailUtils {
    private ExampleComparator comparator = new ExampleComparator();

    private class ExampleComparator implements Comparator<CreditTransactionDisplayBean> {


        @Override
        public int compare(CreditTransactionDisplayBean self, CreditTransactionDisplayBean other) {
            java.util.Date timeSelf = self.date;
            java.util.Date timeOther = other.date;
            if(timeSelf.before(timeOther)) return 1;
            return -1;

        }
    }
        //数据处理
    public CreditTransactionDisplayBean[] handleData(List<CreditTransactionDisplayBean> list) {
        CreditTransactionDisplayBean[] jsonNodes = new CreditTransactionDisplayBean[list.size()];
        list.toArray(jsonNodes);
        Arrays.sort(jsonNodes, comparator);
        return jsonNodes;
    }
}
