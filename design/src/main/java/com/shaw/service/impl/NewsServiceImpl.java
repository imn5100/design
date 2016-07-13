package com.shaw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaw.bo.News;
import com.shaw.mapper.NewsMapper;
import com.shaw.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsMapper newsMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return newsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(News record) {
		return newsMapper.insert(record);
	}

	@Override
	public int insertSelective(News record) {
		return newsMapper.insertSelective(record);
	}

	@Override
	public News selectByPrimaryKey(Integer id) {
		return newsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(News record) {
		return newsMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(News record) {
		return newsMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(News record) {
		return newsMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<News> selectByQuery(Map<String, Object> map) {
		return newsMapper.selectByQuery(map);
	}

	@Override
	public News selectByTitle(String title) {
		return this.newsMapper.selectByTitle(title);
	}

	@Override
	public void batchInsert(List<News> list) {
		 this.newsMapper.batchInsert(list);
	}
}