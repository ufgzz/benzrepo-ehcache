package com.core.model;

import com.core.util.EVideoMap;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseModel extends BaseModel{

	private static final long serialVersionUID = 1L;

	/**
	 * attrs实例化
	 */
	public ResponseModel() {
	    this.setSN("-1");
	    this.setRequestID("-1");
		this.setErrorCode("-1");
		this.setErrorMsg("");
		Map<String,Object> responseParams = new EVideoMap<>();
		this.getAttrs().put("ResponseParams", responseParams);
	}

	/**
	 * 设置数据
	 * @param attrs
	 */
	public ResponseModel(Map<String, Object> attrs) {
		this.getAttrs().putAll(attrs);
		this.setErrorCode("-1");
		this.setErrorMsg("");
	}

	/**
	 * 设置响应数据协议头
	 * @param errorCode 错误号：0 成功 ，其他表示失败
	 */
	public ResponseModel(String errorCode) {
		this.setSN("-1");
	    this.setRequestID("-1");
		this.setErrorCode(errorCode);
		this.setErrorMsg("");
		Map<String,Object> responseParams = new EVideoMap<>();
		this.getAttrs().put("ResponseParams", responseParams);
	}

	/**
	 * 设置响应数据协议头
	 * @param errorCode 错误号：0 成功 ，其他表示失败
	 * @param errorMsg 提示错误信息
	 */
	public ResponseModel(String errorCode,String errorMsg) {
		this.setSN("-1");
	    this.setRequestID("-1");
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
		Map<String,Object> responseParams = new HashMap<String,Object>();
		this.getAttrs().put("ResponseParams",responseParams);
	}

	/**
	 * 设置响应数据协议头
	 * @param errorCode 错误号：0 成功 ，其他表示失败
	 * @param errorMsg 提示错误信息
	 * @param exceptMessage 异常错误信息
	 */
	public ResponseModel(String errorCode,String errorMsg,String exceptMessage) {
		this.setSN("-1");
	    this.setRequestID("-1");
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
//		this.setExceptMessage(exceptMessage);
		Map<String,Object> responseParams = new EVideoMap<>();
		this.getAttrs().put("ResponseParams",responseParams);
	}

	/**
	 * 设置数据类型为String的responseparams
	 * @param responseInfo 需要设置ResponseInfo的数据，Error时，responseparams=""
	 */
	public void setResponseParams(String responseInfo) {
		this.getAttrs().put("ResponseParams", responseInfo);
	}
	
	/**
	 * 设置数据类型为Map<String, Object>的ResponseInfo
	 * @param responseInfo 需要设置ResponseInfo的数据
	 */
	public void setResponseParams(Map<String, Object> responseInfo) {
		this.getAttrs().put("ResponseParams", responseInfo);
	}

	/**
	 * 设置数据类型为List<Map<String, Object>>的ResponseInfo
	 * @param responseInfo 需要设置ResponseInfo的数据
	 */
	public void setResponseParams(List<Map<String, Object>> responseInfo) {
		this.getAttrs().put("ResponseParams", responseInfo);
	}
	public void setResponseExtra(List<Map<String, Object>> responseInfo) {
		this.getAttrs().put("ResponseExtra", responseInfo);
	}

	/**
	 * 获取数据类型为List<Map<String, Object>>的ResponseParams
	 * @return 返回ResponseInfo数据
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getResponseParamsList() {
		Object responseInfoObj = this.getAttrs().get("ResponseParams");
		if(responseInfoObj != null){
			try {
				List<Map<String, Object>> responseInfoList = (List<Map<String, Object>>) this.getAttrs().get("ResponseParams");
				return responseInfoList;
			}catch (Exception e){
				String responseInfoContent = responseInfoObj + "";
				if(responseInfoContent.length() > 200){
					responseInfoContent = responseInfoContent.substring(0,200)+"...";
				}
				log.error("ResponseInfo转换成List<Map<String, Object>>类型出错，ResponseInfo内容:"+responseInfoContent);
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 * 获取数据类型为Map<String, Object>的ResponseParam
	 * @return 返回ResponseInfo数据
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getResponseParamsMap() {
		try {
			Map<String, Object> responseInfo = (Map<String, Object>) this.getAttrs().get("ResponseParams");
			if(responseInfo instanceof EVideoMap){
				return responseInfo;
			}else{
				Map<String,Object> newResponseInfo = new EVideoMap<>();
				newResponseInfo.putAll(responseInfo);
				this.getAttrs().put("ResponseParams",newResponseInfo);
				return (Map<String, Object>) this.getAttrs().get("ResponseParams");
			}
		}catch (Exception e){
			String responseInfoContent = this.getAttrs().get("ResponseParams") + "";
			if(responseInfoContent.length() > 200){
				responseInfoContent = responseInfoContent.substring(0,200)+"...";
			}
			log.error("ResponseInfo转换成Map<String, Object>类型出错，ResponseInfo内容:"+responseInfoContent);
			return null;
		}
	}

	/**
	 * 设置OutMap，调用存储过程时，返回参数
	 * @param returnMap 设置ResponseInfo中的ReturnMap的值
	 */
	public void setReturnMap(Map<String, Object> returnMap) {
		if(returnMap != null){
			if(returnMap.size() == 0){
				returnMap = null;
			}
		}
		this.putResp("returnmap", returnMap);
	}

	/**
	 * 获取OutMap，调用存储过程时，返回参数
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getReturnMap() {
		Object object = this.getResponseParamsMap().get("returnmap");
		if(object != null){
			try {
				Map<String, Object> returnMap = (Map<String, Object>) this.getResp("returnmap");
				Map<String,Object> newReturnMap = new EVideoMap<>();
				newReturnMap.putAll(returnMap);
				this.getResponseParamsMap().put("returnmap",newReturnMap);
				return (Map<String, Object>) this.getResp("returnmap");
			}catch (Exception e){
				log.error("ReturnMap转换成Map<String, Object>类型出错，ReturnMap内容:"+this.getAttrs().get("returnmap"));
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 设置ErrorCode
	 * @param errorCode ErrorCode的值
	 */
	public void setErrorCode(String errorCode) {
		this.getAttrs().put("ErrorCode", errorCode);
	}

	/**
	 * 获取ErrorCode
	 * @return 获得ErrorCode的值
	 */
	public String getErrorCode() {
		return this.getStr("ErrorCode");
	}

	/**
	 * 设置ErrorMsg
	 * @param errorMsg
	 */
	public void setErrorMsg(String errorMsg) {
		this.getAttrs().put("ErrorMsg", errorMsg);
	}

	/**
	 * 获取ErrorMessage
	 * @return 获得ErrorMessage的值
	 */
	public String getErrorMsg() {
		return this.getStr("ErrorMsg");
	}

	/**
	 * 设置ExceptMessage
	 * @param exceptMessage ErrorMessage的值
	 */
	public void setExceptMessage(String exceptMessage) {
		this.getAttrs().put("ExceptMessage", exceptMessage);
	}

	/**
	 * 获取ExceptMessage
	 * @return 获得ErrorMessage的值
	 */
	public String getExceptMessage() {
		return this.getStr("ExceptMessage");
	}
	
	/**
	 * 设置RecordCount，分页时使用，数据总条数
	 * @param recordCount 数据条数
	 */
	public void setRecordCount(String recordCount) {
		this.putResp("RecordCount", recordCount);
	}
	
	/**
	 * 获取RecordCount，分页时使用，数据总条数
	 * @return 数据条数
	 */
	public int getRecordCount() {
		return this.getRespInt("RecordCount");
	}

	/**
	 * 设置PageTotal，分页时使用，总页数
	 * @param pageTotal 页数
	 */
	public void setPageTotal(String pageTotal) {
		this.putResp("PageTotal", pageTotal);
	}
	
	/**
	 * 获取PageTotal，分页时使用，总页数
	 * @return 总页数
	 */
	public int getPageTotal() {
		return this.getRespInt("PageTotal");
	}

	/**
	 * 设置Data数据列表
	 * @param data ResponseInfo中的Data的值
	 */
	public void setData(List<Map<String, Object>> data) {
		this.putResp("Data", data);
	}

	/**
	 * 存放数据
	 * @param key 键
	 * @param value 值
	 */
	public void putResp(String key, Object value) {
		this.getResponseParamsMap().put(key, value);
	}

	/**
	 * 获取Object类型数据
	 * @param key 键
	 * @return 值
	 */
	public Object getResp(String key) {
		return this.getResponseParamsMap().get(key);
	}

	/**
	 * 获取String数据
	 * @param key 键
	 * @return 值
	 */
	public String getRespStr(String key) {
		return (String) this.getResponseParamsMap().get(key);
	}
	
	public String getRespStr(String key,String defaultValue) {
		String value = (String) this.getResponseParamsMap().get(key);
		if(value == null){
			value = defaultValue;
		}
		return value;
	}

	/**
	 * 获取int数据
	 * @param key 键
	 * @return 返回int类型
	 */
	public int getRespInt(String key) {
		String value = this.getRespStr(key);
		return Integer.parseInt(value);
	}

	/**
	 * 获取int数据，不存在就使用默认值
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return 返回int类型
	 */
	public int getRespInt(String key, int defaultValue) {
		String value = this.getRespStr(key);
		int intValue = defaultValue;
		if (StringUtils.isNotEmpty(value) && (!"null".equals(value))) {
			intValue = Integer.parseInt(value);
		}
		return intValue;
	}

	/**
	 * 获取Model对象
	 * @param key 键
	 * @return 返回Model类型对象
	 */
	public ResponseModel getRespModel(String key) {
		return (ResponseModel) this.getResponseParamsMap().get(key);
	}

	/**
	 * 获取double数据
	 * @param key 键
	 * @return 返回double类型
	 */
	public double getRespDouble(String key) {
		String value = this.getRespStr(key);
		return Double.parseDouble(value);
	}

	/**
	 * 获取double数据，不存在就使用默认值
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return 返回double类型
	 */
	public double getRespDouble(String key, double defaultValue) {
		String value = this.getRespStr(key);
		double doubleValue = defaultValue;
		if (StringUtils.isNotEmpty(value) && (!"null".equals(value))) {
			doubleValue = Double.parseDouble(value);
		}
		return doubleValue;
	}

	/**
	 * 在ResponseInfo设置是否分页，0不分页，1分页
	 */
	public void setRespIsPage(int isPage) {
		this.putResp("IsPage",String.valueOf(isPage));
	}
	
	/**
	 * 是否分页，0不分页，1分页
	 * @return 返回是否分页
	 */
	public int getRespIsPage() {
		return this.getRespInt("IsPage");
	}

	/**
	 * 在ResponseInfo设置页码，默认1
	 * @param pageNo 返回页码
	 */
	public void setRespPageNo(int pageNo) {
		this.putResp("PageNo",String.valueOf(pageNo));
	}
	
	/**
	 * 获取ResponseInfo设置页码，默认1
	 * @return 返回页码
	 */
	public int getRespPageNo() {
		return this.getRespInt("PageNo",1);
	}

	/**
	 * 在ResponseInfo设置页码数量，默认10
	 * @param pageSize 返回每页数量
	 */
	public void setRespPageSize(int pageSize) {
		this.putResp("PageSize",String.valueOf(pageSize));
	}
	
	/**
	 * 获取ResponseInfo设置页码数量，默认10
	 * @return 返回每页数量
	 */
	public int getRespPageSize() {
		return this.getRespInt("PageSize",10);
	}

}
