package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookCartDAO;

public class BookCartDelAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
	// 카트번호에 해당하는 장바구니 주문목록을  DB에서 삭제하는 컨트롤러
		
		String[] num = request.getParameterValues("delChecks");
		
		BookCartDAO dao = BookCartDAO.getInstance();
		int check = dao.deleteBookCart(num);
		
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(check > 0) {
			forward.setRedirect(true);
			forward.setPath("book_cart_list.do");
		}else {
			out.println("<script>");
			out.println("alert('장바구니가 삭제되지 않았습니다 ')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	
	}

}
