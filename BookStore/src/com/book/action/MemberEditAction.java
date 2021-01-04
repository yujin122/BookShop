package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class MemberEditAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 
		
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.getMemberInfo(mem_id);
		
		request.setAttribute("edit", dto);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("mypage/member_edit.jsp");
		
		return forward;
	}

}
