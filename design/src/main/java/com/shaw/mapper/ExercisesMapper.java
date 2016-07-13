package com.shaw.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.shaw.bo.Exercises;
import com.shaw.vo.ExerciseStatisticsVo;

public interface ExercisesMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Exercises record);

	int insertSelective(Exercises record);

	Exercises selectByPrimaryKey(Integer id);

	List<Exercises> randomGetExe(@Param("subjectId") Integer subjectId, @Param("num") Integer num);

	int updateByPrimaryKeySelective(Exercises record);

	int updateByPrimaryKey(Exercises record);

	List<ExerciseStatisticsVo> ExerciseStatistics();

	List<Exercises> selectByIds(@Param("ids") List<Integer> ids);

	List<Exercises> selectBySubjectIds(@Param("subjectId") Integer subjectId);

	List<Exercises> selectByQuery(Map<String, Object> params);

	Integer countByQuery(Map<String, Object> params);

	Integer updateSubjectNameBySubject(@Param("subjectId") Integer subjectId, @Param("subjectName") String subjectName);
}