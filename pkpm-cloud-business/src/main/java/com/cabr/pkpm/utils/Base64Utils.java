package com.cabr.pkpm.utils;

import java.nio.charset.StandardCharsets;
public class Base64Utils {
	
	   private static final String s_sB64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	   
	   private static String stringFromByte(byte[] bytes)
	    {
	        return new String(bytes, StandardCharsets.UTF_8);
	    }

	    private static byte[] byteFromString(String src)
	    {
	        return src.getBytes(StandardCharsets.UTF_8);
	    }

	    private static byte[] getKey(String password)
	    {
	        byte[] bytePw = byteFromString(password);
	        byte[] byteKey = new byte[16];
	        for (int ix = 0; ix < 16; ++ix)
	        {
	            if (ix < bytePw.length)
	            {
	                byteKey[ix] = bytePw[ix];
	            }
	            else
	            {
	                byteKey[ix] = 7;
	            }
	        }
	        return byteKey;

	    }

	    public static String b64FromString(String src)
	    {
	        byte[] byB64Enc = Base64.getEncoder().encode(byteFromString(src));
	        return stringFromByte(byB64Enc);
	    }

	    public static String stringFromB64(String b64)
	    {
	        byte[] bySrc = Base64.getDecoder().decode(b64);
	        return stringFromByte(bySrc);
	    }
	    
	    public static boolean isB64(String src)
	    {
	        if (null == src || src.isEmpty())
	        {
	            return true;
	        }
	        if (src.contains("\n") || src.contains(" "))
	        {
	            return false;
	        }

	        for (int ix = 0; ix < src.length(); ++ix)
	        {
	            if (s_sB64.indexOf(src.codePointAt(ix)) < 0)
	            {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    public static void main(String[] args) {
			System.out.println("加密前的密码"+stringFromB64("gt6GcgT8zqT="));
		}
}
