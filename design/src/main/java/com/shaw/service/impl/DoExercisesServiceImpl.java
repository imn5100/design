package com.shaw.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shaw.bo.DoExercises;
import com.shaw.bo.Exercises;
import com.shaw.mapper.DoExercisesMapper;
import com.shaw.mapper.ExercisesMapper;
import com.shaw.service.DoExercisesService;
import com.shaw.service.UserInfoService;
import com.shaw.vo.ExercisesVo;
import com.shaw.vo.OptionVo;
import com.shaw.vo.StudentCount;

@Service
public class DoExercisesServiceImpl implements DoExercisesService {
	@Autowired
	private DoExercisesMapper doExercisesMapper;
	@Autowired
	private ExercisesMapper exercisesMapper;
	@Autowired
	private UserInfoService userInfoService;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return this.doExercisesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DoExercises record) {
		return doExercisesMapper.insert(record);
	}

	@Override
	public int insertSelective(DoExercises record) {
		return this.doExercisesMapper.insertSelective(record);
	}

	@Override
	public DoExercises selectByPrimaryKey(Integer id) {
		return this.doExercisesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DoExercises record) {
		return this.doExercisesMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DoExercises record) {
		return this.doExercisesMapper.updateByPrimaryKey(record);
	}

	@Override
	public void batchInsertExVo(List<ExercisesVo> list) {
		this.doExercisesMapper.batchInsertExVo(list);
	}

	@Override
	public List<Long> selectTimes(Integer id) {
		return this.doExercisesMapper.selectTimes(id);
	}

	@Override
	public List<ExercisesVo> selectLatelyExe(Integer id) {
		List<DoExercises> list = this.doExercisesMapper.selectLatelyExe(id);
		List<ExercisesVo> voList = new ArrayList<ExercisesVo>();
		for (DoExercises de : list) {
			Exercises ex = this.exercisesMapper.selectByPrimaryKey(de.getExercisesId());
			ExercisesVo vo = new ExercisesVo();
			BeanUtils.copyProperties(ex, vo);
			vo.setaUid(id);
			vo.setaUsername(de.getStudentName());
			vo.setCreatetime(de.getCreatetime());
			vo.setTeacherId(de.getTeacherId());
			vo.setTeacherName(de.getTeacherName());
			vo.setAnswer(vo.getAnswer().toUpperCase());
			vo.setUserAnswer(de.getAnswer().toUpperCase());
			vo.setOptionVo(JSONObject.parseObject(ex.getOptions(), OptionVo.class));
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public List<ExercisesVo> selectByTime(Long time, Integer uid) {
		List<DoExercises> list = this.doExercisesMapper.selectByTime(time, uid);
		List<ExercisesVo> voList = new ArrayList<ExercisesVo>();
		for (DoExercises de : list) {
			Exercises ex = this.exercisesMapper.selectByPrimaryKey(de.getExercisesId());
			ExercisesVo vo = new ExercisesVo();
			BeanUtils.copyProperties(ex, vo);
			vo.setaUid(uid);
			vo.setaUsername(de.getStudentName());
			vo.setCreatetime(de.getCreatetime());
			vo.setTeacherId(de.getTeacherId());
			vo.setTeacherName(de.getTeacherName());
			vo.setAnswer(vo.getAnswer().toUpperCase());
			vo.setUserAnswer(de.getAnswer().toUpperCase());
			vo.setOptionVo(JSONObject.parseObject(ex.getOptions(), OptionVo.class));
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public List<StudentCount> selectMyStudentCount(Integer teacherId) {
		List<StudentCount> list = this.doExercisesMapper.selectMyStudentCount(teacherId);
		for (StudentCount sc : list) {
			sc.setInfo(userInfoService.selectByUserId(sc.getStudentId()));
		}
		return list;
	}

	@Override
	public List<Long> selectTimesByTidAndSid(Integer sid, Integer tid) {
		return this.doExercisesMapper.selectTimesByTidAndSid(sid, tid);
	}

	@Override
	public List<ExercisesVo> selectByQuery(Map<String, Object> params) {
		List<DoExercises> list = this.doExercisesMapper.selectByQuery(params);
		List<ExercisesVo> voList = new ArrayList<ExercisesVo>();
		for (DoExercises de : list) {
			Exercises ex = this.exercisesMapper.selectByPrimaryKey(de.getExercisesId());
			ExercisesVo vo = new ExercisesVo();
			BeanUtils.copyProperties(ex, vo);
			vo.setaUid((Integer) params.get("studentId"));
			vo.setaUsername(de.getStudentName());
			vo.setCreatetime(de.getCreatetime());
			vo.setTeacherId(de.getTeacherId());
			vo.setTeacherName(de.getTeacherName());
			vo.setAnswer(vo.getAnswer().toUpperCase());
			vo.setUserAnswer(de.getAnswer().toUpperCase());
			vo.setOptionVo(JSONObject.parseObject(ex.getOptions(), OptionVo.class));
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public Integer deleteByExeId(Integer id) {
		return this.doExercisesMapper.deleteByExeId(id);
	}

}
