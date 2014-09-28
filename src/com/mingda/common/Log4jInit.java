package com.mingda.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public Log4jInit() {
		super();
	}

	public void init() throws ServletException {
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j");
		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
		}

		/*
		 * WebApplicationContext ctx = WebApplicationContextUtils
		 * .getWebApplicationContext(getServletContext()); DictionaryService
		 * dictionaryService = (DictionaryService) ctx
		 * .getBean("dictionaryService");
		 * createDictionaryXML(dictionaryService);
		 */

		System.out.println("*******************************************************************");
		System.out.println("**                                                               **");
		System.out.println("**                                                               **");
		System.out.println("**             医疗救助----服务器启动成功                                                                    **");
		System.out.println("**                                                               **");
		System.out.println("**                                                               **");
		System.out.println("*******************************************************************");
	}

	/*
	 * private void createDictionaryXML(DictionaryService dictionaryService) {
	 * String path = this.getClass().getClassLoader().getResource("") .getPath()
	 * + "com/winter/common/dictionary.xml"; XMLWriter xmlWriter; try {
	 * xmlWriter = new XMLWriter(new FileOutputStream(path));
	 * xmlWriter.write(dictionaryService.findDictionary()); xmlWriter.close(); }
	 * catch (UnsupportedEncodingException e) { e.printStackTrace(); } catch
	 * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException e)
	 * { e.printStackTrace(); } }
	 */
	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
	}
}
