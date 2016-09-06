package org.think2framework.common;

import org.think2framework.common.exception.SimpleException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhoubin on 16/7/22. http servlet工具
 */
public class HttpServletUtils {

	public static final String ENCODING = "utf-8"; // 编码

	private final static String IPV4_REGEX = "(?:[01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])(?:\\.(?:[01]?[0-9]{1,2}|2[0-4][0-9]|25[0-5])){3}";

	public final static Matcher IPV4_MATCHER = Pattern.compile(IPV4_REGEX).matcher("");

	/**
	 * 将字符串写入返回response
	 *
	 * @param response
	 *            返回对象
	 * @param value
	 *            写入值
	 */
	public static void writeResponse(HttpServletResponse response, String value) {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");// 允许跨域
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "No-cache");
			byte[] bytes = value.getBytes(ENCODING);
			response.setCharacterEncoding(ENCODING);
			response.setContentType("text/plain");
			response.setContentLength(bytes.length);
			response.getOutputStream().write(bytes);
		} catch (IOException e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * 获取请求的ip地址
	 * 
	 * @param request
	 *            请求
	 * @return ip地址
	 */
	public static String getRequestIpAddress(HttpServletRequest request) {
		String realIP = request.getHeader("x-forwarded-for");
		if (realIP != null && realIP.length() != 0) {
			while ((realIP != null && realIP.equals("unknow"))) {
				realIP = request.getHeader("x-forwarded-for");
			}
			if (realIP.indexOf(',') >= 0) {
				realIP = realIP.substring(0, realIP.indexOf(','));
			}
		}
		if (realIP == null || realIP.length() == 0 || "unknown".equalsIgnoreCase(realIP)) {
			realIP = request.getHeader("Proxy-Clint-IP");
		}
		if (realIP == null || realIP.length() == 0 || "unknown".equalsIgnoreCase(realIP)) {
			realIP = request.getHeader("WL-Proxy-Clint-IP");
		}
		if (realIP == null || realIP.length() == 0 || "unknown".equalsIgnoreCase(realIP)) {
			realIP = request.getRemoteAddr();
		}
		return realIP;
	}

	public static int ipv4ToInt(String ipv4) {
		if (!IPV4_MATCHER.reset(ipv4).matches()) {
			throw new IllegalArgumentException("IPv4 format ERROR!");
		}
		String[] strs = ipv4.split("\\.");
		int result = 0;
		for (int i = 0, k = strs.length; i < k; i++) {
			result |= Integer.parseInt(strs[i]) << ((k - 1 - i) * 8);
		}
		return result;
	}

	public static String int4Ipv4(int ipv4Int) {
		StringBuilder sb = new StringBuilder(15);
		for (int i = 0; i < 4; i++) {
			if (i > 0) {
				sb.append('.');
			}
			sb.append((ipv4Int >>> ((3 - i) * 8)) & 0xff);
		}
		return sb.toString();
	}

	public static boolean isIpInForbiddenIp(String forbiddenIp, String ip) {
		if (StringUtils.isBlank(forbiddenIp))
			return false;
		if (StringUtils.isBlank(ip))
			return true;
		if (forbiddenIp.equals(ip))
			return true;
		if (forbiddenIp.indexOf("*") >= 0) {
			String[] fip = forbiddenIp.split("\\.");
			String[] fromIp = ip.split("\\.");
			if (fip.length != 4 || fromIp.length != 4)
				return false;
			for (int i = 0; i < 4; i++) {
				String s1 = fip[i];
				String s2 = fromIp[i];
				if (!s1.equals(s2)) {
					if (!s1.equals("*"))
						return false;
				}
			}
			return true;
		}
		return false;
	}

}
