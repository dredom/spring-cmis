// Copyright (c) 2010 - Level Studios
// All rights reserved.
package com.lvls.cmis.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Old style with config mapping.
 * @author auntiedt
 *
 */
@Controller
public class HelloController2 extends AbstractController {

    protected final Log logger = LogFactory.getLog(getClass());

    private String view;

	public final void setView(String view) {
		this.view = view;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	       logger.info("Returning hello2 view");
	        System.out.println("stdout - Returning hello view");

	        ModelAndView mv = new ModelAndView(view);
	        mv.addObject("name");
	        return mv;
	}
}
