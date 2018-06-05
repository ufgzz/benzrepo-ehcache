package com.core.util;

import java.util.Map;

/**
 * 调用存储过程时需要用到的参数
 */
public class ExecParam {
	
	//希望得到的第几个返回集，必须大于0
	private int howMany;
	//是否有提示信息，0为没有返回提示信息，1为有返回提示信息
	private int hasReturnErrorCode;
	//是否分页，0表示不分页，1表示分页
	private int isPage;
	//call 存储过程，如：dbo.BL_HV_QueryCheckOutInfo(?,?,?,?,?,?,?,?)
    private String callString;
    //存储过程参数返回值定义 Map<字段名称,数据类型>
    private Map<String, Integer> outPutMap;
    
    public ExecParam() {
	
    }
    
    /**
     * @param howMany 希望得到的第几个返回集，必须大于0
     * @param hasReturnErrorCode 是否有提示信息，0为没有返回提示信息，1为有返回提示信息
     * @param isPage 是否分页，0表示不分页，1表示分页
     * @param callString 存储过程，如：dbo.BL_HV_QueryCheckOutInfo(?,?,?,?,?,?,?,?)
     * @param outPutMap 存储过程参数返回值定义 
     */
    public ExecParam(int howMany, int hasReturnErrorCode, int isPage, String callString, Map<String, Integer> outPutMap) {
    	this.howMany = howMany;
    	this.hasReturnErrorCode = hasReturnErrorCode;
    	this.isPage = isPage;
    	this.callString = callString;
    	this.outPutMap = outPutMap;
    }
    
    /**
     * 希望得到的第几个返回集，必须大于0
     * @return
     */
	public int getHowMany() {
		return howMany;
	}
	/**
	 * 希望得到的第几个返回集，必须大于0
	 * @param howMany
	 */
	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}
	/**
	 * 是否有提示信息，0为没有返回提示信息，1为有返回提示信息
	 * @return
	 */
	public int getHasReturnErrorCode() {
		return hasReturnErrorCode;
	}
	/**
	 * 是否有提示信息，0为没有返回提示信息，1为有返回提示信息
	 * @param hasReturnErrorCode
	 */
	public void setHasReturnErrorCode(int hasReturnErrorCode) {
		this.hasReturnErrorCode = hasReturnErrorCode;
	}
	/**
	 * isPage 是否分页，0表示不分页，1表示分页
	 * @return
	 */
	public int getIsPage() {
		return isPage;
	}
	/**
	 * isPage 是否分页，0表示不分页，1表示分页
	 * @param isPage
	 */
	public void setIsPage(int isPage) {
		this.isPage = isPage;
	}
	/**
	 * 存储过程，如：dbo.BL_HV_QueryCheckOutInfo(?,?,?,?,?,?,?,?)
	 * @return
	 */
	public String getCallString() {
		return callString;
	}
	/**
	 * 存储过程，如：dbo.BL_HV_QueryCheckOutInfo(?,?,?,?,?,?,?,?)
	 * @param callString
	 */
	public void setCallString(String callString) {
		this.callString = callString;
	}
	/**
	 * 存储过程参数返回值定义 
	 * @return
	 */
	public Map<String, Integer> getOutPutMap() {
		return outPutMap;
	}
	/**
	 * 存储过程参数返回值定义 
	 * @param outPutMap
	 */
	public void setOutPutMap(Map<String, Integer> outPutMap) {
		this.outPutMap = outPutMap;
	}
	
}
