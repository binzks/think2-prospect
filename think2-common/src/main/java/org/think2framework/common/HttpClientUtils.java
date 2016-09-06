package org.think2framework.common;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.think2framework.common.exception.SimpleException;

public class HttpClientUtils {

	private static final String DEFAULT_ENCODING = "UTF-8";

	/**
	 * http get请求，UTF-8编码格式，返回字符串
	 *
	 * @param url
	 *            请求url
	 * @return 返回值字符串
	 */
	public static String get(String url) {
		return get(url, null, DEFAULT_ENCODING);
	}

	/**
	 * http get请求，UTF-8编码格式，返回字符串
	 *
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @return 返回值字符串
	 */
	public static String get(String url, Map<String, Object> params) {
		return get(url, params, DEFAULT_ENCODING);
	}

	/**
	 * http get请求，返回字符串
	 *
	 * @param url
	 *            请求url
	 * @param encoding
	 *            编码格式
	 * @return 返回值字符串
	 */
	public static String get(String url, String encoding) {
		return get(url, null, encoding);
	}

	/**
	 * map转换为http请求的参数
	 *
	 * @param value
	 *            map参数
	 * @return http请求参数
	 */
	public static List<NameValuePair> mapToNameValuePairs(Map<String, Object> value) {
		List<NameValuePair> nameValuePairs = null;
		if (null != value && value.size() > 0) {
			nameValuePairs = new ArrayList<>();
			for (Map.Entry<String, Object> entry : value.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}
		return nameValuePairs;
	}

	/**
	 * 获取http get的请求url
	 * 
	 * @param url
	 *            url
	 * @param params
	 *            参数
	 * @param encoding
	 *            编码
	 * @return url拼接参数
	 * @throws Exception
	 *             异常
	 */
	private static String getUrl(String url, Map<String, Object> params, String encoding) throws Exception {
		String result = url;
		if (null != params && params.size() > 0) {
			List<NameValuePair> nameValuePairs = mapToNameValuePairs(params);
			String p = new UrlEncodedFormEntity(nameValuePairs, encoding).toString();
			if (null != p) {
				if (result.contains("?")) {
					result += "&" + p;
				} else {
					result += "?" + p;
				}
			}
		}
		return result;
	}

	private static String getResponseString(CloseableHttpResponse response, String encoding) throws Exception {
		if (response.getStatusLine().getStatusCode() != 200) {
			throw new Exception("http response status line " + response.getStatusLine());
		}
		HttpEntity entity = response.getEntity();
		String result = EntityUtils.toString(entity, encoding);
		EntityUtils.consume(entity);
		return result;
	}

	/**
	 * http get请求，返回字符串
	 *
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @param encoding
	 *            编码格式
	 * @return 返回值字符串
	 */
	public static String get(String url, Map<String, Object> params, String encoding) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(getUrl(url, params, encoding));
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				return getResponseString(response, encoding);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * http get请求，返回byte数组
	 *
	 * @param url
	 *            请求url
	 * @return 返回byte数组
	 */
	public static byte[] getByteArray(String url) {
		return getByteArray(url, null, DEFAULT_ENCODING);
	}

	/**
	 * http get请求，返回byte数组
	 *
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @return 返回byte数组
	 */
	public static byte[] getByteArray(String url, Map<String, Object> params, String encoding) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(getUrl(url, params, encoding));
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new Exception("http get response status line " + response.getStatusLine());
				}
				HttpEntity entity = response.getEntity();
				byte[] result = EntityUtils.toByteArray(entity);
				EntityUtils.consume(entity);
				return result;
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new SimpleException(e);
		}
	}

	/***
	 * http post请求，UTF-8编码格式，返回字符串
	 *
	 * @param url
	 *            请求url
	 * @return 返回值字符串
	 */
	public static String post(String url) {
		return post(url, null, DEFAULT_ENCODING);
	}

	/***
	 * http post请求，UTF-8编码格式，返回字符串
	 *
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @return 返回值字符串
	 */
	public static String post(String url, Map<String, Object> params) {
		return post(url, params, DEFAULT_ENCODING);
	}

	/**
	 * http post请求，返回字符串
	 *
	 * @param url
	 *            请求url
	 * @param params
	 *            请求参数
	 * @param encoding
	 *            编码格式
	 * @return 返回值字符串
	 */
	public static String post(String url, Map<String, Object> params, String encoding) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			if (null != params && params.size() > 0) {
				List<NameValuePair> nameValuePairs = mapToNameValuePairs(params);
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, encoding));
			}
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				return getResponseString(response, encoding);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new SimpleException(e);
		}
	}

	/**
	 * http post一个json字符串数据,utf-8编码
	 * 
	 * @param url
	 *            url
	 * @param params
	 *            参数字符串
	 * @return 返回字符串
	 */
	public static String postJson(String url, String params) {
		return postJson(url, params, DEFAULT_ENCODING);
	}

	/**
	 * http post一个json字符串数据
	 * 
	 * @param url
	 *            url
	 * @param params
	 *            参数字符串
	 * @param encoding
	 *            编码
	 * @return 返回字符串
	 */
	public static String postJson(String url, String params, String encoding) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-type", "application/json; charset=" + encoding);
			httpPost.setHeader("Accept", "application/json");
			StringEntity entity = new StringEntity(params, Charset.forName(encoding));
			entity.setContentEncoding(encoding);
			entity.setContentType("application/json");
			httpPost.setEntity(entity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				return getResponseString(response, encoding);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			throw new SimpleException(e);
		}
	}

}
