package org.think2framework.cmf.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.think2framework.cmf.bean.Admin;
import org.think2framework.cmf.security.SessionFactory;
import org.think2framework.cmf.support.Constants;
import org.think2framework.common.EncryptUtils;
import org.think2framework.common.StringUtils;
import org.think2framework.common.exception.NonExistException;
import org.think2framework.common.exception.SimpleException;
import org.think2framework.context.ModelFactory;
import org.think2framework.orm.Query;

import java.util.List;

/**
 * Created by zhoubin on 16/4/29. 系统管理员控制器
 */
@Controller
@RequestMapping(value = "admin")
public class AdminController extends BaseController {

	@RequestMapping(value = "/index.do")
	public String index() {
		return "login";
	}

	@RequestMapping(value = "/offline.do")
	public String offline(Model model) {
		model.addAttribute("errMsg", "用户已在其他地方登录");
		return "login";
	}

	@RequestMapping(value = "/empower.do")
	public String empower(Model model) {
		model.addAttribute("errMsg", "用户尚未授权该模块");
		return "login";
	}

	@RequestMapping(value = "/home")
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request) {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Query query = ModelFactory.createQuery(Admin.class);
			query.eq("admin_code", username);
			query.eq("admin_status", Constants.STATUS_VALID);
			List<Admin> admins = query.queryForList(Admin.class);
			if (admins.size() == 0) {
				throw new SimpleException("用户不存在");
			} else if (admins.size() > 1) {
				throw new SimpleException("用户异常,请联系管理员");
			}
			Admin admin = admins.get(0);
			if (!admin.getPassword().equals(EncryptUtils.md5(EncryptUtils.md5(password)))) {
				throw new SimpleException("密码错误");
			}
			if (StringUtils.isBlank(admin.getRoleCode())) {
				throw new NonExistException("用户所属角色不存在");
			}
			if (null == admin.getRoleModules()) {
				throw new SimpleException("用户所属角色没有任何权限");
			}
			SessionFactory.buildAdmin(request.getSession(), admin);
			return "redirect:/admin/home.view";
		} catch (Exception e) {
			if (null != e.getMessage()) {
				model.addAttribute("errMsg", e.getMessage());
			} else {
				model.addAttribute("errMsg", e);
			}
			return "login";
		}
	}

	/**
	 * 登出，记录日志销毁session
	 *
	 * @param request
	 *            请求
	 * @return 返回到登录页面
	 */
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "login";
	}
}
