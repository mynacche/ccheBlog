package cn.cche.dbutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

/**
 * @author chexingyou
 * @date 2013-5-11
 */
public class Jdbc {

	private static DataSource dataSource;

	private Jdbc() {

	}

	public static DataSource getDataSource() {

		if (dataSource != null) {
			return dataSource;
		}
		BasicDataSource dbcpDataSource = new BasicDataSource();
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(Jdbc.class.getResource("/").getPath()
					+ "jdbc.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		dbcpDataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
		dbcpDataSource.setUrl(properties.getProperty("jdbc.url"));
		dbcpDataSource.setUsername(properties.getProperty("jdbc.username"));
		dbcpDataSource.setPassword(properties.getProperty("jdbc.password"));
		dbcpDataSource.setDefaultAutoCommit(false);
		dbcpDataSource.setMaxActive(100);
		dbcpDataSource.setMaxIdle(30);
		dbcpDataSource.setMaxWait(500);
		dataSource = (DataSource) dbcpDataSource;

		return dataSource;
	}

	public static Runner getRunner() throws SQLException{
		
		return new Runner(getQueryRunner());
		
	}
	
	public static QueryRunner getQueryRunner() {

		return new QueryRunner(getDataSource());

	}

	public static TransMgr getTransMgr(Connection conn) {

		return new TransMgr(conn);
	}

	public static Connection getConnection() {

		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(Connection conn) {

		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}