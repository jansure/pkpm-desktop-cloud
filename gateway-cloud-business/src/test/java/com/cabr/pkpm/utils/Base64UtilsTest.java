package com.cabr.pkpm.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.gateway.cloud.business.utils.Base64Utils;

public class Base64UtilsTest {
	@Test
    public void testIsB64()
    {
        System.out.println("isB64");
        String src = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
        assertTrue(Base64Utils.isB64(src));
        
        String srcB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz0123456789+/=";
        assertFalse(Base64Utils.isB64(srcB));
        
        String srcC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ\nabcdefghijklmnopqrstuvwxyz0123456789+/=";
        assertFalse(Base64Utils.isB64(srcC));
        
        String srcD = "ABCDEFGHIJKLMNOPQRSTUVWXYZ中文abcdefghijklmnopqrstuvwxyz0123456789+/=";
        assertFalse(Base64Utils.isB64(srcD));
    }
	
	@Test
    public void testB64Chn()
    {
        /*String src = "this is the source. \n 中文输入!（）【】";
        String b64 = Base64Utils.b64FromString(src);
        System.out.println(b64);
        assertTrue(Base64Utils.isB64(b64));*/
        
        String result = Base64Utils.stringFromB64("h7TLbgT6bqT5zVr=");
      //  assertEquals(result, src);
        System.out.println(result);
        
       /* String str = "pkpm~!@#$%^&*()_+";
        String b64Str = Base64Utils.b64FromString(str);
        System.out.println(b64Str);
        assertTrue(Base64Utils.isB64(b64Str));
        
        String resultStr = Base64Utils.stringFromB64(b64Str);
        assertEquals(resultStr, str);
        System.out.println(resultStr);*/
    }
}
