package com.wxcz.carpenter.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author Administrator
 *  密码加密工具 类
 */
public class MD5Utils {
	//干扰数据 盐 防破解
    private static final String SALT = "mar%#$@";
    //散列算法类型为MD5
    private static final String ALGORITH_NAME = "md5";
    //hash的次数
    private static final int HASH_ITERATIONS = 2;

    /**
     * @param: [pswd]  需要加密的 密码
     * @return: java.lang.String 加密后的密码
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 用户密码 使用这个方法
     */
    public static String encrypt(String pswd) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
        return newPassword;
    }

    /**
     * @param: [username, pswd] 用户名（String） 和 需要加密的 密码
     * @return: java.lang.String 加密后的密码
     * @author: cxd
     * @Date: 2020/8/7
     * 描述 : 传入 用户名 加密码 加密
     */
    public static String encrypt(String username, String pswd) {
        String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(username + SALT),
                HASH_ITERATIONS).toHex();
        return newPassword;
    }

    
}
