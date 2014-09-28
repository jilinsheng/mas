package com.mingda.interceptor;

import java.util.Map;

import com.mingda.dto.UserDTO;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthorityInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -7775299532450817539L;

	@SuppressWarnings("rawtypes")
	public String intercept(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();

		Map session = ctx.getSession();

		UserDTO userInfoDTO = (UserDTO) session.get("user");

		if (null == userInfoDTO) {
			return Action.LOGIN;
		} else {
			return invocation.invoke();
		}
	}

}
