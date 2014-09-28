package com.mingda.common;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class ReadXML {
	private String xmlPath;

	public ReadXML(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public Document readXml() {
		String path = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ xmlPath;
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return document;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public static void main(String[] args) {
	}

}
