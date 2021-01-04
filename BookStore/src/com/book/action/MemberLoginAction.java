package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class MemberLoginAction implements Action {
	//main/login.jsp에서 이동됨.
	//로그인 시 이용하는 메서드.
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		String mem_id = request.getParameter("mem_id");
		String mem_pwd = request.getParameter("mem_pwd");
		MemberDAO dao = MemberDAO.getInstance();
		int res = dao.inspectMember(mem_id,mem_pwd);
		MemberDTO dto = dao.getMemberInfo(mem_id);
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		if(res == 1) {
			//세션에 저장된 회원 정보. 형식) session_컬럼명.
			HttpSession session = request.getSession();
			session.setAttribute("session_mem_addr", dto.getMem_addr());
			session.setAttribute("session_mem_age", dto.getMem_age());		//컬럼 형식) 20-29, 30-39 ...
			session.setAttribute("session_mem_birth", dto.getMem_birth());	//컬럼 형식) mm-dd 예) 09-12
			session.setAttribute("session_mem_email", dto.getMem_email());
			session.setAttribute("session_mem_gender", dto.getMem_gender());//컬럼 형식) F/M/U 중 하나.
			session.setAttribute("session_mem_id", dto.getMem_id());
			session.setAttribute("session_mem_name", dto.getMem_name());
			session.setAttribute("session_mem_nickname", dto.getMem_nickname());
			session.setAttribute("session_mem_number", dto.getMem_number());
			session.setAttribute("session_mem_pwd", dto.getMem_pwd());
			session.setAttribute("session_mem_regdate", dto.getMem_regdate());
			
			forward.setRedirect(true);
			forward.setPath("main.do");
			
		}else if(res==-1) {
			out.println("<script>");
			out.println("alert('비밀번호가 틀렸습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}else if(res ==-2) {
			out.println("<script>");
			out.println("alert('아이디가 틀렸습니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
