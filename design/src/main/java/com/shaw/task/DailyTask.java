package com.shaw.task;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.shaw.bo.News;
import com.shaw.service.NewsService;
import com.shaw.vo.SinaNewsResponse;
import com.shaw.vo.SineNews;

@Component
public class DailyTask {
	private Logger logger = LoggerFactory.getLogger(DailyTask.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private NewsService newsService;

	public static final String NEWS_URL = "http://api.sina.cn/sinago/list.json?channel=news_tech";

	@Scheduled(cron = "0 0 0/2 * * ?")
	public void startGetNews() throws Exception {
		logger.info("获取sina新闻TASK开始");
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

	}
}
