package com.core.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * 通过数据库连接池获取数据库连接
 * @author zhangchuanzhao
 * 2015-9-22上午11:07:45
 */
public class ConnectionPool {
	private static ComboPooledDataSource ds = null;
	private static Logger log = Logger.getLogger(ConnectionPool.class);
	//在静态代码块中创建数据库连接池
	static{
		try{
			//获取数据源
			ds = new ComboPooledDataSource();
			ds.setDriverClass("com.mysql.jdbc.Driver");
			ds.setJdbcUrl("jdbc:mysql://localhost:3306/ehcachetest?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8");
			ds.setUser("root");
			ds.setPassword("");
			ds.setInitialPoolSize(15);
			ds.setMinPoolSize(15);
			ds.setAcquireIncrement(5);
			ds.setMaxPoolSize(100);
			ds.setMaxIdleTime(100);
			ds.setAcquireRetryAttempts(1);
			ds.setAcquireRetryDelay(100);
		}catch (Exception e) {
			log.error("获取数据库信息失败",e);
			throw new ExceptionInInitializerError(e);
		}
	}
	/**
	 * 获取数据库连接
	 * @return
	 */
    public synchronized static Connection getConnection() {    
        try {
//        	System.out.println("正在使用连接数:"+ds.getNumBusyConnections());
//        	System.out.println("空闲连接数:"+ds.getNumIdleConnections());
//        	System.out.println("总连接数:"+ds.getNumConnections());
        	//从数据源中获取数据库连接
            return ds.getConnection();  
        } catch (SQLException e) {
	        log.error("数据库连接失败",e);
            e.printStackTrace();  
        }  
        return null;  
    }  
	
    /**
     * 释放数据库连接,执行sql调用
     * @param conn
     * @param pst
     * @param rs
     */
	public static void release(Connection conn,PreparedStatement pst,ResultSet rs){
		if(rs!=null){
			try{
				//关闭存储查询结果的ResultSet对象
				rs.close();
			}catch (Exception e) {
				log.error("关闭ResultSet对象失败",e);
				e.printStackTrace();
			}
			rs = null;
		}
		if(pst!=null){
			try{
				//关闭负责执行SQL命令的PreparedStatement对象
				pst.close();
			}catch (Exception e) {
				log.error("关闭PreparedStatement对象失败",e);
				e.printStackTrace();
			}
			pst = null;
		}
		if(conn!=null){
			try{
				//将Connection连接对象还给数据库连接池
				conn.close();
			}catch (Exception e) {
				log.error("关闭Connection对象失败",e);
				e.printStackTrace();
			}
		}
	}
	
	/**
     * 释放数据库连接,执行存储过程调用
     * @param conn
     * @param cst
     * @param rs
     */
	public static void release(Connection conn,CallableStatement cst,ResultSet rs){
		if(rs!=null){
			try{
				//关闭存储查询结果的ResultSet对象
				rs.close();
			}catch (Exception e) {
				log.error("关闭ResultSet对象失败",e);
				e.printStackTrace();
			}
			rs = null;
		}
		if(cst!=null){
			try{
				//关闭负责执行存储过程命令的CallableStatement对象
				cst.close();
			}catch (Exception e) {
				log.error("关闭CallableStatement对象失败",e);
				e.printStackTrace();
			}
			cst = null;
		}
		if(conn!=null){
			try{
				//将Connection连接对象还给数据库连接池
				conn.close();
			}catch (Exception e) {
				log.error("关闭Connection对象失败",e);
				e.printStackTrace();
			}
		}
	}
}