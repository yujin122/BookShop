package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;


public class BookSaleDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public BookSaleDAO() {	}
	
	private static BookSaleDAO instance = null;
	
	public static BookSaleDAO getInstance() {
		if(instance == null) {
			instance = new BookSaleDAO();
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
	
	//카테고리 코드 없이 검색하는 메서드.
	public List<ListDTO> searchBook_Main(String book_sale_search_category, String book_sale_search_item) {
		List<ListDTO> list = new ArrayList<ListDTO>();
		
		try {
			openConn();
			if(book_sale_search_category.equals("b_isbn")) {
				sql = "select * from book_info, book_sale, member where b_isbn like ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+book_sale_search_item+"%");
			}else if(book_sale_search_category.equals("b_name")) {
				sql = "select * from book_info, book_sale, member where b_name like ? and book_info.b_num = book_sale.s_bnum  and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+book_sale_search_item+"%");
			}else if(book_sale_search_category.equals("b_author")) {
				sql = "select * from book_info, book_sale, member where b_author like ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+book_sale_search_item+"%");
			}else if(book_sale_search_category.equals("b_pub_company")) {
				sql = "select * from book_info, book_sale, member where b_pub_company like ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, "%"+book_sale_search_item+"%");
			}
			
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
				dto.setMem_nickname(rs.getString("mem_nickname"));
				
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
	
	
	// 위의 메서드를 오버로딩한 메서드. 카테고리 코드를 포함해서 검색하는 메서드.
	public List<ListDTO> searchBook_Main(String book_sale_search_category, String book_sale_search_item,int cate_code) {
		List<ListDTO> list = new ArrayList<ListDTO>();
		try {
			openConn();
			if(cate_code==100 || cate_code==200 || cate_code==300|| cate_code==400|| cate_code==500|| cate_code==600) {
				if(book_sale_search_category.equals("b_isbn")) {
					sql = "select * from book_info, book_sale, member where b_isbn like ? and b_cate_fk like ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setString(2, "%"+ Integer.toString(cate_code).substring(0, 2) +"%");
				}else if(book_sale_search_category.equals("b_name")) {
					sql = "select * from book_info, book_sale, member where b_name like ? and b_cate_fk like ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setString(2, "%"+ Integer.toString(cate_code).substring(0, 2) +"%");
				}else if(book_sale_search_category.equals("b_author")) {
					sql = "select * from book_info, book_sale, member where b_author like ? and b_cate_fk like ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setString(2, "%"+ Integer.toString(cate_code).substring(0, 2) +"%");
				}else if(book_sale_search_category.equals("b_pub_company")) {
					sql = "select * from book_info, book_sale, member where b_pub_company like ? and b_cate_fk like ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setString(2, "%"+ Integer.toString(cate_code).substring(0, 2) +"%");
				}
			}else {
				if(book_sale_search_category.equals("b_isbn")) {
					sql = "select * from book_info, book_sale, member where b_isbn like ? and b_cate_fk = ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setInt(2, cate_code);
				}else if(book_sale_search_category.equals("b_name")) {
					sql = "select * from book_info, book_sale, member where b_name like ? and b_cate_fk = ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setInt(2, cate_code);
				}else if(book_sale_search_category.equals("b_author")) {
					sql = "select * from book_info, book_sale, member where b_author like ? and b_cate_fk = ? and book_info.b_num = book_sale.s_bnum and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setInt(2, cate_code);
				}else if(book_sale_search_category.equals("b_pub_company")) {
					sql = "select * from book_info, book_sale, member where b_pub_company like ? and b_cate_fk = ? and book_info.b_num = book_sale.s_bnum  and member.mem_id = book_sale.s_member order by book_sale.s_date desc";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+book_sale_search_item+"%");
					pstmt.setInt(2, cate_code);
				}
			}
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
				dto.setMem_nickname(rs.getString("mem_nickname"));
				
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
	
	// 나머지 카테고리 상품 리스트 조회 메서드
	public List<ListDTO> prodListOther(int code, int page, int rowsize) {
	List<ListDTO> list = new ArrayList<ListDTO>();
	
	int startNo = (page * rowsize) - (rowsize - 1);
	int endNo = (page * rowsize);
	
	try {
		openConn();
		
		sql = "select * from (select s.*, book_info.*, member.mem_nickname, row_number() over(order by s.s_num desc) rnum " + 
				"from book_sale s inner join book_info on s.s_bnum = book_info.b_num "
				+ "inner join book_category on book_info.b_cate_fk = book_category.cate_code "
				+ "inner join member on member.mem_id = s.s_member where cate_code_ref = ?) " + 
				"where rnum >= ? and rnum <= ?";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, code);
		pstmt.setInt(2, startNo);
		pstmt.setInt(3, endNo);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			ListDTO dto = new ListDTO();
			
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
			dto.setS_hit(rs.getInt("s_hit"));
			
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
			
			dto.setMem_nickname(rs.getString("mem_nickname"));
			
			/*dto.setCate_name(rs.getString("cate_name"));
			dto.setCate_code(rs.getInt("cate_code"));
			dto.setCate_code_ref(rs.getInt("cate_code_ref"));*/
			
			list.add(dto);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}	
	
	
	// 전체 카테고리 클릭시 보여지는 상품 리스트 메서드
	public List<ListDTO> prodList(int page, int rowsize) {
	List<ListDTO> list = new ArrayList<ListDTO>();

	int startNo = (page * rowsize) - (rowsize - 1);
	int endNo = (page * rowsize);
	
	try {
		openConn();
		
		sql = "select * from (select book_sale.*, book_info.*, member.mem_nickname, row_number() over(order by book_sale.s_num desc) rnum " +
				"from book_sale inner join book_info on book_sale.s_bnum = book_info.b_num " +
				"inner join member on member.mem_id = book_sale.s_member) " + 
				"where rnum >= ? and rnum <= ?";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, startNo);
		pstmt.setInt(2, endNo);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			ListDTO dto = new ListDTO();
			
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
			dto.setS_hit(rs.getInt("s_hit"));
			
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
			
			dto.setMem_nickname(rs.getString("mem_nickname"));
		
			list.add(dto);
		}} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return list;
	}
	
	// 중분류 카테고리 상품 리스트 조회 메서드
	public List<ListDTO> middleProdList(int code, int page, int rowsize) {
		List<ListDTO> list = new ArrayList<ListDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		try {
			openConn();
			sql = "select * from (select s.*, book_info.*, member.mem_nickname, row_number() over(order by s.s_num desc) rnum " + 
				  "from book_sale s inner join book_info on book_info.b_num = s.s_bnum "
				  + "inner join member on member.mem_id = s.s_member "
				  + "where book_info.b_cate_fk = ?) " 
				  + "where rnum >= ? and rnum <= ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, code);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ListDTO dto = new ListDTO();
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
				dto.setS_hit(rs.getInt("s_hit"));
				
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
				
				dto.setMem_nickname(rs.getString("mem_nickname"));
				
				list.add(dto);
				
			}} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				closeConn(rs, pstmt, con);
			}
			return list;
		}
	
	// 해당도서 판매 정보 
	public BookSaleDTO getBookSale(int s_num) {
		BookSaleDTO dto = new BookSaleDTO();
		
		openConn();
		
		sql = "select * from book_sale where s_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, s_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setS_num(rs.getInt(1));
				dto.setS_member(rs.getString(2));
				dto.setS_bnum(rs.getInt(3));
				dto.setS_qty(rs.getInt(4));
				dto.setS_state(rs.getString(5));
				dto.setS_quality(rs.getString(6));
				dto.setS_price(rs.getInt(7));
				dto.setS_charge(rs.getInt(8));
				dto.setS_direct(rs.getString(9));
				dto.setS_contents(rs.getString(10));
				dto.setS_image(rs.getString(11));
				dto.setS_date(rs.getString(12));
				dto.setS_hit(rs.getInt(13));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
	}
	
	// 구매시 수량 변경(차감)
	public void buySaleQty(int s_num, int qty) {
		openConn();
		
		sql = "update book_sale set s_qty = s_qty-? where s_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qty);
			pstmt.setInt(2, s_num);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		
	}
	
	// 구매 취소시 수량 변경
	public void cancleSaleQty(int num, int qty) {
		openConn();
		
		sql = "update book_sale set s_qty = s_qty+? where s_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qty);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
	}
	
	// 중고도서 추가
	public int insertBookSale(BookSaleDTO bsale_dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
		
		try {
			sql = "select max(s_num) from book_sale";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into book_sale values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, default)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, bsale_dto.getS_member());
			pstmt.setInt(3, bsale_dto.getS_bnum());
			pstmt.setInt(4, bsale_dto.getS_qty());
			pstmt.setString(5, bsale_dto.getS_state());
			pstmt.setString(6, bsale_dto.getS_quality());
			pstmt.setInt(7, bsale_dto.getS_price());
			pstmt.setInt(8, bsale_dto.getS_charge());
			pstmt.setString(9, bsale_dto.getS_direct());
			pstmt.setString(10, bsale_dto.getS_contents());
			pstmt.setString(11, bsale_dto.getS_image());
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 삭제
	public int deleteBookSale(int num) {
		int res = 0;
		
		openConn();
		
		sql = "delete from book_sale where s_num = ?";
		
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
	
	// 판매 목록 가져오기
	public List<ListDTO> getBookSaleList(String mem_id, int page, int rowsize) {
		List<ListDTO> list = new ArrayList<ListDTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select s_num, s_qty, s_state, s_price, s_date, b_name, b_author, b_pub_company, "
				+ "b_price, b_image, s_image, b_pub_date, row_number() over(order by s_num desc) rnum "
				+ "from book_sale bs join book_info bi on bs.s_bnum = bi.b_num where s_member = ?)"
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ListDTO dto = new ListDTO();
				
				dto.setS_num(rs.getInt(1));
				dto.setS_qty(rs.getInt(2));
				dto.setS_state(rs.getString(3));
				dto.setS_price(rs.getInt(4));
				dto.setS_date(rs.getString(5));
				dto.setB_name(rs.getString(6));
				dto.setB_author(rs.getString(7));
				dto.setB_pub_company(rs.getString(8));
				dto.setB_price(rs.getInt(9));
				dto.setB_image(rs.getString(10));
				dto.setS_image(rs.getString(11));
				dto.setB_pub_date(rs.getString(12));
				
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
	
	// 판매 수정
	public int updateBookSale(BookSaleDTO dto) {
		int res = 0;
		
		openConn();
		
		sql = "update book_sale set s_qty = ?, s_state = ?, s_quality = ?, s_price = ?, "
				+ "s_charge = ?, s_direct = ?, s_contents = ?, s_image = ? "
				+ "where s_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getS_qty());
			pstmt.setString(2, dto.getS_state());
			pstmt.setString(3, dto.getS_quality());
			pstmt.setInt(4, dto.getS_price());
			pstmt.setInt(5, dto.getS_charge());
			pstmt.setString(6, dto.getS_direct());
			pstmt.setString(7, dto.getS_contents());
			pstmt.setString(8, dto.getS_image());
			pstmt.setInt(9, dto.getS_num());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 전체 리스트 개수
	public int getBookSaleCount(String mem_id) {
		int res = 0;
		
		openConn();
		
		sql = "select count(s_num) from book_sale where s_member = ?";
		
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
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}

	public int getBookSaleCount(String mem_id, String string) {
		int res = 0;
		
		openConn();
		
		sql = "select count(s_num) from book_sale where s_member = ? and s_state = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setString(2, string);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 테이블의 전체 게시물의 수 조회하는 메서드
	public int getListCount() {
		int count = 0;
		
		try {
			openConn();
			sql = "select count(*) from book_sale";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	}	//getListCount() end
	
	// side 카테고리 번호에 해당하는 게시물의 수
	public int sideListCount(int no) {
		int count = 0;
		
		try {
			openConn();
			sql = "select count(*) from book_sale " + 
					"inner join book_info on book_sale.s_bnum = book_info.b_num " + 
					"inner join book_category on book_info.b_cate_fk = book_category.cate_code " + 
					"inner join member on member.mem_id = book_sale.s_member " + 
					"where cate_code_ref = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	}
	
	// 상단 카테고리 번호에 해당하는 게시물의 수
	public int topListCount(int no) {
		int count = 0;
		
		try {
			openConn();
			sql = "select count(s_num) from book_sale " + 
					"inner join book_info on book_sale.s_bnum = book_info.b_num " + 
					"where book_info.b_cate_fk = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		return count;
	}	

	// 조회수
	public void hit_bookSale(int num) {
		openConn();
		
		sql = "update book_sale set s_hit = s_hit + 1 where s_num = ?";
		
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
	
	// 회원 탈퇴시 삭제
	public void deleteBookSale(String mem_id) {
		openConn();
		
		sql = "delete from book_sale where s_member = ?";
		
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
