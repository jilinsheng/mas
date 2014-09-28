package com.mingda.interceptor;

import java.util.Map;

import com.mingda.dto.UserDTO;
import com.mingda.service.AuthorityService;
import com.mingda.service.BaseBizService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ReportRightInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -7775299532450817539L;
	private AuthorityService authorityService;

	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation invocation) throws Exception {

		String name = invocation.getInvocationContext().getName();
		String namespace = invocation.getProxy().getNamespace();
		if ((namespace != null) && (namespace.trim().length() > 0)) {
			if ("/".equals(namespace.trim())) {
			} else {
				namespace += "/";
			}
		}
		String URL = namespace + invocation.getProxy().getActionName();
		URL += ".action";
	    
		ActionContext ctx = invocation.getInvocationContext();

		Map session = ctx.getSession();

		UserDTO userInfoDTO = (UserDTO) session.get("user");

		if (authorityService.checkReportRight(URL, userInfoDTO.getOrganizationId())) {
			return invocation.invoke();
		} else {
			return Action.INPUT;
		}

	}

	public AuthorityService getAuthorityService() {
		return authorityService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}
}
