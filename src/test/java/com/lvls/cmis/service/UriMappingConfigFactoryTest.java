package com.lvls.cmis.service;

import static junit.framework.Assert.*;

import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class UriMappingConfigFactoryTest {

	static String[][] testData = {
		{ "/hello", "hello" },
		{ "/hello/{name: [a-z]+}", "hello" },
		{ "/product/{fruit}/{name}", "fruit/name" },
	};
	static String[][] expectedPatternNames = {
		{ "/hello", "" },
		{ "/hello/[a-z]+", "name" },
		{ "/product/.*/.*", "fruit,name" },
	};

	private UriMappingConfigFactoryImpl service;

	@Before
	public void setup() {
		service = new UriMappingConfigFactoryImpl();
		Properties properties = new Properties();
		for (String[] data : testData) {
			properties.put(data[0], data[1]);
		}
		service.setUriMappingsProperties(properties);
	}

	@Test
	public void uriPatterns() {

		Pattern[] patterns = service.getUriPatternsCompiled();
		assertNotNull(patterns);
		assertEquals("count", testData.length, patterns.length);
	}

	@Test
	public void uriPatternPathNames() {

		Map<String, String[]> map = service.getUriPatternPathNames();
		assertNotNull(map);
		assertEquals("count", testData.length, map.size());
		for (String[] patternName : expectedPatternNames) {
			String[] names = map.get(patternName[0]);
			assertNotNull("Missing uri pattern '" + patternName[0] + "'", names);
			String[] expectedNames = patternName[1].split(",");
			if (patternName[1].length() == 0) {
				expectedNames = new String[0];
			}
			for (int j = 0; j < expectedNames.length; j++) {
				assertEquals("Names for uri pattern '" + patternName[0] + "'", expectedNames[j], names[j]);
			}
		}
	}

	@Test
	public void uriMappings() {
		service.setPropertiesFilename("test-case-4");
		Properties mappings = service.getUriMappings();
		assertNotNull(mappings);
		mappings.list(System.out);
	}
}
