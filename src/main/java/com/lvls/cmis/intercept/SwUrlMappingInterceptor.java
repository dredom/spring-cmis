// Copyright (c) 2010 - Level Studios
// All rights reserved.
package com.lvls.cmis.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author auntiedt
 *
 */
public class SwUrlMappingInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String msg = "I am an interceptor";
		request.setAttribute("switch.msg", msg);
		return true;
	}

}
