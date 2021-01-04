package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class CommuDAO {
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public CommuDAO() {	}
	
	private static CommuDAO instance = null;
	
	public static CommuDAO getInstance() {
		if(instance == null) {
			instance = new CommuDAO();
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
	//총 게시물 수 구하는 메서드.
	public int getCommutotalNum() {
		int result = 0;
		try {
			openConn();
			sql = "select count(*) from commu";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//페이지별 리스트 메서드.
	public List<CommuDTO> getCommuList(int page, int rowsize) {
		List<CommuDTO> list = new ArrayList<CommuDTO>();
		// 해당 페이지에서 시작 번호
				int startNo = (page * rowsize) - (rowsize - 1);
				
				// 해당 페이지의 끝 번호
				int endNo = (page * rowsize);
		try {
			openConn();
			sql = "select * from  (select c.*, row_number() over(order by c_group desc, c_step) rnum  from commu c)  where rnum >= ? and rnum <= ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startNo);
			pstmt.setInt(2, endNo);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CommuDTO dto = new CommuDTO();
				dto.setC_content(rs.getString("c_content"));
				dto.setC_group(rs.getInt("c_group"));
				dto.setC_hit(rs.getInt("c_hit"));
				dto.setC_id_nickname(rs.getString("c_id_nickname"));
				dto.setC_indent(rs.getInt("c_indent"));
				dto.setC_like(rs.getInt("c_like"));
				dto.setC_num(rs.getInt("c_num"));
				dto.setC_pwd(rs.getString("c_pwd"));
				dto.setC_step(rs.getInt("c_step"));
				dto.setC_title(rs.getString("c_title"));
				dto.setC_date(rs.getString("c_date"));
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
	//글 입력 메서드
	public int insertCommu(CommuDTO dto) {
		int result = 0;
		
		try {
			openConn();
			sql = "insert into commu values(?,?,0,0,?,?,?,?,0,0,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getC_num());
			pstmt.setInt(2, dto.getC_group());
			pstmt.setString(3, dto.getC_title());
			pstmt.setString(4, dto.getC_id_nickname());
			pstmt.setString(5, dto.getC_pwd());
			pstmt.setString(6, dto.getC_content());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	//최대 번호값 구하는 메서드.
	public int maxCommu() {
		int max = 0;
		try {
			openConn();
			sql = "select max(c_num) from commu";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				max = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return max;
	}
	//본문 가져오는 메서드.
	public CommuDTO getCont(int no) {
		CommuDTO dto = new CommuDTO();
		try {
			openConn();
			sql = "select * from commu where c_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto.setC_content(rs.getString("c_content"));
				dto.setC_group(rs.getInt("c_group"));
				dto.setC_hit(rs.getInt("c_hit"));
				dto.setC_id_nickname(rs.getString("c_id_nickname"));
				dto.setC_indent(rs.getInt("c_indent"));
				dto.setC_like(rs.getInt("c_like"));
				dto.setC_num(rs.getInt("c_num"));
				dto.setC_pwd(rs.getString("c_pwd"));
				dto.setC_step(rs.getInt("c_step"));
				dto.setC_title(rs.getString("c_title"));
				dto.setC_date(rs.getString("c_date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return dto;
	}

	public int deleteCont(int c_num) {
		int result = 0;
		try {
			openConn();
			sql = "update commu set c_title = '삭제된 글입니다.', c_id_nickname = '----', c_content = '삭제된 글입니다.' where c_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c_num);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}

	public String getPwd(int c_num) {
		String c_pwd = "";
		try {
			openConn();
			sql = "select c_pwd from commu where c_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				c_pwd=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return c_pwd;
	}

	public void upHit(int c_num) {
		try {
			openConn();
			sql = "update commu set c_hit = c_hit+1 where c_num = ?";
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

	public void upLike(int c_num) {
		try {
			openConn();
			sql = "update commu set c_like = c_like+1 where c_num = ?";
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

	public int updateCont(CommuDTO dto) {
		int result = 0;
		try {
			openConn();
			sql = "update commu set c_title = ?, c_content = ?, c_id_nickname = ? where c_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getC_title());
			pstmt.setString(2, dto.getC_content());
			pstmt.setString(3, dto.getC_id_nickname());
			pstmt.setInt(4, dto.getC_num());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}

	public int insertReplyCommu(CommuDTO dto) {
		int result = 0;
		try {
			openConn();
			sql = "insert into commu values(?,?,?,?,?,?,?,?,0,0,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getC_num());
			pstmt.setInt(2, dto.getC_group());
			pstmt.setInt(3, dto.getC_step()+1);
			pstmt.setInt(4, dto.getC_indent()+1);
			pstmt.setString(5, dto.getC_title());
			pstmt.setString(6, dto.getC_id_nickname());
			pstmt.setString(7, dto.getC_pwd());
			pstmt.setString(8, dto.getC_content());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}

	public void updateMentStep(int c_group, int c_step) {
		try {
			openConn();
			sql = "update commu set c_step = c_step + 1 where c_group = ? and c_step > ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c_group);
			pstmt.setInt(2, c_step);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
	}

	// 회원탈퇴시 삭제
	public void deleteMemCont(String mem_id) {

		try {
			openConn();
			sql = "update commu set c_title = '삭제된 글입니다.', c_id_nickname = '----', c_content = '삭제된 글입니다.' where c_id_nickname = ?";
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

	// 마이페이지 내가 쓴글
	public List<CommuDTO> getMemberCommu(String mem_id, int page, int rowsize) {
		List<CommuDTO> list = new ArrayList<CommuDTO>();
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);
		
		// 해당 페이지의 끝 번호
		int endNo = (page * rowsize);
		
		try {
			openConn();
			sql = "select * from (select c.*, row_number() over(order by c_group desc, c_step) rnum  from commu c where c.c_id_nickname = ?)  where rnum >= ? and rnum <= ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, startNo);
			pstmt.setInt(3, endNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CommuDTO dto = new CommuDTO();
				dto.setC_content(rs.getString("c_content"));
				dto.setC_group(rs.getInt("c_group"));
				dto.setC_hit(rs.getInt("c_hit"));
				dto.setC_id_nickname(rs.getString("c_id_nickname"));
				dto.setC_indent(rs.getInt("c_indent"));
				dto.setC_like(rs.getInt("c_like"));
				dto.setC_num(rs.getInt("c_num"));
				dto.setC_pwd(rs.getString("c_pwd"));
				dto.setC_step(rs.getInt("c_step"));
				dto.setC_title(rs.getString("c_title"));
				dto.setC_date(rs.getString("c_date"));
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
	
	// 마이페이지 내가쓴글 총글수
	public int getMemberCommuCount(String mem_id) {
		int result = 0;
		
		try {
			openConn();
			sql = "select count(*) from commu where c_id_nickname = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// 마이페이지에서 삭제
	public int deleteCommu(String[] num) {
		int result = 0;
		
		try {
			openConn();
			
			for(int i = 0; i < num.length; i++) {
				sql = "update commu set c_title = '삭제된 글입니다.', c_id_nickname = '----', c_content = '삭제된 글입니다.' where c_num = ?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, Integer.parseInt(num[i]));
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	
	
}
