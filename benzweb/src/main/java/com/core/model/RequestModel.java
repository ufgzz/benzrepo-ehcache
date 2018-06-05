package com.core.model;

import com.core.util.EVideoMap;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class RequestModel extends BaseModel{

	private static final long serialVersionUID = 1L;

	/**
	 * attrs实例化
	 */
	public RequestModel() {
	    this.setSN("-1");
	    this.setRequestID("-1");
	}

	/**
	 * 设置数据
	 * @param attrs
	 */
	public RequestModel(Map<String, Object> attrs) {
		this.getAttrs().putAll(attrs);
	}

	/**
	 * 获取RequestInfo信息并转成Model对象
	 * @return 返回ResponseInfo数据Model类型
	 */
	@SuppressWarnings("unchecked")
	public RequestModel getRequestInfoModel() {
		return new RequestModel((Map<String, Object>) getAttrs().get("RequestInfo"));
	}

	/**
	 * 获取RequestInfo信息 Map<String,Object>数据
	 * @return 返回RequestInfo数据
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getRequestInfoMap() {
		try {
			Map<String, Object> requestInfo = (Map<String, Object>) getAttrs().get("RequestInfo");
			if(requestInfo instanceof EVideoMap){
				return requestInfo;
			}else{
				Map<String,Object> newRequestInfo = new EVideoMap<>();
				//newRequestInfo.putAll(requestInfo);
				getAttrs().put("RequestInfo",newRequestInfo);
				return (Map<String, Object>) getAttrs().get("RequestInfo");
			}
		}catch (Exception e){
			log.error("RequestInfo转换成Map<String, Object>类型出错，RequestInfo内容:"+getAttrs().get("RequestInfo"));
			return null;
		}
	}

	/**
	 * 设置数据类型为Map<String, Object>RequestInfo
	 * @param requestInfo 返回RequstInfo
	 */
	public void setRequestInfo(Map<String, Object> requestInfo) {
		this.getAttrs().put("RequestInfo", requestInfo);
	}
	/**
	 * 存放数据
	 * @param key 键
	 * @param value 值
	 */
	public void putReq(String key, Object value) {
		this.getRequestInfoMap().put(key, value);
	}

	/**
	 * 获取Object类型数据
	 * @param key 键
	 * @return 值
	 */
	public Object getReq(String key) {
		return this.getRequestInfoMap().get(key);
	}

	/**
	 * 获取String数据
	 * @param key 键
	 * @return 值
	 */
	public String getReqStr(String key) {
		return (String) this.getRequestInfoMap().get(key);
	}
	
	/**
	 * 获取String数据，不存在就使用默认值
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return 值
	 */
	public String getReqStr(String key, String defaultValue) {
		String value = (String) this.getRequestInfoMap().get(key);
		if ((null == value) || ("null".equals(value))) {
			value = defaultValue;
		}
		return value; 
	}
	

	/**
	 * 获取int数据
	 * @param key 键
	 * @return 值
	 */
	public int getReqInt(String key) {
		String value = this.getReqStr(key);
		return Integer.parseInt(value);
	}

	/**
	 * 获取int数据，不存在就使用默认值
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return 返回int类型
	 */
	public int getReqInt(String key, int defaultValue) {
		String value = this.getReqStr(key);
		int intValue = defaultValue;
		if (StringUtils.isNotEmpty(value) && (!"null".equals(value))) {
			intValue = Integer.parseInt(value);
		}
		return intValue;
	}

	/**
	 * 获取Model对象
	 * @param key 键
	 * @return 返回Model类型
	 */
	public RequestModel getReqModel(String key) {
		return (RequestModel) this.getRequestInfoMap().get(key);
	}

	/**
	 * 获取double数据
	 * @param key 键
	 * @return 返回double类型
	 */
	public double getReqDouble(String key) {
		String value = this.getReqStr(key);
		return Double.parseDouble(value);
	}

	/**
	 * 获取double数据，不存在就使用默认值
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return 返回double类型
	 */
	public double getReqDouble(String key, double defaultValue) {
		String value = this.getReqStr(key);
		double doubleValue = defaultValue;
		if (StringUtils.isNotEmpty(value) && (!"null".equals(value))) {
			doubleValue = Double.parseDouble(value);
		}
		return doubleValue;
	}

	/**
	 * 获取MachineIP
	 * @return 返回MachineIP
	 */
	public String getMachineIp() {
		return (String) getAttrs().get("MachineIP");
	}

	/**
	 * 获取AppTag
	 * @return 返回AppTag的值
	 */
	public String getAppTag() {
		return (String) getAttrs().get("AppTag");
	}
	
	/**
	 * 在RequestInfo获取是否分页，0不分页，1分页
	 * @return 返回是否分页的值
	 */
	public int getReqIsPage() {
		return this.getReqInt("IsPage",0);
	}

	/**
	 * 在RequestInfo获取页码，默认1
	 * @return 返回页码
	 */
	public int getReqPageNo() {
		return this.getReqInt("PageNo",1);
	}

	/**
	 * 在RequestInfo获取页码数量，默认10
	 * @return 返回每页 数量
	 */
	public int getReqPageSize() {
		return this.getReqInt("PageSize",10);
	}
	
}
