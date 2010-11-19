// Copyright (c) 2010 - Level Studios
// All rights reserved.
package com.lvls.cmis.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author auntiedt
 *
 */
//@RequestMapping("/sw")
@Controller
public class SwitchController {

    protected final Log logger = LogFactory.getLog(getClass());

    private String view = "switch";

    // Annotation to inject the canonical URL pattern value (provided by intercepter?)

    @RequestMapping(value = "/**", method = RequestMethod.GET, headers = "accept=text/*")
	public ModelAndView getContent(HttpServletRequest request, WebRequest webRequest) {
	       logger.info("Returning switch name view");
	       System.out.println("stdout - Switch msg = " + request.getAttribute("switch.msg"));
	        System.out.println("stdout - Returning switch view " + request.getRequestURI());

	        ModelAndView mv = new ModelAndView(view);
	        mv.addObject("request", request);
	        mv.addObject("msg", request.getAttribute("switch.msg"));
	        List<Map.Entry<String, String>> lines = new ArrayList<Map.Entry<String,String>>();
	        lines.add(entry("requestURI", request.getRequestURI()));
	        lines.add(entry("contextPath", webRequest.getContextPath()));
	        lines.add(entry("contextPath", request.getContextPath()));
	        lines.add(entry("servletPath", request.getServletPath()));
	        mv.addObject("lines", lines);

	        return mv;
	}

    private Map.Entry<String, String> entry(final String key, final String value) {
    	return new Map.Entry<String, String>() {
			public String getKey() { return key; }
			public String getValue() { return value; }
			public String setValue(String value) { return null; }
		};
    }
	public final void setView(String view) {
		this.view = view;
	}
}
