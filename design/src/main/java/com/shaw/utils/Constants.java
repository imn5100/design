package com.shaw.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Constants {
	public static final int COOKIE_TIME = 60 * 60;
	public static final String NULL = "null";
	public static final BigDecimal MAX_VALUE = new BigDecimal("99999999999999.98");
	public static final String USER_EXERCISES = "user_exercises_%s_%s";
	public static final Long USER_EXERCISES_EXPIRE = 60 * 60 * 24L;
	public static final String ERROR_LOGGER = "Error";

	// 可以直接通过网页文件名称名跳转的页面 注册
	public static final List<String> PAGESNAME = Arrays.asList("about", "news", "login", "register", "changePsw",
			"unrole");
}
