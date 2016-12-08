package com.kaurihealth.datalib.repository;


import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 描述：仓库 公共方法工厂
 */

public class RepositoryFactory {

    private static class SingletonHolder {
        private static final RepositoryFactory INSTANCE = new RepositoryFactory();
    }
    public static RepositoryFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

  public  interface UploadCaseSucceed {
        void imageSucceed(List<DocumentDisplayBean> beanList, ArrayList<String> paths);
    }

}
