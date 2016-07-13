package com.shaw.controller;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.shaw.annotation.RolePassport;
import com.shaw.bo.News;
import com.shaw.bo.User;
import com.shaw.service.NewsService;
import com.shaw.utils.ErrorCode;
import com.shaw.utils.RequestMap;
import com.shaw.utils.ResponseMap;
import com.shaw.vo.SinaNewsResponse;
import com.shaw.vo.SineNews;

@Controller
public class MsgController {
	@Autowired
	private NewsService newsService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RestTemplate restTemplate;

	@ResponseBody
	@RequestMapping("/releaseByJson")
	@RolePassport(roleLv = 2)
	public ResponseMap<String> releaseByJson(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user != null) {
			if (user.getRole() == 1) {
				ret.setData("无发布通知权限");
				ret.setCode(ErrorCode.BIZERROR);
				return ret;
			}
			String content = map.getStringValue("content");
			String title = map.getStringValue("title");
			if (StringUtils.isBlank(content)) {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("内容不能为空");
				return ret;
			}
			if (StringUtils.isBlank(title)) {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("标题不能为空");
				return ret;
			}
			News news = new News();
			news.setDetail(content);
			news.setTitle(title);
			news.setUid(user.getId());
			news.setType(2);
			news.setUname(user.getUsername());
			news.setCreatetime(System.currentTimeMillis());
			newsService.insert(news);
			ret.setData("发布成功");
			return ret;
		} else {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("未登录，或登录超时,请登录后重试");
			return ret;
		}
	}

	@ResponseBody
	@RequestMapping("/deleteNoti")
	@RolePassport(roleLv = 2)
	public ResponseMap<String> delete(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user != null) {
			Integer id = map.getIntValue("id");
			News news = newsService.selectByPrimaryKey(id);
			if (news == null) {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("通知不存在");
				return ret;
			}
			if (user.getRole() != 3 && news.getUid() != user.getId()) {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("无法删除他人通知");
				return ret;
			}
			newsService.deleteByPrimaryKey(news.getId());
			ret.setData("删除成功");
		} else {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("未登录，或登录超时,请登录后重试");
		}
		return ret;
	}

	@ResponseBody
	@RequestMapping("/updateNoti")
	@RolePassport(roleLv = 2)
	public ResponseMap<String> update(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user != null) {
			Integer id = map.getIntValue("id");
			News news = newsService.selectByPrimaryKey(id);
			if (news == null) {
				ret.setData("通知不存在");
				return ret;
			}
			if (user.getRole() != 3 && news.getUid() != user.getId()) {
				ret.setData("无法修改他人通知");
				return ret;
			}
			String title = map.getStringValue("title");
			String content = map.getStringValue("content");
			news.setTitle(title);
			news.setDetail(content);
			newsService.updateByPrimaryKeySelective(news);
			ret.setData("修改成功");
		} else {
			ret.setData("未登录，或登录超时,请登录后重试");
		}
		return ret;
	}

	public static final String NEWS_URL = "http://api.sina.cn/sinago/list.json?channel=news_tech";

	@ResponseBody
	@RequestMapping("/reloadNews")
	@RolePassport(roleLv = 3)
	public ResponseMap<String> reloadNews(HttpServletRequest request, HttpServletResponse httpResponse) {
		logger.info("管理员启动新闻抓取");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		String response = restTemplate.postForObject(NEWS_URL, params, String.class);
		System.out.println("response:\n" + response);
		JSONObject sinaNewsResponse = JSONObject.parseObject(response);
		SinaNewsResponse news = JSONObject.parseObject(sinaNewsResponse.get("data").toString(), SinaNewsResponse.class);
		List<News> saveDatas = new LinkedList<News>();
		Set<String> titles = new HashSet<String>();
		for (SineNews n : news.getList()) {
			News dbNews = n.toNews();
			if (newsService.selectByTitle(dbNews.getTitle()) == null && !titles.contains(dbNews.getTitle())) {
				if (dbNews.getDetail() != null) {
					saveDatas.add(dbNews);
					titles.add(dbNews.getTitle());
				}
			}
		}
		if (saveDatas.size() > 0) {
			newsService.batchInsert(saveDatas);
			logger.info("获取" + saveDatas.size() + "条新闻");
		}
		ResponseMap<String>   ret= new ResponseMap<String>();
		ret.setMsg("刷新成功");
		return ret;
	}
}
