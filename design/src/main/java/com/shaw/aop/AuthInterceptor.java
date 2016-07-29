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
			// 判断是否是ajax请求
			User user = null;
			Boolean isAjax = (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"));
			if (rolePassport == null || rolePassport.validate() == false) {
				return true;
			} else if ((user = (User) request.getSession().getAttribute("user")) != null) {
				if (rolePassport.roleLv() == 1) {
					return true;
				}
				if (rolePassport.roleLv() == 2) {
					if (user.getRole() == 1) {
						redirect(request, response, isAjax, false);
						return false;
					}
				}
				if (rolePassport.roleLv() == 3) {
					if (user.getRole() != 3) {
						redirect(request, response, isAjax, false);
						return false;
					}
				}
				return true;
			} else {
				Cookie cookie = new Cookie("user", null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				redirect(request, response, isAjax, true);
				return false;
			}
		}
		return true;
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, Boolean isAjax, Boolean toLogin)
			throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		if (isAjax) {
			if (toLogin) {
				response.setHeader("sessionstatus", "sessionOut");
				response.getWriter().write("登录已失效，将调用JS自动跳转至登录页...");
				response.getWriter().write("<script>$(function (){window.location.replace('login.html');});</script>");
			} else {
				response.setHeader("sessionstatus", "sessionOut");
				response.getWriter().write("权限不足");
				response.getWriter().write("<script>$(function (){window.location.replace('unrole.html');});</script>");
			}
		} else {
			if (toLogin) {
				response.sendRedirect("login.html?redirectUrl=" + request.getRequestURI());
			} else {
				response.sendRedirect("unrole.html?redirectUrl=" + request.getRequestURI());
			}
		}

	}
}