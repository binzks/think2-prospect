package org.think2framework.wap.bean.weixin;

import org.think2framework.orm.core.ClassUtils;
import org.think2framework.orm.persistence.Column;
import org.think2framework.orm.persistence.Table;

/**
 * Created by zhoubin on 16/8/4. 微信用户
 */
@Table(name = "weixin_user", pk = "weixin_user_id", uniques = {"weixin_app_id,openid"}, comment = "微信用户")
public class WeixinUser {

	@Column(name = "weixin_user_id", type = ClassUtils.TYPE_INTEGER, length = 11, comment = "主键")
	private Integer id;

	@Column(name = "weixin_app_id", comment = "微信appid")
	private String appId;

	@Column(type = ClassUtils.TYPE_INTEGER, length = 10, comment = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
	private String subscribe;

	@Column(name = "openid", comment = "用户openid")
	private String openId;

	@Column(comment = "用户的昵称")
	private String nickname;

	@Column(type = ClassUtils.TYPE_INTEGER, length = 10, comment = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
	private String sex;

	@Column(comment = "用户所在城市")
	private String city;

	@Column(comment = "用户所在国家")
	private String country;

	@Column(comment = "用户所在省份")
	private String province;

	@Column(comment = "用户的语言，简体中文为zh_CN")
	private String language;

	@Column(name = "headimgurl", length = 255, comment = "用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。\n")
	private String headImgUrl;

	@Column(name = "subscribe_time", length = 20, comment = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
	private String subscribeTime;

	@Column(name = "unionid", comment = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
	private String unionId;

	@Column(length = 255, comment = "公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注")
	private String remark;

	@Column(name = "groupid", length = 255, comment = "用户所在的分组ID（兼容旧的用户分组接口）")
	private String groupId;

	@Column(name = "tagid_list", length = 255, comment = "用户被打上的标签ID列表")
	private String tagIdList;

	@Column(name = "modify_time", type = ClassUtils.TYPE_INTEGER, length = 20, comment = "最后修改时间")
	private Integer modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getTagIdList() {
		return tagIdList;
	}

	public void setTagIdList(String tagIdList) {
		this.tagIdList = tagIdList;
	}

	public Integer getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Integer modifyTime) {
		this.modifyTime = modifyTime;
	}
}
