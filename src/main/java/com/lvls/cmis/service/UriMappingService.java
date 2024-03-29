package com.lvls.cmis.service;

import java.util.Map;

/**
 * Maps request URIs to the URI patterns which can then be used to map to templates.
 *
 * <p>Cannot use static RESTful URL mapping because the Switch core does not know
 * what the URLs are. Thus dynamic mapping from configuration is required.
 *
 * @author  auntiedt
 * @version 1.0, 2010-11
 */
public interface UriMappingService {

	/**
	 * Return a URI pattern, REST style, which can be used for mapping to templates and things.
	 * @param appRequestUri without the app prefix, eg "/hello/tom"
	 * @return String URI pattern, eg "/hello/[^/]*"
	 */
	String getUriPattern(String appRequestUri);

	/**
	 * Maps a URI pattern to some target key for templating.
	 * @param uriPattern
	 * @return String key for templating
	 */
	String getTargetForUriPattern(String uriPattern);

	/**
	 *
	 * @param appRequestUri eg "/hello/tom"
	 * @param uriPattern eg "/hello/[^/]*" (any characters not "/")
	 * @return {@link Map} eg "name, tom" for a path definition of "/hello/{name}"
	 */
	Map<String, String> getPathSegmentValues(String appRequestUri, String uriPattern);

}
