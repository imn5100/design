package com.shaw.mapper;

import java.util.List;
import java.util.Map;

import com.shaw.bo.News;

public interface NewsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(News record);

	int insertSelective(News record);

	News selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(News record);

	int updateByPrimaryKeyWithBLOBs(News record);

	int updateByPrimaryKey(News record);

	List<News> selectByQuery(Map<String, Object> map);

	News selectByTitle(String title);

	void batchInsert(List<News> list);
}