package com.shaw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.shaw.annotation.RolePassport;
import com.shaw.bo.Exercises;
import com.shaw.bo.Subject;
import com.shaw.bo.User;
import com.shaw.service.DoExercisesService;
import com.shaw.service.ExercisesService;
import com.shaw.service.SubjectService;
import com.shaw.service.UserService;
import com.shaw.service.impl.RedisClient;
import com.shaw.utils.CommonUtils;
import com.shaw.utils.Constants;
import com.shaw.utils.ErrorCode;
import com.shaw.utils.Page;
import com.shaw.utils.RequestMap;
import com.shaw.utils.ResponseMap;
import com.shaw.utils.TimeUtils;
import com.shaw.vo.ExerciseStatisticsDataVo;
import com.shaw.vo.ExerciseStatisticsVo;
import com.shaw.vo.ExercisesShowVo;
import com.shaw.vo.ExercisesVo;
import com.shaw.vo.OptionVo;
import com.shaw.vo.SimpleAVo;
import com.shaw.vo.TableData;
import com.shaw.vo.UserVo;

@Controller
public class ExercisesController {
	@Autowired
	private ExercisesService exercisesService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private RedisClient redisClient;
	@Autowired
	private UserService userService;
	@Autowired
	private DoExercisesService doExercisesService;

