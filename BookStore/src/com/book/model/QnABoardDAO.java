package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class QnABoardDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public QnABoardDAO() {	}
	
	private static QnABoardDAO instance = null;
	
	public static QnABoardDAO getInstance() {
		if(instance == null) {
			instance = new QnABoardDAO();
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
	
	// 문의글 추가
	public int insertQnA(QnABoardDTO dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
	
		try {
			sql = "select max(q_num) from qna_board";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into qna_board values(?, ?, ?, ?, sysdate, default, ?, default, default)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getQ_mem());
			pstmt.setInt(3, dto.getQ_sale_num());
			pstmt.setString(4, dto.getQ_contents());
			pstmt.setInt(5, cnt);
			
			res = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 문의글 리스트
	public JsonObject getQnA(int page, int rowsize, int s_num) {
		JsonArray jarr = new JsonArray();
		JsonObject object = new JsonObject();
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		openConn();
		
		sql = "select * from (select q.*, m.mem_nickname, row_number() over(order by q.q_group desc, q.q_step) rnum "
				+ "from qna_board q join member m on q.q_mem = m.mem_id(+) where q_sale_num = ?) "
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, s_num);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
		
			object.addProperty("isData", false);
			
			while(rs.next()) {
				object.addProperty("isData", true);
				
				JsonObject jobj = new JsonObject();
				
				jobj.addProperty("q_num", rs.getInt(1));
				jobj.addProperty("q_mem", rs.getString(2));
				jobj.addProperty("q_sale_num", rs.getInt(3));
				jobj.addProperty("q_contents", rs.getString(4));
				jobj.addProperty("q_date", rs.getString(5));
				jobj.addProperty("q_hit", rs.getInt(6));
				jobj.addProperty("q_group", rs.getInt(7));
				jobj.addProperty("q_step", rs.getInt(8));
				jobj.addProperty("q_indent", rs.getInt(9));
				jobj.addProperty("q_mem_nickname", rs.getString(10));		
				
				jarr.add(jobj);
			}
			
			object.add("data", jarr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return object;
		
	}

	// 문의글 수
	public int getQnACount(int s_num) {
		int cnt = 0;
		
		openConn();
		
		sql = "select count(q_num) from qna_board where q_sale_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, s_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return cnt;
	}

	// (답글) 문의글 가져오기 
	public QnABoardDTO getQnA(int qna_num) {
		QnABoardDTO dto = new QnABoardDTO();
		
		openConn();
		
		sql = "select * from qna_board where q_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setQ_num(rs.getInt(1));
				dto.setQ_mem(rs.getString(2));
				dto.setQ_sale_num(rs.getInt(3));
				dto.setQ_contents(rs.getString(4));
				dto.setQ_date(rs.getString(5));
				dto.setQ_hit(rs.getInt(6));
				dto.setQ_group(rs.getInt(7));
				dto.setQ_step(rs.getInt(8));
				dto.setQ_indent(rs.getInt(9));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return dto;
		
	}
	
	// 기존 답변글 업뎃
	public void replyUpdate(QnABoardDTO dto) {
		openConn();
		
		sql = "update qna_board set q_step = q_step+1 where q_step > ? and q_group = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getQ_step());
			pstmt.setInt(2, dto.getQ_group());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
	}

	public int replyQnA(QnABoardDTO dto) {
		int res = 0;
		int cnt = 0;
		
		openConn();
		
		try {
			sql = "select max(q_num) from qna_board";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1)+1;
			}else {
				cnt = 1;
			}
			
			sql = "insert into qna_board values(?, ?, ?, ?, sysdate, default, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, cnt);
			pstmt.setString(2, dto.getQ_mem());
			pstmt.setInt(3, dto.getQ_sale_num());
			pstmt.setString(4, dto.getQ_contents());
			pstmt.setInt(5, dto.getQ_group());
			pstmt.setInt(6, dto.getQ_step()+1);
			pstmt.setInt(7, dto.getQ_indent()+1);
			
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
	public int hitQnA(int num) {
		int res = 0;
		
		openConn();
		
		sql = "update qna_board set q_hit = q_hit+1 where q_num = ?";
		
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
	
	// 문의글 수정
	public int updateQnA(int num, String txt) {
		int res = 0;
		
		openConn();
		
		sql = "update qna_board set q_contents = ? where q_num = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, txt);
			pstmt.setInt(2, num);
			
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	//문의글 삭제
	public int deleteQnA(int num) {
		int res = 0;
		
		openConn();
		
		//sql = "delete from qna_board where q_num = ?";
		sql = "update qna_board set q_contents = '-', q_mem = '-' where q_num = ?";
		
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
	
	// 회원탈퇴 시 삭제
	public void deleteQnA(String mem_id) {
		openConn();
		
		//sql = "delete from qna_board where q_mem = ?";
		sql = "update qna_board set q_contents = '-', q_mem = '-' where q_mem = ?";
		
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
	
	// 중고도서 삭제시 삭제
	public void deleteSaleQnA(int num) {
		openConn();
		
		sql = "delete from qna_board where q_sale_num = ?";
		
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

}
