package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BookNoticeDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public BookNoticeDAO() {	}
	
	private static BookNoticeDAO instance = null;
	
	public static BookNoticeDAO getInstance() {
		if(instance == null) {
			instance = new BookNoticeDAO();
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
	
	// 공지사항 리스트
	public List<BookNoticeDTO> noticeList(int page, int rowsize) {
		List<BookNoticeDTO> list = new ArrayList<BookNoticeDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		try {
			sql = "select * from (select n.*, row_number() over(order by n.n_num desc) rnum from book_notice n) "
				     + "where rnum >= ? and rnum <= ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookNoticeDTO dto = new BookNoticeDTO();
				
				dto.setN_num(rs.getInt("n_num"));
				dto.setN_title(rs.getString("n_title"));
				dto.setN_cont(rs.getString("n_cont"));
				dto.setN_date(rs.getString("n_date"));
				dto.setN_hit(rs.getInt("n_hit"));
				
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
	
	// 공지사항 게시물 개수
	public int getNoticeCount() {
		int res = 0;
		
		openConn();
		
		try {
			sql = "select count(*) from book_notice";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next() ) {
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
	
	// 검색 페이지 게시물 개수
	public int getNoticeSearchCount(String search_list, String search_word) {
		int res = 0;
		
		openConn();
		
		try {
			if(search_list.equals("title")) {
				sql = "select count(*) from book_notice where n_title like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_word+"%");
			}else if(search_list.equals("cont")) {
				sql = "select count(*) from book_notice where n_cont like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_word+"%");
			}else if(search_list.equals("title_cont")) {
				sql = "select count(*) from book_notice where n_title like ? or n_cont like ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_word+"%");
				pstmt.setString(2, "%"+search_word+"%");
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
	
	// 공지 검색
	public List<BookNoticeDTO> searchNotice(int page, int rowsize, String search_list, String search_word) {
		List<BookNoticeDTO> list = new ArrayList<BookNoticeDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		try {
			if(search_list.equals("title")) {
				sql = "select * from (select n.*, row_number() over(order by n.n_num desc) rnum "
						+ "from book_notice n where n_title like ?) "
						+ "where rnum >= ? and rnum <= ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_word+"%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);
				
			}else if(search_list.equals("cont")) {
				sql = "select * from (select n.*, row_number() over(order by n.n_num desc) rnum "
						+ "from book_notice n where n_cont like ?) "
						+ "where rnum >= ? and rnum <= ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_word+"%");
				pstmt.setInt(2, startNo);
				pstmt.setInt(3, endNo);
				
			}else if(search_list.equals("title_cont")) {
				sql = "select * from (select n.*, row_number() over(order by n.n_num desc) rnum "
						+ "from book_notice n where n_title like ? or n_cont = ?) "
						+ "where rnum >= ? and rnum <= ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+search_word+"%");
				pstmt.setString(2, "%"+search_word+"%");
				pstmt.setInt(3, startNo);
				pstmt.setInt(4, endNo);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookNoticeDTO dto = new BookNoticeDTO();
				dto.setN_num(rs.getInt("n_num"));
				dto.setN_title(rs.getString("n_title"));
				dto.setN_cont(rs.getString("n_cont"));
				dto.setN_date(rs.getString("n_date"));
				dto.setN_hit(rs.getInt("n_hit"));
				
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
	
	// 상세 내용
	public BookNoticeDTO getNoticeCont(int num) {
		BookNoticeDTO dto = new BookNoticeDTO();
		
		openConn();
		
		try {
			sql = "select * from book_notice where n_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setN_num(rs.getInt("n_num"));
				dto.setN_title(rs.getString("n_title"));
				dto.setN_cont(rs.getString("n_cont"));
				dto.setN_date(rs.getString("n_date"));
				dto.setN_hit(rs.getInt("n_hit"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return dto;
	}
	
	// 공지사항 수정
	public int updateNotice(BookNoticeDTO dto) {
		int res = 0;
		
		openConn();
		
		try {
			sql = "update book_notice set n_title = ?, n_cont = ? where n_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getN_title());
			pstmt.setString(2, dto.getN_cont());
			pstmt.setInt(3, dto.getN_num());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return res;
	}
	
	
	// 공지사항 삭제
	public int deleteNotice(int num) {
		int res = 0;
		
		openConn();
		
		try {
			sql = "delete from book_notice where n_num = ?";
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
	
	// 공지사항 추가
	public int insertNotice(BookNoticeDTO dto) {
		int res = 0;
		
		openConn();
		
		try {
			sql = "insert into book_notice values(notice_seq.nextval,?,?,sysdate,default)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getN_title());
			pstmt.setString(2, dto.getN_cont());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return res;
	}
	
	// 조회수
	public void hit_bookNotice(int num) {
		openConn();
		
		try {
			sql = "update book_notice set n_hit = n_hit + 1 where n_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
	
	
	
}
