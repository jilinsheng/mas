package com.mingda.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class FileUpload {
	private static final int BUFFER_SIZE = 16 * 1024;
	private static FileUpload instance;
	private static String xmlpath = "com/mingda/common/file-cfg.xml";
	public String filepath;

	public static FileUpload getInstance(String type) {
		if (null == instance) {
			instance = new FileUpload(type);
		}
		return instance;
	}

	public FileUpload(String type) {
		Document doc = readDictXml();
		filepath = doc.selectSingleNode(type).getText();
	}

	public void MakeDir(String filename) {
		File file = new File(filename);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	private Document readDictXml() {
		String path = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ xmlpath;
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}
}
