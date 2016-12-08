package com.kaurihealth.utilslib;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public class AppUtils {
    private static String Tag = "AppUtils";

    public static String encrypt(String phoneNumber) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        String time = format.format(Calendar.getInstance().getTime());
        String result = encrypt(phoneNumber, "kaurihealth" + time);
        return result;
    }

    public static String encrypt(String phoneNumber, String key) {
        try {
            byte[] bytesPhoneNumber = phoneNumber.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytesKey = Arrays.copyOf(md.digest(key.getBytes("UTF-8")), 24);
            for (int j = 0, k = 16; j < 8; ) {
                bytesKey[k++] = bytesKey[j++];
            }
            KeySpec keySpec = new DESedeKeySpec(bytesKey);
            SecretKey key1 = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key1);
            byte[] buf = cipher.doFinal(bytesPhoneNumber);
            String result = Base64.encodeToString(buf, Base64.DEFAULT);
            if (result.endsWith("==\n")) {
                result=result.substring(0,result.length()-1);
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //Bugtags.sendException(e);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           // Bugtags.sendException(e);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           // Bugtags.sendException(e);
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
           // Bugtags.sendException(e);
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Bugtags.sendException(e);
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Bugtags.sendException(e);
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Bugtags.sendException(e);
        }
        return null;
    }
}
