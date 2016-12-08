package com.kaurihealth.kaurihealth.patient_v;

/**
 * Created by mip on 2016/6/27.
 * 介绍：
 */
public class LabTestUtil {
    private static String[] blood = {
            "血常规","血电解质","血糖","肝功能","肾功能","血脂","血尿酸",
            "血流变","血沉","纤维蛋白原","血浆D二聚体测定","凝血功能检查"
    };

    private static String[] urine = {
            "尿常规","尿糖","尿渗透压测定",
            "尿相差镜检","尿蛋白或尿β2微球蛋白",
            "尿电解质"

    };
    private static String[] faece = {
            "便常规","便潜血"
    };
    private static String[] special = {
            "肿瘤标志物","抗链O","类风湿因子","其它抗体与补体",
            "自身免疫性肝炎检查","口服葡萄糖耐量试验",
            "胰岛素释放实验","c肽释放实验","糖化血红蛋白定量测定",
            "甲状腺功能","垂体功能","肾上腺皮质功能",
            "性激素","生长激素","肝炎抗体","结核抗体","细菌培养"
    };
    private static String[] other = {
            "血气分析","骨髓分类白细胞分类","浆膜腔液检查",
            "脑脊液检查","前列腺液检查","血液寄生虫检查",
            "阴道分泌物检查","其它"
    };
    private static String[] defaultValue = new String[blood.length + urine.length + faece.length + special.length + other.length];


    public static String[] getNewLabTestDetailDisplayBean(String subject) {
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
