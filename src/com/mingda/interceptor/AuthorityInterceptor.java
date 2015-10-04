package com.mingda.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.mingda.dto.UserDTO;
import com.mingda.service.SystemDataService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -7775299532450817539L;

	private SystemDataService systemDataService;

	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();

		HttpServletRequest request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String url = request.getServletPath();

		System.out.println(url);

		Map session = ctx.getSession();

		UserDTO userInfoDTO = (UserDTO) session.get("user");

		if (null == userInfoDTO) {
			return Action.LOGIN;
		} else {

			boolean zx = systemDataService.findEmprole(userInfoDTO.getEmpid(),
					url);
			if (zx) {
				return invocation.invoke();
			} else {
				return "norole";
			}
		}
	}

	public SystemDataService getSystemDataService() {
		return systemDataService;
	}

	public void setSystemDataService(SystemDataService systemDataService) {
		this.systemDataService = systemDataService;
	}

}
