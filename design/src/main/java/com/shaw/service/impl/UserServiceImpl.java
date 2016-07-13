package com.shaw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaw.bo.SnoTokens;
import com.shaw.bo.User;
import com.shaw.bo.UserInfo;
import com.shaw.mapper.UserInfoMapper;
import com.shaw.mapper.UserMapper;
import com.shaw.service.SnoTokensService;
import com.shaw.service.UserService;
import com.shaw.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserInfoMapper userInfoMapper;
	@Autowired
	private SnoTokensService snoTokensService;

	@Override
	public User login(String username, String password, Integer role) {
		return userMapper.login(username, password, role);
	}

	@Override
	public User selectByUsername(String username) {
		return userMapper.selectByUsername(username);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User registerStudent(String username, String password, SnoTokens sno) throws Exception {
		User user = new User();
		UserInfo userInfo = new UserInfo();
		userInfo.setSno(sno.getCode());
		userInfo.setType(1);
		userInfo.setClazz("");
		userInfoMapper.insert(userInfo);

		user.setInfoid(userInfo.getId());
		user.setNick(username);
		user.setRole(1);
		user.setUsername(username);
		user.setPassword(password);
		userMapper.insert(user);

		sno.setSid(user.getId());
		sno.setStatus(1);
		snoTokensService.updateByPrimaryKeySelective(sno);
		return user;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public User registerTeacher(String username, String password, String nick) throws Exception {
		UserInfo userInfo = new UserInfo();
		userInfo.setType(2);
		userInfoMapper.insert(userInfo);
		User user = new User();
		user.setInfoid(userInfo.getId());
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(2);
		user.setNick(nick);
		userMapper.insert(user);
		return user;
	}

	@Override
	public User changePsw(User user, String psw) {
		user.setPassword(psw);
		userMapper.updateByPrimaryKeySelective(user);
		return user;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void changeInfo(User user, UserInfo userInfo) throws Exception {
		this.userMapper.updateByPrimaryKeySelective(user);
		this.userInfoMapper.updateByPrimaryKeySelective(userInfo);
	}

	@Override
	public UserVo getUserVoById(Integer id) {
		User user = this.userMapper.selectByPrimaryKey(id);
		UserInfo info = userInfoMapper.selectByPrimaryKey(user.getInfoid());
		UserVo userVo = new UserVo();
		userVo.setId(user.getId());
		if (info != null) {
			org.springframework.beans.BeanUtils.copyProperties(info, userVo);
		}
		org.springframework.beans.BeanUtils.copyProperties(user, userVo);
		return userVo;
	}

	@Override
	public List<UserVo> selectByQuery(Map<String, Object> params) {
		return userMapper.selectByQuery(params);
	}

	@Override
	public Integer countByQuery(Map<String, Object> params) {
		return userMapper.countByQuery(params);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

}
