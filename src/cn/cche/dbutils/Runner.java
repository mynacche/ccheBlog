/*
 * Copyright 2013 The UTFoodPortalSE Project. Zhuhai Unitech Power Technology Co.,Ltd. All Rights Reserved.
 */

package cn.cche.dbutils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

/**
 * @author chexingyou
 * @date   2013-5-13
 */
public class Runner {

	private QueryRunner queryRunner;
	private Connection connection;
	private TransMgr transMng;
	
	public Runner(){}
	
	public Runner(QueryRunner qr) throws SQLException{
		this.queryRunner = qr;
		this.connection = qr.getDataSource().getConnection();
		this.transMng = new TransMgr(this.connection);
	}

	public QueryRunner getQueryRunner() {
	
		return queryRunner;
	}

	public void setQueryRunner(QueryRunner queryRunner) {
	
		this.queryRunner = queryRunner;
	}

	public Connection getConnection() {
	
		return connection;
	}

	public void setConnection(Connection connection) {
	
		this.connection = connection;
	}

	public TransMgr getTransMng() {
	
		return transMng;
	}

	public void setTransMng(TransMgr transMng) {
	
		this.transMng = transMng;
	}
	
}
