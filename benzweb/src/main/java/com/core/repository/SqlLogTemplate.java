package com.core.repository;

import com.core.util.BarConfig;
import com.core.util.ExecParam;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqlLogTemplate {
	protected Logger log = Logger.getLogger(SqlLogTemplate.class);
	
	/**
	 * 保存存储过程的可执行sql日志（根据barConfig.properties的is_print_log_for_sql配置）
	 * @param execProc 执行的存储过程明
	 * @param execProc 执行的存储过程明
	 * @param execParam
	 * @param params
	 */
	public void spSaveSqlLog(String className, String execProc, ExecParam execParam, List<Object> params){
		if ("1".equals(BarConfig.IS_PRINT_LOG_FOR_SQL)){
			StringBuffer sbf = new StringBuffer();
			StringBuffer sbfForSelect = new StringBuffer();
			
			sbf.append("["+className+"]可执行sql: ");
			
			
			//注册自定义返回参数
			Map<String, Integer> outPutMap = execParam.getOutPutMap();
			if (null != outPutMap) {
				sbf.append("declare ");
				Iterator<Map.Entry<String, Integer>> entries = outPutMap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<String, Integer> entry = entries.next();
					sbf.append("@"+entry.getKey()+" "+getTypesStr(entry.getValue())+", ");
				}
				//最后多余的", "要去掉
				sbf = new StringBuffer(sbf.toString().substring(0, sbf.length()-2));
				sbf.append(" ");
			}

			//hasReturnErrorCode==1时，才有返回提示信息
			if(execParam.getHasReturnErrorCode() == 1){
				if (null != outPutMap) {
					sbf.append(", @ErrorCode int, @ErrorMessage NVARCHAR(100), @ExceptMessage NVARCHAR(4000) ");
				}else{
					sbf.append("declare @ErrorCode int, @ErrorMessage NVARCHAR(100), @ExceptMessage NVARCHAR(4000) ");
				}
			}
			
			sbf.append(" exec "+execProc+" ");
			
			if((null != outPutMap) || (execParam.getHasReturnErrorCode() == 1)){
				sbfForSelect.append(" select ");
			}
			
			int i = 0;
			if(params != null){
				for (i = 0; i < params.size(); i++) {
					sbf.append("'"+params.get(i)+"', ");
				}
			}
			//注册自定义返回参数
			if (null != outPutMap) {
				Iterator<Map.Entry<String, Integer>> entries = outPutMap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry<String, Integer> entry = entries.next();
					sbf.append("@"+entry.getKey()+" output, ");
					sbfForSelect.append("@"+entry.getKey()+",");
				}
			}

			//hasReturnErrorCode==1时，才有返回提示信息
			if(execParam.getHasReturnErrorCode() == 1){
				//注册返回参数
				sbf.append("@ErrorCode output, @ErrorMessage output, @ExceptMessage output, ");
				sbfForSelect.append("@ErrorCode,@ErrorMessage,@ExceptMessage,");
			}
			
			if ( (params != null) || (null != outPutMap) || (execParam.getHasReturnErrorCode() == 1) ){
				//最后多余的", "要去掉
				sbf = new StringBuffer(sbf.toString().substring(0, sbf.length()-2));
				sbf.append(" ");
			}
			
			if((null != outPutMap) || (execParam.getHasReturnErrorCode() == 1)){
				//最后多余的","要去掉
				sbfForSelect = new StringBuffer(sbfForSelect.toString().substring(0, sbfForSelect.length()-1));
			}
			
			sbf.append(sbfForSelect);
			
			
			System.out.println("dddddd sqlLog="+sbf.toString());
			
			//isPage==1时，才有分页，返回分页信息 ，先不管
			log.info(sbf.toString());
			
		}
	}
	
	public void spSaveSqlStrLog(String className,String execSql,List<Object> params){
		if ("1".equals(BarConfig.IS_PRINT_LOG_FOR_SQL)){
			StringBuffer sbf = new StringBuffer();
			
			sbf.append("["+className+"]可执行sql: ").append(execSql);
			
			sbf.append(",参数列表【image的字段为base64Str】：");
			
			int i = 0;
			if(params != null){
				for (i = 0; i < params.size(); i++) {
					sbf.append("'"+params.get(i)+"', ");
				}
			}
			
			if(params != null){
				//最后多余的", "要去掉
				sbf = new StringBuffer(sbf.toString().substring(0, sbf.length()-2));
				sbf.append(" ");
			}
			
			System.out.println("dddddd sqlLog="+sbf.toString());
			
			//isPage==1时，才有分页，返回分页信息 ，先不管
			log.info(sbf.toString());
			
		}
	}
	
	
	protected String getTypesStr(int key){
		String result = "";
		
		switch(key){
		case -7:
			result = "BIT";
			break;
		case -6:
			result = "TINYINT";
			break;
		case 5:
			result = "SMALLINT";
			break;
		case 4:
			result = "INTEGER";
			break;
		case -5:
			result = "BIGINT";
			break;
		case 6:
			result = "FLOAT";
			break;
		case 7:
			result = "REAL";
			break;
		case 8:
			result = "DOUBLE";
			break;
		case 2:
			result = "NUMERIC";
			break;
		case 3:
			result = "DECIMAL";
			break;
		case 1:
			result = "CHAR";
			break;
		case 12:
			result = "VARCHAR(1000)";//result = "VARCHAR";
			break;
		case -1:
			result = "LONGVARCHAR";
			break;
		case 91:
			result = "DATE";
			break;
		case 92:
			result = "TIME";
			break;
		case 93:
			result = "DATETIME";//result = "TIMESTAMP";
			break;
		case -2:
			result = "BINARY";
			break;
		case -3:
			result = "VARBINARY";
			break;
		case -4:
			result = "LONGVARBINARY";
			break;
		case 1111:
			result = "OTHER";
			break;
		case 2000:
			result = "JAVA_OBJECT";
			break;
		case 2001:
			result = "DISTINCT";
			break;
		case 2002:
			result = "STRUCT";
			break;
		case 2003:
			result = "ARRAY";
			break;
		case 2004:
			result = "BLOB";
			break;
		case 2005:
			result = "CLOB";
			break;
		case 2006:
			result = "REF";
			break;
		case 70:
			result = "DATALINK";
			break;
		case 16:
			result = "BOOLEAN";
			break;
		case -8:
			result = "ROWID";
			break;
		case -15:
			result = "NCHAR";
			break;
		case -9:
			result = "NVARCHAR";
			break;
		case -16:
			result = "LONGNVARCHAR";
			break;
		case 2011:
			result = "NCLOB";
			break;
		case 2009:
			result = "SQLXML";
			break;
		case 2012:
			result = "REF_CURSOR";
			break;
		case 2013:
			result = "TIME_WITH_TIMEZONE";
			break;
		case 2014:
			result = "TIMESTAMP_WITH_TIMEZONE";
			break;
		default:
			result = "VARCHAR";
			break;
		}
		
		return result;
	}
}

