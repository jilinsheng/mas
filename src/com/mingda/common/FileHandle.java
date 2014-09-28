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

public class FileHandle {
	private static final int BUFFER_SIZE = 16 * 1024;
	private static String filepath = "com/mingda/common/file-cfg.xml";
	private static FileHandle instance;
	public String picpath1;
	public String picpath2;
	public String picpath3;
	public String uploadpath1;
	public String uploadpath2;
	public String uploadpath3;
	public String webpath1;
	public String webpath2;
	public String webpath3;
	public String remoteserver;

	public static FileHandle getInstance() {
		if (null == instance) {
			instance = new FileHandle();
		}
		return instance;
	}

	public FileHandle() {
		Document doc = readCfgXml();
		picpath1 = doc.selectSingleNode("/file/picpath1").getText();
		picpath2 = doc.selectSingleNode("/file/picpath2").getText();
		picpath3 = doc.selectSingleNode("/file/picpath3").getText();
		uploadpath1 = doc.selectSingleNode("/file/fileuploadpath1").getText();
		uploadpath2 = doc.selectSingleNode("/file/fileuploadpath2").getText();
		uploadpath3 = doc.selectSingleNode("/file/fileuploadpath3").getText();
		webpath1 = doc.selectSingleNode("/file/webpath1").getText();
		webpath2 = doc.selectSingleNode("/file/webpath2").getText();
		webpath3 = doc.selectSingleNode("/file/webpath3").getText();
		remoteserver = doc.selectSingleNode("/file/remoteserver").getText();
	}

	private Document readCfgXml() {
		String path = this.getClass().getClassLoader().getResource("")
				.getPath()
				+ filepath;
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
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

	public static void main(String args[]) throws Exception {
	}

}
