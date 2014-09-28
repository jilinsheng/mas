package com.mingda.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.mingda.dto.TempDTO;
import com.mingda.model.TempCalcRuleSpe;
import com.mingda.service.TempServiceImpl;

public class FtpUpload {
	static Logger log = Logger.getLogger(FtpUpload.class);
	private static String filepath = "com/mingda/common/ftp-cfg.xml";

	private String hostName;

	private String userName;

	private String password;

	private String remoteDir;

	private String port;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public FtpUpload() {
		if (remoteDir == null || remoteDir.equalsIgnoreCase("")) {
			remoteDir = null;
		}
		hostName = this.getProperty("remoteip");
		userName = this.getProperty("username");
		password = this.getProperty("userpwd");
		remoteDir = this.getProperty("remotedir");
		port = this.getProperty("remoteport");
	}

	public boolean UploadFile(InputStream localfileins, String remotefilename) {

		FTPClient ftp = new FTPClient();
		int reply;
		try {
			ftp.connect(hostName, Integer.parseInt(port));
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return false;
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					f.printStackTrace();
					return false;
				}
			}
			e.printStackTrace();
		}

		try {
			if (!ftp.login(userName, password)) {
				ftp.logout();
				return false;
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			ftp.enterLocalPassiveMode();

			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

			InputStream input = localfileins;

			String[] dirs = remotefilename.split("/");

			ftp.changeWorkingDirectory("/");

			for (int i = 1; i < dirs.length - 1; i++) {
				if (!ftp.changeWorkingDirectory(dirs[i])) {
					ftp.makeDirectory(dirs[i]);
				}
				ftp.changeWorkingDirectory(dirs[i]);
			}

			ftp.storeFile(dirs[dirs.length - 1], input);

			input.close();

			ftp.logout();

		} catch (FTPConnectionClosedException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unused")
	public boolean UploadFile(String localfilename, String remotefilename) {
		remotefilename = "/" + remotefilename;
		FTPClient ftp = new FTPClient();
		int reply;
		try {
			ftp.connect(hostName, Integer.parseInt(port));
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return false;
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					f.printStackTrace();
					return false;
				}
			}
			e.printStackTrace();
		}

		try {
			if (!ftp.login(userName, password)) {
				ftp.logout();
				return false;
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			InputStream input = new FileInputStream(localfilename);
			if (input == null) {
				log.debug("本地文件不存在");
			}
			String[] dirs = remotefilename.split("/");

			for (String a : dirs) {
				log.debug(a);
			}
			log.debug(dirs.length);

			ftp.changeWorkingDirectory("/");

			for (int i = 1; i < dirs.length - 1; i++) {
				if (!ftp.changeWorkingDirectory(dirs[i])) {
					ftp.makeDirectory(dirs[i]);
				}
				ftp.changeWorkingDirectory(dirs[i]);
			}

			ftp.storeFile(remotefilename, input);
			input.close();
			ftp.logout();
		} catch (FTPConnectionClosedException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
		}
		return true;
	}

	private Document readDictXml() {
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

	private String getProperty(String propertyname) {
		Document doc = this.readDictXml();
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("/ftp/property[@name='"
				+ propertyname + "']");
		log.debug(node.getText());
		return node.getText();
	}

	public static void main(String args[]) throws Exception {
		/*
		 * log.debug("sssssssssss"); FtpUpload ftpup = new FtpUpload();
		 * ftpup.UploadFile("c:/mywebapp-debug.log",
		 * "/html/aa/a/a/debuglog.log"); log.debug("sssssssssss");
		 */

		String text = "<GetCIAssistByPaperID><ReturnFlag>1</ReturnFlag><ResultFlag>1</ResultFlag><PaySumAssistIn>4505.06</PaySumAssistIn>  <PaySumAssistOut>0</PaySumAssistOut>  <SumMedicareScope>13769.96</SumMedicareScope>  <PayCIAssist>2884.980</PayCIAssist></GetCIAssistByPaperID>";
		Document document = DocumentHelper.parseText(text);

		String a = document.selectSingleNode(
				"//GetCIAssistByPaperID/ReturnFlag").getText();

		System.out.println(a);
		TempServiceImpl t = new TempServiceImpl();
		TempCalcRuleSpe tempCalcRuleSpe=new TempCalcRuleSpe();
		tempCalcRuleSpe.setM1(new BigDecimal("2000"));
		tempCalcRuleSpe.setM2(new BigDecimal("5000"));
		tempCalcRuleSpe.setM3(new BigDecimal("8000"));
		tempCalcRuleSpe.setM4(new BigDecimal("10000"));
		tempCalcRuleSpe.setScale1(new BigDecimal("0.49"));
		tempCalcRuleSpe.setScale2(new BigDecimal("0.27"));
		tempCalcRuleSpe.setScale3(new BigDecimal("0.2"));
		tempCalcRuleSpe.setScale4(new BigDecimal("0.1"));
		tempCalcRuleSpe.setScale5(new BigDecimal("0.05"));
		TempDTO tempDTO = new TempDTO();
		//t.findtempspemoney(tempCalcRuleSpe ,tempDTO);
	}

}
