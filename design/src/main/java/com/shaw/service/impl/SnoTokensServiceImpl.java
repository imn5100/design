package com.shaw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaw.bo.SnoTokens;
import com.shaw.mapper.SnoTokensMapper;
import com.shaw.service.SnoTokensService;

@Service
public class SnoTokensServiceImpl implements SnoTokensService {
	@Autowired
	private SnoTokensMapper snoTokenMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return snoTokenMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(SnoTokens record) {
		return snoTokenMapper.insert(record);
	}

	@Override
	public int insertSelective(SnoTokens record) {
		return snoTokenMapper.insertSelective(record);
	}

	@Override
	public SnoTokens selectByPrimaryKey(Integer id) {
		return snoTokenMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(SnoTokens record) {
		return snoTokenMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(SnoTokens record) {
		return snoTokenMapper.updateByPrimaryKey(record);
	}

	@Override
	public SnoTokens selectByCode(String code) {
		return snoTokenMapper.selectByCode(code);
	}

	@Override
	public List<SnoTokens> selectByQuery(Map<String, Object> map) {
		return this.snoTokenMapper.selectByQuery(map);
	}

	@Override
	public Integer countByQuery(Map<String, Object> map) {
		return snoTokenMapper.countByQuery(map);
	}

}
