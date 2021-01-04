package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class BookInfoDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public BookInfoDAO() {	}
	
	private static BookInfoDAO instance = null;
	
	public static BookInfoDAO getInstance() {
		if(instance == null) {
			instance = new BookInfoDAO();
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
	
	// 책 정보 가저오기
	public BookInfoDTO getBookInfo(int b_num) {
		BookInfoDTO dto = new BookInfoDTO();
		
		openConn();
		
		sql = "select * from book_info where b_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setB_num(rs.getInt(1));
				dto.setB_cate_fk(rs.getInt(2));
				dto.setB_isbn(rs.getString(3));
				dto.setB_name(rs.getString(4));
				dto.setB_author(rs.getString(5));
				dto.setB_translator(rs.getString(6));
				dto.setB_pub_company(rs.getString(7));
				dto.setB_pub_date(rs.getString(8));
				dto.setB_image(rs.getString(9));
				dto.setB_price(rs.getInt(10));
				dto.setB_contents(rs.getString(11));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
		
	}
	
	// 이미 데이터가 존재하는지 확인
	public int checkBookInfo(BookInfoDTO binfo_dto) {
		int res = 0;
		
		openConn();
	
		try {
			sql = "select b_num from book_info where b_cate_fk = ? and b_name = ? and b_author = ? and b_pub_company = ? "
					+ "and b_pub_date = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, binfo_dto.getB_cate_fk());
			pstmt.setString(2, binfo_dto.getB_name());
			pstmt.setString(3, binfo_dto.getB_author());
			pstmt.setString(4, binfo_dto.getB_pub_company());
			pstmt.setString(5, binfo_dto.getB_pub_date());
			
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
	
	// 책 정보 insert
	public int insertBookInfo(BookInfoDTO binfo_dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
	
		try {
			sql = "select max(b_num) from book_info";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into book_info values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, cnt);
			pstmt.setInt(2, binfo_dto.getB_cate_fk());
			pstmt.setString(3, binfo_dto.getB_isbn());
			pstmt.setString(4, binfo_dto.getB_name());
			pstmt.setString(5, binfo_dto.getB_author());
			pstmt.setString(6, binfo_dto.getB_translator());
			pstmt.setString(7, binfo_dto.getB_pub_company());
			pstmt.setString(8, binfo_dto.getB_pub_date());
			pstmt.setString(9, binfo_dto.getB_image());
			pstmt.setInt(10, binfo_dto.getB_price());
			pstmt.setString(11, binfo_dto.getB_contents());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		
		return res;
	}
	
	// 조회수에 따라 리스트를 구하는 메서드.
	public List<ListDTO> getList_By_CatenHit() {
		List<ListDTO> list = new ArrayList<ListDTO>();
		
		try {
			openConn();
			for(int i = 100; i<=600; i= i+100) {
				sqlByCateCode(list, i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	public void sqlByCateCode(List<ListDTO> list, int cate_code) {
		
		sql = "select * from "
				+ "(select s.*, i.*, c.*, row_number() over(order by s.s_hit desc) rnum"
				+ " from book_sale s, book_info i, book_category c where i.b_num = s.s_bnum and i.b_cate_fk = c.cate_code"
				+ " and (c.cate_code = "+cate_code+" or c.cate_code_ref = "+cate_code+"))"
				+ " where rnum <=2";
		
		try {
			openConn();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ListDTO dto = new ListDTO();
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_cate_fk(rs.getInt("b_cate_fk"));
				dto.setB_isbn(rs.getString("b_isbn"));
				dto.setB_name(rs.getString("b_name"));
				dto.setB_author(rs.getString("b_author"));
				dto.setB_translator(rs.getString("b_translator"));
				dto.setB_pub_company(rs.getString("b_pub_company"));
				dto.setB_pub_date(rs.getString("b_pub_date"));
				dto.setB_image(rs.getString("b_image"));
				dto.setB_price(rs.getInt("b_price"));
				dto.setB_contents(rs.getString("b_contents"));
				dto.setS_num(rs.getInt("s_num"));
				dto.setS_member(rs.getString("s_member"));
				dto.setS_bnum(rs.getInt("s_bnum"));
				dto.setS_qty(rs.getInt("s_qty"));
				dto.setS_state(rs.getString("s_state"));
				dto.setS_quality(rs.getString("s_quality"));
				dto.setS_price(rs.getInt("s_price"));
				dto.setS_contents(rs.getString("s_contents"));
				dto.setS_image(rs.getString("s_image"));
				dto.setS_date(rs.getString("s_date"));
				dto.setS_charge(rs.getInt("s_charge"));
				dto.setS_direct(rs.getString("s_direct"));
				dto.setS_hit(rs.getInt("s_hit"));
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
	
	// 조회수에 따라 JSON를 구하는 메서드.
	public JSONArray getJson_By_CatenHit() {
		JSONArray array = new JSONArray();
		try {
		
			for(int i = 100; i<=600; i= i+100) {
				sqlByCateCodeHitJson(array, i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array;
	}
	
	public void sqlByCateCodeHitJson(JSONArray array, int cate_code) {
		sql = "select * from "
				+ "(select s.*, i.*, c.*, row_number() over(order by s.s_hit desc) rnum"
				+ " from book_sale s, book_info i, book_category c where i.b_num = s.s_bnum and i.b_cate_fk = c.cate_code"
				+ " and (c.cate_code = "+cate_code+" or c.cate_code_ref = "+cate_code+"))"
				+ " where rnum <=2";
		
		try {
			openConn();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				JSONObject object = new JSONObject();
				object.put("b_num", rs.getInt("b_num"));
				object.put("b_cate_fk", rs.getInt("b_cate_fk"));
				object.put("b_isbn",rs.getString("b_isbn"));
				object.put("b_name", rs.getString("b_name"));
				object.put("b_image", rs.getString("b_image"));
				object.put("s_image", rs.getString("s_image"));
				object.put("s_num", rs.getInt("s_num"));
				array.add(object);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
	
	// 최신순에 따라 리스트를 구하는 메서드.
	public List<ListDTO> getList_By_CatenNum() {
		List<ListDTO> list = new ArrayList<ListDTO>();
		
		try {
			openConn();
			for(int i = 100; i<=600; i= i+100) {
				sqlByCateCode(list, i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	public void sqlByCateCodeNum(List<ListDTO> list, int cate_code) {
		sql = "select * from "
				+ "(select s.*, i.*, c.*, row_number() over(order by s.s_hit desc) rnum"
				+ " from book_sale s, book_info i, book_category c where i.b_num = s.s_bnum and i.b_cate_fk = c.cate_code"
				+ " and (c.cate_code = "+cate_code+" or c.cate_code_ref = "+cate_code+"))"
				+ " where rnum <=2";
		
		try {
			openConn();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
					
				ListDTO dto = new ListDTO();
				dto.setB_num(rs.getInt("b_num"));
				dto.setB_cate_fk(rs.getInt("b_cate_fk"));
				dto.setB_isbn(rs.getString("b_isbn"));
				dto.setB_name(rs.getString("b_name"));
				dto.setB_author(rs.getString("b_author"));
				dto.setB_translator(rs.getString("b_translator"));
				dto.setB_pub_company(rs.getString("b_pub_company"));
				dto.setB_pub_date(rs.getString("b_pub_date"));
				dto.setB_image(rs.getString("b_image"));
				dto.setB_price(rs.getInt("b_price"));
				dto.setB_contents(rs.getString("b_contents"));
				dto.setS_num(rs.getInt("s_num"));
				dto.setS_member(rs.getString("s_member"));
				dto.setS_bnum(rs.getInt("s_bnum"));
				dto.setS_qty(rs.getInt("s_qty"));
				dto.setS_state(rs.getString("s_state"));
				dto.setS_quality(rs.getString("s_quality"));
				dto.setS_price(rs.getInt("s_price"));
				dto.setS_contents(rs.getString("s_contents"));
				dto.setS_image(rs.getString("s_image"));
				dto.setS_date(rs.getString("s_date"));
				dto.setS_charge(rs.getInt("s_charge"));
				dto.setS_direct(rs.getString("s_direct"));
				dto.setS_hit(rs.getInt("s_hit"));
				list.add(dto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
	}
	
	// 최신순에 따라 JSON를 구하는 메서드.
	public JSONArray getJson_By_CatenNum() {
		JSONArray array = new JSONArray();
		
		try {
			for(int i = 100; i<=600; i= i+100) {
				sqlByCateCodeNumJson(array, i);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	
	public void sqlByCateCodeNumJson(JSONArray array, int cate_code) {
		sql = "select * from "
				+ "(select s.*, i.*, c.*, row_number() over(order by s.s_hit desc) rnum"
				+ " from book_sale s, book_info i, book_category c where i.b_num = s.s_bnum and i.b_cate_fk = c.cate_code"
				+ " and (c.cate_code = "+cate_code+" or c.cate_code_ref = "+cate_code+"))"
				+ " where rnum <=2";
		
		try {
			openConn();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
	
			while(rs.next()) {
					
				JSONObject object = new JSONObject();
				object.put("b_num", rs.getInt("b_num"));
				object.put("b_cate_fk", rs.getInt("b_cate_fk"));
				object.put("b_isbn",rs.getString("b_isbn"));
				object.put("b_name", rs.getString("b_name"));
				object.put("b_image", rs.getString("b_image"));
				object.put("s_image", rs.getString("s_image"));
				object.put("s_num", rs.getInt("s_num"));
				array.add(object);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
	}
}
