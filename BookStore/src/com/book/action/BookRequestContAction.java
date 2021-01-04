package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookRequestDAO;
import com.book.model.BookRequestDTO;
import com.book.model.MemberDAO;

public class BookRequestContAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int page = Integer.parseInt(request.getParameter("page").trim());
		int num = Integer.parseInt(request.getParameter("num").trim());
		String search_label = null;
		String search_txt = null;
		
		if(request.getParameter("search_label") != null && request.getParameter("search_txt") != null) {
			search_label = request.getParameter("search_label");
			search_txt = request.getParameter("search_txt");
		}
		
		BookRequestDAO dao = BookRequestDAO.getInstance();
		MemberDAO mem_dao = MemberDAO.getInstance();
		
		dao.hitBookRequest(num);
		
		BookRequestDTO dto = dao.getRequestCont(num);
		
		request.setAttribute("dto", dto);
		request.setAttribute("page", page);
		request.setAttribute("search_label", search_label);
		request.setAttribute("search_txt", search_txt);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("requestbook/book_request_cont.jsp");
		
		return forward;
	}

}
