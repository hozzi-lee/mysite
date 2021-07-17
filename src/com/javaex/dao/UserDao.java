package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
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
	
	// INSERT
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
			System.out.println("ERROR: " + count);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
	// oneUser
	public UserVo getUser(String id, String pw) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" SELECT "
					+ " 	no, "
					+ " 	name "
					+ " FROM "
					+ " 	users "
					+ " WHERE "
					+ " 	id = ? "
					+ " 	AND password = ? "
					);
			
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
//				int no = rs.getInt("no");
//				String name = rs.getString("name");
				
				// 2개짜리 생성자가 없는 경우
				/*
				 * userVo.setNo(no);
				 * userVo.setName(name);
				 */
				
				userVo = new UserVo(rs.getInt("no"), rs.getString("name"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return userVo;
	}
	
	// id로 시도 == 실패
	/*
	public UserVo getUser(String id) {
		UserVo userVo = new UserVo();
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" SELECT "
					+ " 	id, "
					+ " 	name, "
					+ " 	password, "
					+ " 	gender "
					+ " FROM "
					+ " 	users "
					+ " WHERE "
					+ " 	id = ? "
					);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				userVo = new UserVo(rs.getString("id"), rs.getString("name"), rs.getString("password"), rs.getString("gender"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return userVo;
	}
	*/
	
	public UserVo getUser(int no) {
		UserVo userVo = null;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" SELECT "
					+ " 	no, "
					+ " 	id, "
					+ " 	password, "
					+ " 	name, "
					+ " 	gender "
					+ " FROM "
					+ " 	users "
					+ " WHERE "
					+ " 	no = ? "
					);
			
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				userVo = new UserVo(rs.getInt("no"), rs.getString("id"), rs.getString("password"), rs.getString("name"), rs.getString("gender"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return userVo;
	}
	
	public int update(UserVo uVo) {
		int count = -1;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" UPDATE "
					+ " 	users "
					+ " SET "
					+ " 	password = ?, "
					+ " 	name = ?, "
					+ " 	gender = ? "
					+ " WHERE "
					+ " 	no = ? "
					+ " 	AND id = ? "
					);
			
			pstmt.setString(1, uVo.getPassword());
			pstmt.setString(2, uVo.getName());
			pstmt.setString(3, uVo.getGender());
			pstmt.setInt(4, uVo.getNo());
			pstmt.setString(5, uVo.getId());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	/*
	public UserVo idOver(String id) {
		UserVo userVo = new UserVo();
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" SELECT "
					+ " 	id "
					+ " FROM "
					+ " 	users "
					+ " WHERE "
					+ " 	id = ? "
					);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				userVo = new UserVo(rs.getString("id"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return userVo;
	} */

}
