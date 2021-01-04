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

public class EditEmailCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		String mem_email = request.getParameter("mem_email").trim();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		MemberDTO dto = dao.getMemberInfo(mem_id);

		String email = dao.editEmailCheck(mem_email);
		String data;
		
		if(email.equals(dto.getMem_email()) || email == "") {
			data = "사용 가능한 이메일입니다.";
		}else {
			data = "있는 이메일 입니다.";
		}
		
		PrintWriter out = response.getWriter();
		out.println(data);
		out.close();
		
		return null;
	}

}
