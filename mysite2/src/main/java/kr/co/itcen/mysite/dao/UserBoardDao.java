package kr.co.itcen.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.itcen.mysite.pagination.Pagination;
import kr.co.itcen.mysite.vo.UserBoardVo;

public class UserBoardDao {

	
	/////select 게시판 첫 조회하기User랑 Board랑 조인한 부분/////
	
	public List<UserBoardVo> getList(String keyword,Pagination pagination) {
		List<UserBoardVo> result = new ArrayList<UserBoardVo>();
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = getConnection();
		
			String sql = "select b.no, b.title, u.name, b.hit, b.reg_date, b.depth, b.user_no ,b.status " + 
					" from user u, board b " + 
					" where u.no = b.user_no" + 
					" and (title like ?" + 
					" or contents like ?) " + 
					" order by b.g_no desc, b.o_no asc " + 
					" limit ?, 10;";
			
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword +"%");
			pstmt.setInt(3, (pagination.getCurrentPage() - 1) * pagination.getListSize());
		
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				String registerDate = rs.getString(5);
				
				UserBoardVo vo = new UserBoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setRegisterDate(registerDate);
				vo.setDepth(rs.getInt("depth"));
				vo.setUserNo(rs.getLong("user_no"));
				vo.setStatus(rs.getInt("status"));
		
				result.add(vo);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/////검색됬을 경우에도 페이징 되도록/////
	
	public int getBoardCnt(String keyword) {
		int cnt = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();

			String sql = "select count(*) as 'cnt' from board where title like ? or contents like ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + ((keyword == null) ? "" : keyword) + "%");
			pstmt.setString(2, "%" + ((keyword == null) ? "" : keyword) + "%");

			rs = pstmt.executeQuery();

			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cnt;
	}


	public UserBoardVo getBoard(UserBoardVo userBoardVo, boolean increaseHitCnt) {
		UserBoardVo vo = null;
			return vo;
	}

	private Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.1.78:3306/webdb?characterEncoding=utf8";
			connection = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("Fail to Loading Driver:" + e);
		}

		return connection;
	}
}
