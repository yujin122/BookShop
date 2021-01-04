package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookRequestDAO;
import com.book.model.BookRequestDTO;

public class BookRequestAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 도서 요청 리스트
		
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		String book_title = request.getParameter("book_title").trim();
		String book_pub_company = request.getParameter("book_pub_company").trim();
		String book_author = request.getParameter("book_author").trim();
		String book_price = request.getParameter("book_price").trim();
		String book_contact = request.getParameter("book_contact").trim();
		String request_title = request.getParameter("request_title").trim();
		String request_cont = request.getParameter("request_cont").trim();
		
		BookRequestDTO dto = new BookRequestDTO();
		
		dto.setR_member(mem_id);
		dto.setR_book_title(book_title);
		dto.setR_pub_company(book_pub_company);
		dto.setR_author(book_author);
		dto.setR_price(book_price);
		dto.setR_contact(book_contact);
		dto.setR_title(request_title);
		dto.setR_contents(request_cont);
		
		BookRequestDAO dao = BookRequestDAO.getInstance();
		
		int res = dao.insertBookRequest(dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("book_request_list.do");
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('도서 요청 실패')");
			out.println("history.back()");
			out.println("</script>");			
		}
		return forward;
	}

}
