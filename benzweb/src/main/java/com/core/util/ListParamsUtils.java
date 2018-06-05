package com.core.util;

import com.core.model.RequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设置执行sql或存储过程需要的参数
 * @author zhangchuanzhao
 * 2015-10-29 下午4:46:38
 */
public class ListParamsUtils {
	
	/**
	 * 设置参数
	 * @param objs 参数列表
	 * @return 返回List数据
	 */
	public static List<Object> setParams(Object...objs){
		List<Object> params = new ArrayList<Object>();
		if(null != objs){
			for (int i = 0; i < objs.length; i++) {
				params.add(objs[i]);
			}
		}
		return params;
	}
	
	/**
	 * 设置有返回代码的参数
	 * @param objs 参数列表
	 * @return 返回List数据
	 */
	public static List<Object> setParamsReturnCode(Object...objs){
		List<Object> params = new ArrayList<Object>();
		if(null != objs){
			for (int i = 0; i < objs.length; i++) {
				params.add(objs[i]);
			}
		}
		//ErrorCode
		params.add("-1");
		//ErrorMessage
		params.add("");
		//ExceptMessage
		params.add("");
		return params;
	}
	
	/**
	 * 设置需要基础参数
	 * @param model 请求的数据对象
	 * @param objs 参数列表
	 * @return 返回List数据
	 */
	public static List<Object> setRequestObjectToList(RequestModel model, Object...objs){
		List<Object> params = setBaseRequestToList(model);
		if(null != objs){
			for (int i = 0; i < objs.length; i++) {
				params.add(objs[i]);
			}
		}
		return params;
	}
	
	/**
	 * 设置需要基础参数
	 * @param model 请求的数据对象
	 * @param objs 参数列表
	 * @return 返回List数据
	 */
	public static List<Object> setRequestModelToList(RequestModel model,String...objs){
		List<Object> params = setBaseRequestToList(model);
		if(null != objs){
			for (int i = 0; i < objs.length; i++) {
				params.add(model.get(objs[i]));
			}
		}
		return params;
	}
	
	/**
	 * 设置需要基础参数
	 * @param model 请求的数据对象
	 * @param objs 参数列表
	 * @return 返回List数据
	 */
	public static List<Object> setRequestInfoToList(RequestModel model,String... objs){
		List<Object> params = setBaseRequestToList(model);
		if(null != objs){
			Map<String, Object> requestInfo = model.getRequestInfoMap();
			for (int i = 0; i < objs.length; i++) {
				params.add(requestInfo.get(objs[i]));
			}
		}
		return params;
	}
	
	public static List<Object> setBaseRequestToList(RequestModel model){
		List<Object> params = new ArrayList<Object>();
		params.add(model.get("Version"));
		params.add(model.get("DeviceID"));
		params.add(model.get("ChannelID"));
		params.add(model.get("UserID"));
		params.add(model.get("SoftWareID"));
		params.add(model.get("DeviceType"));
		return params;
	}
	
	
	/**
	 * 设置需要分页的参数
	 * @param model 请求的数据对象
	 * @param objs 参数列表
	 * @return 返回List数据
	 */
	public static List<Object> setParamsPage(RequestModel model,Object...objs){
		List<Object> params = new ArrayList<Object>();
		if(null != objs){
			for (int i = 0; i < objs.length; i++) {
				params.add(objs[i]);
			}
		}
		//IsPage
		params.add(model.getReqIsPage());
		//PageNo
		params.add(model.getReqPageNo());
		//PageSize
		params.add(model.getReqPageSize());
		//RecordCount
		params.add("0");
		//PageTotal
		params.add("0");
		return params;
	}
	
	/**
	 * 设置需要分页并且有返回代码的参数
	 * @param model 请求的数据对象
	 * @param objs 参数列表
	 * @return 返回List数据
	 */
	public static List<Object> setParamsPageReturnCode(RequestModel model,Object...objs){
		List<Object> params = new ArrayList<Object>();
		if(null != objs){
			for (int i = 0; i < objs.length; i++) {
				params.add(objs[i]);
			}
		}
		//IsPage
		params.add(model.getReqIsPage());
		//PageNo
		params.add(model.getReqPageNo());
		//PageSize
		params.add(model.getReqPageSize());
		//RecordCount
		params.add("0");
		//PageTotal
		params.add("0");
		//ErrorCode
		params.add("-1");
		//ErrorMessage
		params.add("");
		//ExceptMessage
		params.add("");
		return params;
	}

}
