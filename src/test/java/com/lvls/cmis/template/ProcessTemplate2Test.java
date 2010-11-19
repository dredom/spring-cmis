package com.lvls.cmis.template;

import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/app-config.xml")
public class ProcessTemplate2Test {

	@Autowired
	private ProcessTemplate pt;

	@Test
	public void process() throws Exception {
		final String templateName = "news.ftl";
		String result = pt.process(templateName, Locale.ENGLISH);
		System.out.println(result);
		System.out.println();
		String result2 = pt.process(templateName, Locale.GERMAN);
		System.out.println(result2);
	}
}
