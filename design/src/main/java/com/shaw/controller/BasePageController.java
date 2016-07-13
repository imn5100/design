package com.shaw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.shaw.annotation.RolePassport;
import com.shaw.bo.News;
import com.shaw.bo.SnoTokens;
import com.shaw.bo.Subject;
import com.shaw.bo.User;
import com.shaw.bo.UserInfo;
import com.shaw.service.DoExercisesService;
import com.shaw.service.NewsService;
import com.shaw.service.SnoTokensService;
import com.shaw.service.SubjectService;
import com.shaw.service.UserInfoService;
import com.shaw.service.UserService;
import com.shaw.utils.Constants;
import com.shaw.utils.Page;
import com.shaw.vo.StudentCount;
import com.shaw.vo.UserVo;

@Controller
public class BasePageController {

	@Autowired
	private UserService userService;
	@Autowired
	private SnoTokensService snoTokensService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private DoExercisesService doExercisesService;

	@RequestMapping("/{pageName}.html")
	public ModelAndView getParkingCardType(@PathVariable String pageName, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(pageName);
		if (pageName.equals("about")) {
			Map<String, Object> params = new HashMap<String, Object>();
			Page page = new Page();
			page.setEveryPage(3);
			params.put("page", page);
			params.put("type", 1);
			List<News> news = newsService.selectByQuery(params);
			mav.addObject("news", news);
			params.put("type", 2);
			List<News> notis = newsService.selectByQuery(params);
			mav.addObject("notis", notis);
		}
		if (pageName.equals("news")) {
			Map<String, Object> params = new HashMap<String, Object>();
			Page page = new Page();
			params.put("page", page);
			page.setEveryPage(10);
			params.put("type", 2);
			// 获取通知
			List<News> notis = newsService.selectByQuery(params);
			mav.addObject("notis", notis);
			// 获取新闻
			params.put("haveImg", "true");
			params.put("haveUrl", "true");
			params.put("type", 1);
			List<News> urlNews = newsService.selectByQuery(params);
			mav.addObject("urlNews", urlNews);
		}
		if (pageName.equals("pages")) {
			mav.setViewName("redirect:/myPage");
			return mav;
		}
		if (!Constants.PAGESNAME.contains(pageName)) {
			mav.setViewName("unrole");
		}
		return mav;
	}

	@RequestMapping("/userLogin")
	public ModelAndView login(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "role", required = true) int role, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", username);
		mav.setViewName("login");
		User user = this.userService.login(username, password, role);
		if (user != null) {
			user.setPassword(null);
			request.getSession().setAttribute("user", user);
			Cookie cookie = new Cookie("user", JSONObject.toJSONString(user));
			cookie.setMaxAge(Constants.COOKIE_TIME);
			response.addCookie(cookie);
			mav.setViewName("redirect:/myPage");
			return mav;
		}
		mav.addObject("msg", "您的用户名或者密码不正确 - 请重试");
		return mav;
	}

	@RequestMapping("/userRegister")
	public ModelAndView register(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "sno", required = true) String sno, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", username);
		mav.setViewName("register");
		if (this.userService.selectByUsername(username) != null) {
			mav.addObject("msg", "用户名已被注册 - 请更换重试");
			return mav;
		}
		SnoTokens tokens = snoTokensService.selectByCode(sno);
		if (tokens == null || tokens.getStatus() != 0) {
			mav.addObject("msg", "认证学号无效或已失效，请获取正确认证学号后注册");
			return mav;
		}
		try {
			User user = this.userService.registerStudent(username, password, tokens);
			user.setPassword(null);
			request.getSession().setAttribute("user", user);
			Cookie cookie = new Cookie("user", JSONObject.toJSONString(user));
			cookie.setMaxAge(Constants.COOKIE_TIME);
			response.addCookie(cookie);
			mav.setViewName("myPage");
			return mav;
		} catch (Exception e) {
			mav.addObject("msg", "注册出现异常，请稍候重试或联系管理员处理,");
			return mav;
		}
	}

	@RequestMapping("/userChangePsw")
	public ModelAndView changePsw(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "password2", required = true) String password2,
			@RequestParam(value = "sno", required = true) String code, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", username);
		mav.setViewName("changePsw");
		User user = this.userService.login(username, password, 1);
		if (user == null) {
			mav.addObject("msg", "您的用户名或者旧密码不正确");
			return mav;
		}
		SnoTokens tokens = snoTokensService.selectByCode(code);
		if (tokens == null || tokens.getSid() != user.getId()) {
			mav.addObject("msg", "认证学号验证失败,请使用您注册时的有效学号验证");
			return mav;
		}
		try {
			this.userService.changePsw(user, password2);
			mav.addObject("msg", "修改密码成功，请使用新密码进行登录");
			mav.setViewName("login");
			return mav;
		} catch (Exception e) {
			mav.addObject("msg", "修改密码出现异常，请稍候重试或联系管理员处理,");
			return mav;
		}
	}

	@RequestMapping("/logout")
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("user", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		return mav;
	}

	@RequestMapping("/myPage")
	@RolePassport
	public ModelAndView myPages(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		if (user == null) {
			return logout(request, response);
		} else {
			if (user.getRole() == 2 || user.getRole() == 3) {
				UserInfo info = userInfoService.selectByPrimaryKey(user.getInfoid());
				UserVo userVo = new UserVo();
				if (info != null) {
					BeanUtils.copyProperties(info, userVo);
				}
				BeanUtils.copyProperties(user, userVo);
				mav.addObject("userVo", userVo);
				Map<String, Object> params = new HashMap<String, Object>();
				Page page = new Page();
				params.put("page", page);
				page.setEveryPage(20);
				params.put("type", 2);
				params.put("uid", user.getId());
				List<News> notis = newsService.selectByQuery(params);
				mav.addObject("notis", notis);
				List<Subject> subs = subjectService.selectAllSubject();
				mav.addObject("subs", subs);
				List<StudentCount> sc = doExercisesService.selectMyStudentCount(user.getId());
				mav.addObject("sc", sc);
				if (user.getRole() == 3)
					mav.setViewName("aPage");
				else
					mav.setViewName("tPage");
			} else if (user.getRole() == 1) {
				UserInfo info = userInfoService.selectByPrimaryKey(user.getInfoid());
				UserVo userVo = new UserVo();
				if (info != null) {
					org.springframework.beans.BeanUtils.copyProperties(info, userVo);
				}
				org.springframework.beans.BeanUtils.copyProperties(user, userVo);
				mav.addObject("userVo", userVo);
				List<Subject> subs = subjectService.selectAllSubject();
				mav.addObject("subs", subs);
				List<Long> times = doExercisesService.selectTimes(user.getId());
				if (times != null && times.size() > 0) {
					mav.addObject("times", times);
				}
				mav.setViewName("sPage");
			}
		}
		return mav;
	}
	@RequestMapping("/adminPage")
	@RolePassport(roleLv=3)
	public ModelAndView adminPage(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		if (user == null) {
			return logout(request, response);
		}else{
			mav.setViewName("admin");
		}
		return mav;
	}
	
	@RequestMapping("/adminPageForExe")
	@RolePassport(roleLv=2)
	public ModelAndView adminPageForExe(HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		if (user == null) {
			return logout(request, response);
		}else{
			mav.setViewName("admin2");
		}
		return mav;
	}
}
