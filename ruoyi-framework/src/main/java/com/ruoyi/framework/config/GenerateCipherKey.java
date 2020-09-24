package com.ruoyi.framework.config;


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * @program: tingnan-aliyun
 * @description: shiro随机秘钥
 * @author: 韩以虎
 * @create: 2020-09-24 10:58
 **/

public class GenerateCipherKey {

    /**
     * 随机生成秘钥，参考org.apache.shiro.crypto.AbstractSymmetricCipherService#generateNewKey(int)
     *
     * @return
     */
    public static byte[] generateNewKey() {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException var5) {
            String msg = "Unable to acquire AES algorithm.  This is required to function.";
            throw new IllegalStateException(msg, var5);
        }

        kg.init(128);
        SecretKey key = kg.generateKey();
        byte[] encoded = key.getEncoded();
        return encoded;
    }
}
