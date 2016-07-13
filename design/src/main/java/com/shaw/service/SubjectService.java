package com.shaw.service;

import java.util.List;
import java.util.Map;

import com.shaw.bo.Subject;

public interface SubjectService {
	int deleteByPrimaryKey(Integer id);

	int insert(Subject record);

	int insertSelective(Subject record);

	Subject selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Subject record);

	int updateByPrimaryKey(Subject record);

	List<Subject> selectAllSubject();

	Integer countByQuery(Map<String, Object> params);

	List<Subject> selectByQuery(Map<String, Object> params);
}