package com.lvls.cmis.template;

import java.util.Locale;

import org.junit.Test;

public class ProcessTemplateTest {

	@Test
	public void process() throws Exception {
		final String templateName = "news.ftl";
		ProcessTemplate pt = new ProcessTemplate();
		pt.setTemplateDirName("src/main/resources/templates");
		String result = pt.process(templateName, Locale.ENGLISH);
		System.out.println(result);
		System.out.println();
		String result2 = pt.process(templateName, Locale.GERMAN);
		System.out.println(result2);
	}
}
