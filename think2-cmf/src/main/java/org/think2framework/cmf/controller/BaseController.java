package org.think2framework.cmf.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import org.think2framework.cmf.bean.Module;
import org.think2framework.cmf.view.ViewFactory;
import org.think2framework.cmf.view.core.View;
import org.think2framework.common.JsonUtils;
import org.think2framework.common.exception.SimpleException;

/**
 * Created by zhoubin on 16/4/29. 基类控制器,所有控制器都需要继承此类
 */
public class BaseController {

	public final static String ENCODING = "UTF-8"; // 默认编码

	/**
	 * 从页面请求获取对应的view,获取view的时候校验用户是否拥有该模块的权限
	 * 
	 * @param request
	 *            页面请求
	 * @return view
	 */
	protected View getViewFromRequest(HttpServletRequest request) {
		String moduleId = request.getParameter("m");
		if (null == moduleId) {
			throw new SimpleException("错误的模块参数,请联系管理员!");
		}
		Map<String, Module> modules = JsonUtils.readString(request.getSession().getAttribute("adminModules").toString(),
				new TypeReference<Map<String, Module>>() {
				});
		Module module = modules.get(moduleId);
		if (null == module) {
			throw new SimpleException("用户没有模块[" + moduleId + "]的权限,请联系管理员!");
		}
		return ViewFactory.get(module.getModel());
	}

	/**
	 * 将value写入返回response
	 *
	 * @param response
	 *            返回对象
	 * @param value
	 *            写入值
	 */
	public void writeResponse(HttpServletResponse response, String value) {
		try {
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

}
