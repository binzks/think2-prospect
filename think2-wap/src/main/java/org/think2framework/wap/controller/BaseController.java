package org.think2framework.wap.controller;

import org.think2framework.common.HttpServletUtils;
import org.think2framework.context.MessageFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhoubin on 16/7/18. 基础控制器,所有控制器父类
 */
public class BaseController {

	public void writeData(HttpServletResponse response, Object data) {
		HttpServletUtils.writeResponse(response, MessageFactory.getSuccessJsonMessage(data));
	}

}
