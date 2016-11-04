package com.kaurihealth.kaurihealth.clinical_v.Utils;


import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by mip on 2016/8/22.
 */
public class compatorForMyClinicalUtils {


    private Date selfData;
    private Date otherData;
    private ExampleComparator comparator = new ExampleComparator();

    private class ExampleComparator implements Comparator<MedicalLiteratureDisPlayBean> {
        SimpleDateFormat formatStrToData = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat isNowDay = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public int compare(MedicalLiteratureDisPlayBean self, MedicalLiteratureDisPlayBean other) {
            try {

                if (self.lastUpdateTime != null && isNowDay.parse(self.creatTime).equals(isNowDay.parse(other.creatTime))) {
                    selfData = formatStrToData.parse(self.lastUpdateTime);
                } else {
                    selfData = formatStrToData.parse(self.creatTime);
                }
                if (other.lastUpdateTime != null && isNowDay.parse(self.creatTime).equals(isNowDay.parse(other.creatTime))) {
                    otherData = formatStrToData.parse(other.lastUpdateTime);
                } else {
                    otherData = formatStrToData.parse(other.creatTime);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date timeSelf = selfData;
            Date timeOther = otherData;
            if (timeSelf.before(timeOther)) return 1;
            return -1;

        }
    }

    //数据处理
    public MedicalLiteratureDisPlayBean[] handleData(List<MedicalLiteratureDisPlayBean> list) {
        MedicalLiteratureDisPlayBean[] jsonNodes = new MedicalLiteratureDisPlayBean[list.size()];
        list.toArray(jsonNodes);
        Arrays.sort(jsonNodes, comparator);
        return jsonNodes;
    }
}
