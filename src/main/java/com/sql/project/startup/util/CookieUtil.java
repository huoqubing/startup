package com.sql.project.startup.util;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sql.project.startup.entity.User;
import com.sql.project.startup.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName:     CookieUtil.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2013-07-13 12:39:14 
 */
public class CookieUtil {

	private final static String cookieDomainName = "gsid";// cookieName
	private final static String webKey = "startup";// webkey
	private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 2;// cookie默认有效期2个礼拜
	/** 
	 * @Title:        saveCookie 
	 * @Description:  自动登录成功之后保存相应的cookie到客户端本地 目前的做法为 Base64(用户名+Cookie有效期+ MD5(用户名+用户ID+Cookie有效期+webkey))
	 * @param:        @param user 
	 * @param:        @param response    
	 * @return:       void    
	 * @throws 
	 * @author        huafeng.huang
	 * @Date          2013-7-10 下午3:11:32 
	 */
	public static void saveCookie(User user, HttpServletResponse response) {
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 1000);
		String cookieValueWithMd5 = getMD5(user.getLoginName() + ":"
				+ user.getId() + ":" + validTime + ":" + webKey);
		String cookieValue = user.getLoginName() + ":" + validTime + ":"
				+ cookieValueWithMd5;
		String cookieValueBase64 = new String(Base64.encode(cookieValue
				.getBytes()));
		Cookie cookie = new Cookie(cookieDomainName, cookieValueBase64);
		cookie.setMaxAge(60 * 60 * 24 * 365 * 1);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/** 
	 * @Title:        compareCookie 
	 * @Description:  比较Cookie值，如果验证通过，则返回true，否则 返回false 并清除Cookie 
	 * @param:        @param request
	 * @param:        @param response
	 * @param:        @return    
	 * @return:       boolean    
	 * @throws java.io.UnsupportedEncodingException
	 * @throws com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException
	 * @throws 
	 * @author        huafeng.huang
	 * @Date          2013-7-10 下午3:16:27 
	 */
	public static User compareCookie(HttpServletRequest request,
			HttpServletResponse response,UserService service) throws UnsupportedEncodingException, Base64DecodingException {
		Cookie cookies[] = request.getCookies();
		String cookieValue = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookieDomainName.equals(cookies[i].getName())) {
					cookieValue = cookies[i].getValue();
					break;
				}
			}
		}
		if (cookieValue == null) {
			return null;
		}
		String cookieValueAfterDecode = new String(Base64.decode(cookieValue),
				"utf-8");
		String cookieValues[] = cookieValueAfterDecode.split(":");
		if (cookieValues.length != 3) {
			clearCookie(response);
			return null;
		}
		long validTimeInCookie = new Long(cookieValues[1]);
		if (validTimeInCookie < System.currentTimeMillis()) {
			clearCookie(response);
			return null;
		}
		String loginName = cookieValues[0];
		User user = (User)service.getByLoginName(loginName);
		if (user != null) {
			String md5ValueInCookie = cookieValues[2];
			String md5ValueFromUser = getMD5(user.getLoginName() + ":"
					+ user.getId()
					+ ":" + validTimeInCookie + ":" + webKey);
			// 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
			if (md5ValueFromUser.equals(md5ValueInCookie)) {
				return user;
			}
		} else {
			clearCookie(response);
			return null;
		}
		return null;
	}
	public static void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieDomainName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	// 获取Cookie组合字符串的MD5码的字符串----------------------------------------------------------------------------
	public static String getMD5(String value) {
		String result = null;
		try {
			byte[] valueByte = value.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(valueByte);
			result = toHex(md.digest());
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		return result;
	}
	private static String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);
		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}
		return sb.toString();
	}
}
