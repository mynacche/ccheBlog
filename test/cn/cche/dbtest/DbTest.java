/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.dbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.Test;

import cn.cche.dbutils.Jdbc;
import cn.cche.dbutils.Runner;
import cn.cche.dbutils.TransMgr;

/**
 * @author chexingyou
 * @date 2013-5-11
 */
public class DbTest {

	@Test
	public void test() {

		QueryRunner runner = Jdbc.getQueryRunner();
		ArrayListHandler handler = new ArrayListHandler();
		try {
			List<Object[]> list = runner.query("select * from t_db_test", handler);
			for (Object[] o : list) {
				System.out.println(o[0]);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test1() {

		QueryRunner runner = Jdbc.getQueryRunner();
		Connection conn = Jdbc.getConnection();
		//System.out.println(runner.getDataSource().getConnection().equals(runner.getDataSource().getConnection()));
		TransMgr tm = Jdbc.getTransMgr(conn);
		
		try {
			tm.beginTransaction();
			runner.update(conn,"insert into t_db_test(name) values(?)", "哈哈1");
			tm.commitAndClose();
		} catch (Exception e) {
			tm.rollbackAndClose();
			e.printStackTrace();
		}finally{
			test();
		}

	}

	@Test
	public void test2() {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = Jdbc.getConnection();
			stmt = conn.prepareStatement("insert into t_db_test(name) values(?)");
			stmt.setString(1, "dd阿凡");
			stmt.executeUpdate();
			//Class.forName("aaaaaaa");
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				conn.commit();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}finally{
			test();
		}

	}

	@Test
	public void test3() {

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/db_blog?characterEncoding=utf-8", "sa", "123");
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement("insert into t_db_test(name) values(?)");
			stmt.setString(1, "老百姓");
			stmt.executeUpdate();
			//Class.forName("aaaaaaa");
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
				conn.commit();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			test();
		}

	}
	@Test
	public void test4() throws SQLException {

		QueryRunner runner = Jdbc.getQueryRunner();
		Connection conn = null;
		
		try {
			conn =runner.getDataSource().getConnection();
			
			runner.update(conn,"insert into t_db_test(name) values(?)", "哈哈1");
			//Class.forName("aaaaaaa");
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			test();
		}

	}
	@Test
	public void test5() throws SQLException {
		
		Runner runner = null;
		QueryRunner query = null;
		TransMgr transMng = null;
		Connection conn = null;
		try {
			runner = Jdbc.getRunner();
			transMng = runner.getTransMng();
			conn = runner.getConnection();
			query = runner.getQueryRunner();
			
			transMng.beginTransaction();
			query.update(conn,"insert into t_db_test(name) values(?)", "哈哈1");
			transMng.commitAndClose();
			
		} catch (Exception e) {
			transMng.rollbackAndClose();
			e.printStackTrace();
		}finally{
			test();
		}

	}
	@Test
	public void test6() {

		QueryRunner runner = Jdbc.getQueryRunner();
		Connection conn = Jdbc.getConnection();
		//System.out.println(runner.getDataSource().getConnection().equals(runner.getDataSource().getConnection()));
		TransMgr tm = Jdbc.getTransMgr(conn);
		
		try {
			tm.beginTransaction();
			runner.update(conn,"delete from t_db_test where name=?", "哈哈1");
			tm.commitAndClose();
		} catch (Exception e) {
			tm.rollbackAndClose();
			e.printStackTrace();
		}finally{
			test();
		}

	}
	@Test
	public void test7() {

		QueryRunner runner = Jdbc.getQueryRunner();
		Connection conn = Jdbc.getConnection();
		//System.out.println(runner.getDataSource().getConnection().equals(runner.getDataSource().getConnection()));
		TransMgr tm = Jdbc.getTransMgr(conn);
		
		try {
			tm.beginTransaction();
			runner.update(conn,"update t_db_test set name=? where name=?","程序员","车兴友");
			tm.commitAndClose();
		} catch (Exception e) {
			tm.rollbackAndClose();
			e.printStackTrace();
		}finally{
			test();
		}

	}
}