	@ResponseBody
	@RequestMapping("/addExercise")
	@RolePassport(roleLv = 2)
	public ResponseMap<String> addByJson(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user == null) {
			ret.setCode(ErrorCode.UNLOGIN);
			ret.setData("未登录，或登录超时，请重新登录后添加");
			return ret;
		}
		Integer type = map.getIntValue("type");
		String exQu = map.getStringValue("exQu");
		Integer subId = map.getIntValue("exSu");
		String exAn = map.getStringValue("exAn");
		if (!CommonUtils.allIsNotBlank(exQu, exAn)) {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("题目&答案不能为空");
			return ret;
		}
		Subject sub = null;
		if (subId != null) {
			sub = subjectService.selectByPrimaryKey(subId);
		}
		if (sub == null) {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("所选科目不存在!请重新选择正确科目，或添加新科目");
		}
		Exercises exer = new Exercises();
		if (type == 1) {
			String exAnA = map.getStringValue("exAnA");
			String exAnB = map.getStringValue("exAnB");
			String exAnC = map.getStringValue("exAnC");
			String exAnD = map.getStringValue("exAnD");
			if (!CommonUtils.allIsNotBlank(exAnA, exAnB, exAnC, exAnD)) {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("选择题不能有选项为空");
				return ret;
			}
			OptionVo options = new OptionVo();
			options.setAnswerA(exAnA);
			options.setAnswerB(exAnB);
			options.setAnswerC(exAnC);
			options.setAnswerD(exAnD);
			String optionsJstr = JSONObject.toJSONString(options);
			exer.setQuestion(exQu);
			exer.setAnswer(exAn);
			exer.setOptions(optionsJstr);
			exer.setCreatetime(System.currentTimeMillis());
			exer.setSubjectName(sub.getName());
			exer.setSubjectId(sub.getId());
			exer.setType(1);
			exer.setTeacherId(user.getId());
			exer.setTeacherName(user.getUsername());
			exercisesService.insert(exer);
		} else if (type == 2) {
			exer.setType(2);
			exer.setQuestion(exQu);
			exer.setAnswer(exAn);
			exer.setCreatetime(System.currentTimeMillis());
			exer.setSubjectName(sub.getName());
			exer.setSubjectId(sub.getId());
			exer.setTeacherId(user.getId());
			exer.setTeacherName(user.getUsername());
			exercisesService.insert(exer);
		} else {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("题目类型错误,请选择正确题目类型");
		}
		ret.setData("添加成功");
		return ret;
	}

	@ResponseBody
	@RequestMapping("/updateExe")
	@RolePassport(roleLv = 2)
	public ResponseMap<String> updateExe(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user == null) {
			ret.setCode(ErrorCode.UNLOGIN);
			ret.setData("未登录，或登录超时，请重新登录后添加");
			return ret;
		}
		Integer type = map.getIntValue("type");
		Integer id = map.getIntValue("id", 0);
		String exQu = map.getStringValue("exQu", "");
		String exAn = map.getStringValue("exAn", "");
		if (!CommonUtils.allIsNotBlank(exQu)) {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("题目不能为空");
			return ret;
		}
		Exercises exer = exercisesService.selectByPrimaryKey(id);
		if (exer == null) {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("需要修改的习题不存在");
			return ret;
		}
		if (user.getRole() != 3 && user.getId() != exer.getTeacherId()) {
			ret.setBizErrorCode();
			ret.setData("权限不足无法修改其他老师习题数据");
			return ret;
		}
		if (type == 1) {
			String exAnA = map.getStringValue("exAnA");
			String exAnB = map.getStringValue("exAnB");
			String exAnC = map.getStringValue("exAnC");
			String exAnD = map.getStringValue("exAnD");
			if (!CommonUtils.allIsNotBlank(exAnA, exAnB, exAnC, exAnD)) {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("选择题不能有选项为空");
				return ret;
			}
			OptionVo options = new OptionVo();
			options.setAnswerA(exAnA);
			options.setAnswerB(exAnB);
			options.setAnswerC(exAnC);
			options.setAnswerD(exAnD);
			String optionsJstr = JSONObject.toJSONString(options);

			exer.setQuestion(exQu);// 更新问题
			if (StringUtils.isNotBlank(exAn)) {
				exer.setAnswer(exAn); // 更新答案
			}
			exer.setOptions(optionsJstr); // 更新选项
			exercisesService.updateByPrimaryKeySelective(exer);
		} else if (type == 2) {
			if (StringUtils.isNotBlank(exAn)) {
				exer.setAnswer(exAn); // 更新答案
			} else {
				ret.setCode(ErrorCode.BIZERROR);
				ret.setData("答案不能为空");
				return ret;
			}
			exer.setQuestion(exQu);
			exercisesService.updateByPrimaryKeySelective(exer);
		} else {
			ret.setCode(ErrorCode.BIZERROR);
			ret.setData("题目类型错误,请选择正确题目类型");
		}
		ret.setData("修改成功");
		return ret;
	}

	@ResponseBody
	@RequestMapping("/exeStatistics")
	@RolePassport(roleLv = 1)
	public ResponseMap<ExerciseStatisticsDataVo> exeStatistics(HttpServletRequest request,
			HttpServletResponse response) {
		List<ExerciseStatisticsVo> list = this.exercisesService.ExerciseStatistics();
		Multimap<String, ExerciseStatisticsVo> subjectMap = ArrayListMultimap.create();
		Multimap<Integer, ExerciseStatisticsVo> typeMap = ArrayListMultimap.create();
		Set<String> subjectNameSet = new HashSet<String>();
		for (ExerciseStatisticsVo vo : list) {
			subjectMap.put(vo.getSubjectName(), vo);
			subjectNameSet.add(vo.getSubjectName());
			typeMap.put(vo.getType(), vo);
		}
		int type1 = 0; // 选择题总题数
		int type2 = 0; // 简答题总题数
		for (ExerciseStatisticsVo vo : typeMap.get(1)) {
			type1 += vo.getCountType();
		}
		for (ExerciseStatisticsVo vo : typeMap.get(2)) {
			type2 += vo.getCountType();
		}
		Map<String, Integer> subjectCountMap = new HashMap<String, Integer>();
		for (String subjectName : subjectNameSet) {
			int count = 0;
			for (ExerciseStatisticsVo vo : subjectMap.get(subjectName)) {
				count += vo.getCountSubject();
			}
			subjectCountMap.put(subjectName, count);
		}
		ExerciseStatisticsDataVo data = new ExerciseStatisticsDataVo();
		data.setType1(type1);
		data.setType2(type2);
		data.setSubjectNameCount(subjectCountMap);
		ResponseMap<ExerciseStatisticsDataVo> ret = new ResponseMap<ExerciseStatisticsDataVo>();
		ret.setData(data);
		return ret;
	}

	@ResponseBody
	@RequestMapping("/getExercises")
	@RolePassport(roleLv = 1)
	public ResponseMap<List<Exercises>> getExercises(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		int num = map.getIntValue("num", 10);
		Integer subjectId = map.getIntValue("subjectId", 0);
		ResponseMap<List<Exercises>> ret = new ResponseMap<List<Exercises>>();
		if (subjectId == 0) {
			ret.setCode(ErrorCode.BIZERROR);
			return ret;
		}
		List<Exercises> es = this.exercisesService.randomGetExe(subjectId, num);
		ret.setData(es);
		return ret;
	}

	@ResponseBody
	@RequestMapping("/submitExercises")
	@RolePassport(roleLv = 1)
	public ResponseMap<String> submitExercises(@RequestBody RequestMap map, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user == null) {
			ret.setCode(ErrorCode.UNLOGIN);
			ret.setData("未登录，或登录超时，请重新登录后添加");
			return ret;
		}
		List<String> opA = map.getStringList("OpA");
		List<SimpleAVo> saList = map.getListByType("SiA", SimpleAVo.class);
		String toTeacherName = map.getStringValue("teacherName");
		String ids = map.getStringValue("ids");
		if (StringUtils.isBlank(ids) || StringUtils.isBlank(toTeacherName)) {
			ret.setBizErrorCode();
			ret.setMsg("提交数据错误");
			return ret;
		}
		User tUser = userService.selectByUsername(toTeacherName);
		if (tUser == null || tUser.getRole() < 2) {
			ret.setBizErrorCode();
			ret.setMsg("找不到对应教师，请重新确认后输入");
			return ret;
		}
		String[] idsl = ids.split(",");
		List<Integer> intIds = new ArrayList<Integer>();
		for (String id : idsl) {
			try {
				intIds.add(Integer.valueOf(id));
			} catch (Exception e) {
				ret.setBizErrorCode();
				ret.setMsg("提交数据错误");
				return ret;
			}
		}
		Map<Integer, String> opAMap = new HashMap<Integer, String>();
		for (String a : opA) {
			String[] idAndAnswer = a.split("-");
			try {
				if (idAndAnswer.length != 2) {
					ret.setBizErrorCode();
					ret.setMsg("提交数据错误");
					return ret;
				}
				Integer id = Integer.valueOf(idAndAnswer[1]);
				String answer = idAndAnswer[0];
				if (opAMap.containsKey(id)) {
					opAMap.put(id, opAMap.get(id) + answer);
				} else {
					opAMap.put(id, answer);
				}
			} catch (Exception e) {
				ret.setBizErrorCode();
				ret.setMsg("提交数据错误");
				return ret;
			}
		}
		// opAMap 选择题作答，siAMap 简答题作答,data 本次联系题库
		Map<Integer, String> siAMap = SimpleAVo.toMap(saList);
		List<Exercises> data = exercisesService.selectByIds(intIds);
		List<ExercisesVo> list = new ArrayList<ExercisesVo>();
		Integer subjectId = 0;
		Long createTime = System.currentTimeMillis();
		for (Exercises e : data) {
			ExercisesVo vo = new ExercisesVo();
			if (e.getType() == 1) {
				vo.setUserAnswer(opAMap.get(e.getId()));
			} else {
				vo.setUserAnswer(siAMap.get(e.getId()));
			}
			BeanUtils.copyProperties(e, vo);
			vo.setaUid(user.getId());
			vo.setaUsername(user.getUsername());
			vo.setToTeacherId(tUser.getId());
			vo.setToTeacherName(tUser.getUsername());
			vo.setCreatetime(createTime);
			if (vo.getUserAnswer() == null) {
				vo.setUserAnswer("");
			}
			list.add(vo);
			subjectId = e.getSubjectId();
		}
		if (subjectId != 0) {
			redisClient.del(String.format(Constants.USER_EXERCISES, user.getId(), subjectId));
			doExercisesService.batchInsertExVo(list);
			ret.setMsg("提交成功");
			return ret;
		} else {
			ret.setBizErrorCode();
			ret.setMsg("数据异常");
			return ret;
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/doExercises")
	@RolePassport(roleLv = 1)
	public ModelAndView doExercises(@RequestParam("subjectId") Integer subjectId,
			@RequestParam(value = "num", required = false) Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (user == null) {
			mav.setViewName("login");
			return mav;
		}
		if (num == null) {
			num = 10;
		}
		if (redisClient.exists(String.format(Constants.USER_EXERCISES, user.getId(), subjectId))) {
			List<ExercisesVo> evos = ((List<ExercisesVo>) redisClient
					.get(String.format(Constants.USER_EXERCISES, user.getId(), subjectId)));
			mav.addObject("exMsg", "已成功加载该科目上次未完成的练习");
			mav.addObject("es", evos);
		} else {
			List<Exercises> es = this.exercisesService.randomGetExe(subjectId, num);
			List<ExercisesVo> evos = new ArrayList<ExercisesVo>();
			for (Exercises e : es) {
				ExercisesVo vo = new ExercisesVo();
				BeanUtils.copyProperties(e, vo);
				vo.setOptionVo(JSONObject.parseObject(e.getOptions(), OptionVo.class));
				evos.add(vo);
			}
			if (evos.size() <= 0) {
				mav.addObject("msg", "该科目暂无习题，如果需要请联系老师添加习题");
			}else{
				redisClient.set(String.format(Constants.USER_EXERCISES, user.getId(), subjectId), evos);
				redisClient.expire(String.format(Constants.USER_EXERCISES, user.getId(), subjectId),
						Constants.USER_EXERCISES_EXPIRE);
				mav.addObject("es", evos);
			}
		}

		mav.setViewName("tDoExercises");
		return mav;
	}

	@RequestMapping("/getLatelyExe")
	@RolePassport(roleLv = 1)
	public ModelAndView getLatelyExe(@RequestParam(value = "time", required = false) Long time,
			HttpServletRequest request, HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (user == null) {
			mav.setViewName("login");
			return mav;
		}
		List<ExercisesVo> evos = null;
		if (time != null) {
			evos = this.doExercisesService.selectByTime(time, user.getId());
		} else {
			evos = this.doExercisesService.selectLatelyExe(user.getId());
		}
		if (evos == null || evos.size() <= 0) {
			mav.addObject("msg", "没有习题记录！");
		} else {
			mav.addObject("subject", evos.get(0).getSubjectName());
			mav.addObject("size", evos.size());
			mav.addObject("createTime", TimeUtils.getFormatTimeYMDHM(evos.get(0).getCreatetime()));
			mav.addObject("es", evos);
		}
		mav.setViewName("exercisesPage");
		return mav;
	}

	@RequestMapping("/getStudentExes")
	@RolePassport(roleLv = 2)
	public ModelAndView getStudentExes(@RequestParam(value = "time", required = true) Long time,
			@RequestParam(value = "sid", required = true) Integer sid, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (user == null) {
			mav.setViewName("login");
			return mav;
		}
		List<ExercisesVo> evos = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("studentId", sid);
		params.put("teacherId", user.getId());
		params.put("createTime", time);
		evos = this.doExercisesService.selectByQuery(params);
		if (evos == null || evos.size() <= 0) {
			mav.addObject("msg", "没有习题记录！");
		} else {
			mav.addObject("subject", evos.get(0).getSubjectName());
			mav.addObject("size", evos.size());
			mav.addObject("createTime", TimeUtils.getFormatTimeYMDHM(evos.get(0).getCreatetime()));
			mav.addObject("es", evos);
		}
		mav.setViewName("exercisesPage");
		return mav;
	}

	@RequestMapping("/studentExes")
	@RolePassport(roleLv = 2)
	public ModelAndView studentExes(@RequestParam(value = "id", required = true) Integer id, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView mav = new ModelAndView();
		if (user == null) {
			mav.setViewName("login");
			return mav;
		}
		List<Long> times = this.doExercisesService.selectTimesByTidAndSid(id, user.getId());
		UserVo student = this.userService.getUserVoById(id);
		if (times != null && times.size() > 0) {
			mav.addObject("times", times);
			mav.addObject("student", student);
		}
		mav.setViewName("sExecisesDetails");
		return mav;
	}

	@ResponseBody
	@RequestMapping("/showExeTable")
	@RolePassport(roleLv = 2)
	public TableData<ExercisesShowVo> showSubjectTable(
			@RequestParam(value = "page", required = false) Integer currentPage,
			@RequestParam(value = "rows", required = false) Integer everyPage, HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User) request.getSession().getAttribute("user");
		TableData<ExercisesShowVo> td = new TableData<ExercisesShowVo>();
		if (user == null) {
			return td;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (currentPage == null || currentPage <= 0) {
			currentPage = 1;
		}
		if (everyPage == null || everyPage <= 0) {
			everyPage = 10;
		}
		if (user.getRole() != 3) {// 1.LV2以上权限，非管理员权限，只能操作自己的数据
			params.put("teacherId", user.getId());
		}
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setEveryPage(everyPage);
		params.put("page", page);
		td.setTotal(exercisesService.countByQuery(params));
		td.setRows(exercisesService.selectByQuery(params));
		return td;
	}

	@ResponseBody
	@RequestMapping("/destroyExe")
	@RolePassport(roleLv = 2)
	public ResponseMap<String> destroyExe(@RequestParam("id") Integer id, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ResponseMap<String> ret = new ResponseMap<String>();
		if (user != null) {
			if (user.getRole() > 1) {
				if (id != null) {
					try {
						Exercises ex = this.exercisesService.selectByPrimaryKey(id);
						if (ex != null && user.getRole() != 3 && user.getId() != ex.getTeacherId()) {
							ret.setBizErrorCode();
							ret.setData("权限不足无法删除其他老师习题数据");
						} else {
							this.exercisesService.deleteByPrimaryKey(id);
						}
					} catch (Exception e) {
						ret.setBizErrorCode();
						ret.setData("删除数据异常，请稍候再试");
					}
				} else {
					ret.setBizErrorCode();
					ret.setData("需要删除的数据不存在");
				}
			} else {
				ret.setBizErrorCode();
				ret.setData("权限不足");
			}
		} else {
			ret.setBizErrorCode();
			ret.setData("未登录，或登录超时，请重新登录后操作");
		}
		return ret;
	}

}
