package com.shaw.service;

import com.shaw.bo.UserInfo;

public interface UserInfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
    
    
    UserInfo  selectByUserId(Integer id);
}