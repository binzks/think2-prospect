package org.think2framework.cmf.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.think2framework.cmf.bean.Model;

/**
 * Created by zhoubin on 16/7/24. 统一的异常处理,返回错误页面
 */
public class SimpleCmfExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = LogManager.getLogger(SimpleCmfExceptionHandler.class); // log4j日志对象

	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) {
		logger.error(e);
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("msg", e);
		return modelAndView;
	}

}
