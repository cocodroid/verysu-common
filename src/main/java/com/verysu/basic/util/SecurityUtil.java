
package com.verysu.basic.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * 安全工具类：加密
 * @author Sjg
 * 2014-10-2
 * 下午07:03:25
 */
public class SecurityUtil {

    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish       
	
	public static String md5(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		return new BigInteger(1,md.digest()).toString(16);
	}
	
	public static String md5(String username,String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(username.getBytes());
		md.update(password.getBytes());
		return new BigInteger(1,md.digest()).toString(16);
	}
	
	/**
	 * 3DES解密
	 * @param value 待解密字符串
	 * @param key 原始密钥字符串
	 * @return
	 * @throws Exception
	 */
    public static String Decrypt3DES(String value, String key) throws Exception {
    	byte[] b = decryptMode(GetKeyBytes(key), Base64.decodeBase64(value));
        return new String(b);
    }
 
    /**
     * 3DES加密
     * @param value 待加密字符串
     * @param key 原始密钥字符串
     * @return
     * @throws Exception
     */
    public static String Encrypt3DES(String value, String key) throws Exception {
        String str = byte2Base64(encryptMode(GetKeyBytes(key), value.getBytes()));
        return str;

    }

    /**
     * 计算24位长的密码byte值,首先对原始密钥做MD5算hash值，再用前8位数据对应补全后8位
     * @param strKey
     * @return
     * @throws Exception
     */
    public static byte[] GetKeyBytes(String strKey) throws Exception {
        if (null == strKey || strKey.length() < 1)
            throw new Exception("key is null or empty!");
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
        alg.update(strKey.getBytes());
        byte[] bkey = alg.digest();
//        System.out.println("md5key.length=" + bkey.length);
//        System.out.println("md5key=" + byte2hex(bkey));
        int start = bkey.length;
        byte[] bkey24 = new byte[24];
        for (int i = 0; i < start; i++) {
            bkey24[i] = bkey[i];
        }

        for (int i = start; i < 24; i++) {//为了与.net16位key兼容
            bkey24[i] = bkey[i - start];
        }
//        System.out.println("byte24key.length=" + bkey24.length);
//        System.out.println("byte24key=" + byte2hex(bkey24));
        return bkey24;

    }

    /**
     * @param keybyte 加密密钥，长度为24字节
     * @param src 为被加密的数据缓冲区（源）  
     * @return
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); //加密 
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
       } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;

    }

    /**
     * 解密模式操作
     * @param keybyte 加密密钥，长度为24字节
     * @param src 加密后的缓冲区
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try { //生成密钥   
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密     
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成base64编码
     * @param b
     * @return
     */
    public static String byte2Base64(byte[] b) {
        return Base64.encodeBase64String(b);
    }

    /**
     * 转换成十六进制字符串  
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }


	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(md5("adminCocodroid","123456"));
		
		String key = "abcd1234";
        String password = "password";
        System.out.println("key=" + key + ",password=" + password);
        System.out.println();
        System.out.println("----------示例开始：使用java写的算法加密解密-----------");
       try {
            String encrypt = "";
            String decrypt = "";
           byte[] bkey = SecurityUtil.GetKeyBytes(key);
            encrypt = SecurityUtil.byte2Base64(SecurityUtil.encryptMode(bkey, password.getBytes()));
            System.out.println("用预转换密钥算加密结果=" + encrypt);
            System.out.println("加密后base64表示=" + SecurityUtil.byte2hex(Base64.decodeBase64(encrypt)));
            System.out.println("调用原始密钥算加密结果=" + SecurityUtil.Encrypt3DES(password, key));
           try {
                decrypt = new String(SecurityUtil.decryptMode(bkey, Base64.decodeBase64(encrypt)));
                System.out.println("用预转换密钥算解密结果=" + decrypt);
                System.out.println("调用原始密钥算解密结果=" + SecurityUtil.Decrypt3DES(encrypt, key));
            } catch (Exception ex) {
                System.out.println("Exception:" + ex.getMessage());
            }
        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getMessage());
        }
        System.out.println("----------示例结束：使用java写的算法加密解密-----------");

	}
	
}
