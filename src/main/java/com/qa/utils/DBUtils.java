package com.qa.utils;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;

import com.qa.pages.BaseTest;

import io.qameta.allure.testng.AllureTestNg;
public class DBUtils extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	ResultSet rs;
	Connection con;
	PreparedStatement statement;

	public void printDBDetails(String queryString) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection(config.getProperty("db.url"), config.getProperty("db.user"),
				config.getProperty("db.password"));
		LOGGER.info("SQL: " + queryString);
		PreparedStatement statement = con.prepareStatement(queryString);
		ResultSet rs = statement.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			LOGGER.info(rsmd.getColumnName(i) + "\t");
		}
		while (rs.next()) {
			for (int i = 1; i <= columnCount; i++) {
				LOGGER.info(rs.getString(i) + "\t");
			}

		}
		closeStatementAndConnection(rs, statement, con);
	}

	public void closeStatementAndConnection(ResultSet rs, Statement statement, Connection connection) {
		closeDbResultSet(rs);
		closeStatement(statement);
		closeDbConnection(connection);
	}

	public void closeDbResultSet(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			LOGGER.info("Error while closing the connection, connection: " + resultSet + ", Error: " + e);
		}

	}

	public void closeDbConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			LOGGER.info("Error while closing the connection, connection: " + connection + ", Error: " + e);
		}

	}

	public void closeStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			LOGGER.info("Error while closing statement: " + statement + ", Error: " + e);
		}

	}
}
