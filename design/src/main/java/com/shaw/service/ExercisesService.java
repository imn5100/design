package com.shaw.service;

import java.util.List;
import java.util.Map;

import com.shaw.bo.Exercises;
import com.shaw.vo.ExerciseStatisticsVo;
import com.shaw.vo.ExercisesShowVo;

public interface ExercisesService {

	int deleteByPrimaryKey(Integer id) throws Exception;

	int insert(Exercises record);

	int insertSelective(Exercises record);

	Exercises selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Exercises record);

	int updateByPrimaryKey(Exercises record);

	List<ExerciseStatisticsVo> ExerciseStatistics();

	List<Exercises> randomGetExe(Integer subjectId, Integer num);

	List<Exercises> selectByIds(List<Integer> ids);

	List<Exercises> selectBySubjectIds(Integer subjectId);

	Integer updateSubjectNameBySubject(Integer subjectId, String subjectName);

	List<ExercisesShowVo> selectByQuery(Map<String, Object> params);

	Integer countByQuery(Map<String, Object> params);
}