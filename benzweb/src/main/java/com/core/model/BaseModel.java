package com.core.model;

import com.core.util.EVideoMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Map;

public abstract class BaseModel implements Serializable{
	protected static final long serialVersionUID = 1L;
	//获取Logger对象
	protected Logger log = Logger.getLogger(this.getClass());
	// 数据都存放在attrs
	private Map<String, Object> attrs = new EVideoMap<>();
	
	/**
	 * 存放数据
	 * @param key 键
	 * @param value 值
	 */
	public void put(String key, Object value) {
		this.attrs.put(key, value);
	}

	/**
	 * 获取Object类型数据
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {
		return attrs.get(key);
	}

	/**
	 * 获取String数据
	 * @param key 键
	 * @return 值
	 */
	public String getStr(String key) {
		return (String) attrs.get(key);
	}

	/**
	 * 获取int数据
	 * @param key 键
	 * @return 值
	 */
	public int getInt(String key) {
		return (int) attrs.get(key);
	}

	/**
	 * 获取int数据，不存在就使用默认值
	 * @param key attr的key
	 * @param defaultValue 默认值
	 * @return 返回int类型
	 */
	public int getInt(String key, int defaultValue) {
		String value = attrs.get(key) + "";
		int intValue = defaultValue;
		if (StringUtils.isNotEmpty(value)) {
			intValue = Integer.parseInt(value);
		}
		return intValue;
	}

	/**
	 * 获取double数据
	 * @param key attr的key
	 * @return 返回double类型
	 */
	public double getDouble(String key) {
		String value = this.getStr(key);
		return Double.parseDouble(value);
	}

	/**
	 * 获取double数据，不存在就使用默认值
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return 返回double类型
	 */
	public double getDouble(String key, int defaultValue) {
		String value = this.getStr(key);
		double doubleValue = defaultValue;
		if (StringUtils.isNotEmpty(value)) {
			doubleValue = Double.parseDouble(value);
		}
		return doubleValue;
	}


	/**
	 * 设置SN
	 * @param
	 */
	public void setSN(String SN) {
		attrs.put("SN", SN);
	}

	/**
	 * 获取SN
	 * @return 获得SN的值
	 */
	public String getSN() {
		return this.getStr("SN");
	}
	
	/**
	 * 设置RequestID
	 * @param
	 */
	public void setRequestID(String requestID) {
		attrs.put("RequestID", requestID);
	}

	/**
	 * 获取RequestID
	 * @return 获得RequestID的值
	 */
	public String getRequestID() {
		return this.getStr("RequestID");
	}

	/**
	 * 获取attrs
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getAttrs() {
		return attrs;
	}

	/**
	 * 设置attrs
	 * @param attrs Map<String, Object>
	 */
	public void setAttrs(Map<String, Object> attrs) {
		this.attrs = attrs;
	}
}
