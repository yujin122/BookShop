package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class MemberContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 수정할  회원의 정보를 조회하는 작업
		
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = dao.getMemberInfo(mem_id);
		
		request.setAttribute("cont", dto);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("mypage/member_cont.jsp");
		
		return forward;
	}

}
