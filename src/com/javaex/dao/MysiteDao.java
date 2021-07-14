package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class MysiteDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR - 드라이버 로딩 실패: " + e);
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
	}
	
	private void getClose() {
		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			System.out.println("ERROR: " + e);
		}
		
	}
	
	public int insert(UserVo uVo) {
		int count = -1;
		
		getConnection();
		
		try {
		pstmt = conn.prepareStatement(
				" INSERT INTO "
				+ " 	users "
				+ " VALUES "
				+ " 	( sqc_users_no.NEXTVAL, ?, ?, ?, ? ) "
				);
		pstmt.setString(1, uVo.getId());
		pstmt.setString(2, uVo.getPassword());
		pstmt.setString(3, uVo.getName());
		pstmt.setString(4, uVo.getGender());
		
		count = pstmt.executeUpdate();
		
		if (count > 0) {
			System.out.println("[ID: " + uVo.getId() + ", NAME: " + uVo.getName() + "] 가입완료");
		} else {
			System.out.println("]관리자에게 문의]");
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}

}
