package com.shaw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shaw.bo.UserInfo;
import com.shaw.mapper.UserInfoMapper;
import com.shaw.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoMapper userInfoMapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userInfoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserInfo record) {
		return userInfoMapper.insert(record);
	}

	@Override
	public int insertSelective(UserInfo record) {
		return userInfoMapper.insertSelective(record);
	}

	@Override
	public UserInfo selectByPrimaryKey(Integer id) {
		return userInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserInfo record) {
		return userInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserInfo record) {
		return userInfoMapper.updateByPrimaryKey(record);
	}

	@Override
	public UserInfo selectByUserId(Integer id) {
		return userInfoMapper.selectByUserId(id);
	}

}
