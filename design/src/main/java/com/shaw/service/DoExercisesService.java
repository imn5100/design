package com.shaw.service;

import java.util.List;
import java.util.Map;

import com.shaw.bo.DoExercises;
import com.shaw.vo.ExercisesVo;
import com.shaw.vo.StudentCount;

public interface DoExercisesService {
	int deleteByPrimaryKey(Integer id);

	int insert(DoExercises record);

	int insertSelective(DoExercises record);

	DoExercises selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(DoExercises record);

	int updateByPrimaryKey(DoExercises record);

	void batchInsertExVo(List<ExercisesVo> list);

	List<Long> selectTimes(Integer id);

	List<ExercisesVo> selectLatelyExe(Integer id);
	
	List<ExercisesVo> selectByTime(Long time,Integer uid);
	
	List<StudentCount> selectMyStudentCount(Integer teacherId);
	
	List<Long> selectTimesByTidAndSid( Integer sid, Integer tid);
	
	List<ExercisesVo> selectByQuery(Map<String,Object>  params);

	Integer deleteByExeId(Integer id); 
}