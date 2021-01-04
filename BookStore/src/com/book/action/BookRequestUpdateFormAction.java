package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookRequestDAO;
import com.book.model.BookRequestDTO;

public class BookRequestUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());
		
		BookRequestDAO dao = BookRequestDAO.getInstance();
		
		BookRequestDTO dto = dao.getRequestCont(num);
		
		request.setAttribute("dto", dto);
		request.setAttribute("page", page);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("requestbook/book_request_update_form.jsp");
		
		return forward;
	}

}
