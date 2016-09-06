package org.think2framework.context.bean;

import java.util.List;

/**
 * Created by zhoubin on 16/8/3. 微信json对象
 */
public class Weixin {

	private Boolean cluster; // 是否集群部署

	private List<WeixinAccount> accounts; // 账号列表

	public Boolean getCluster() {
		return cluster;
	}

	public void setCluster(Boolean cluster) {
		this.cluster = cluster;
	}

	public List<WeixinAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<WeixinAccount> accounts) {
		this.accounts = accounts;
	}
}
