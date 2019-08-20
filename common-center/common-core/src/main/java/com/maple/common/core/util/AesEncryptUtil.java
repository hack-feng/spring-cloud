package com.maple.common.core.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.hutool.core.codec.Base64;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.nio.charset.StandardCharsets;

/**
 * 密码加密解密工具
 *
 * @author zhua
 * @date 2019/8/20
 */
@RefreshScope
@UtilityClass
public class AesEncryptUtil {

    private final String KEY_ALGORITHM = "AES";

//    // 加密
//    public String Encrypt(String sSrc, String sKey) throws Exception {
//        if (sKey == null) {
//            System.out.print("Key为空null");
//            return null;
//        }
//        // 判断Key是否为16位
//        if (sKey.length() != 16) {
//            System.out.print("Key长度不是16位");
//            return null;
//        }
//        byte[] raw = sKey.getBytes("utf-8");
//        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
//        //"算法/模式/补码方式"
////        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
//        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
//        //此处使用BASE64做转码功能，同时能起到2次加密的作用。
//        return Base64.encode(encrypted);
//    }
//
//    // 解密
//    public String Decrypt(String sSrc, String sKey) {
//        try {
//            // 判断Key是否正确
//            if (sKey == null) {
//                System.out.print("Key为空null");
//                return null;
//            }
//            // 判断Key是否为16位
//            if (sKey.length() != 16) {
//                System.out.print("Key长度不是16位");
//                return null;
//            }
//            byte[] raw = sKey.getBytes("utf-8");
//            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
////            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//            //先用base64解密
//            byte[] encrypted1 = Base64.decode(sSrc.getBytes(StandardCharsets.UTF_8));
//            try {
//                byte[] original = cipher.doFinal(encrypted1);
//                String originalString = new String(original,"utf-8");
//                return originalString;
//            } catch (Exception e) {
//                System.out.println(e.toString());
//                return null;
//            }
//        } catch (Exception ex) {
//            System.out.println(ex.toString());
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        /*
//         * 此处使用AES-128-ECB加密模式，key需要为16位。
//         */
//        String cKey = "1234567812345678";
//        // 需要加密的字串
//        String cSrc = "123456";
//        System.out.println(cSrc);
//        // 加密
//        String enString = AesEncryptUtil.Encrypt(cSrc, cKey);
//        System.out.println("加密后的字串是：" + enString);
//
//        // 解密
//        String DeString = AesEncryptUtil.Decrypt(enString, cKey);
//        System.out.println("解密后的字串是：" + DeString);
//    }

    /**
     * 加密方法
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     */
    public static String encrypt(String data, String key, String iv) {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"NoPadding PkcsPadding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return Base64.encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     */
    public static String desEncrypt(String data, String key, String iv) {
        try {
            byte[] encrypted1 = Base64.decode(data.getBytes(StandardCharsets.UTF_8));

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     */
    public static String encrypt(String data, String key) {
        return encrypt(data, key, key);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     */
    public static String desEncrypt(String data, String key) {
        return desEncrypt(data, key, key);
    }

    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {
        //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
        String KEY = "1234567812345678";
        String IV = "1234567812345678";

        String test1 = "123456";
        String test =new String(test1.getBytes(),"UTF-8");
        String data = null;
        String key =  KEY;
        String iv = IV;
        // /g2wzfqvMOeazgtsUVbq1kmJawROa6mcRAzwG1/GeJ4=
        data = encrypt(test, key, iv);
        System.out.println("数据："+test);
        System.out.println("加密："+data);
        String jiemi =desEncrypt(data, key, iv).trim();
        System.out.println("解密："+jiemi);

    }
}
