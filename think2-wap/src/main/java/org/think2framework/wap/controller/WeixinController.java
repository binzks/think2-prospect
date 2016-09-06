package org.think2framework.wap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.think2framework.common.StringUtils;
import org.think2framework.context.ModelFactory;
import org.think2framework.context.WeixinFactory;
import org.think2framework.orm.Writer;
import org.think2framework.wap.bean.weixin.WeixinUser;
import org.think2framework.weixin.core.WeixinObject;
import org.think2framework.weixin.exception.WeixinErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by zhoubin on 16/8/2. 微信控制器
 */
@Controller
@RequestMapping(value = "/weixin")
public class WeixinController extends BaseController {

	/**
	 * 更新所有的微信用户,存储到表
	 * 
	 * @param request
	 *            页面请求参数 wx表示后台定义的微信名称
	 * @param response
	 *            页面响应,返回结果
	 */
	@RequestMapping(value = {"/user/update"})
	public void getOpenIds(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("wx"); // 微信名称
		WeixinObject weixinObject = WeixinFactory.get(name);
		List<String> openIds = weixinObject.getAllOpenIds();
		StringBuilder result = new StringBuilder();
		int errCount = 0;
		Writer writer = ModelFactory.createWriter(WeixinUser.class);
		String appId = weixinObject.getAppId();
		for (String openId : openIds) {
			try {
				Map<String, Object> map = weixinObject.getUserInfo(openId);
				map.put("modify_time", System.currentTimeMillis() / 1000);
				map.put("weixin_app_id", appId);
				String nick = map.get("nickname").toString();
				if (StringUtils.isNotBlank(nick)) {
					nick = nick.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
				}
				map.put("nickname", nick);
				writer.save(map, "weixin_app_id", "openid");
			} catch (WeixinErrorException e) {
				errCount++;
				result.append("用户[" + openId + "]获取信息错误:" + e.getMessage() + "\n");
			}
		}
		if (errCount > 0) {
			int successCount = openIds.size() - errCount;
			writeData(response, "用户更新完成,成功" + successCount + "个,错误" + errCount + "个\n" + result.toString());
		} else {
			writeData(response, "用户更新完成,成功" + openIds.size() + "个");
		}
	}

	/**
	 * 获取微信jsapi的ticket
	 * 
	 * @param request
	 *            页面请求参数 wx表示后台定义的微信名称
	 * @param response
	 *            页面响应,返回结果
	 */
	@RequestMapping(value = {"/getJsTicket"})
	public void getJsTicket(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("wx"); // 微信名称
		WeixinObject weixinObject = WeixinFactory.get(name);
		writeData(response, weixinObject.getJsApiTicket());
	}

	/**
	 * 获取微信js页面的签名
	 * 
	 * @param request
	 *            页面请求参数,wx表示后台定义的微信名称,time时间戳,url页面url
	 * @param response
	 *            页面响应,返回结果
	 */
	@RequestMapping(value = {"/getJsSignature"})
	public void getJsSignature(HttpServletRequest request, HttpServletResponse response) {
		String name = request.getParameter("wx"); // 微信名称
		String timestamp = request.getParameter("time"); // 时间戳
		String url = request.getParameter("url"); // 页面url
		WeixinObject weixinObject = WeixinFactory.get(name);
		String sign = weixinObject.getJsSignature(timestamp, url);
		writeData(response, sign);
	}

}
