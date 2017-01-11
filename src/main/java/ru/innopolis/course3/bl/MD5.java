package ru.innopolis.course3.bl;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by korot on 10.01.2017.
 */
public class MD5 {
    public static String md5Apache(String st, String salt) {//salt from DB
        //String newSalt = doSomeWithSalt(salt);
        String md5Hex = DigestUtils.md5Hex((DigestUtils.md5Hex(st) + salt));
        return md5Hex;
    }
}
