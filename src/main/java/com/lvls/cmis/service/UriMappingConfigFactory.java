// Copyright (c) 2010 - Level Studios
// All rights reserved.
package com.lvls.cmis.service;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Parses a {@code uri-mappings.properties} file.
 * <pre>
 *  /hello=hello
 *  /hello/{name}=hello
 * </pre>
 * The value is a key to the template processing to be used.
 *
 * @author auntiedt
 * @version 1.0, 2010-11
 */
public interface UriMappingConfigFactory {

	/**
	 * List of 'compiled' URI patterns parsed from {@code uri-mappings.properties} file
	 * mapping to templating keys.
	 * @return {@link Properties} key: URI pattern, value: templating key
	 */
	Properties getUriMappings();

	/**
	 * These are the regex patterns used to match an incoming request.
	 * @return {@link Pattern}[] of URI patterns
	 */
	Pattern[] getUriPatternsCompiled();

	/**
	 * In <code> /hello/{name}=hello </code> the path segment name is <code>{name}</code>.
	 * These parameterized path segments are replaced with regex patterns, generally
	 * "<code>.*</code>", and used for matching and returning the path values.
	 * @return {@link Map} key: URI pattern, value: array of path segment names
	 */
	Map<String, String[]> getUriPatternPathNames();
}
