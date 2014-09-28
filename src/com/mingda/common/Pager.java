package com.mingda.common;

public class Pager {

	// 当前页面
	private int currentpage;

	// 每页显示信息条数
	private int pagesize = 16;

	// 下一页
	private int nextpage;

	// 上一页
	private int previouspage;

	// 总记录数
	private int all;

	// 总页数
	private int pagecount;

	// 开始行数
	private int start;

	// 结束行数
	private int end;

	// 生成菜单编码
	private String toolsmenu = "";

	// 连接
	private String url;

	//

	public int getNextpage() {
		return nextpage;
	}

	public void setNextpage(int nextpage) {
		this.nextpage = nextpage;
	}

	public int getPreviouspage() {
		return previouspage;
	}

	public void setPreviouspage(int previouspage) {
		this.previouspage = previouspage;
	}

	public int getAll() {
		return all;
	}

	public void setAll(int all) {
		this.all = all;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public String getToolsmenu() {
		return this.genToolsmenu();
	}

	public void setToolsmenu(String toolsmenu) {
		this.toolsmenu = toolsmenu;
	}

	public Pager(int currentpage, int all, String url, int pagesize) {
		this.currentpage = currentpage;
		if (this.currentpage > 0 && all > 0) {
			nextpage = this.currentpage + 1;
			previouspage = this.currentpage - 1;
			if (all % pagesize > 0) {
				pagecount = (all / pagesize) + 1;
			} else {
				pagecount = all / pagesize;
			}
			if (1 == pagecount) {
				this.currentpage = 1;
			}
			start = (this.currentpage - 1) * pagesize;
			end = this.currentpage * pagesize;
			this.url = url;
			this.pagesize = pagesize;
			this.all=all;
		} else {
			nextpage = 0;
			all = 0;
			end = 0;
			start = 0;
			this.pagesize = pagesize;
		}
	}

	public String genToolsmenu() {
		toolsmenu = "";

		if (url == null || url.equals("")) {
			return null;
		}

		String temp = "";
		if (url.indexOf("?") == -1) {
			temp = "?";
		} else {
			temp = "&";
		}
		if (1 == currentpage) {
			toolsmenu += "首页 上一页&nbsp;";
		} else {
			toolsmenu += "<a href='" + url + temp + "cur_page=1'>首页</a>&nbsp;";
			toolsmenu += "<a href='" + url + temp + "cur_page=" + previouspage
					+ "'>上一页</a>&nbsp;";
		}
		if (pagecount == currentpage) {
			toolsmenu += "下一页 尾页&nbsp;";
		} else {
			toolsmenu += "<a href='" + url + temp + "cur_page=" + nextpage
					+ "'>下一页</a>&nbsp;";
			toolsmenu += "<a href='" + url + temp + "cur_page=" + pagecount
					+ "'>尾页</a>&nbsp;";
		}
		toolsmenu += "&nbsp;共<b>" + all + "</b>条记录&nbsp;共<b>" + pagecount
				+ "</b>页";
		toolsmenu += "&nbsp;转到<input id=\"page\" type=\"text\" value=\""
				+ currentpage
				+ "\" size=\"5\"/><input type=\"button\" value=\"转到\" onclick=\"document.location.reload('"
				+ url + temp
				+ "cur_page='+document.getElementById('page').value)\"/>";
		return toolsmenu;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
