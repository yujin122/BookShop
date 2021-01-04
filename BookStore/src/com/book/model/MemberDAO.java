package com.book.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	public static final int ADMIN_LOGIN_SUCCESS = 1;
	public static final int ADMIN_LOGIN_PWD_FAIL = -1;
	public static final int ADMIN_LOGIN_NONEXIST = -2;
	
	public MemberDAO() {	}
	
	private static MemberDAO instance = null;
	
	public static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
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
	
	//로그인 함수
	public int inspectMember(String mem_id, String mem_pwd) {
		int result = 0;
		try {
			openConn();
			sql = "select * from member where mem_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString("mem_pwd").equals(mem_pwd)) {
					result = ADMIN_LOGIN_SUCCESS;
				}else {
					result = ADMIN_LOGIN_PWD_FAIL;
				}
			}else {
				result = ADMIN_LOGIN_NONEXIST;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	//로그인 할때 세션에 저장할 정보를 얻어오는 메서드.
	public MemberDTO getMemberInfo(String mem_id) {
		MemberDTO dto = new MemberDTO();
		try {
			openConn();
			
			sql = "select * from member where mem_id = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setMem_name(rs.getString("mem_name"));
				dto.setMem_age(rs.getString("mem_age"));
				dto.setMem_nickname(rs.getString("mem_nickname"));
				dto.setMem_gender(rs.getString("mem_gender"));
				dto.setMem_birth(rs.getString("mem_birth"));
				dto.setMem_id(rs.getString("mem_id"));
				dto.setMem_pwd(rs.getString("mem_pwd"));
				dto.setMem_number(rs.getString("mem_number"));
				dto.setMem_addr(rs.getString("mem_addr"));
				dto.setMem_email(rs.getString("mem_email"));
				dto.setMem_regdate(rs.getString("mem_regdate"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return dto;
	}
	
	//아이디 찾기 함수
	public String searchId(String mem_name, String mem_email) {
		String mem_id = null;
		
		try {
			openConn();
			sql = "select * from member where mem_name = ? and mem_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_name);
			pstmt.setString(2, mem_email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mem_id = rs.getString("mem_id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return mem_id;
	}
	
	//비밀번호 찾기에서 입력한 정보가 맞는지 확인하는 함수
	public int ConfirmMemberPwd(String mem_name, String mem_id) {
		int result = 0;
		try {
			openConn();
			sql = "select * from member where mem_name = ? and mem_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_name);
			pstmt.setString(2, mem_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	//비밀번호 분실 시 새로운 비밀번호를 넣는 메서드.
	public int insertNewpwd(String new_mem_pwd, String mem_name, String mem_id) {
		int result = 0;
		try {
			openConn();
			sql = "update member set mem_pwd = ? where mem_name = ? and mem_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, new_mem_pwd);
			pstmt.setString(2, mem_name);
			pstmt.setString(3, mem_id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	//이메일만 얻어오는 메서드(메일 보내기 용)
	public String searchEmail(String mem_name, String mem_id) {
		String mem_email = null;
		try {
			openConn();
			sql = "select * from member where mem_name =? and mem_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_name);
			pstmt.setString(2, mem_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mem_email = rs.getString("mem_email");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mem_email;
	}
	
	//회원 가입 함수
	public int insertMember(MemberDTO dto) {
		int result = 0;
		try {
			openConn();
			sql = "insert into member values(?,?,?,?,?,?,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getMem_name());
			pstmt.setString(2, dto.getMem_age());
			pstmt.setString(3, dto.getMem_nickname());
			pstmt.setString(4, dto.getMem_gender());
			pstmt.setString(5, dto.getMem_birth());
			pstmt.setString(6, dto.getMem_id());
			pstmt.setString(7, dto.getMem_pwd());
			pstmt.setString(8, dto.getMem_number());
			pstmt.setString(9, dto.getMem_addr());
			pstmt.setString(10, dto.getMem_email());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	//중복 아이디 찾기 함수
	public int searchId(String mem_id) {
		int result = 0;
		
		try {
			openConn();
			sql = "select * from member where mem_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return result;
	}
	
	//중복 아이디 체크 메서드.
	public String idCheck(String mem_id) {
		String res = "";
		try {
			openConn();
			sql = "select * from member where mem_id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				res = "있는 아이디 입니다.";
			}else {
				res = "사용 가능한 아이디 입니다.";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return res;
	}
	
	//회원정보를 수정하는 메서드
	public int updateMember(MemberDTO dto) {
		int result = 0;

		try {
			openConn();
			sql = "update member set mem_name = ?, "
					+ " mem_age = ?, mem_nickname = ?, "
					+ " mem_gender = ?, mem_birth = ?, mem_pwd = ?, "
					+ " mem_number = ?, mem_addr = ?, mem_email = ? where mem_id = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getMem_name());
			pstmt.setString(2, dto.getMem_age());
			pstmt.setString(3, dto.getMem_nickname());
			pstmt.setString(4, dto.getMem_gender());
			pstmt.setString(5, dto.getMem_birth());
			pstmt.setString(6, dto.getMem_pwd());
			pstmt.setString(7, dto.getMem_number());
			pstmt.setString(8, dto.getMem_addr());
			pstmt.setString(9, dto.getMem_email());
			pstmt.setString(10, dto.getMem_id());
	
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
			
		}
		return result;
	}
	
	//중복 이메일 체크 메서드.
	public String emailCheck(String mem_email) {
		String res = "";
		try {
			openConn();
			sql = "select * from member where mem_email = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_email);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				res = "있는 이메일 입니다.";
			}else {
				res = "사용 가능한 이메일 입니다.";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		return res;
	}

	// 회원탈퇴
	public int deleteMember(String mem_id) {
		int res = 0;
		
		openConn();
		
		sql = "delete from member where mem_id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return res;
	}
	
	// 회원수정 이메일 체크
	public String editEmailCheck(String mem_email) {
		String email = "";
		
		openConn();
		
		sql = "select mem_email from member where mem_email = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				email = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			closeConn(rs, pstmt, con);
		}
		
		return email;
	}
	
	// 회원탈퇴 비밀번호 확인
	public String getPwd(String mem_id) {
		String pwd = "";
		
		openConn();
		
		sql = "select mem_pwd from member where mem_id = ?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pwd = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeConn(rs, pstmt, con);
		}
		
		return pwd;
	}
	
}
