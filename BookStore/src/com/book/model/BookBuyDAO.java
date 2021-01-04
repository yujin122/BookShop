package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BookBuyDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public BookBuyDAO() {	}
	
	private static BookBuyDAO instance = null;
	
	public static BookBuyDAO getInstance() {
		if(instance == null) {
			instance = new BookBuyDAO();
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
	
	// 중고 도서 구매 추가
	public int insertBookBuy(BookBuyDTO dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
		
		try {
			sql = "select max(buy_num) from book_buy";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into book_buy values(?, ?, ?, ?, ?, ?, ?, ?, default, sysdate)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, cnt);
			pstmt.setInt(2, dto.getBuy_snum());
			pstmt.setString(3, dto.getBuy_member());
			pstmt.setString(4, dto.getBuy_name());
			pstmt.setInt(5, dto.getBuy_qty());
			pstmt.setString(6, dto.getBuy_trans());
			pstmt.setString(7, dto.getBuy_phone());
			pstmt.setString(8, dto.getBuy_request());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 구매 리스트
	public List<BookBuyListDTO> getBookBuyList(String mem_id, int page, int rowsize) {
		List<BookBuyListDTO> list = new ArrayList<BookBuyListDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select buy_num, b_image, b_name, b_author, b_pub_company, b_price, "
				+ "s_price, s_charge, buy_qty, buy_state, s_member, buy_date, s_image, mem_nickname, b_pub_date, "
				+ "row_number() over(order by buy_num desc) rnum "
				+ "from book_info bi, book_sale bs, book_buy bb, member m "
				+ "where bb.buy_snum = bs.s_num and bi.b_num = bs.s_bnum and bs.s_member = m.mem_id and bb.buy_member = ?)"
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBuyListDTO dto = new BookBuyListDTO();
				
				dto.setBuy_num(rs.getInt(1));
				dto.setB_image(rs.getString(2));
				dto.setB_name(rs.getString(3));
				dto.setB_author(rs.getString(4));
				dto.setB_pub_company(rs.getString(5));
				dto.setB_price(rs.getInt(6));
				dto.setS_price(rs.getInt(7));
				dto.setS_charge(rs.getInt(8));
				dto.setBuy_qty(rs.getInt(9));
				dto.setBuy_state(rs.getString(10));
				dto.setS_member(rs.getString(11));
				dto.setBuy_date(rs.getString(12));
				dto.setS_image(rs.getString(13));
				dto.setMem_nickname(rs.getString(14));
				dto.setB_pub_date(rs.getString(15));
				
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
	
	// 구매 관리 메뉴에 구매리스트
	public List<BookBuyListDTO> getBookBuyList(String mem_id, String menu, int page, int rowsize) {
		
		List<BookBuyListDTO> list = new ArrayList<BookBuyListDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select buy_num, b_image, b_name, b_author, b_pub_company, b_price, "
				+ "s_price, s_charge, buy_qty, buy_state, s_member, buy_date, s_image, mem_nickname, b_pub_date, "
				+ "row_number() over(order by buy_num desc) rnum "
				+ "from book_info bi, book_sale bs, book_buy bb, member m "
				+ "where bb.buy_snum = bs.s_num and bi.b_num = bs.s_bnum and bs.s_member = m.mem_id and bb.buy_member = ? and bb.buy_state = ?)"
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, menu);
			pstmt.setInt(3, startNo);
			pstmt.setInt(4, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBuyListDTO dto = new BookBuyListDTO();
				
				dto.setBuy_num(rs.getInt(1));
				dto.setB_image(rs.getString(2));
				dto.setB_name(rs.getString(3));
				dto.setB_author(rs.getString(4));
				dto.setB_pub_company(rs.getString(5));
				dto.setB_price(rs.getInt(6));
				dto.setS_price(rs.getInt(7));
				dto.setS_charge(rs.getInt(8));
				dto.setBuy_qty(rs.getInt(9));
				dto.setBuy_state(rs.getString(10));
				dto.setS_member(rs.getString(11));
				dto.setBuy_date(rs.getString(12));
				dto.setS_image(rs.getString(13));
				dto.setMem_nickname(rs.getString(14));
				dto.setB_pub_date(rs.getString(15));
				
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
	
	// 구매 데이터 가져오기
	public BookBuyDTO getBookBuy(int num) {
		BookBuyDTO dto = new BookBuyDTO();
		
		openConn();
		
		sql = "select * from book_buy where buy_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				dto.setBuy_num(rs.getInt(1));
				dto.setBuy_snum(rs.getInt(2));
				dto.setBuy_member(rs.getString(3));
				dto.setBuy_name(rs.getString(4));
				dto.setBuy_qty(rs.getInt(5));
				dto.setBuy_trans(rs.getString(6));
				dto.setBuy_phone(rs.getString(7));
				dto.setBuy_request(rs.getString(8));
				dto.setBuy_state(rs.getString(9));
				dto.setBuy_date(rs.getString(10));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	// 구매 정보 수정
	public int updateBookBuy(BookBuyDTO dto) {
		int res = 0;
		
		openConn();
		
		sql = "update book_buy set buy_name = ?, buy_trans = ?, buy_phone = ?, buy_request = ? where buy_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getBuy_name());
			pstmt.setString(2, dto.getBuy_trans());
			pstmt.setString(3, dto.getBuy_phone());
			pstmt.setString(4, dto.getBuy_request());
			pstmt.setInt(5, dto.getBuy_num());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		
		return res;
	}
	
	// 구매 취소
	public int updateBookBuyCancle(int num) {
		int res = 0;
		
		openConn();
		
		sql = "update book_buy set buy_state = '취소' where buy_num = ?";
		
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
	
	// 판매 관리 메뉴에 구매리스트
	public List<BookBuyListDTO> getBookSaleList(String mem_id, String menu, int page, int rowsize) {
		List<BookBuyListDTO> list = new ArrayList<BookBuyListDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select buy_num, b_image, b_name, b_author, b_pub_company, b_price, "
				+ "s_price, s_charge, buy_qty, buy_state, s_member, buy_date, buy_member, s_image, mem_nickname, b_pub_date, "
				+ "row_number() over(order by buy_num desc) rnum "
				+ "from book_info bi, book_sale bs, book_buy bb, member m "
				+ "where bb.buy_snum = bs.s_num and bi.b_num = bs.s_bnum and buy_member = m.mem_id and bs.s_member = ? and bb.buy_state = ?)"
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, menu);
			pstmt.setInt(3, startNo);
			pstmt.setInt(4, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBuyListDTO dto = new BookBuyListDTO();
				
				dto.setBuy_num(rs.getInt(1));
				dto.setB_image(rs.getString(2));
				dto.setB_name(rs.getString(3));
				dto.setB_author(rs.getString(4));
				dto.setB_pub_company(rs.getString(5));
				dto.setB_price(rs.getInt(6));
				dto.setS_price(rs.getInt(7));
				dto.setS_charge(rs.getInt(8));
				dto.setBuy_qty(rs.getInt(9));
				dto.setBuy_state(rs.getString(10));
				dto.setS_member(rs.getString(11));
				dto.setBuy_date(rs.getString(12));
				dto.setBuy_member(rs.getString(13));
				dto.setS_image(rs.getString(14));
				dto.setMem_nickname(rs.getString(15));
				dto.setB_pub_date(rs.getString(16));
				
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
	
	// 구매(판매) 완료
	public int updateBookBuyComp(int num) {
		int res = 0;
		
		openConn();
		
		sql = "update book_buy set buy_state = '완료' where buy_num = ?";
		
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

	// 삭제 시 구매요청(신청)이 있을시 삭제 X
	public int checkState(int num) {
		int res = 0;
		
		openConn();
	
		try {
			sql = "select buy_num from book_buy where buy_state = ? and buy_snum = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "신청");
			pstmt.setInt(2, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = 1;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}

	// 삭제
	public void deleteBookBuy(int num) {
		openConn();
		
		sql = "delete from book_buy where buy_snum = ?";
		
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
	
	// 마이페이지 구매 개수
	public int getmyBuyCount(String mem_id) {
		int res = 0;
		
		openConn();
		
		sql = "select count(buy_num) from book_buy where buy_member = ? and buy_state = '완료'";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
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
	
	// 구매 리스트 개수
	public int getBookBuyCount(String mem_id) {
		int res = 0;
		
		openConn();
		
		sql = "select count(buy_num) from book_buy where buy_member = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
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
	
	// 카테고리에 따른 구매 리스트 개수
	public int getBookBuyMenuCount(String mem_id, String menu) {
		int res = 0;
		
		openConn();
		
		sql = "select count(buy_num) from book_buy where buy_member = ? and buy_state = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, menu);
			
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
	
	// 판매관리 구매 리스트
	public int getBookSaleCount(String mem_id, String menu) {
		int res = 0;
		
		openConn();
		
		sql = "select count(buy_num)"
				+ "from book_info bi, book_sale bs, book_buy bb, member m "
				+ "where bb.buy_snum = bs.s_num and bi.b_num = bs.s_bnum and bb.buy_member = m.mem_id and bs.s_member = ? and bb.buy_state = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, menu);
			
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

	public void deleteBookBuy(String mem_id) {
		openConn();
		
		sql = "delete from book_buy where buy_member = ?";
		
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
	
	// 날짜별 검색 카운트
	public int getBookBuySearchCount(String mem_id, String date1, String date2) {
		int res = 0;
		
		openConn();
		
		sql = "select count(buy_num) from book_buy "
				+ "where buy_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')+0.99999"
				+ "and buy_member = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, date1);
			pstmt.setString(2, date2);
			pstmt.setString(3, mem_id);
			
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

	// 날짜별 검색
	public List<BookBuyListDTO> getBookBuySearchList(String mem_id, int page, int rowsize, String date1, String date2) {
		List<BookBuyListDTO> list = new ArrayList<BookBuyListDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select buy_num, b_image, b_name, b_author, b_pub_company, b_price, "
				+ "s_price, s_charge, buy_qty, buy_state, s_member, buy_date, s_image, mem_nickname, b_pub_date, "
				+ "row_number() over(order by buy_num desc) rnum "
				+ "from book_info bi, book_sale bs, book_buy bb, member m "
				+ "where bb.buy_snum = bs.s_num and bi.b_num = bs.s_bnum and bs.s_member = m.mem_id and bb.buy_member = ?"
				+ "and bb.buy_date BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')+0.99999)"
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, date1);
			pstmt.setString(3, date2);
			pstmt.setInt(4, startNo);
			pstmt.setInt(5, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookBuyListDTO dto = new BookBuyListDTO();
				
				dto.setBuy_num(rs.getInt(1));
				dto.setB_image(rs.getString(2));
				dto.setB_name(rs.getString(3));
				dto.setB_author(rs.getString(4));
				dto.setB_pub_company(rs.getString(5));
				dto.setB_price(rs.getInt(6));
				dto.setS_price(rs.getInt(7));
				dto.setS_charge(rs.getInt(8));
				dto.setBuy_qty(rs.getInt(9));
				dto.setBuy_state(rs.getString(10));
				dto.setS_member(rs.getString(11));
				dto.setBuy_date(rs.getString(12));
				dto.setS_image(rs.getString(13));
				dto.setMem_nickname(rs.getString(14));
				dto.setB_pub_date(rs.getString(15));
				
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
}
