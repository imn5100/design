package com.shaw.test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.shaw.bo.News;
import com.shaw.service.NewsService;
import com.shaw.vo.SinaNewsResponse;
import com.shaw.vo.SineNews;

public class TestObj extends SpringTestCase {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private NewsService newsService;

	@Test
	public void testAi() throws Exception {
		String url = "http://api.sina.cn/sinago/list.json?channel=news_tech";
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		String response = restTemplate.postForObject(url, params, String.class);
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
		if (saveDatas.size() > 0)
			newsService.batchInsert(saveDatas);
	}
}
