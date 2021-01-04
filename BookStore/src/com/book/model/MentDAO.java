package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MentDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public static final int ADMIN_LOGIN_SUCCESS = 1;
	public static final int ADMIN_LOGIN_PWD_FAIL = -1;
	public static final int ADMIN_LOGIN_NONEXIST = -2;
	
	public MentDAO() {	}
	
	private static MentDAO instance = null;
	
	public static MentDAO getInstance() {
		if(instance == null) {
			instance = new MentDAO();
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
	//글번호에 해당하는 모든 댓글 가져오는 메서드. 
	public List<MentDTO> getMent(int c_num) {
		List<MentDTO> list = new ArrayList<MentDTO>();
		try {
			openConn();
			sql = "select * from ment where c_num_fk = ? order by ment_group, ment_step";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, c_num);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MentDTO dto = new MentDTO();
				dto.setC_num_fk(rs.getInt("c_num_fk"));
				dto.setMent_cont(rs.getString("ment_cont"));
				dto.setMent_date(rs.getString("ment_date"));
				dto.setMent_group(rs.getInt("ment_group"));
				dto.setMent_id_nickname(rs.getString("ment_id_nickname"));
				dto.setMent_indent(rs.getInt("ment_indent"));
				dto.setMent_num(rs.getInt("ment_num"));
				dto.setMent_pwd(rs.getString("ment_pwd"));
				dto.setMent_step(rs.getInt("ment_step"));
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

	public int getMaxMent() {
		int max = 0;
		try {
			openConn();
			sql = "select max(ment_num) from ment";
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

	public int writeMent(MentDTO dto) {
		int result = 0;
		try {
			openConn();
			sql = "insert into ment values(?,?,?,0,0,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getC_num_fk());
			pstmt.setInt(2, dto.getMent_num());
			pstmt.setInt(3, dto.getMent_group());
			pstmt.setString(4, dto.getMent_cont());
			pstmt.setString(5, dto.getMent_id_nickname());
			pstmt.setString(6, dto.getMent_pwd());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}

	public int writeMentReply(MentDTO dto) {
		int result = 0;
		try {
			openConn();
			sql = "insert into ment values(?,?,?,?,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getC_num_fk());
			pstmt.setInt(2, dto.getMent_num());
			pstmt.setInt(3, dto.getMent_group());
			pstmt.setInt(4, dto.getMent_step()+1);
			pstmt.setInt(5, dto.getMent_indent()+1);
			pstmt.setString(6, dto.getMent_cont());
			pstmt.setString(7, dto.getMent_id_nickname());
			pstmt.setString(8, dto.getMent_pwd());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}

	public void updateMentStep(int ment_group, int ment_step) {
		try {
			openConn();
			sql = "update ment set ment_step = ment_step + 1 where ment_group = ? and ment_step > ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ment_group);
			pstmt.setInt(2, ment_step);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
	}

	public int deleteMent(int ment_num, String delete_pwd) {
		int result = 0;
		try {
			openConn();
			sql = "select * from ment where ment_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ment_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("ment_pwd").equals(delete_pwd)) {
					sql = "update ment set ment_cont = '삭제된 댓글입니다.', ment_id_nickname= '----' where ment_num = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, ment_num);
					result = pstmt.executeUpdate();
				}else {
					result = -1;
				}
				
			}else {
				result = -2;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeConn(rs, pstmt, con);
		}
		return result;
	}

	public void deleteMent(String nickname_id) {
		try {
			openConn();
			
			sql = "update ment set ment_cont = '삭제된 댓글입니다.', ment_id_nickname= '----' where ment_id_nickname = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname_id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			closeConn(rs, pstmt, con);
		}
		
	}

}
