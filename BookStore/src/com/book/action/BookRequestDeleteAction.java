package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookRequestDAO;

public class BookRequestDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page").trim());
		
		BookRequestDAO dao = BookRequestDAO.getInstance();
		
		int res = dao.deleteBookRequest(num);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("book_request_list.do?page="+page);
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('도서 요청 삭제 실패')");
			out.println("history.back()");
			out.println("</script>");			
		}
		return forward;
	}

}
