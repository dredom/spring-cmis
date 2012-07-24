// Copyright (c) 2010 - Level Studios
// All rights reserved.
package com.lvls.cmis.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author auntiedt
 *
 */
@Controller
public class HelloController {

    protected final Log logger = LogFactory.getLog(getClass());

    private String view;

    @RequestMapping(value = "/hello", method = RequestMethod.GET, headers = "accept=text/*")
	public ModelAndView get() {
	       logger.info("Returning hello view");
	        System.out.println("stdout - Returning hello view");

	        ModelAndView mv = new ModelAndView(view);
	        mv.addObject("name");
	        return mv;
	}

    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
	public ModelAndView getWithName(@PathVariable("name") String name) {
	       logger.info("Returning hello name view");
	        System.out.println("stdout - Returning hello name view");

	        ModelAndView mv = new ModelAndView(view);
	        mv.addObject("name", name);
	        return mv;
	}

	public final void setView(String view) {
		this.view = view;
	}
}
