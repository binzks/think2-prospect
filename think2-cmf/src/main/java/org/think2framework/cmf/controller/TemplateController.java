package org.think2framework.cmf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.think2framework.cmf.view.core.View;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "tpl")
public class TemplateController extends BaseController {

	@RequestMapping(value = {"/list{page}"})
	public String getList(@PathVariable Integer page, Model model, HttpServletRequest request) {
		View view = getViewFromRequest(request);
		//
		// ListView listView = viewModel.createListView();
		// String listHtml = listView.build(request, mid, module.getActions(),
		// module.getColumns(),
		// null == page ? 1 : page);
		// model.addAttribute("title", viewModel.getTitle());
		// model.addAttribute("html", listHtml);
		return "/template/list";
	}
}
