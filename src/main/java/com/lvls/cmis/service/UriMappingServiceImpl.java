package com.lvls.cmis.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UriMappingServiceImpl implements UriMappingService, UriMappingService_1 {

	private Properties uriMappings;
	private Pattern[] uriPatternsCompiled;
	private Map<String, String[]> uriPatternPathNames;

	/**
	 * "[^/]*" (any characters not "/")
	 */
	private static final Pattern ANY = Pattern.compile("\\[\\^/]\\*");

	@Override
	public String getUriPattern(String appRequestUri) {
		for (Pattern pattern : uriPatternsCompiled) {
			Matcher matcher = pattern.matcher(appRequestUri);
			boolean aha = matcher.matches();
			if (aha) {
				return pattern.pattern();	// aha!
			}
		}
		return null;	// not found
	}

	@Override
	public String getTargetForUriPattern(String uriPattern) {
		return uriMappings.getProperty(uriPattern);
	}

	/**
	 * Note: <code>/prod/{name: [a-z]+}</code> style variable regex patterns not supported yet.
	 */
	@Override
	public Map<String, String> getPathSegmentValues(String appRequestUri, String uriPattern) {
		Matcher matcher = ANY.matcher(uriPattern);

		String[] pathNames = uriPatternPathNames.get(uriPattern);

		if (pathNames.length == 0) {
			return null;
		}

		return getPathValues(appRequestUri, uriPattern, matcher, pathNames);
	}

	/**
	 * Example:
	 *  appRequestUri   /prod/apple/gala
	 *  uriPattern      /prod/[^/]* /[^/]*
	 *  matcher         [^/]*
	 *  pathNames       fruit, name - from /prod/{fruit}/{name}
	 *
	 * Logic:
	 * - Regex find() thru the uriPattern, each find() gives start and end positions.
	 * - inputPos tracks the position in appRequestUri.
	 * - The start of the named value is
	 *      inputPos + difference between the last find() end and the new find() start.
	 *
	 * Thoughts:
	 *  What about    /prod/{name: [a-z]+}   =>  /prod/[a-z]+   (Would need list of the regex patterns for uri)
	 *  Perhaps embedded in the pathNames, Jersey style.
	 *
	 * @param appRequestUri must match the uriPattern exactly
	 * @param uriPattern
	 * @param matcher
	 * @param pathNames must match uriPattern exactly
	 * @return map of named values
	 */
	private Map<String, String> getPathValues(String appRequestUri, String uriPattern, Matcher matcher, String[] pathNames) {
		Map<String, String> map = new HashMap<String, String>();

		int namesPos = 0;
		int inputPos = 0;
		int lastPatternEnd = 0;
		while (matcher.find()) {
			String value;
			int start = matcher.start();
			int end = matcher.end();
			if (end < uriPattern.length()) {
				char endChar = uriPattern.charAt(end);
				int endCharPos = appRequestUri.indexOf(endChar, start - inputPos);
				value = appRequestUri.substring(inputPos + (start - lastPatternEnd), endCharPos);
				inputPos = endCharPos;
			} else {
				value = appRequestUri.substring(inputPos + (start - lastPatternEnd));
			}
			lastPatternEnd = end;	// save

			String pathName = pathNames[namesPos++];
			map.put(pathName, value);
		}
		return map;
	}

	public final void setUriMappings(Properties uriMappings) {
		this.uriMappings = uriMappings;
	}

	public final void setUriPatternsCompiled(Pattern[] uriPatternsCompiled) {
		this.uriPatternsCompiled = uriPatternsCompiled;
	}

	public final void setUriPatternPathNames(Map<String, String[]> uriPatternPathNames) {
		this.uriPatternPathNames = uriPatternPathNames;
	}

}
