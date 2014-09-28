package com.mingda.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mingda.common.ExportExcel;
import com.mingda.service.SearchService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class DownloadExcelAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private SearchService searchService;
	private String sql;
	private HashMap<String, String> title = new HashMap<String, String>();
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@SuppressWarnings("rawtypes")
	public String execute() throws Exception {
		Map session = ActionContext.getContext().getSession();
		title =(HashMap<String, String>) session.get("title");
		sql = (String) session.get("sql");
		String f = new String("Éú³Éexcel".getBytes("gb2312"), "ISO8859-1");
		fileName = "attachment; filename=" + f + ".xls";
		return super.execute();
	}

	@SuppressWarnings("rawtypes")
	public InputStream getExcelFile() {
		ByteArrayInputStream bais = null;
		List<HashMap> rs = searchService.findAll(sql);
		ExportExcel ee = new ExportExcel();
		ByteArrayOutputStream baos = ee.genExcelData(title, rs);
		if (null != baos) {
			byte[] ba = baos.toByteArray();
			bais = new ByteArrayInputStream(ba);
		}
		return bais;
	}

	public SearchService getSearchService() {
		return searchService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public HashMap<String, String> getTitle() {
		return title;
	}

	public void setTitle(HashMap<String, String> title) {
		this.title = title;
	}
}
