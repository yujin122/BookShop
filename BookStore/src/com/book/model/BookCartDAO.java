package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BookCartDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public BookCartDAO() {	}
	
	private static BookCartDAO instance = null;
	
	public static BookCartDAO getInstance() {
		if(instance == null) {
			instance = new BookCartDAO();
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
	
	// 장바구니 추가
	public int insertBookCart(BookCartDTO bc_dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
	
		try {
			sql = "select c_num from book_cart where c_member = ? and c_sale_num = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bc_dto.getC_member());
			pstmt.setInt(2, bc_dto.getC_sale_num());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = -1;
			}else {
				sql = "select max(c_num) from book_cart";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					cnt = rs.getInt(1)+1;
				}else {
					cnt = 1;
				}
				
				sql = "insert into book_cart values(?, ?, ?, ?, sysdate)";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cnt);
				pstmt.setInt(2, bc_dto.getC_sale_num());
				pstmt.setString(3, bc_dto.getC_member());
				pstmt.setInt(4, bc_dto.getC_qty());
				
				res = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 판매자가 중고도서 삭제시 해당 도서 삭제
	public void deleteBookCart(int num) {
		openConn();
		
		sql = "delete from book_cart where c_sale_num = ?";
		
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
	
	// 마이페이지 장바구니 개수
	public int cartCount(String mem_id) {
		int cnt = 0;

		openConn();
		
		sql = "select count(c_num) from book_cart where c_member = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cnt;
	}
	
	// 장바구니 조회
	public List<BookCartListDTO> getBookCart(String mem_id, int page, int rowsize) {
		List<BookCartListDTO> list = new ArrayList<BookCartListDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		
		openConn();
		
		sql = "select * from (select b_name, b_author, b_pub_company, b_pub_date, b_image, "
				+ "s_price, s_member, s_image, c_qty, c_date, mem_nickname, s_num, c_num, b_price, row_number() over(order by c_num desc) rnum "
				+ "from book_cart bc, book_info bi, book_sale bs, member m "
				+ "where bc.c_member = ? and bc.c_sale_num = bs.s_num and bs.s_bnum = bi.b_num and bs.s_member = m.mem_id)"
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookCartListDTO dto = new BookCartListDTO();
				
				dto.setB_name(rs.getString(1));
				dto.setB_author(rs.getString(2));
				dto.setB_pub_company(rs.getString(3));
				dto.setB_pub_date(rs.getString(4));
				dto.setB_image(rs.getString(5));
				dto.setS_price(rs.getInt(6));
				dto.setS_member(rs.getString(7));
				dto.setS_image(rs.getString(8));
				dto.setC_qty(rs.getInt(9));
				dto.setC_date(rs.getString(10));
				dto.setMem_nickname(rs.getString(11));
				dto.setS_num(rs.getInt(12));
				dto.setC_num(rs.getInt(13));
				dto.setB_price(rs.getInt(14));
				
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
	
	// 구매시 장바구니 삭제
	public void deleteCartBuy(int c_num) {
		openConn();
		
		sql = "delete from book_cart where c_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c_num);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}	
	}
	
	// 장바구니 테이블의 전체 리스트를 조회하는 메서드
	public List<BookCartDTO>getCartList() {
		List<BookCartDTO> list = new ArrayList<BookCartDTO>();
		
		
		try {
			openConn();
			sql = "select * from book_cart " + " order by c_num desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);	
		}
		return list;
	}
	
	// 장바구니 삭제
	public int deleteBookCart(String[] num) {
		int res = 0;
		
		openConn();
		
		try {
			for(int i = 0; i < num.length; i++) {
				sql = "delete from book_cart where c_num = ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(num[i]));
				
				res = pstmt.executeUpdate();
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 회원가입시 삭제
	public void deleteBookCart(String mem_id) {
		openConn();
		
		sql = "delete from book_cart where c_member = ?";
		
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
