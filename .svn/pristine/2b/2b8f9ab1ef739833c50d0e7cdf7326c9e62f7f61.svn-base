package com.kaurihealth.utilslib;

/**
 * Created by jianghw on 2016/10/19.
 * <p/>
 * Describe:
 */

public class OnClickUtils {

    private static volatile long exitTime = 0;

    public static boolean onNoDoubleClick() {
        if ((System.currentTimeMillis() - exitTime) > 1500) {
            exitTime = System.currentTimeMillis();
            return false;
        } else {
            System.out.println(System.currentTimeMillis() - exitTime);
            return true;
        }
    }
}
