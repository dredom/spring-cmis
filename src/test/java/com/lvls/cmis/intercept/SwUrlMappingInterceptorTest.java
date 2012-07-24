package com.lvls.cmis.intercept;

import static junit.framework.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class SwUrlMappingInterceptorTest {

	private TestParams testParams;
	private String expected;


	static class TestParams {
		public String contextPath;
		public String servletPath;
		public String reqUri;
		public TestParams(String contextPath, String servletPath, String reqUri) {
			super();
			this.contextPath = contextPath;
			this.servletPath = servletPath;
			this.reqUri = reqUri;
		}
	}
	@Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{ new TestParams("/spring-cmis", "/app", "/spring-cmis/app/sweet"), "/sweet" },
			{ new TestParams("/spring-cmis", "", "/spring-cmis/sweet"), "/sweet" },
			{ new TestParams("", "/app", "/app/sweet"), "/sweet" },
			{ new TestParams("", "", "/sweet"), "/sweet" },
		});
	}

	public SwUrlMappingInterceptorTest(TestParams testParams, String expected) {
		super();
		this.testParams = testParams;
		this.expected = expected;
	}

	@Test
	public void appRequestUri() {
		SwUrlMappingInterceptor service = new SwUrlMappingInterceptor();
		String appReqUri = service.getAppRequestUri(testParams.reqUri, testParams.contextPath, testParams.servletPath);
		assertEquals(expected, appReqUri);
	}

}
