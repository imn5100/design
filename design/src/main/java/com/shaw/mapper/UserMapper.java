package com.shaw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shaw.bo.User;
import com.shaw.vo.UserVo;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	User login(@Param("username") String username, @Param("password") String password, @Param("role") Integer role);

	User selectByUsername(String username);

	List<UserVo> selectByQuery(Map<String, Object> params);

	Integer countByQuery(Map<String, Object> params);
}