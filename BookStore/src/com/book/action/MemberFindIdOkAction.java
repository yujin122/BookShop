package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;

public class MemberFindIdOkAction implements Action {
	//아이디 찾기 메서드.
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
request.setCharacterEncoding("UTF-8");
		
		String mem_name = request.getParameter("mem_name").trim();
		String mem_email = request.getParameter("mem_email").trim();
		MemberDAO dao = MemberDAO.getInstance();
		String mem_id = dao.searchId(mem_name, mem_email);
		
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(mem_id == null) {
			out.println("<script>");
			out.println("alert('없는 회원입니다.')");
			out.println("history.back()");
			out.println("</script>");
		}else {
			//찾은 아이디를 find_id_ok.jsp로 넘겨줌.
			request.setAttribute("mem_id", mem_id);
			forward.setRedirect(false);
			forward.setPath("user/find_id_ok.jsp");
		}
		
		return forward;
	}
}
