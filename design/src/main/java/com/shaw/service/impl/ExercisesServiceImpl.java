package com.shaw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shaw.bo.Exercises;
import com.shaw.mapper.ExercisesMapper;
import com.shaw.service.DoExercisesService;
import com.shaw.service.ExercisesService;
import com.shaw.vo.ExerciseStatisticsVo;
import com.shaw.vo.ExercisesShowVo;

@Service
public class ExercisesServiceImpl implements ExercisesService {
	@Autowired
	private ExercisesMapper exercisesMapper;
	@Autowired
	private DoExercisesService doExercisesService;
	
	
	private Logger logger = LoggerFactory.getLogger(ExercisesServiceImpl.class);

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteByPrimaryKey(Integer id) throws Exception {
		try {
			doExercisesService.deleteByExeId(id);
			return this.exercisesMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public int insert(Exercises record) {
		return this.exercisesMapper.insert(record);
	}

	@Override
	public int insertSelective(Exercises record) {
		return this.exercisesMapper.insertSelective(record);
	}

	@Override
	public Exercises selectByPrimaryKey(Integer id) {
		return this.exercisesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Exercises record) {
		return this.exercisesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Exercises record) {
		return this.exercisesMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ExerciseStatisticsVo> ExerciseStatistics() {
		return this.exercisesMapper.ExerciseStatistics();
	}

	@Override
	public List<Exercises> randomGetExe(Integer subjectId, Integer num) {
		return this.exercisesMapper.randomGetExe(subjectId, num);
	}

	@Override
	public List<Exercises> selectByIds(List<Integer> ids) {
		return this.exercisesMapper.selectByIds(ids);
	}

	@Override
	public List<Exercises> selectBySubjectIds(Integer subjectId) {
		return this.exercisesMapper.selectBySubjectIds(subjectId);
	}

	@Override
	public Integer updateSubjectNameBySubject(Integer subjectId, String subjectName) {
		return exercisesMapper.updateSubjectNameBySubject(subjectId, subjectName);
	}

	@Override
	public List<ExercisesShowVo> selectByQuery(Map<String, Object> params) {
		List<Exercises> list = this.exercisesMapper.selectByQuery(params);
		List<ExercisesShowVo> voList = new ArrayList<ExercisesShowVo>();
		for (Exercises ex : list) {
			ExercisesShowVo vo = new ExercisesShowVo(ex);
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public Integer countByQuery(Map<String, Object> params) {
		return this.exercisesMapper.countByQuery(params);
	}

}
