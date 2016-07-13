package com.shaw.service;

import java.util.List;
import java.util.Map;

import com.shaw.bo.SnoTokens;
import com.shaw.bo.User;
import com.shaw.bo.UserInfo;
import com.shaw.vo.UserVo;

public interface UserService {
	
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
	
	User login(String username, String password, Integer role);

	User selectByUsername(String username);

	User registerStudent(String username, String password, SnoTokens sno) throws Exception;

	User changePsw(User user, String psw);

	void changeInfo(User user, UserInfo userInfo) throws Exception;

	UserVo getUserVoById(Integer id);

	List<UserVo> selectByQuery(Map<String, Object> params);

	Integer countByQuery(Map<String, Object> params);

	User registerTeacher(String username, String password, String nick) throws Exception;
}
