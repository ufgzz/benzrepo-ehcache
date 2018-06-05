package com.core.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BarConfig {
    
	//酒吧系统中，log.info级日志信息，是否要打印出接口调用的存储过程（可在slqserver中执行的sql）0 不打印 ， 1打印
    public static String IS_PRINT_LOG_FOR_SQL;    
    //系统是否做大小写区分判断
    public static String IS_JSON_CAMEL;

    public static String DB_SECTION;


	@Value("${is_json_camel}")
	public void setIsJsonCamel(String isJsonCamel) {
		BarConfig.IS_JSON_CAMEL = isJsonCamel;
	}

	
	@Value("${is_print_log_for_sql}")
	public void setIsPrintLogForSql(String isPrintLogForSql) {
		BarConfig.IS_PRINT_LOG_FOR_SQL = isPrintLogForSql;
	}

	@Value("${db_section}")
	public void setDbSection(String dbSection) {
		DB_SECTION = dbSection;
	}

}
