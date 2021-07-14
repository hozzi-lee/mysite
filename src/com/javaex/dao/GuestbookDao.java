package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

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
	
	public int insert(GuestbookVo gVo) {
		int count = -1;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" INSERT INTO "
					+ " 	guestbook "
					+ " VALUES "
					+ " 	( sqc_no.NEXTVAL, ?, ?, ?, sysdate ) "
					);
			pstmt.setString(1, gVo.getName());
			pstmt.setString(2, gVo.getPassword());
			pstmt.setString(3, gVo.getContent());
			
			count = pstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("[" + gVo.getName() + "] 방명록 작성");
			} else {
				System.out.println("ERROR: " + count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
	public int delete(GuestbookVo gVo) {
		int count = -1;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" DELETE FROM "
					+ " 	guestbook "
					+ " WHERE "
					+ " 	no = ? "
					+ " 	AND password = ? "
					);
			
			pstmt.setInt(1, gVo.getNo());
			pstmt.setString(2, gVo.getPassword());
			
			count = pstmt.executeUpdate();
			
			if (count > 0) {
				System.out.println("[" + gVo.getNo() + "]번 방명록 삭제");
			} else {
				System.out.println("ERROR: " + count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
	public List<GuestbookVo> getList() {
		List<GuestbookVo> gList = new ArrayList<GuestbookVo>();
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" SELECT "
					+ " 	no, "
					+ " 	name, "
					+ " 	password, "
					+ " 	reg_date, "
					+ " 	content "
					+ " FROM "
					+ " 	guestbook "
					+ " ORDER BY "
					+ " 	no DESC "
					);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				/*
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String date = rs.getString("reg_date");
				String ct = rs.getString("content");
				
				GuestbookVo gVo = new GuestbookVo(no, name, password, date, ct);
				
				gList.add(gVo);
				*/
				
				gList.add(new GuestbookVo(rs.getInt("no"), rs.getString("name"), rs.getString("password"), rs.getString("reg_date"), rs.getString("content")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return gList;
	}

}
