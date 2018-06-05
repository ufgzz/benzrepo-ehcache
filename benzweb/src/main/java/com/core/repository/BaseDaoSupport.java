package com.core.repository;


import org.apache.log4j.Logger;


/**
 * 数据操作基类
 * @author zhangchuanzhao
 * 2015-9-14 上午9:26:21
 */
public abstract class BaseDaoSupport {
	
	//获取Logger对象
	protected Logger log = Logger.getLogger(this.getClass());
	
	private JdbcModelTemplate jdbcModelTemplate = new JdbcModelTemplate();
	
	private SqlLogTemplate sqlLogTemplate = new SqlLogTemplate();
	
	protected String className = this.getClass().getName();
	
	protected JdbcModelTemplate getJdbcModelTemplate() {
		return jdbcModelTemplate;
	}

	public SqlLogTemplate getSqlLogTemplate() {
		return sqlLogTemplate;
	}
	
}
