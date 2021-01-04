package com.book.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookBuyDAO;
import com.book.model.BookCartDAO;
import com.book.model.BookSaleDAO;
import com.book.model.BookSaleDTO;
import com.book.model.QnABoardDAO;

public class BookSaleDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int page = Integer.parseInt(request.getParameter("page"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		BookBuyDAO bb_dao = BookBuyDAO.getInstance();
		int check = bb_dao.checkState(num);
		
		ActionForward forward = new ActionForward();
		
		PrintWriter out = response.getWriter();
		
		if(check == 1) {
			out.println("<script>");
			out.println("alert('구매 요청이 존재하여 삭제할 수 없습니다.')");
			out.println("history.back()");
			out.println("</script>");
			
			return null;
		}else {
			BookSaleDAO bs_dao = BookSaleDAO.getInstance();
			BookCartDAO bc_dao = BookCartDAO.getInstance();
			QnABoardDAO qb_dao = QnABoardDAO.getInstance();
			
			BookSaleDTO bs_dto = bs_dao.getBookSale(num);
			
			qb_dao.deleteSaleQnA(num);
			bb_dao.deleteBookBuy(num);
			bc_dao.deleteBookCart(num);
			int res = bs_dao.deleteBookSale(num);
			
			if(res > 0) {
				System.out.println(num);
				
				System.out.println(bs_dto.getS_image());
				
				String fileName = bs_dto.getS_image();
				
				System.out.println(fileName);
				
				if(fileName != null) {
					String upload = "C:\\Users\\yyj01\\OneDrive\\문서\\GitHub\\JSPProject\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\BookShop\\upload\\book";
					String uploadFileName = upload + fileName;
					System.out.println(uploadFileName);
					
					File uploadFile = new File(uploadFileName);
					uploadFile.delete();
				}
				
				forward.setRedirect(true);
				forward.setPath("book_sale_list.do?page="+page);
			}else {
				out.println("<script>");
				out.println("alert('삭제 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
			
			 return forward;
		}
		
	}

}
