package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CategoryDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public CategoryDAO() {	}
	
	private static CategoryDAO instance = null;
	
	public static CategoryDAO getInstance() {
		if(instance == null) {
			instance = new CategoryDAO();
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
	
	//카테고리를 반환하는 메서드(카테고리 전체)
	public List<CategoryDTO> getCategory() {
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		openConn();
		try {
			sql = "select * from book_category order by cate_code asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				dto.setCate_name(rs.getString("cate_name"));
				dto.setCate_code(rs.getInt("cate_code"));
				dto.setCate_code_ref(rs.getInt("cate_code_ref"));
				list.add(dto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	// 카테고리 테이블의 전체 리스트를 조회하는 메서드(대분류)
	public List<CategoryDTO> categoryList() {
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		
		try {
			openConn();
			sql = "select * from book_category where cate_code like '%00' order by cate_code";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				dto.setCate_name(rs.getString("cate_name"));
				dto.setCate_code(rs.getInt("cate_code"));
				dto.setCate_code_ref(rs.getInt("cate_code_ref"));
				
				list.add(dto);   
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	} 
	
	// 카테고리 전체 중분류 리스트 가져오는 메서드
	public List<CategoryDTO> cateAllList() {
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		
		try {
			openConn();
			sql = "select * from book_category where cate_code not like '%00' order by cate_code";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				dto.setCate_name(rs.getString("cate_name"));
				dto.setCate_code(rs.getInt("cate_code"));
				dto.setCate_code_ref(rs.getInt("cate_code_ref"));
				
				list.add(dto);   
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	// 해당 카테고리 중분류 리스트 조회하는 메서드
	public List<CategoryDTO> SubCateList(int code) {
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		
		try {
			openConn();
			sql = "select * from book_category where cate_code_ref = ? order by cate_code";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				dto.setCate_name(rs.getString("cate_name"));
				dto.setCate_code(rs.getInt("cate_code"));
				dto.setCate_code_ref(rs.getInt("cate_code_ref"));
				
				list.add(dto);   
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	// 중고도서 상세페이지 카테고리
	public List<CategoryDTO> getBookCategory(int b_cate_fk) {
		int cate_code = 0;
		
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		
		openConn();
		
		try {
			sql = "select * from book_category where cate_code = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_cate_fk);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cate_code = rs.getInt("cate_code_ref");
			}
			
			sql = "select * from book_category where cate_code = ? or cate_code = ? order by cate_code";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_cate_fk);
			pstmt.setInt(2, cate_code);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				
				dto.setCate_name(rs.getString(1));
				dto.setCate_code(rs.getInt(2));
				dto.setCate_code_ref(rs.getInt(3));
				
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
	
	
	// 중분류에 해당하는 대분류 카테고리 가져오기
	public int getSubCate(int cate_code) {
		int cate = 0;
			
		openConn();
		
		sql = "select cate_code_ref from book_category where cate_code = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cate_code);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cate = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		
		return cate;
	}
	
	
}
