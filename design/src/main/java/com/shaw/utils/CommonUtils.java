package com.shaw.utils;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {
	public static boolean allIsNotBlank(String... strs) {
		for (String str : strs) {
			if (StringUtils.isBlank(str))
				return false;
		}
		return true;
	}
}
