package com.shaw.service;

import java.util.List;
import java.util.Map;

import com.shaw.bo.SnoTokens;

public interface SnoTokensService {
	int deleteByPrimaryKey(Integer id);

	int insert(SnoTokens record);

	int insertSelective(SnoTokens record);

	SnoTokens selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SnoTokens record);

	int updateByPrimaryKey(SnoTokens record);

	SnoTokens selectByCode(String code);

	List<SnoTokens> selectByQuery(Map<String, Object> map);
	
	Integer  countByQuery(Map<String,Object>  map);
}