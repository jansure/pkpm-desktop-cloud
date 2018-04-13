/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cabr.pkpm.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author boxi.li
 */
public class AESUtil
{

    private static final String s_sB64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
    private static final String s_sAlgorithm = "AES/CBC/PKCS5Padding";

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

    public static String encryptAES(String message, String password)
    {
        if (password == null || password.isEmpty())
        {
            return message;
        }

        String b64EncryptedString = null;
        try
        {
            byte[] byteKey = getKey(password);

            byte[] iv = new byte[16];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(iv);

            SecretKeySpec keyspec = new SecretKeySpec(byteKey, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            // Instantiate the cipher
            Cipher cipher = Cipher.getInstance(s_sAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

            byte[] encrypted = cipher.doFinal(byteFromString(message));

            byte[] source = new byte[16 + encrypted.length];
            System.arraycopy(iv, 0, source, 0, 16);
            System.arraycopy(encrypted, 0, source, 16, encrypted.length);

            // B64
            byte[] b64Enc = Base64.getEncoder().encode(source);
            b64EncryptedString = stringFromByte(b64Enc);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException ex)
        {
            LogManager.getLogger().error("encrypt", ex);
        }
        catch (InvalidKeyException e)
        {
            LogManager.getLogger().warn("decrypt", e);
        }

        return b64EncryptedString;
    }

    public static String decryptAES(String code, String password)
    {
        String sOriginal = null;
        try
        {
            byte[] encrypted = Base64.getDecoder().decode(code);
            if (encrypted.length < 16)
            {
                return null;
            }

            byte[] iv = new byte[16];
            System.arraycopy(encrypted, 0, iv, 0, 16);
            byte[] source = new byte[encrypted.length - 16];
            System.arraycopy(encrypted, 16, source, 0, source.length);

            byte[] byteKey = getKey(password);
            SecretKeySpec keyspec = new SecretKeySpec(byteKey, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            Cipher cipherD = Cipher.getInstance(s_sAlgorithm);
            cipherD.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipherD.doFinal(source);

            sOriginal = stringFromByte(original);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException | InvalidAlgorithmParameterException ex)
        {
            LogManager.getLogger().error("decrypt", ex);
        }
        catch (InvalidKeyException e)
        {
            LogManager.getLogger().warn("invalid key", e);
        }

        return sOriginal;
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
    
}
