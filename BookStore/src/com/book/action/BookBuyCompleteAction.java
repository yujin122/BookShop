package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookBuyDAO;

public class BookBuyCompleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		int page = Integer.parseInt(request.getParameter("page"));
		String menu = URLEncoder.encode("완료", "utf-8");
		
		BookBuyDAO dao = BookBuyDAO.getInstance();
		
		int res = dao.updateBookBuyComp(num);

		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("sale_list_menu.do?page="+page+"&menu="+menu);
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('판매 완료 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
