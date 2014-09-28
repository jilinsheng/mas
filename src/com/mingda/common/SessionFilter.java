package com.mingda.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mingda.dto.UserDTO;

/**
 * Servlet Filter implementation class SessionFilter
 */
public class SessionFilter implements Filter {

	public SessionFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest requests = (HttpServletRequest) request;
			HttpServletResponse responses = (HttpServletResponse) response;
			HttpSession session = requests.getSession();
			UserDTO user = (UserDTO) session.getAttribute("user");
			if (null == user) {
				session.invalidate();
				responses.sendRedirect(requests.getContextPath()
						+ "/logout.jsp");
			} else {
				chain.doFilter(requests, responses);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
