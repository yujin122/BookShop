package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AllQnADAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public AllQnADAO() {	}
	
	private static AllQnADAO instance = null;
	
	public static AllQnADAO getInstance() {
		if(instance == null) {
			instance = new AllQnADAO();
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
	
	// qna 전체 리스트
	public List<AllQnADTO> getQnA(int page, int rowsize) {
		List<AllQnADTO> list = new ArrayList<AllQnADTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select a.*, m.mem_nickname, row_number() over(order by a.aq_group desc, a.aq_step) rnum "
				+ "from all_qna a join member m on a.aq_writer = m.mem_id(+)) "
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AllQnADTO dto = new AllQnADTO();
				
				dto.setAq_num(rs.getInt(1));
				dto.setAq_category(rs.getString(2));
				dto.setAq_title(rs.getString(3));
				dto.setAq_content(rs.getString(4));
				dto.setAq_writer(rs.getString(5));
				dto.setAq_date(rs.getString(6));
				dto.setAq_hit(rs.getInt(7));
				dto.setAq_group(rs.getInt(8));
				dto.setAq_step(rs.getInt(9));
				dto.setAq_indent(rs.getInt(10));
				dto.setAq_pwd(rs.getString(11));
				dto.setAq_lock(rs.getString(12));
				dto.setAq_mem_nickname(rs.getString(13));
				
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

	// qna 전체 리스트 개수
	public int getAllQnACount() {
		int res = 0;
		
		openConn();
		
		sql = "select count(aq_num) from all_qna";
		
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
	
	// 메뉴에 해당하는 qan 개수
	public int getAllQnACount(String cate) {
		int res = 0;
		
		openConn();
		
		sql = "select count(aq_num) from all_qna where aq_category = ?";
		
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
	
	// 메뉴에 해당하는 qna 리스트
	public List<AllQnADTO> getQnA(String cate, int page, int rowsize) {
		List<AllQnADTO> list = new ArrayList<AllQnADTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select a.*, m.mem_nickname, row_number() over(order by a.aq_group desc, a.aq_step) rnum "
				+ "from all_qna a join member m on a.aq_writer = m.mem_id(+) where a.aq_category = ?) "
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cate);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AllQnADTO dto = new AllQnADTO();
				
				dto.setAq_num(rs.getInt(1));
				dto.setAq_category(rs.getString(2));
				dto.setAq_title(rs.getString(3));
				dto.setAq_content(rs.getString(4));
				dto.setAq_writer(rs.getString(5));
				dto.setAq_date(rs.getString(6));
				dto.setAq_hit(rs.getInt(7));
				dto.setAq_group(rs.getInt(8));
				dto.setAq_step(rs.getInt(9));
				dto.setAq_indent(rs.getInt(10));
				dto.setAq_pwd(rs.getString(11));
				dto.setAq_lock(rs.getString(12));
				dto.setAq_mem_nickname(rs.getString(13));
				
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
	
	// 문의글 추가
	public int insertAllQnA(AllQnADTO dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
		
		try {
			sql = "select max(aq_num) from all_qna";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into all_qna values(?, ?, ?, ?, ?, sysdate, default, ?, default, default, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getAq_category());
			pstmt.setString(3, dto.getAq_title());
			pstmt.setString(4, dto.getAq_content());
			pstmt.setString(5, dto.getAq_writer());
			pstmt.setInt(6, cnt);
			pstmt.setString(7, dto.getAq_pwd());
			pstmt.setString(8, dto.getAq_lock());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 해당 번호 qna 정보
	public AllQnADTO getAllQna(int num) {
		AllQnADTO dto = new AllQnADTO();
		
		openConn();
		
		sql = "select q.*, m.mem_nickname from all_qna q join member m on q.aq_writer = m.mem_id(+) where aq_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setAq_num(rs.getInt(1));
				dto.setAq_category(rs.getString(2));
				dto.setAq_title(rs.getString(3));
				dto.setAq_content(rs.getString(4));
				dto.setAq_writer(rs.getString(5));
				dto.setAq_date(rs.getString(6));
				dto.setAq_hit(rs.getInt(7));
				dto.setAq_group(rs.getInt(8));
				dto.setAq_step(rs.getInt(9));
				dto.setAq_indent(rs.getInt(10));
				dto.setAq_pwd(rs.getString(11));
				dto.setAq_lock(rs.getString(12));
				dto.setAq_mem_nickname(rs.getString(13));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return dto;
	}
	
	// 조회수
	public void allQnAHit(int num) {
		
		openConn();
		
		sql = "update all_qna set aq_hit = aq_hit+1 where aq_num = ?";
		
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
	
	// 삭제
	public int deleteAllQnA(int num) {
		int res = 0;
		
		openConn();
		
		//sql = "delete from all_qna where aq_num = ?";
		sql = "update all_qna set aq_title = '삭제된 게시물입니다.', aq_content = '-', aq_writer = '-', aq_pwd = null, aq_lock = 0 where aq_num = ?";
		
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
	
	// 수정
	public int updateAllQnA(AllQnADTO dto) {
		int res = 0;
		
		openConn();
		
		sql = "update all_qna set aq_category = ?, aq_title = ?, aq_content = ?, aq_writer = ?, aq_pwd = ?, aq_lock = ? where aq_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getAq_category());
			pstmt.setString(2, dto.getAq_title());
			pstmt.setString(3, dto.getAq_content());
			pstmt.setString(4, dto.getAq_writer());
			pstmt.setString(5, dto.getAq_pwd());
			pstmt.setString(6, dto.getAq_lock());
			pstmt.setInt(7, dto.getAq_num());

			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 기존 글 업데이트
	public void replyUpdate(AllQnADTO dto) {
		openConn();
		
		sql = "update all_qna set aq_step = aq_step+1 where aq_group = ? and aq_step > ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getAq_group());
			pstmt.setInt(2, dto.getAq_step());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}
	
	// 답글 추가
	public int ReplyAllQnA(AllQnADTO dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
		
		try {
			sql = "select max(aq_num) from all_qna";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into all_qna values(?, ?, ?, ?, ?, sysdate, default, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getAq_category());
			pstmt.setString(3, dto.getAq_title());
			pstmt.setString(4, dto.getAq_content());
			pstmt.setString(5, dto.getAq_writer());
			pstmt.setInt(6, dto.getAq_group());
			pstmt.setInt(7, dto.getAq_step() + 1);
			pstmt.setInt(8, dto.getAq_indent() + 1);
			pstmt.setString(9, dto.getAq_pwd());
			pstmt.setString(10, dto.getAq_lock());
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 문의글 비밀번호 확인
	public int checkPwd(int num, String pwd) {
		int res = 0;
		
		openConn();
		
		sql = "select aq_pwd from all_qna where aq_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(pwd.equals(rs.getString(1))) {
					res = 1;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return res;
	}

	// 검색 리스트
	public List<AllQnADTO> searchAllQnA(String search_cate, String search_label, String search_txt, int page, int rowsize) {
		List<AllQnADTO> list = new ArrayList<AllQnADTO>();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		try {
			if(search_cate.equals("전체")) {
				if(search_label.equals("title")) {
					sql = "select * from (select a.*, m.mem_nickname, row_number() over(order by a.aq_group desc, a.aq_step) rnum "
							+ "from all_qna a join member m on a.aq_writer = m.mem_id(+) where aq_title like ?) "
							+ "where rnum >= ? and rnum <= ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search_txt+"%");
					pstmt.setInt(2, startNo);
					pstmt.setInt(3, endNo);
					
				}else if(search_label.equals("writer")) {
					sql = "select * from (select a.*, m.mem_nickname, row_number() over(order by a.aq_group desc, a.aq_step) rnum "
							+ "from all_qna a join member m on a.aq_writer = m.mem_id(+) where aq_writer like ? or m.mem_nickname like ?) "
							+ "where rnum >= ? and rnum <= ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search_txt+"%");
					pstmt.setString(2, "%"+search_txt+"%");
					pstmt.setInt(3, startNo);
					pstmt.setInt(4, endNo);
					
				}
			}else {
				if(search_label.equals("title")) {
					sql = "select * from (select a.*, m.mem_nickname, row_number() over(order by a.aq_group desc, a.aq_step) rnum "
							+ "from all_qna a join member m on a.aq_writer = m.mem_id(+) where a.aq_category = ? and aq_title like ?) "
							+ "where rnum >= ? and rnum <= ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, search_cate);
					pstmt.setString(2, "%"+search_txt+"%");
					pstmt.setInt(3, startNo);
					pstmt.setInt(4, endNo);
					
				}else if(search_label.equals("writer")) {
					sql = "select * from (select a.*, m.mem_nickname, row_number() over(order by a.aq_group desc, a.aq_step) rnum "
							+ "from all_qna a join member m on a.aq_writer = m.mem_id(+) where a.aq_category = ? and aq_writer like ? or m.mem_nickname like ?) "
							+ "where rnum >= ? and rnum <= ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, search_cate);
					pstmt.setString(2, "%"+search_txt+"%");
					pstmt.setString(3, "%"+search_txt+"%");
					pstmt.setInt(4, startNo);
					pstmt.setInt(5, endNo);
					
				}
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AllQnADTO dto = new AllQnADTO();
				
				dto.setAq_num(rs.getInt(1));
				dto.setAq_category(rs.getString(2));
				dto.setAq_title(rs.getString(3));
				dto.setAq_content(rs.getString(4));
				dto.setAq_writer(rs.getString(5));
				dto.setAq_date(rs.getString(6));
				dto.setAq_hit(rs.getInt(7));
				dto.setAq_group(rs.getInt(8));
				dto.setAq_step(rs.getInt(9));
				dto.setAq_indent(rs.getInt(10));
				dto.setAq_pwd(rs.getString(11));
				dto.setAq_lock(rs.getString(12));
				dto.setAq_mem_nickname(rs.getString(13));
				
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
	
	// 검색 리스트 개수
	public int searchQnACount(String search_cate, String search_label, String search_txt) {
		int res = 0;
		
		openConn();
		
		try {
			if(search_cate.equals("전체")) {
				if(search_label.equals("title")) {
					sql = "select count(aq_num) from all_qna where aq_title like ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search_txt+"%");
					
				}else if(search_label.equals("writer")) {
					sql = "select count(aq_num) from all_qna a join member m on a.aq_writer = m.mem_id(+) "
							+ "where aq_writer like ? or m.mem_nickname like ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, "%"+search_txt+"%");
					pstmt.setString(2, "%"+search_txt+"%");
				}
			}else {
				if(search_label.equals("title")) {
					sql = "select count(aq_num) from all_qna where aq_category = ? and aq_title like ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, search_cate);
					pstmt.setString(2, "%"+search_txt+"%");
					
				}else if(search_label.equals("writer")) {
					sql = "select count(aq_num) from all_qna a join member m on a.aq_writer = m.mem_id(+) "
							+ "where a.aq_category = ? and aq_writer like ? or m.mem_nickname like ?";
					
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, search_cate);
					pstmt.setString(2, "%"+search_txt+"%");
					pstmt.setString(3, "%"+search_txt+"%");
				}
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
	
	// 회원 탈퇴시 삭제
	public void deleteAllQnA(String mem_id) {
		openConn();
		
		//sql = "delete from all_qna where aq_writer = ?";
		sql = "update all_qna set aq_title = '삭제된 게시물입니다.', aq_content = '-', aq_writer = '-', aq_pwd = null, aq_lock = 0 where aq_writer = ?";
		
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
