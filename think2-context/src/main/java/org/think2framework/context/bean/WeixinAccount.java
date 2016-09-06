package org.think2framework.context.bean;

/**
 * Created by zhoubin on 16/8/1. 微信账号信息
 */
public class WeixinAccount {

	private String name; // 微信的名称

	private String appId; // 微信appId

	private String appSecret; // 微信app密钥

	private Integer expiresIn; // token和ticket有效期,单位秒,默认7000秒

	private String nonce; // 使用JS-JDK的页面生成签名的默认随机字符串

	private String token; // 服务端配置令牌

	private String encodingAESKey; // 服务端配置消息加解密密钥

	public WeixinAccount() {
		this.expiresIn = 7000;
	}

	public WeixinAccount(String name, String appId, String appSecret, String nonce) {
		this.name = name;
		this.appId = appId;
		this.appSecret = appSecret;
		this.nonce = nonce;
		this.expiresIn = 7000;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEncodingAESKey() {
		return encodingAESKey;
	}

	public void setEncodingAESKey(String encodingAESKey) {
		this.encodingAESKey = encodingAESKey;
	}
}
