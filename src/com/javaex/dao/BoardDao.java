package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.PrimitiveType;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	// CONECCTION
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
	
	// CLOSE
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
	
	// SELECT + search
	public List<BoardVo> getList(String k) {
		List<BoardVo> bList = new ArrayList<BoardVo>();
		
		getConnection();
		
		try {
			String query = "";
				query += " SELECT "
						+ " 	board.no, "
						+ " 	board.title, "
						+ " 	users.name, "
						+ " 	board.hit, "
						+ " 	board.reg_date, "
						+ " 	board.user_no "
						+ " FROM "
						+ " 	users FULL OUTER JOIN board "
						+ " 	ON users.no = board.user_no ";
				
				if ( k == null || "".equals(k)) {
					query += " ORDER BY "
							+ " 	board.no DESC ";
					
				} else {
					query += " WHERE "
							+ " 	board.title LIKE '%" + k + "%' "
							+ " 	OR users.name LIKE '% " + k + "%' "
							+ " ORDER BY "
							+ " 	board.no DESC ";
					
				}
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				bList.add(new BoardVo(rs.getInt("no"), rs.getString("title"), rs.getString("name"), rs.getInt("hit"), rs.getString("reg_date"), rs.getInt("user_no")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return bList;
	}
	
	// listCount
	public int listCount(String k) {
		
		int count = 0;
		
		getConnection();
		
		try {
			String query = "";
				query += " SELECT "
						+ " 	COUNT(no) ";
				
			if ( k == null || "".equals(k)) {
				query += " FROM "
						+ " 	board ";
			} else {
				query += " FROM "
						+ " 	( SELECT "
						+ " 		board.no "
						+ " 	FROM "
						+ " 		users FULL OUTER JOIN board "
						+ " 		ON users.no = board.user_no "
						+ " 	WHERE "
						+ " 		board.title LIKE '%" + k + "%' "
						+ " 		OR users.name LIKE '%" + k + "%' "
						+ " 		OR board.no LIKE '%" + k + "%' "
						+ " 	) ";
			}
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				count = rs.getInt("COUNT(no)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
	// INSERT
	public int insert(BoardVo bVo) {
		int count = -1;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" INSERT INTO "
					+ " 	board "
					+ " VALUES "
					+ " 	( sqc_board_no.NEXTVAL, ?, ?, 0, sysdate, ? ) "
					);
			
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getUserNo());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
	// SELECT listOne
	public BoardVo readList(int no) {
		BoardVo boardVo = new BoardVo();
		
		getConnection();

		try {
			pstmt = conn.prepareStatement(
					" SELECT "
					+ " 	users.name, "
					+ " 	board.hit, "
					+ " 	board.reg_date, "
					+ " 	board.title, "
					+ " 	board.content, "
					+ " 	board.user_no, "
					+ " 	board.no "
					+ " FROM "
					+ " 	users, "
					+ " 	board "
					+ " WHERE "
					+ " 	users.no = board.user_no "
					+ " 	AND board.no = ? "
					);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				boardVo = new BoardVo(rs.getString("name"), rs.getInt("hit"), rs.getString("reg_date"), rs.getString("title"), rs.getString("content"), rs.getInt("user_no"), rs.getInt("no"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return boardVo;
	}
	
	// countHit
	public int countHit(int no) {
		int count = -1;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" UPDATE "
					+ " 	board "
					+ " SET "
					+ " 	hit = (hit + 1) "
					+ " WHERE "
					+ " 	no = ? "
					);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
	// UPDATE
	public int update(BoardVo bVo) {
		int count = -1;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" UPDATE "
					+ " 	board "
					+ " SET "
					+ " 	title = ?, "
					+ " 	content = ? "
					+ " WHERE "
					+ " 	no = ? "
					);
			pstmt.setString(1, bVo.getTitle());
			pstmt.setString(2, bVo.getContent());
			pstmt.setInt(3, bVo.getNo());
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
	// DELETE
	public int delete(int no) {
		int count = -1;
		
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(
					" DELETE FROM "
					+ " 	board "
					+ " WHERE "
					+ " 	no = ? "
					);
			pstmt.setInt(1, no);
			
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		getClose();
		
		return count;
	}
	
}
