package com.kaurihealth.utilslib;

import android.content.Context;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：Android Saripaar 框架的错误提示封装
 */
public class ValidatorUtils {

    public static String validationErrorMessage(Context context, List<ValidationError> errors) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ValidationError error : errors) {
            String collatedErrorMessage = error.getCollatedErrorMessage(context);
            stringBuilder.append(collatedErrorMessage).append("\n");
        }
        return stringBuilder.toString();
    }
}
