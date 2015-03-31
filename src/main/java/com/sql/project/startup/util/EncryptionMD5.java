package com.sql.project.startup.util;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName:     EncryptionMD5.java
 * @Description:   MD5加密
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-06 13:19:38 
 */
public class EncryptionMD5 {
	private static final Logger log = Logger.getLogger(EncryptionMD5.class);
	/**
	 * @Title:       getMD5
	 * @Description: 生成MD5密文
	 * @return:      String   
	 * @throws
	 */
	public static String getMD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			log.warn("NoSuchAlgorithmException caught!"+e.getMessage());
		} catch (UnsupportedEncodingException e) {
			log.warn(e);
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();
		
		int length = byteArray.length;
		
		for (int i = 0; i < length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
}
