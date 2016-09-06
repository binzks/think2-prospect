package org.think2framework.wap.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.think2framework.common.HttpServletUtils;
import org.think2framework.context.MessageFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoubin on 16/7/24. 统一的api异常处理,返回错误
 */
public class SimpleApiExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = LogManager.getLogger(SimpleApiExceptionHandler.class); // log4j日志对象

	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object o, Exception e) {
		logger.error(e);
		HttpServletUtils.writeResponse(httpServletResponse, MessageFactory.getJsonMessage(e.getMessage()));
		return null;
	}

}
