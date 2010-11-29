// Copyright (c) 2010 - Level Studios
// All rights reserved.
package com.lvls.cmis.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Regex path segment name patterns in style <pre>
 *   {myname}
 *   {myname: [a-z]}
 * </pre>
 * Note: "{myname: [a-z]}" not yet supported.
 * @author auntiedt
 * @version 1.0, 2010-11
 */
public class UriMappingConfigFactoryImpl implements UriMappingConfigFactory {

	private static final Pattern NAME_PATTERN = Pattern.compile("\\{[^}]*\\}");
	private static final String PATH_PATTERN_STRING = "[^/]*";

	private static Log log = LogFactory.getLog(UriMappingConfigFactory.class);

	private String propertiesFilename;
	private Properties uriMappingsProperties;

	private Pattern[] uriPatternsCompiled;
	private Map<String, String[]> uriPatternPathNames;
	private Properties uriMappings;

	private void init() throws RuntimeException {
		log.debug("Parsing URI mapping configuration file " + propertiesFilename + "...");
		List<Pattern> patternList = new ArrayList<Pattern>();
		Properties uriMappings = new Properties();
		Map<String, String[]> uriPatternPathNames = new HashMap<String, String[]>();
		Enumeration<String> propertyNames = (Enumeration<String>) uriMappingsProperties.propertyNames();
		String line = "";
		int count = 0;
		try {
			while (propertyNames.hasMoreElements()) {
				String uriLine = propertyNames.nextElement();
				line = uriLine + "=" + uriMappingsProperties.getProperty(uriLine);
				String[] pathNames = buildPathNames(uriLine);
				String[] pathPatterns = buildPathPatterns(uriLine, pathNames);
				Pattern pathPattern = buildPathPattern(uriLine, pathPatterns);
				patternList.add(pathPattern);
				pathNames = purifyPathNames(pathNames);
				uriPatternPathNames.put(pathPattern.pattern(), pathNames);
				uriMappings.put(pathPattern.pattern(), uriMappingsProperties.getProperty(uriLine));
				count++;
			}
		} catch (Exception e) {
			log.error("URI mapping configuration fail on line '" + line + "'", e);
			throw new IllegalArgumentException(e);
		} finally {
			this.uriPatternsCompiled = patternList.toArray(new Pattern[0]);
			this.uriPatternPathNames = uriPatternPathNames;
			this.uriMappings = uriMappings;
		}
		log.info("Parsing of URI mapping configuration file " + propertiesFilename + " completed with " + count + " entries.");
	}

	private String[] buildPathNames(String uriPath) {
		Matcher matcher = NAME_PATTERN.matcher(uriPath);
		List<String> names = new ArrayList<String>();
		while (matcher.find()) {
			String name = matcher.group();
			// strip '{' and '}'
			names.add(name.substring(1, name.length() - 1));
		}
		return names.toArray(new String[0]);
	}

	private String[] buildPathPatterns(String line, String[] pathNames) {
		if (pathNames == null) {
			return null;
		}
		String[] pathPatterns = new String[pathNames.length];
		for (int i = 0; i < pathNames.length; i++) {
			String name = pathNames[i];
			if (name.indexOf(':') > 0) {
				int j = name.indexOf(':');
				String pattern = name.substring(j + 1).trim();
				// Check compile
				Pattern.compile(pattern);
				pathPatterns[i] = pattern;
				log.error("Custom regex patterns not supported: '" + line + "'. Use plain {name} syntax only.");
				log.error(" '{" + name + "}' is gonna fail!");
			} else {
				pathPatterns[i] = PATH_PATTERN_STRING;
			}
		}
		return pathPatterns;
	}

	private Pattern buildPathPattern(String uriPath, String[] pathPatterns) {
		String[] segments = NAME_PATTERN.split(uriPath);
		StringBuilder sb = new StringBuilder();
		int ip = 0, is = 0;
		while ( (ip < pathPatterns.length) || (is < segments.length)) {
			if (is < segments.length) {
				sb.append(segments[is++]);
			}
			if (ip < pathPatterns.length) {
				sb.append(pathPatterns[ip++]);
			}
		}
		return Pattern.compile(sb.toString());
	}

	private String[] purifyPathNames(String[] pathNames) {
		if (pathNames == null) {
			return null;
		}
		String[] clean = new String[pathNames.length];
		for (int i = 0; i < pathNames.length; i++) {
			String pathName = pathNames[i];
			if (pathName.indexOf(':') > 0) {
				int j = pathName.indexOf(':');
				String name = pathName.substring(0, j).trim();
				clean[i] = name;
			} else {
				clean[i] = pathName;
			}
		}
		return clean;
	}

	public final Properties getUriMappingsProperties() {
		return uriMappingsProperties;
	}
	public final void setUriMappingsProperties(Properties uriMappingsProperties) {
		this.uriMappingsProperties = uriMappingsProperties;
	}
	public final Pattern[] getUriPatternsCompiled() {
		if (uriPatternsCompiled == null) {
			init();
		}
		return uriPatternsCompiled;
	}
	public final void setUriPatternsCompiled(Pattern[] uriPatternsCompiled) {
		this.uriPatternsCompiled = uriPatternsCompiled;
	}
	public final Map<String, String[]> getUriPatternPathNames() {
		if (uriPatternPathNames == null) {
			init();
		}
		return uriPatternPathNames;
	}
	public final void setUriPatternPathNames(Map<String, String[]> uriPatternPathNames) {
		this.uriPatternPathNames = uriPatternPathNames;
	}

	public final Properties getUriMappings() {
		if (uriMappings == null) {
			init();
		}
		return uriMappings;
	}

	public final void setUriMappings(Properties uriMappings) {
		this.uriMappings = uriMappings;
	}

	public final String getPropertiesFilename() {
		return propertiesFilename;
	}

	public final void setPropertiesFilename(String propertiesFilename) {
		this.propertiesFilename = propertiesFilename;
	}

}
