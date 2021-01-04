package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FaQDAO {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public FaQDAO() {	}
	
	private static FaQDAO instance = null;
	
	public static FaQDAO getInstance() {
		if(instance == null) {
			instance = new FaQDAO();
		}
		
		return instance;
	}
	
	public void openConn() {
		
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource)ic.lookup("java:comp/env/jdbc/myoracle");	
			con = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeConn(ResultSet rs, PreparedStatement pstmt, Connection con) {
		
		try {
			
			if(rs != null)	rs.close();
			if(pstmt != null) pstmt.close();
			if(con != null) con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 전체 FAQ 리스트
	public List<FaQDTO> getFaQ(int page, int rowsize) {
		List<FaQDTO> list = new ArrayList<FaQDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select f.*, row_number() over(order by f.f_num) rnum "
				+ "from faq f) "
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FaQDTO dto = new FaQDTO();
				
				dto.setF_num(rs.getInt(1));
				dto.setF_category(rs.getString(2));
				dto.setF_question(rs.getString(3));
				dto.setF_answer(rs.getString(4));
				
				list.add(dto);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	}
	
	// 카테고리에 따른 FAQ 리스트
	public List<FaQDTO> getFaQCate(String cate, int page, int rowsize) {
		List<FaQDTO> list = new ArrayList<FaQDTO>();
			
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select f.*, row_number() over(order by f.f_num) rnum "
				+ "from faq f where f_category = ?) "
				+ "where rnum >= ? and rnum <= ?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cate);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FaQDTO dto = new FaQDTO();
				
				dto.setF_num(rs.getInt(1));
				dto.setF_category(rs.getString(2));
				dto.setF_question(rs.getString(3));
				dto.setF_answer(rs.getString(4));
				
				list.add(dto);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return list;
	}
	
	// FAQ 추가
	public int insertFaQ(FaQDTO dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
		
		try {
			sql = "select max(f_num) from faq";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into faq values(?, ?, ?, ?, sysdate)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getF_category());
			pstmt.setString(3, dto.getF_question());
			pstmt.setString(4, dto.getF_answer());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
			
		}
		
		return res;
	}
	
	// FAQ 전체 개수
	public int getFaQCount() {
		int res = 0;

		openConn();
		
		sql = "select count(f_num) from faq";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = rs.getInt(1);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 해당 카테고리의 FAQ 개수
	public int getFaQCount(String cate) {
		int res = 0;

		openConn();
		
		sql = "select count(f_num) from faq where f_category = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cate);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = rs.getInt(1);			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 수정폼 내용 가져오기
	public FaQDTO getFaQ(int num) {
		FaQDTO dto = new FaQDTO();
			
		openConn();
		
		sql = "select * from faq where f_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setF_num(rs.getInt(1));
				dto.setF_category(rs.getString(2));
				dto.setF_question(rs.getString(3));
				dto.setF_answer(rs.getString(4));
				dto.setF_date(rs.getString(5));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	// FAQ 수정
	public int updatetFaQ(FaQDTO dto) {
		int res = 0;
		
		openConn();
		
		try {
			sql = "update faq set f_category = ?, f_question = ?, f_answer = ? where f_num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getF_category());
			pstmt.setString(2, dto.getF_question());
			pstmt.setString(3, dto.getF_answer());
			pstmt.setInt(4, dto.getF_num());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
			
		}
		
		return res;
	}

	public int deleteFaQ(int num) {
		int res = 0;
		
		openConn();
		
		sql = "delete from faq where f_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
}
