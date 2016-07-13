package com.shaw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shaw.bo.DoExercises;
import com.shaw.vo.ExercisesVo;
import com.shaw.vo.StudentCount;

public interface DoExercisesMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(DoExercises record);

	int insertSelective(DoExercises record);

	DoExercises selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(DoExercises record);

	int updateByPrimaryKey(DoExercises record);

	void batchInsertExVo(List<ExercisesVo> list);

	List<Long> selectTimes(Integer id);

	List<DoExercises> selectLatelyExe(Integer id);

	List<DoExercises> selectByTime(@Param("time") Long time, @Param("id") Integer uid);

	List<StudentCount> selectMyStudentCount(Integer teacherId);

	List<Long> selectTimesByTidAndSid(@Param("sid") Integer sid, @Param("tid") Integer tid);
	
	List<DoExercises> selectByQuery(Map<String,Object>  params); 
	
	Integer deleteByExeId(Integer id); 
}