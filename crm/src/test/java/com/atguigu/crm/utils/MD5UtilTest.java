package com.atguigu.crm.utils;

import junit.framework.TestCase;

/**
 * @create 2021-12-09 10:10
 */
public class MD5UtilTest extends TestCase {

    public void testGetMD5() {

        String md5 = MD5Util.getMD5("fxy123");
        System.out.println(md5);
    }
}