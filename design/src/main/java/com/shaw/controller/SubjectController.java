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
import com.shaw.bo.Subject;
import com.shaw.service.SubjectService;
import com.shaw.utils.ErrorCode;
import com.shaw.utils.Page;
import com.shaw.utils.RequestMap;
import com.shaw.utils.ResponseMap;
import com.shaw.vo.TableData;

@Controller
public class SubjectController {
	@Autowired
	private SubjectService subjectService;

	@RequestMapping("/addSubject")
	@ResponseBody
	@RolePassport(roleLv = 2)
	public ResponseMap<Subject> addSubject(@RequestBody RequestMap map, HttpServletRequest request) {
		ResponseMap<Subject> ret = new ResponseMap<Subject>();
		String name = map.getStringValue("name");
		Float score = map.getFloatValue("score", 0.0f);
		String detail = map.getStringValue("detail");
		if (name == null || score == null) {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData(null);
			return ret;
		}
		Subject subject = new Subject();
		subject.setName(name);
		subject.setScore(score);
		subject.setDetail(detail);
		subjectService.insert(subject);
		ret.setData(subject);
		return ret;
	}

	@RequestMapping("/updateSubject")
	@ResponseBody
	@RolePassport(roleLv = 2)
	public ResponseMap<String> updateSubject(@RequestParam("id") Integer id, @RequestParam("name") String name,
			@RequestParam("score") Float score, @RequestParam("detail") String detail, HttpServletRequest request) {
		ResponseMap<String> ret = new ResponseMap<String>();
		if (name == null || score == null) {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setMsg("名字或学分不能为空");
			return ret;
		}
		Subject subject = new Subject();
		subject.setId(id);
		subject.setName(name);
		subject.setScore(score);
		subject.setDetail(detail);
		subjectService.updateByPrimaryKeySelective(subject);
		return ret;
	}

	@ResponseBody
	@RequestMapping("/showSubjectTable")
	@RolePassport(roleLv = 2)
	public TableData<Subject> showSubjectTable(@RequestParam(value = "page", required = false) Integer currentPage,
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
		TableData<Subject> td = new TableData<Subject>();
		td.setTotal(subjectService.countByQuery(params));
		td.setRows(subjectService.selectByQuery(params));
		return td;
	}
}
