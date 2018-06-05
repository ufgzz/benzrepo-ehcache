package com.core.repository;


import com.core.model.ResponseModel;
import com.core.util.ConnectionPool;
import com.core.util.EVideoMap;
import com.core.util.ExecParam;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JdbcModelTemplate {
	protected Logger log = Logger.getLogger(JdbcModelTemplate.class);

	/**
	 * 通用SQL更新操作，insert update delete
	 * @param sql 更新SQL语句，如果语句中有参数使用?
	 * @param params SQL语句中“?”对应的值
	 * @return 影响的行数:-1表示操作出错
	 */
	public int commonUpdate(String sql, List<Object> params){
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = ConnectionPool.getConnection();
			if(conn != null){
				pst = conn.prepareStatement(sql);
				if(params != null){
					for (int i = 0; i < params.size(); i++) {
						pst.setObject(i+1, params.get(i));
					}
				}
				return pst.executeUpdate();
			}else{
				log.error("数据库连接失败");
				return -1;
			}
		} catch (SQLException e) {
			log.error("方法：【this.getJdbcModelTemplate().commonUpdate】执行sql出错，sql:【"+sql+"】,参数：【"+params+"】",e);
			e.printStackTrace();
			//事务回滚
			this.rollback(conn);
			return -1;
		} finally {
			//释放资源
			ConnectionPool.release(conn, pst, null);
		}
	}

	/**
	 * SQL获取一条记录并且转成Map类型
	 * @param sql 查询SQL语句
	 * @param params SQL语句中的“?”对应的值
	 * @return 返回Map
	 */
	public ResponseModel queryForMap(String sql, List<Object> params){
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResponseModel responseModel = new ResponseModel();
		try {
			conn = ConnectionPool.getConnection();
			if(conn != null){
				pst = conn.prepareStatement(sql);
				if(params != null){
					for (int i = 0; i < params.size(); i++) {
						pst.setObject(i+1, params.get(i));
					}
				}
				rs = pst.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int colcount = rsmd.getColumnCount();
				Map<String, Object> map = new EVideoMap<>();
				if(rs.next()){
					// rs-->map
					for (int i = 1; i <= colcount; i++) {
						String colname = rsmd.getColumnName(i);
						String value = rs.getString(i);
						value = value == null ? "" : value;
						//不存在该value的时候才存放记录
					    if(!map.containsKey(colname)){
					    	map.put(colname, value);
					    }
					}
				}
				responseModel.setResponseParams(map);
				responseModel.setErrorCode("0");
			}else{
				responseModel.setErrorCode("-1");
				responseModel.setErrorMsg("数据库连接失败");
				log.error("数据库连接失败");
			}
		} catch (SQLException e) {
			responseModel.setErrorCode("-1");
			responseModel.setErrorMsg("执行SQL失败");
			//responseModel.setExceptMessage(e.toString());
			log.error("方法：【queryForMap】执行sql出错，sql:【"+sql+"】,参数：【"+params+"】",e);
			e.printStackTrace();
			//事务回滚
			this.rollback(conn);
		} finally {
			//释放资源
			ConnectionPool.release(conn, pst, rs);
		}
		return responseModel;
	}
	
	/**
	 * SQL获取列表并且转成Map的List类型
	 * @param sql 查询SQL语句
	 * @param params SQL语句中的“？”对应的值
	 * @return 返回Map
	 */
	public ResponseModel queryForListMap(String sql,List<Object> params){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResponseModel responseModel = new ResponseModel();
		try {
			conn = ConnectionPool.getConnection();
			if(conn != null){
				pst = conn.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.size(); i++) {
						pst.setObject(i + 1, params.get(i));
					}
				}
				rs = pst.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int colcount = rsmd.getColumnCount();
				while (rs.next()) {
					Map<String, Object> map = new EVideoMap<>();
					// rs-->map
					for (int i = 1; i <= colcount; i++) {
						String colname = rsmd.getColumnName(i);
						String value = rs.getString(i);
						value = value == null ? "" : value;
						//不存在该value的时候才存放记录
					    if(!map.containsKey(colname)){
					    	map.put(colname, value);
					    }
					}
					resultList.add(map);
				}
				responseModel.setResponseParams(resultList);
				responseModel.setErrorCode("0");
			}else{
				responseModel.setErrorCode("-1");
				responseModel.setErrorMsg("数据库连接失败");
				log.error("数据库连接失败");
			}
		} catch (Exception e) {
			responseModel.setErrorCode("-1");
			responseModel.setErrorMsg("执行SQL失败");
			//responseModel.setExceptMessage(e.toString());
			log.error("方法：【queryForListMap】执行sql出错，sql:【"+sql+"】,参数：【"+params+"】",e);
			e.printStackTrace();
			//事务回滚
			this.rollback(conn);
		} finally {
			//释放资源
			ConnectionPool.release(conn, pst, rs);
		}
		return responseModel;
	}

	/**
	 * 返回列表记录多结果集的存储过程(同时有list和output的时候，返回map)
	 * 该方法包括查询前面有执行更新、增加、删除操作，最后返回一个结果集的情况
	 * @param execParam 设置参数
	 * execParam包含以下参数：
	 * howMany 希望得到的第几个返回集，必须大于0
	 * hasReturnErrorCode是否有提示信息，0为没有返回提示信息，1为有返回提示信息
	 * isPage是否分页，0表示不分页，1表示分页
	 * callString 存储过程，如：dbo.BL_HV_QueryCheckOutInfo(?,?,?,?,?,?,?,?)
     * outPutMap存储过程参数返回值定义 Map<字段名称,数据类型>
	 * @param params 存储过程中用到的参数集
	 * @return 结果集
	 */
	@SuppressWarnings("resource")
	public ResponseModel spMoreResultSetList(ExecParam execParam, List<Object> params){
		ResponseModel responseModel = new ResponseModel();
		//先不考虑分页
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		Connection conn = ConnectionPool.getConnection();
		if(conn != null){
			CallableStatement cst = null;
			ResultSet rs = null;
			try {
				cst = conn.prepareCall("{call "+execParam.getCallString()+"}");
				//设置存储过程需要的参数
				spSetParams(cst, execParam, params);

				cst.execute();
				rs = cst.getResultSet();
				//更新的数据条数，更新操作的时候，该值大于等于0，有结果集或者没有更新并且没有结果集的时候，该值为-1，
				int updateCount = cst.getUpdateCount();
				//计算遍历过有返回集的个数
				int hasResultSetNum = 0;
				//只有当有更新操作或者有返回结果集的时候，才循环
				while((updateCount != -1) || (updateCount == -1 && rs != null)){
		            if(rs != null){
		            	hasResultSetNum ++;
		            	if(hasResultSetNum == execParam.getHowMany()){
		            		ResultSetMetaData rsmd = rs.getMetaData();
			    			int colcount = rsmd.getColumnCount();
			    			while (rs.next()) {
			    				Map<String, Object> rsMap = new EVideoMap<>();
			    				// rs-->map
			    				for (int i = 1; i <= colcount; i++) {
			    					String colname = rsmd.getColumnName(i);
			    					String value = rs.getString(i);
								    value = value == null ? "" : value;
								    //不存在该value的时候才存放记录
								    if(!rsMap.containsKey(colname)){
								    	rsMap.put(colname, value);
								    }
			    				}
			    				resultList.add(rsMap);
			    			}
			    			break;
		            	}
		            }
		            cst.getMoreResults();
	            	updateCount = cst.getUpdateCount();
	            	rs = cst.getResultSet();
				}
				
			    responseModel.setResponseParams(resultList);
				
				//获取存储过程返回的参数
	            spGetParams(cst, execParam, responseModel, 3);
			}catch (Exception e) {
				responseModel.setErrorCode("-1");
				responseModel.setErrorMsg("执行存储过程失败");
				//responseModel.setExceptMessage(e.toString());
				log.error("方法：【spMoreResultSetList】执行存储过程出错，" +
						"存储过程:【"+execParam.getCallString()+"】,参数：【"+params+"】",e);
				e.printStackTrace();
				//事务回滚
				this.rollback(conn);
			} finally {
				ConnectionPool.release(conn, cst, rs);
			}
		}else{
			responseModel.setErrorCode("-1");
			responseModel.setErrorMsg("数据库连接失败");
			log.error("数据库连接失败");
		}
		return responseModel;
	}

	/**
	 * 返回单条记录的结果集的存储过程 和 output的
	 * 该方法包括查询前面有执行更新、增加、删除操作，最后返回一条记录一个结果集的情况
	 * @param execParam 设置参数
	 * execParam包含以下参数：
	 * howMany 希望得到的第几个返回集，必须大于0
	 * hasReturnErrorCode是否有提示信息，0为没有返回提示信息，1为有返回提示信息
	 * isPage是否分页，0表示不分页，1表示分页
	 * callString 存储过程，如：dbo.BL_HV_QueryCheckOutInfo(?,?,?,?,?,?,?,?)
     * outPutMap存储过程参数返回值定义 Map<字段名称,数据类型>
	 * @param params 存储过程中用到的参数集
	 * @return 结果集
	 */
	@SuppressWarnings("resource")
	public ResponseModel spMoreResultSetMap(ExecParam execParam, List<Object> params){
		
		ResponseModel responseModel = new ResponseModel();
		Map<String, Object> responseInfo = new EVideoMap<>();
		
		Connection conn = ConnectionPool.getConnection();

		if(conn != null){
			CallableStatement cst = null;
			ResultSet rs = null;
			try {
				cst = conn.prepareCall("{call "+execParam.getCallString()+"}");
				
				//设置存储过程需要的参数
				spSetParams(cst, execParam, params);
				
				cst.execute();
				rs = cst.getResultSet();
				//更新的数据条数，更新操作的时候，该值大于等于0，有结果集或者没有更新并且没有结果集的时候，该值为-1，
				int updateCount = cst.getUpdateCount();
				//计算遍历过有返回集的个数
				int hasResultSetNum = 0;
				//只有当有更新操作或者有返回结果集的时候，才循环
				while((updateCount != -1) || (updateCount == -1 && rs != null)){
					if(rs != null){
		            	hasResultSetNum ++;
		            	if(hasResultSetNum == execParam.getHowMany()){
		            		ResultSetMetaData rsmd = rs.getMetaData();
			    			int colcount = rsmd.getColumnCount();
			    			if(rs.next()){
			    				// rs-->map
			    				for (int i = 1; i <= colcount; i++) {
			    					String colname = rsmd.getColumnName(i);
			    					String value = rs.getString(i);
								    value = value == null ? "" : value;
								    //不存在该value的时候才存放记录
								    if(!responseInfo.containsKey(colname)){
								    	responseInfo.put(colname, value);
								    }
			    				}
			    			}
		            	}
		            }
		            cst.getMoreResults();
	            	updateCount = cst.getUpdateCount();
	            	rs = cst.getResultSet();
				}
				responseModel.setResponseParams(responseInfo);
			
				//获取存储过程返回的参数
	            spGetParams(cst, execParam, responseModel, 1);
			
			}catch (Exception e) {
				responseModel.setErrorCode("-1");
				responseModel.setErrorMsg("执行存储过程失败");
				//responseModel.setExceptMessage(e.toString());
				log.error("方法：【spMoreResultSetMap】执行存储过程出错，" +
						"存储过程:【"+execParam.getCallString()+"】,参数：【"+params+"】",e);
				e.printStackTrace();
				//事务回滚
				this.rollback(conn);
			} finally {
				ConnectionPool.release(conn, cst, rs);
			}
		}else{
			responseModel.setErrorCode("-1");
			responseModel.setErrorMsg("数据库连接失败");
			log.error("数据库连接失败");
		}
		
		
		return responseModel;
	}


	/**
	 * 返回所有结果集的多结果集的的存储过程
	 * 返回所有结果集的情况
	 * @param execParam 设置参数
	 * execParam包含以下参数：
	 * howMany 希望得到的第几个返回集，必须大于0
	 * hasReturnErrorCode是否有提示信息，0为没有返回提示信息，1为有返回提示信息
	 * isPage是否分页，0表示不分页，1表示分页
	 * callString 存储过程，如：dbo.BL_HV_QueryCheckOutInfo(?,?,?,?,?,?,?,?)
     * outPutMap存储过程参数返回值定义 Map<字段名称,数据类型>
	 * @param params 存储过程中用到的参数集
	 * @return 结果集
	 */
	@SuppressWarnings("resource")
	public ResponseModel spGetAllMoreResultSetMap(ExecParam execParam,List<Object> params) {
		ResponseModel responseModel = new ResponseModel();
		Map<String, Object> responseInfo = new EVideoMap<>();
		Connection conn = ConnectionPool.getConnection();
		if(conn != null){
			CallableStatement cst = null;
			ResultSet rs = null;
			try {
				cst = conn.prepareCall("{call "+execParam.getCallString()+"}");
				//设置存储过程需要的参数
				spSetParams(cst, execParam, params);
				cst.execute();
				rs = cst.getResultSet();
				//更新的数据条数，更新操作的时候，该值大于等于0，有结果集或者没有更新并且没有结果集的时候，该值为-1，
				int updateCount = cst.getUpdateCount();
	            int j = 0;
				//只有当有更新操作或者有返回结果集的时候，才循环
				while((updateCount != -1) || (updateCount == -1 && rs != null)){
	            	if(rs != null){
	            		j ++;
	                	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	                	ResultSetMetaData rsmd = rs.getMetaData();
	        			int colcount = rsmd.getColumnCount();
	        			while(rs.next()){
	        				Map<String, Object> rsMap = new EVideoMap<>();
	        				// rs-->map
	        				for (int i = 1; i <= colcount; i++) {
	        					String colname = rsmd.getColumnName(i);
	        					String value = rs.getString(i);
						        value = value == null ? "" : value;
	        					//不存在该value的时候才存放记录
							    if(!rsMap.containsKey(colname)){
							    	rsMap.put(colname, value);
							    }
	        				}
	        				list.add(rsMap);
	        			}
	        			responseInfo.put("data_"+j, list);
	            	}

	            	cst.getMoreResults();
	            	updateCount = cst.getUpdateCount();
	            	rs = cst.getResultSet();
	            }
				responseModel.setResponseParams(responseInfo);
				//获取存储过程返回的参数
	            spGetParams(cst, execParam, responseModel, 2);
			}catch (Exception e) {
				responseModel.setErrorCode("-1");
				responseModel.setErrorMsg("执行存储过程失败");
				//responseModel.setExceptMessage(e.toString());
				log.error("方法：【spGetAllMoreResultSetMap】执行存储过程出错，" +
						"存储过程:【"+execParam.getCallString()+"】,参数：【"+params+"】",e);
				e.printStackTrace();
				//事务回滚
				this.rollback(conn);
			} finally {
				ConnectionPool.release(conn, cst, rs);
			}
		}else{
			responseModel.setErrorCode("-1");
			responseModel.setErrorMsg("数据库连接失败");
			log.error("数据库连接失败");
		}
		return responseModel;
	}

	/**
	 * 设置存储过程需要的参数
	 * @param cst
	 * @param execParam
	 * @param params
	 * @throws SQLException
	 */
	private void spSetParams(CallableStatement cst,ExecParam execParam,List<Object> params) throws SQLException{
		int i = 0;
		if(params != null){
			for (i = 0; i < params.size(); i++) {
				cst.setObject(i+1, params.get(i));
			}
		}
		//注册自定义返回参数
		Map<String, Integer> outPutMap = execParam.getOutPutMap();
		if (null != outPutMap) {
			Iterator<Map.Entry<String, Integer>> entries = outPutMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, Integer> entry = entries.next();
				cst.registerOutParameter(entry.getKey(), entry.getValue());
			}
		}

		//hasReturnErrorCode==1时，才有返回提示信息
		if(execParam.getHasReturnErrorCode() == 1){
			//注册返回参数
			cst.registerOutParameter("ErrorCode", Types.INTEGER);
			cst.registerOutParameter("ErrorMessage", Types.VARCHAR);
			cst.registerOutParameter("ExceptMessage", Types.VARCHAR);
		}
		//isPage==1时，才有分页，返回分页信息
		if(execParam.getIsPage() == 1){
			//注册返回参数
			cst.registerOutParameter("RecordCount", Types.INTEGER);
			cst.registerOutParameter("PageTotal", Types.INTEGER);
		}
	}

	/**
	 * 获取存储过程返回的参数
	 * @param cst
	 * @param execParam
	 * @param responseModel
	 * @param spExecNum
	 *        1 执行的是spMoreResultSetMap
	 *        2 执行的是spGetAllMoreResultSetMap //1201,1401_2,1402...
	 *        3 执行的是spMoreResultSetList
	 * @throws SQLException
	 */
	private void spGetParams(CallableStatement cst,ExecParam execParam,ResponseModel responseModel, int spExecNum) throws SQLException {
		 //获取自定义返回参数
	     Map<String, Object> outMap = null;
	     Map<String, Integer> outPutMap = execParam.getOutPutMap();
	     if (null != outPutMap) {
	        outMap = new EVideoMap<>();
			Iterator<Map.Entry<String, Integer>> entries = outPutMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, Integer> entry = entries.next();
				if (!StringUtils.equals(String.valueOf(cst.getObject(entry.getKey())), null)){
					outMap.put(entry.getKey(), cst.getObject(entry.getKey())+"");
				}
			}
			if (1 == spExecNum) {
				
				Map<String,Object> oldResponseMap = responseModel.getResponseParamsMap();
				
				oldResponseMap.putAll(outMap);
				
			    responseModel.setResponseParams(oldResponseMap);
				
			}else if (2 == spExecNum){
				//多个结果集的时候，用map下多个data_n保存多结果集，同时outputmap放到最后
				Map<String,Object> oldResponseMap = responseModel.getResponseParamsMap();
				oldResponseMap.put("returnmap", outMap);
				responseModel.setResponseParams(oldResponseMap);
				
			}else if (3 == spExecNum) {//当为3时，有结果集list，同时存在output
				Map<String, Object> rsMap = new EVideoMap<>();
				rsMap.put("returnmap", outMap);
				//isPage==1时，才有分页信息，如果有分页信息或者有返回参数，用map保存，否则就用list保存数据
				//目前不管分页逻辑
				
				//有output的时候，统一返回map,returnmap里面存output，returnlist里面存返回的结果集列表
				rsMap.put("returnlist",responseModel.getResponseParamsList());
				
				responseModel.setResponseParams(rsMap);
				
			}
		}
	        
	     //hasReturnParams==1时，才有返回参数
		if(execParam.getHasReturnErrorCode() == 1){
			 //设置返回结果代码
			responseModel.setErrorCode(cst.getInt("ErrorCode")+"");
			responseModel.setErrorMsg(cst.getString("ErrorMessage"));
			//responseModel.setExceptMessage(cst.getString("ExceptMessage"));
		}else{
			responseModel.setErrorCode("0");
		}
		//isPage==1时，才有分页，返回分页信息  --这个参数目前没用，都是没有分页
		if(execParam.getIsPage() == 1){
			responseModel.setRecordCount(cst.getInt("RecordCount")+"");
			responseModel.setPageTotal(cst.getInt("PageTotal")+"");
		}
	}
	
	/**
	 * 调用失败后事务回滚
	 * @author zhangchuanzhao
	 * 2016-2-2 下午5:03:39
	 */
	private void rollback(Connection conn) {
		String sql = "dbo.BL_HV_CheckTran()";
		CallableStatement cst;
		try {
			cst = conn.prepareCall("{call "+sql+"}");
			cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	

}
