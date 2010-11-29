package com.lvls.cmis.service;

import static junit.framework.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/app-config.xml")
public class UrlMappingServiceTest {

	static String[][] testData = {
		{ "/hello/*", "" },
		{ "/hello/[^/]*", "name" },
		{ "/product/[^/]*/[^/]*", "fruit,name" },
	};
	static Pattern[] uriPatternsCompiled;
	static Map<String, String[]> uriPatternPathNames;

	@BeforeClass
	public static void setup() {
		uriPatternsCompiled = new Pattern[testData.length];
		uriPatternPathNames = new HashMap<String, String[]>();
		for (int i = 0; i < testData.length; i++) {
			String uriPattern = testData[i][0];
			uriPatternsCompiled[i] = Pattern.compile(uriPattern);
			String[] pathNames = testData[i][1].split(",");
			uriPatternPathNames.put(uriPattern, pathNames);
		}
	}

	@Autowired
	private UriMappingServiceImpl service;

	@Test
	public void mapUrlSimple() {
		service.setUriPatternsCompiled(uriPatternsCompiled);
		String appRequestUri = "/hello";
		String expected = "/hello/*";
		String result = service.getUriPattern(appRequestUri);
		assertNotNull(result);
		assertEquals(expected, result);
		String result2 = service.getUriPattern("/hello/");
		assertNotNull(result2);
		assertEquals(expected, result2);
	}

	@Test
	public void mapUrlPattern() {
		service.setUriPatternsCompiled(uriPatternsCompiled);
		String appRequestUri = "/hello/joleen";
		String expected = "/hello/[^/]*";
		String result = service.getUriPattern(appRequestUri);
		assertNotNull("request uri = " + appRequestUri, result);
		assertEquals(expected, result);
	}

	@Test
	public void mapUrlPatternNotMapped() {
		service.setUriPatternsCompiled(uriPatternsCompiled);
		String appRequestUri = "/hello/joleen/brady";
		String result = service.getUriPattern(appRequestUri);
		assertNull("request uri = " + appRequestUri, result);
	}

	@Test
	public void pathValues() {
		service.setUriPatternPathNames(uriPatternPathNames);
		String appRequestUri = "/hello/joleen";
		String uriPattern = "/hello/[^/]*";
		String expectedKey = "name";
		String expectedValue = "joleen";
		Map<String, String> result = service.getPathSegmentValues(appRequestUri, uriPattern);
		assertNotNull("request uri = " + appRequestUri, result);
		assertTrue(result.containsKey(expectedKey));
		assertEquals(expectedValue, result.get(expectedKey));
	}

	@Test
	public void pathValuesMulti() {
		service.setUriPatternsCompiled(uriPatternsCompiled);
		service.setUriPatternPathNames(uriPatternPathNames);
		String appRequestUri = "/product/apple/gala";
		String expectedKey = "fruit";
		String expectedValue = "apple";
		String expectedKey2 = "name";
		String expectedValue2 = "gala";
		long start = System.currentTimeMillis();
		String uriPattern = service.getUriPattern(appRequestUri);
		long time = System.currentTimeMillis() - start;
		System.out.println(" pathValuesMulti " + time + "ms");
		Map<String, String> result = service.getPathSegmentValues(appRequestUri, uriPattern);
		assertNotNull("request uri = " + appRequestUri, result);
		assertTrue(expectedKey, result.containsKey(expectedKey));
		assertEquals(expectedValue, result.get(expectedKey));
		assertTrue(expectedKey2, result.containsKey(expectedKey2));
		assertEquals(expectedValue2, result.get(expectedKey2));
	}

}
