package com.shaw.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

public class RequestMap extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 4765249909959205645L;

	/**
	 * getStringValue(获取String类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public String getStringValue(String key) {
		String value = (String) this.get(key);

		if (isNullorEmpty(value)) {
			return null;
		}

		return value;
	}

	/**
	 * getStringValue(获取String类型的值，可设置值为null时默认值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public String getStringValue(String key, String defaultValue) {
		String value = getStringValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * getBooleanValue(获取Boolean类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Boolean getBooleanValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Boolean) {
				return (Boolean) value;
			}

			return (Boolean) convert(value, Boolean.class);
		}

		return null;
	}

	/**
	 * getBooleanValue(获取Boolean类型的值，可设置值为null时默认值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Boolean getBooleanValue(String key, Boolean defaultValue) {
		Boolean value = getBooleanValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * getShortValue(获取Short类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Short getShortValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Integer) {
				return ((Integer) value).shortValue();
			}

			return (Short) convert(value, Short.class);
		}

		return null;
	}

	/**
	 * getShortValue(获取Short类型的值，可设置值为null时默认值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Short getShortValue(String key, Short defaultValue) {
		Short value = getShortValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * getIntValue(获取Integer类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Integer getIntValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Integer) {
				return (Integer) value;
			}

			return (Integer) convert(value, Integer.class);
		}

		return null;
	}

	/**
	 * getIntValue(获取Integer类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Integer getIntValue(String key, Integer defaultValue) {
		Integer value = getIntValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * getLongValue(获取Long类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Long getLongValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Long) {
				return (Long) value;
			}

			return (Long) convert(value, Long.class);
		}

		return null;
	}

	/**
	 * getLongValue(获取Long类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Long getLongValue(String key, Long defaultValue) {
		Long value = getLongValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * getFloatValue(获取Float类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Float getFloatValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Float) {
				return (Float) value;
			}

			return (Float) convert(value, Float.class);
		}

		return null;
	}

	/**
	 * getFloatValue(获取Float类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	public Float getFloatValue(String key, Float defaultValue) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Float) {
				return (Float) value;
			}

			return (Float) convert(value, Float.class);
		}

		return null;
	}

	/**
	 * getDoubleValue(获取Double类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	public Double getDoubleValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Double) {
				return (Double) value;
			}

			return (Double) convert(value, Double.class);
		}

		return null;
	}

	/**
	 * getDoubleValue(获取Double类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	public Double getDoubleValue(String key, Double defaultValue) {
		Double value = getDoubleValue(key);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * getBigDecimalValue(获取BigDecimal类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	public BigDecimal getBigDecimalValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof BigDecimal) {
				return (BigDecimal) value;
			}

			return (BigDecimal) convert(value, BigDecimal.class);
		}

		return null;
	}

	/**
	 * getBigDecimalValue(获取BigDecimal类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	public BigDecimal getBigDecimalValue(String key, BigDecimal defaultValue) {
		BigDecimal value = getBigDecimalValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * getDateValue(获取Date类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	public Date getDateValue(String key) {
		Object value = this.get(key);
		if (value != null) {
			if (value instanceof Date) {
				return (Date) value;
			}
			return (Date) convert(value, Date.class);
		}

		return null;
	}

	/**
	 * getDateValue(获取Date类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	public Date getDateValue(String key, Date defaultValue) {
		Date value = getDateValue(key);
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * getValueByType(获取自定义类型的值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValueByType(String key, Class<T> clazz) {
		Map<String, Object> map = (Map<String, Object>) this.get(key);
		if (CollectionUtils.isEmpty(map))
			return null;
		return fillObjectwithMap(clazz, map);
	}

	/**
	 * 
	 * getStringList(获取String类型的集合)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	@SuppressWarnings("unchecked")
	public List<String> getStringList(String key) {
		return (List<String>) this.get(key);
	}

	/**
	 * getListByType(获取自定义类型的集合值)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListByType(String key, Class<T> clazz) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) this.get(key);
		if (CollectionUtils.isEmpty(list))
			return null;
		List<T> valueList = new ArrayList<>();
		// 遍历list集合
		for (Map<String, Object> map : list) {
			valueList.add(fillObjectwithMap(clazz, map));
		}
		return valueList;
	}

	/**
	 * fillObjectwithMap(根据map中的键值填充自定义类型的对应属性)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> T fillObjectwithMap(Class<T> clazz, Map<String, Object> map) {
		// 实例化对象
		T object = BeanUtils.instantiateClass(clazz);

		// 遍历map集合
		for (Map.Entry<String, Object> entry : map.entrySet()) {

			// 排除空键值的数据
			if (!StringUtils.isBlank(entry.getKey()) && entry.getValue() != null) {

				// 如果找到field，将值设置进去
				Field field = ReflectionUtils.findField(clazz, entry.getKey());

				if (field != null) {

					Object fieldValue = null;
					Object value = entry.getValue();

					// 如果属性值为map,即对象中包含对象
					if (value instanceof Map) {

						fieldValue = fillObjectwithMap(field.getType(), (Map<String, Object>) value);

						// 如果属性值为list，即对象中包含集合
					} else if (value instanceof List) {

						List listValue = new ArrayList();

						// 遍历list集合
						for (Object valueObject : (List) value) {

							// 获取field中list里面的类型
							Class<? extends Object> type = (Class<?>) TypeUtils
									.getTypeArguments((ParameterizedType) field.getGenericType()).values().toArray()[0];

							// 如果值为map，即为对象时
							if (valueObject instanceof Map) {
								listValue.add(fillObjectwithMap(type, (Map<String, Object>) valueObject));
							} else {
								valueObject = convert(valueObject, type);

								if (valueObject != null) {
									listValue.add(valueObject);
								}
							}
						}

						ReflectionUtils.makeAccessible(field);
						ReflectionUtils.setField(field, object, listValue);
						continue;

					} else {
						fieldValue = convert(value, field.getType());

						if (fieldValue == null) {
							continue;
						}
					}

					// 设置field为可访问
					ReflectionUtils.makeAccessible(field);

					// 设置field的值
					ReflectionUtils.setField(field, object, fieldValue);
				}
			}
		}
		return object;
	}

	/**
	 * convert(根据给定的类型进行值的类型转换)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 * @Exception 异常对象
	 */
	@SuppressWarnings("rawtypes")
	private Object convert(Object value, Class targetType) {

		// 如果值为字符串类型
		if (value instanceof String) {
			if (value == null || StringUtils.equalsIgnoreCase((String) value, Constants.NULL)) {
				return null;
			}
			// 如果为日期类型，根据指定日期字符串转换
			if (targetType.equals(Date.class)) {
				return parseStringToDate((String) value);
			}
		}

		if (value instanceof Number && targetType.equals(BigDecimal.class)) {
			BigDecimal bd = new BigDecimal(String.valueOf(value));
			value = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
			bd = null;
		}

		Object returnVal = null;

		try {
			returnVal = StrictConvertUtils.getInstance().convert(value, targetType);
		} catch (Exception e) {
			throw new RuntimeException("转换错误");
		}

		// 如果目标转换类型为BigDecimal
		if (targetType.equals(BigDecimal.class)) {
			if (((BigDecimal) returnVal).compareTo(Constants.MAX_VALUE) >= 0) {
				throw new RuntimeException("转换错误");
			}
		}

		return returnVal;
	}

	/**
	 * parseStringToDate(按照定义的日期转换格式将字符串转换为日期)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	private Date parseStringToDate(String value) {
		if (isNullorEmpty(value)) {
			return null;
		}

		try {
			return DateUtils.parseDateStrictly(value, "yyyy-MM-dd", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
					"yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			throw new RuntimeException("日期转换错误");
		}
	}

	/**
	 * isNullorEmpty(验证字符串是否为空,当值为null,""," "及"null"等其中之一时，返回true)
	 * 
	 * @param name
	 *            参数描述
	 * @return name 返回值描述
	 */
	private boolean isNullorEmpty(String value) {
		if (StringUtils.isBlank(value) || StringUtils.equalsIgnoreCase(value, Constants.NULL)) {
			return true;
		}

		return false;
	}

}
