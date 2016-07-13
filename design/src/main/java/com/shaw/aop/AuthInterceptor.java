package com.shaw.aop;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.shaw.annotation.RolePassport;
import com.shaw.bo.User;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			RolePassport rolePassport = ((HandlerMethod) handler).getMethodAnnotation(RolePassport.class);
			User user = null;
			if (rolePassport == null || rolePassport.validate() == false)
				return true;
			else if ((user = (User) request.getSession().getAttribute("user")) != null) {
				if (rolePassport.roleLv() == 1) {
					return true;
				}
				if (rolePassport.roleLv() == 2) {
					if (user.getRole() == 1) {
						response.sendRedirect("unrole.html?redirectUrl=" + request.getRequestURI());
						return false;
					}
				}
				if (rolePassport.roleLv() == 3) {
					if (user.getRole() != 3) {
						response.sendRedirect("unrole.html?redirectUrl=" + request.getRequestURI());
						return false;
					}
				}
				return true;
			} else {
				Cookie cookie = new Cookie("user", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				response.sendRedirect("login.html?redirectUrl=" + request.getRequestURI());
			}
			return false;
		}
		return true;
	}
}