package com.shaw.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaw.bo.Subject;
import com.shaw.mapper.SubjectMapper;
import com.shaw.service.ExercisesService;
import com.shaw.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectMapper subjectMapper;
	@Autowired
	private ExercisesService exercisesService;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return subjectMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Subject record) {
		return subjectMapper.insert(record);
	}

	@Override
	public int insertSelective(Subject record) {
		return subjectMapper.insertSelective(record);
	}

	@Override
	public Subject selectByPrimaryKey(Integer id) {
		return subjectMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateByPrimaryKeySelective(Subject record) {
		Subject old = subjectMapper.selectByPrimaryKey(record.getId());
		if (old.getName()!=null&&!old.getName().equals(record.getName())) {
			exercisesService.updateSubjectNameBySubject(record.getId(), record.getName());
		}
		Integer count = subjectMapper.updateByPrimaryKeySelective(record);
		return count;
	}

	@Override
	public int updateByPrimaryKey(Subject record) {
		return subjectMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Subject> selectAllSubject() {
		return subjectMapper.selectAllSubject();
	}

	@Override
	public Integer countByQuery(Map<String, Object> params) {
		return subjectMapper.countByQuery(params);
	}

	@Override
	public List<Subject> selectByQuery(Map<String, Object> params) {
		return subjectMapper.selectByQuery(params);
	}

}
