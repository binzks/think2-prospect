package org.think2framework.cmf.security;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.think2framework.cmf.bean.*;
import org.think2framework.cmf.support.Constants;
import org.think2framework.common.HttpServletUtils;
import org.think2framework.common.JsonUtils;
import org.think2framework.common.StringUtils;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.common.exception.SimpleException;
import org.think2framework.context.ConstantFactory;
import org.think2framework.context.ModelFactory;
import org.think2framework.orm.Query;

/**
 * Created by zhoubin on 16/5/5. session工厂,登录管理员的session
 */
public class SessionFactory implements HttpSessionListener {

	private static final Logger logger = LogManager.getLogger(SessionFactory.class);

	private static Map<String, HttpSession> sessions;

	private static String systemName; // 系统名称

	private static String systemAbbreviation; // 系统简称,3个字

	private static String systemProfile; // 系统简介

	private static String systemVersion; // 系统版本号

	public SessionFactory() {
		sessions = new HashMap<>();
	}

	/**
	 * 生成一个登录管理员的session,包含用户信息,权限
	 *
	 * @param session
	 *            管理员session
	 * @param admin
	 *            管理员信息
	 */
	public static void buildAdmin(HttpSession session, Admin admin) {
		Query query = ModelFactory.createQuery(Module.class);
		query.eq("module_status", Constants.STATUS_VALID);
		query.asc("module_parent_id", "module_order");
		List<Module> modules = query.queryForList(Module.class);
		Map<String, Module> adminModules = new LinkedHashMap<>();
		Map<String, AdminRolePermissions> rolePermissions = admin.getRoleModules();
		for (Module module : modules) {
			AdminRolePermissions permissions = rolePermissions.get(module.getId().toString());
			if (null == permissions) {
				continue;
			}
			adminModules.put(module.getId().toString(),
					new Module(module.getId(), module.getName(), module.getParentId(), module.getStatus(),
							module.getType(), module.getUri(), module.getIcon(), module.getOrder(), module.getModel(),
							module.getComment(), permissions.getActions(), permissions.getColumns()));
		}
		session.setAttribute("adminId", admin.getId()); // 管理员id
		session.setAttribute("adminCode", admin.getCode()); // 管理员编号
		session.setAttribute("adminName", admin.getName()); // 管理员名称
		session.setAttribute("adminRoleName", admin.getRoleName()); // 管理员角色名称
		session.setAttribute("adminModules", JsonUtils.toString(adminModules)); // 管理员的模块权限
		// 将用户session缓存起来,如果原来已经有用户session则替换掉,把用户最后一个session做为有效session,主要用于判断用户多地登录
		sessions.put(admin.getId() + admin.getCode(), session);
		logger.debug("admin {} {} login success", admin.getId(), admin.getCode());
	}

	/**
	 * 检验请求的授权,如果未登录则跳转登录页面,如果用户在其他地方登录则跳转登录页面并提示,如果用户请求模块功能则先判断是否有模块权限
	 *
	 * @param request
	 *            请求
	 * @return 跳转地址,如果为空表示检验通过,可以进行请求
	 */
	public static String check(HttpServletRequest request) {
		String redirect = "";
		String uri = request.getRequestURI();
		// 过滤掉不拦截的请求
		if ("/".equals(uri) || "/admin/index.do".equals(uri) || "/admin/login.do".equals(uri)
				|| "/admin/offline.do".equals(uri) || "/admin/empower.do".equals(uri)) {
			return redirect;
		}
		HttpSession session = request.getSession();
		Object adminId = session.getAttribute("adminId");
		Object adminCode = session.getAttribute("adminCode");
		logger.info("{\"adminId\":\"" + adminId + "\",\"adminCode\":\"" + adminCode + "\",\"uri\":\""
				+ request.getRequestURI() + "\",\"ip\":\"" + HttpServletUtils.getRequestIpAddress(request)
				+ "\",\"params\":" + JsonUtils.toString(request.getParameterMap()) + "}");
		if (null != adminId && null != adminCode) {
			HttpSession realSession = sessions.get(adminId.toString() + adminCode.toString());
			// 如果当前请求的session和登录管理员的session不一样,表示管理员已经在其他地方登录,销毁session提示用户在其他地方登录
			if (!realSession.equals(session)) {
				session.setAttribute("remote", "true");
				session.invalidate();
				return "/admin/offline.do";
			} else {
				// 如果请求具体模块,则先校验登录管理员是否拥有模块权限
				String moduleId = request.getParameter("m");
				if (StringUtils.isNotBlank(moduleId)) {
                    Map<String, Module> modules = JsonUtils.readString(request.getSession().getAttribute("adminModules").toString(),
                            new TypeReference<Map<String, Module>>() {
                            });
                    Module module = modules.get(moduleId);
                    if (null == module) {
                        throw new SimpleException("用户没有模块[" + moduleId + "]的权限,请联系管理员!");
                    }
					if (null == modules.get(moduleId)) {
						return "/admin/empower.do";
					}
				} else {
					return "";
				}
			}
		} else { // 如果用户adminId和adminCode为null表示用户未登录
			return "/admin/index.do";
		}
		return redirect;
	}

	/**
	 * 获取登录管理员的session
	 * 
	 * @param adminId
	 *            管理员id
	 * @param adminCode
	 *            管理员编号
	 * @return session
	 */
	public static HttpSession getSession(String adminId, String adminCode) {
		HttpSession session = sessions.get(adminId + adminCode);
		if (null == session) {
			throw new NonExistException(HttpSession.class.getName() + " " + adminId + " " + adminCode);
		}
		return session;
	}

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		if (StringUtils.isBlank(systemName)) {
			systemName = ConstantFactory.get("system", "name");
		}
		if (StringUtils.isBlank(systemAbbreviation)) {
			systemAbbreviation = ConstantFactory.get("system", "abbreviation");
		}
		if (StringUtils.isBlank(systemProfile)) {
			systemProfile = ConstantFactory.get("system", "profile");
		}
		if (StringUtils.isBlank(systemVersion)) {
			systemVersion = ConstantFactory.get("system", "version");
		}
		session.setAttribute("systemName", systemName);
		session.setAttribute("systemAbbreviation", systemAbbreviation);
		session.setAttribute("systemProfile", systemProfile);
		session.setAttribute("systemVersion", systemVersion);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		Object adminId = session.getAttribute("adminId");
		Object adminCode = session.getAttribute("adminCode");
		if (null != adminId && null != adminCode) {
			if (null != session.getAttribute("remote")) {
				logger.info("admin {} {} login in other places", adminId, adminCode);
			} else {
				logger.info("admin {} {} logout successful", adminId, adminCode);
			}
			sessions.remove(adminId.toString() + adminCode.toString());
		}
	}
}
