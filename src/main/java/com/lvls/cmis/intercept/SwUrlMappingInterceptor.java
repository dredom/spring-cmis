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
		String msg = "I am the Interceptor";
		request.setAttribute("switch.msg", msg);
		String appRequestUri = getAppRequestUri(request);
		request.setAttribute("switch.appRequestUri", appRequestUri);

		return true;
	}

	/**
	 * Remove context path and servlet path
	 * @param request
	 * @return pure application request URI
	 */
	private String getAppRequestUri(HttpServletRequest request) {
		String reqUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		return getAppRequestUri(reqUri, contextPath, servletPath);
	}

	String getAppRequestUri(String reqUri, String contextPath, String servletPath) {
		String appUri;
		final String prefix = contextPath + servletPath;
		int beginIndex = prefix.length();
		if (beginIndex > 0 && beginIndex < reqUri.length()) {
			appUri = reqUri.substring(beginIndex);
		} else {
			appUri = reqUri;
		}
		return appUri;
	}
}
