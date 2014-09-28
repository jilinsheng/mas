package com.mingda.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class StaticFreemarker {

	/**
	 * 初始化模板引擎
	 * 
	 * @param ftl
	 *            模板名称
	 * @param htmlName
	 *            需要生成html页面的名称
	 * @param map
	 *            模板中需要的参数集合
	 * @param relativePath
	 *            模板相对于根路径的相对路径
	 * @throws IOException
	 * @throws TemplateException
	 */
	@SuppressWarnings("rawtypes")
	public void init(String ftl, String htmlName, Map map, String relativePath)
			throws IOException, TemplateException {

		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setServletContextForTemplateLoading(ServletActionContext
				.getServletContext(), "/" + relativePath);
		freemarkerCfg.setEncoding(Locale.getDefault(), "utf-8");

		Template template = freemarkerCfg.getTemplate(ftl);
		template.setEncoding("utf-8");

		String path = ServletActionContext.getServletContext().getRealPath("/");

		File htmlFile = new File(path + htmlName);

		Writer out = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(htmlFile), "utf-8"));
		template.process(map, out);
		out.flush();
		out.close();
	}

}
