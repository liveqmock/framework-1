package com.mopon.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;


/**
 * 
 * <p>Description:公钥验证  </p>
 * @date 2013年8月15日
 * @author tongbiao
 * @version 1.0
 * <p>Company:Mopon</p>
 * <p>Copyright:Copyright(c)2013</p>
 */
@Component("signProvider")
public class SignProvider {
	
	/**
	 * 
	 * 方法用途: <br>
	 * 实现步骤: 校验数字签名,成功返回true,失败返回false,要求全部参数不能为空<br>
	 * @param   pubKeyText 公钥,BASE64编码
	 * @param   plainText 明文
	 * @param   signText 数字签名的密文,BASE64编码
	 * @return  校验成功返回true 失败返回false
	 * @throws Exception
	 */
	public boolean verify(byte[] pubKeyText, String plainText, byte[] signText) throws Exception {
		try {
			//解密由base64编码的公钥,并构造X509EncodedKeySpec对象
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(new Base64().decode(pubKeyText));
			//RSA对称加密算法
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			//取公钥匙对象
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			//解密由base64编码的数字签名
			byte[] signed = new Base64().decode(signText);
			Signature signatureChecker = Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(pubKey);
			signatureChecker.update(plainText.getBytes());
			//验证签名是否正常
			if (signatureChecker.verify(signed))
				return true;
			else
				return false;
		} catch (Exception e) {
			throw e;
		}
	}
}
