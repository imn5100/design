package com.shaw.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shaw.annotation.RolePassport;
import com.shaw.bo.SnoTokens;
import com.shaw.bo.User;
import com.shaw.bo.UserInfo;
import com.shaw.service.SnoTokensService;
import com.shaw.service.UserInfoService;
import com.shaw.service.UserService;
import com.shaw.utils.ErrorCode;
import com.shaw.utils.Page;
import com.shaw.utils.RequestMap;
import com.shaw.utils.ResponseMap;
import com.shaw.vo.TableData;
import com.shaw.vo.UserVo;

@Controller
public class UserController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserService userService;
	@Autowired
	private SnoTokensService snoTokenService;

	@ResponseBody
	@RequestMapping("/saveInfo")
	@RolePassport
	public ResponseMap<String> saveInfo(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user != null) {
			ret.setCode(ErrorCode.SUCCESS);
			String nick = map.getStringValue("nick");
			String detail = map.getStringValue("detail");
			UserInfo userInfo = userInfoService.selectByPrimaryKey(user.getInfoid());
			userInfo.setDetail(detail);
			user.setNick(nick);
			try {
				if (user.getRole() == 1) {
					String clazz = map.getStringValue("clazz");
					userInfo.setClazz(clazz);
					userService.changeInfo(user, userInfo);
				} else if (user.getRole() == 2 || user.getRole() == 3) {
					String subjects = map.getStringValue("subjects");
					userInfo.setSubjects(subjects);
					userService.changeInfo(user, userInfo);
				}
				ret.setData("修改成功");
			} catch (Exception e) {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("服务器异常，请稍候重试");
			}
		} else {
			ret.setCode(ErrorCode.UNLOGIN);
			ret.setData("未登录，请登录后重试");
		}
		return ret;
	}

	@ResponseBody
	@RequestMapping("/showTokenTable")
	@RolePassport(roleLv = 3)
	public TableData<SnoTokens> showTokenTable(@RequestParam(value = "page", required = false) Integer currentPage,
			@RequestParam(value = "rows", required = false) Integer everyPage, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		if (everyPage == null || everyPage <= 0) {
			everyPage = 10;
		}
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setEveryPage(everyPage);
		params.put("page", page);
		TableData<SnoTokens> td = new TableData<SnoTokens>();
		td.setTotal(snoTokenService.countByQuery(params));
		td.setRows(snoTokenService.selectByQuery(params));
		return td;
	}

	@ResponseBody
	@RequestMapping("/showTeacherTable")
	@RolePassport(roleLv = 3)
	public TableData<UserVo> showTeacherTable(@RequestParam(value = "page", required = false) Integer currentPage,
			@RequestParam(value = "rows", required = false) Integer everyPage, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		if (everyPage == null || everyPage <= 0) {
			everyPage = 10;
		}
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setEveryPage(everyPage);
		params.put("isTRole", true);
		params.put("page", page);
		TableData<UserVo> td = new TableData<UserVo>();
		td.setTotal(userService.countByQuery(params));
		td.setRows(userService.selectByQuery(params));
		return td;
	}

	@ResponseBody
	@RequestMapping("/addToken")
	@RolePassport(roleLv = 3)
	public ResponseMap<String> addToken(@RequestParam(value = "code") String code, HttpServletRequest request,
			HttpServletResponse response) {
		ResponseMap<String> ret = new ResponseMap<String>();
		if (this.snoTokenService.selectByCode(code) != null) {
			ret.setBizErrorCode();
			ret.setMsg("认证学号已存在");
			return ret;
		}
		SnoTokens tokens = new SnoTokens();
		tokens.setCode(code);
		this.snoTokenService.insertSelective(tokens);
		return ret;
	}

	@ResponseBody
	@RequestMapping("/addTeacher")
	@RolePassport(roleLv = 3)
	public ResponseMap<String> addUser(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, @RequestParam(value = "nick") String nick,
			HttpServletRequest request, HttpServletResponse response) {
		ResponseMap<String> ret = new ResponseMap<String>();
		if (this.userService.selectByUsername(username) != null) {
			ret.setBizErrorCode();
			ret.setMsg("用户名已存在");
			return ret;
		}
		try {
			userService.registerTeacher(username, password, nick);
			ret.setMsg("添加成功");
		} catch (Exception e) {
			ret.setBizErrorCode();
			ret.setMsg("注册出现异常，请稍候重试或联系管理员处理,");
		}
		return ret;
	}

	@ResponseBody
	@RequestMapping("/giveRole")
	@RolePassport(roleLv = 3)
	public ResponseMap<String> giveRole(@RequestParam(value = "id") Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user == null || user.getRole() != 3) {
			ret.setCode(ErrorCode.UNLOGIN);
			ret.setData("未登录或权限不足，请登录后重试");
			return ret;
		}
		if (id == null) {
			ret.setBizErrorCode();
			ret.setMsg("参数错误");
			return ret;
		}
		try {
			User target = userService.selectByPrimaryKey(id);
			if (target.getRole() == 2) {
				target.setRole(3);
				userService.updateByPrimaryKeySelective(target);
			} else {
				ret.setBizErrorCode();
				ret.setMsg("所选角色不是教师，或已是管理员，不能执行该操作");
				return ret;
			}
		} catch (Exception e) {
			ret.setBizErrorCode();
			ret.setMsg("注册出现异常，请稍候重试或联系管理员处理,");
		}
		return ret;
	}

}