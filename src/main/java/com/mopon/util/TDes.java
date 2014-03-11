package com.mopon.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>Description: </p>
 * @date 2013年10月29日
 * @author REAGAN
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
public class TDes {

	public static final byte[] Key = "abcdefgh".getBytes();
	/**
	 * 定义 加密算法,可用 DES,DESede,Blowfish
	 */
	private static final String Algorithm = "DES";

	/**
	 * 方法用途: 加密字符串<br>
	 * 实现步骤: <br>
	 * @param keybyte 密钥字节数组
	 * @param src 源字符串
	 * @return 加密后的字符串BYTE数组
	 */
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 加密
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
	 * 方法用途: 解密字符串<br>
	 * 实现步骤: <br>
	 * @param keybyte 密钥字节数组
	 * @param src 源字符串
	 * @return 加密后的字符串BYTE数组
	 */
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try { // 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm); // 解密
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

	public static void main(String[] args) { // 添加新安全算法,如果用JCE就要把它添加进去
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		final byte[] keyBytes = Key; // 8字节的密钥
		String szSrc = "sjfkalsjioawjlnvkl;jf;aowejf;klasjfo;wajefiojaskl;dfjnxv;asljkdf;";
		System.out.println("加密前的字符串:" + szSrc);
		byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());
		System.out.println("加密后的字符串:" + new String(encoded) + " size:" + encoded.length);
		byte[] srcBytes = decryptMode(keyBytes, encoded);
		System.out.println("解密后的字符串:" + (new String(srcBytes)));
	}
}
