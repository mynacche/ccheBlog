package cn.cche.dbutils;

import java.sql.Connection;
import java.sql.SQLException;

import cn.cche.exception.DAOException;

/**
 * 事务管理器
 */
public class TransMgr {
	private Connection conn;

	protected TransMgr(Connection conn) {

		this.conn = conn;
	}

	/** 开启事务 */
	public void beginTransaction() throws DAOException {
		try {
			conn.setAutoCommit(false); // 把事务提交方式改为手工提交
		} catch (SQLException e) {
			throw new DAOException("开户事务时出现异常", e);
		}
	}

	/** 提交事务并关闭连接 */
	public void commitAndClose() throws DAOException {
		try {
			conn.commit(); // 提交事务
		} catch (SQLException e) {
			throw new DAOException("提交事务时出现异常", e);
		} finally {
			Jdbc.close(conn);
		}
	}

	/** 回滚并关闭连接 */
	public void rollbackAndClose() throws DAOException {
		System.out.println("回滚事务");
		try {
			conn.rollback();
		} catch (SQLException e) {
			throw new DAOException("回滚事务时出现异常", e);
		} finally {
			Jdbc.close(conn);
		}
	}
}
