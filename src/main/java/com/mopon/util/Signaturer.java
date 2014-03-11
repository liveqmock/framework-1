package com.mopon.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;


/**
 * 
 * <p>Description: 私钥签名</p>
 * @date 2013年8月15日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component("signaturer")
public class Signaturer {
	
	/**
	 * 
	 * 方法用途:使用私钥签名 <br>
	 * 实现步骤: <br>
	 * @param  priKeyText 私钥,BASE64编码
	 * @param  plainText  明文
	 * @return 成功返回私钥数字签名,失败直接抛异常
	 */
	public byte[] sign(byte[] priKeyText, String plainText) throws Exception{
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new Base64().decode(priKeyText));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);
			Signature signet = Signature.getInstance("MD5withRSA");//用私钥对信息生成数字签名
			signet.initSign(prikey);
			signet.update(plainText.getBytes());
			byte[] signed = new Base64().encode(signet.sign());
			return signed;
		} catch (Exception e) {
			throw e;
		}
	}
}
