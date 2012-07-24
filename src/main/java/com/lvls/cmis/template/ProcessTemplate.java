package com.lvls.cmis.template;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.lvls.cmis.pojo.MyBean;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ProcessTemplate {
	private String templateDirName;

	public String process(String templateName, Locale locale) throws IOException, TemplateException {
		File templateDir = new File(templateDirName);
		System.out.println("Using template directory " + templateDir.getAbsolutePath());
		Configuration config = new Configuration();
		config.setDirectoryForTemplateLoading(templateDir);
		config.setObjectWrapper(new DefaultObjectWrapper());

		Map<String, Object> root = new HashMap<String, Object>();
		MyBean bean = getBean(templateName);
		root.put("bean", bean);

		Template template = config.getTemplate(templateName, locale);

		Writer writer = new StringWriter();

		template.process(root, writer);

		return writer.toString();
	}

	private MyBean getBean(String name) {
		MyBean bean = new MyBean();
		bean.setType("news");
		bean.setDynamicContent("Beware the Jabberwocky, my son!");
		return bean;
	}

	public final void setTemplateDirName(String templateDirName) {
		this.templateDirName = templateDirName;
	}
}
