package com.kaurihealth.datalib.request_bean.builder;


import com.bugtags.library.Bugtags;
import com.kaurihealth.datalib.request_bean.bean.NewDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.RecordDocumentDisplayBean;
import com.youyou.zllibrary.jacksonutil.JsonUtil;


import org.codehaus.jackson.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/19.
 * 备注：
 */
public class NewDocumentDisplayBeanBuilder {
    public List<NewDocumentDisplayBean> Build(String response) {
        List<NewDocumentDisplayBean> listNewDocumentDisplayBean = new ArrayList<>();
        try {
            //将json 转换成List<NewDocumentDisplayBean>
            JsonNode root = JsonUtil.getRoot(response);
            listNewDocumentDisplayBean = new LinkedList<>();
            for (int i = 0; i < root.size(); i++) {
                JsonNode jsonNode = root.path(i);
                listNewDocumentDisplayBean.add(new NewDocumentDisplayBean(
                        jsonNode.path("documentUrl").asText(),
                        jsonNode.path("fileName").asText(),
                        jsonNode.path("displayName").asText(),
                        jsonNode.path("documentFormat").asText(),
                        ""
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Bugtags.sendException(e);
        }
        return listNewDocumentDisplayBean;
    }

  //  public List<RecordDocumentDisplayBean> BuildPatientRecordDisplayBeans(String response, int patientRecordId) {
//        List<RecordDocumentDisplayBean> list = new LinkedList<>();
//        List<NewDocumentDisplayBean> newDocumentDisplayBeen = JSON.parseArray(response, NewDocumentDisplayBean.class);
//        for (NewDocumentDisplayBean iteam : newDocumentDisplayBeen) {
//            RecordDocumentDisplayBean bean = new RecordDocumentDisplayBean();
//            bean.isDeleted = false;
//            bean.recordDocumentId = 0;
//            bean.comment = "";
//            bean.displayName = iteam.displayName;
//            bean.documentFormat = iteam.documentFormat;
//            bean.documentUrl = iteam.documentUrl;
//            bean.fileName = iteam.fileName;
//            bean.patientRecordId = patientRecordId;
//            list.add(bean);
//        }
//        return list;
    //}
}
