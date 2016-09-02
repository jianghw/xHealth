package com.kaurihealth.kaurihealth.common.util;

import com.kaurihealth.datalib.request_bean.bean.NewLabTestDetailDisplayBean;

/**
 * Created by 张磊 on 2016/6/27.
 * 介绍：
 */
public class LabTestUtil {
    private static NewLabTestDetailDisplayBean[] blood = {
            new NewLabTestDetailDisplayBean("血常规"),
            new NewLabTestDetailDisplayBean("血电解质"),
            new NewLabTestDetailDisplayBean("血糖"),
            new NewLabTestDetailDisplayBean("肝功能"),
            new NewLabTestDetailDisplayBean("肾功能"),
            new NewLabTestDetailDisplayBean("血脂"),
            new NewLabTestDetailDisplayBean("血尿酸"),
            new NewLabTestDetailDisplayBean("血流变"),
            new NewLabTestDetailDisplayBean("血沉"),
            new NewLabTestDetailDisplayBean("纤维蛋白原"),
            new NewLabTestDetailDisplayBean("血浆D二聚体测定"),
            new NewLabTestDetailDisplayBean("凝血功能检查"),
    };

    private static NewLabTestDetailDisplayBean[] urine = {new NewLabTestDetailDisplayBean(""),
            new NewLabTestDetailDisplayBean("尿常规"),
            new NewLabTestDetailDisplayBean("尿糖"),
            new NewLabTestDetailDisplayBean("尿渗透压测定"),
            new NewLabTestDetailDisplayBean("尿相差镜检"),
            new NewLabTestDetailDisplayBean("尿蛋白或尿β2微球蛋白"),
            new NewLabTestDetailDisplayBean("尿电解质"),
    };
    private static NewLabTestDetailDisplayBean[] faece = {
            new NewLabTestDetailDisplayBean("便常规"),
            new NewLabTestDetailDisplayBean("便潜血"),
    };
    private static NewLabTestDetailDisplayBean[] special = {
            new NewLabTestDetailDisplayBean("肿瘤标志物"),
            new NewLabTestDetailDisplayBean("抗链O"),
            new NewLabTestDetailDisplayBean("类风湿因子"),
            new NewLabTestDetailDisplayBean("其它抗体与补体"),
            new NewLabTestDetailDisplayBean("自身免疫性肝炎检查"),
            new NewLabTestDetailDisplayBean("口服葡萄糖耐量试验"),
            new NewLabTestDetailDisplayBean("胰岛素释放实验"),
            new NewLabTestDetailDisplayBean("c肽释放实验"),
            new NewLabTestDetailDisplayBean("糖化血红蛋白定量测定"),
            new NewLabTestDetailDisplayBean("甲状腺功能"),
            new NewLabTestDetailDisplayBean("垂体功能"),
            new NewLabTestDetailDisplayBean("肾上腺皮质功能"),
            new NewLabTestDetailDisplayBean("性激素"),
            new NewLabTestDetailDisplayBean("生长激素"),
            new NewLabTestDetailDisplayBean("肝炎抗体"),
            new NewLabTestDetailDisplayBean("结核抗体"),
            new NewLabTestDetailDisplayBean("细菌培养"),
    };
    private static NewLabTestDetailDisplayBean[] other = {new NewLabTestDetailDisplayBean("血气分析"),
            new NewLabTestDetailDisplayBean("骨髓分类白细胞分类"),
            new NewLabTestDetailDisplayBean("浆膜腔液检查"),
            new NewLabTestDetailDisplayBean("脑脊液检查"),
            new NewLabTestDetailDisplayBean("前列腺液检查"),
            new NewLabTestDetailDisplayBean("血液寄生虫检查"),
            new NewLabTestDetailDisplayBean("阴道分泌物检查"),
            new NewLabTestDetailDisplayBean("其它"),
    };
    private static NewLabTestDetailDisplayBean[] defaultValue = new NewLabTestDetailDisplayBean[blood.length + urine.length + faece.length + special.length + other.length];

    static {
        int j = 0;
        for (int i = 0; i < blood.length; i++) {
            defaultValue[j+i] = blood[i];
        }
        j += blood.length;
        for (int i = 0; i < urine.length; i++) {
            defaultValue[j + i] = urine[i];
        }
        j += urine.length;

        for (int i = 0; i < faece.length; i++) {
            defaultValue[j + i] = faece[i];
        }
        j += faece.length;
        for (int i = 0; i < special.length; i++) {
            defaultValue[j + i] = special[i];
        }
        j += special.length;
        for (int i = 0; i < other.length; i++) {
            defaultValue[j + i] = other[i];
        }
    }

    public static NewLabTestDetailDisplayBean[] getNewLabTestDetailDisplayBean(String subject) {
        switch (subject) {
            case "常规血液学检查":
                return blood;
            case "常规尿液检查":
                return urine;
            case "常规粪便检查":
                return faece;
            case "特殊检查":
                return special;
            case "其它":
                return other;
            default:
                return defaultValue;
        }
    }
}
