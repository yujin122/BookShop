package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BookRequestDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public BookRequestDAO() {	}
	
	private static BookRequestDAO instance = null;
	
	public static BookRequestDAO getInstance() {
		if(instance == null) {
			instance = new BookRequestDAO();
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
	
	// 도서 요청 리스트
	public List<BookRequestDTO> getRequestList(int page, int rowsize) {
		List<BookRequestDTO> list = new ArrayList<BookRequestDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select r.*, m.mem_nickname, row_number() over(order by r.r_num desc) rnum "
				+ "from book_request r join member m on r.r_member = m.mem_id) "
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookRequestDTO dto = new BookRequestDTO();
				
				dto.setR_num(rs.getInt(1));
				dto.setR_member(rs.getString(2));
				dto.setR_book_title(rs.getString(3));
				dto.setR_pub_company(rs.getString(4));
				dto.setR_author(rs.getString(5));
				dto.setR_price(rs.getString(6));
				dto.setR_contact(rs.getString(7));
				dto.setR_title(rs.getString(8));
				dto.setR_contents(rs.getString(9));
				dto.setR_date(rs.getString(10));
				dto.setR_hit(rs.getInt(11));
				dto.setR_mem_nickname(rs.getString(12));
				
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
	
	// 도서 요청 추가
	public int insertBookRequest(BookRequestDTO dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
		
		try {
			sql = "select max(r_num) from book_request";
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into book_request values(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, default)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getR_member());
			pstmt.setString(3, dto.getR_book_title());
			pstmt.setString(4, dto.getR_pub_company());
			pstmt.setString(5, dto.getR_author());
			pstmt.setString(6, dto.getR_price());
			pstmt.setString(7, dto.getR_contact());
			pstmt.setString(8, dto.getR_title());
			pstmt.setString(9, dto.getR_contents());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}

	// 요청 게시물 개수
	public int getRequestCount() {
		int res = 0;
		
		openConn();
		
		sql = "select count(*) from book_request";
		
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

	// 상세페이지 불러오기
	public BookRequestDTO getRequestCont(int num) {
		BookRequestDTO dto = new BookRequestDTO();
		
		openConn();
		
		sql = "select r.*, mem_nickname from book_request r join "
				+ "member m on r.r_member = m.mem_id where r_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setR_num(rs.getInt(1));
				dto.setR_member(rs.getString(2));
				dto.setR_book_title(rs.getString(3));
				dto.setR_pub_company(rs.getString(4));
				dto.setR_author(rs.getString(5));
				dto.setR_price(rs.getString(6));
				dto.setR_contact(rs.getString(7));
				dto.setR_title(rs.getString(8));
				dto.setR_contents(rs.getString(9));
				dto.setR_date(rs.getString(10));
				dto.setR_hit(rs.getInt(11));
				dto.setR_mem_nickname(rs.getString(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	// 도서 요청 수정
	public int updateBookRequest(BookRequestDTO dto) {
		int res = 0;
		
		openConn();
		
		sql = "update book_request set r_book_title = ?, r_pub_company = ?,"
				+ " r_author = ?, r_price = ?, r_contact = ?, r_title = ?, r_contents = ? "
				+ "where r_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getR_book_title());
			pstmt.setString(2, dto.getR_pub_company());
			pstmt.setString(3, dto.getR_author());
			pstmt.setString(4, dto.getR_price());
			pstmt.setString(5, dto.getR_contact());
			pstmt.setString(6, dto.getR_title());
			pstmt.setString(7, dto.getR_contents());
			pstmt.setInt(8, dto.getR_num());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return res;
	}
	
	// 삭제
	public int deleteBookRequest(int num) {
		int res = 0;
		
		openConn();
		
		sql = "delete from book_request where r_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}		
		
		return res;
	}
	
	// 검색
	public List<BookRequestDTO> searchBookRequest(int page, int rowsize, String search_label, String search_txt) {
		List<BookRequestDTO> list = new ArrayList<BookRequestDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		try {
			if(search_label.equals("title")) {
				sql = "select * from (select r.*, mem_nickname, row_number() over(order by r.r_num desc) rnum "
						+ "from book_request r join member m on r.r_member = m.mem_id where r_title like ?) "
						+ "where rnum >= ? and rnum <= ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);
				
			}else if(search_label.equals("cont")) {
				sql = "select * from (select r.*, mem_nickname, row_number() over(order by r.r_num desc) rnum "
						+ "from book_request r join member m on r.r_member = m.mem_id where r_contents like ?) "
						+ "where rnum >= ? and rnum <= ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);
				
			}else if(search_label.equals("writer")) {
				sql = "select * from (select r.*, mem_nickname, row_number() over(order by r.r_num desc) rnum "
						+ "from book_request r join member m on r.r_member = m.mem_id where r_member like ? or mem_nickname like ?) "
						+ "where rnum >= ? and rnum <= ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				pstmt.setString(2, "%"+search_txt+"%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);
				
			}else if(search_label.equals("title_cont")) {
				sql = "select * from (select r.*, mem_nickname, row_number() over(order by r.r_num desc) rnum "
						+ "from book_request r join member m on r.r_member = m.mem_id where r_title like ? or r_contents = ?) "
						+ "where rnum >= ? and rnum <= ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				pstmt.setString(2, "%"+search_txt+"%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);
				
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookRequestDTO dto = new BookRequestDTO();
				
				dto.setR_num(rs.getInt(1));
				dto.setR_member(rs.getString(2));
				dto.setR_book_title(rs.getString(3));
				dto.setR_pub_company(rs.getString(4));
				dto.setR_author(rs.getString(5));
				dto.setR_price(rs.getString(6));
				dto.setR_contact(rs.getString(7));
				dto.setR_title(rs.getString(8));
				dto.setR_contents(rs.getString(9));
				dto.setR_date(rs.getString(10));
				dto.setR_hit(rs.getInt(11));
				dto.setR_mem_nickname(rs.getString(12));
				
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
	
	// 검색 페이지 카운트
	public int getRequestCount(String search_label, String search_txt) {
		int res = 0;
		
		openConn();
		
		try {
			if(search_label.equals("title")) {
				sql = "select count(*) from book_request where r_title like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				
			}else if(search_label.equals("cont")) {
				sql = "select count(*) from book_request where r_contents like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				
			}else if(search_label.equals("writer")) {
				sql = "select count(*) from book_request where r_member like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				
			}else if(search_label.equals("title_cont")) {
				sql = "select count(*) from book_request where r_title like ? or r_contents like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_txt+"%");
				pstmt.setString(2, "%"+search_txt+"%");
			}
			
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

	public void hitBookRequest(int num) {
		openConn();
		
		sql = "update book_request set r_hit = r_hit+1 where r_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
	}
	
	// 회원탈퇴시 삭제
	public void deleteBookRequest(String mem_id) {
		openConn();
		
		sql = "delete from book_request where r_member = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
}
