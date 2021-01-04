package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookInfoDAO;
import com.book.model.BookInfoDTO;
import com.book.model.BookSaleDAO;
import com.book.model.BookSaleDTO;
import com.book.model.MemberDAO;
import com.book.model.MemberDTO;

public class BookBuyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 도서 구매 폼 입력
		int s_num = Integer.parseInt(request.getParameter("s_num").trim());
		int cnt = Integer.parseInt(request.getParameter("count").trim());
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		BookSaleDTO bs_dto = bs_dao.getBookSale(s_num);
		
		BookInfoDAO bi_dao = BookInfoDAO.getInstance();
		BookInfoDTO bi_dto = bi_dao.getBookInfo(bs_dto.getS_bnum());
		
		MemberDAO mem_dao = MemberDAO.getInstance();
		MemberDTO mem_dto = mem_dao.getMemberInfo(bs_dto.getS_member());
		
		if(request.getParameter("c_num") != null) {
			if(bs_dto.getS_qty() <= 0) {
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('품절된 도서입니다.')");
				out.println("history.back()");
				out.println("</script>");
				
				return null;
			}else {
				int c_num = Integer.parseInt(request.getParameter("c_num").trim());
				request.setAttribute("c_num", c_num);
			}
		}
		
		request.setAttribute("bs_dto", bs_dto);
		request.setAttribute("bi_dto", bi_dto);
		request.setAttribute("mem_dto", mem_dto);
		request.setAttribute("cnt", cnt);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("book_sale/book_buy_form.jsp");
		
		return forward;
	}

}
