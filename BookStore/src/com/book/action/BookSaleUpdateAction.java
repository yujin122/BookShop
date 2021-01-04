package com.book.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookSaleDAO;
import com.book.model.BookSaleDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BookSaleUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String saveFolder = "C:\\Users\\yyj01\\OneDrive\\문서\\GitHub\\BookShop\\BookShop\\WebContent\\upload\\book";
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();

		int fileSize = 10 * 1024 * 1024;
		
		MultipartRequest multi = new MultipartRequest(request, saveFolder, fileSize, "utf-8", new DefaultFileRenamePolicy());
		
		int page = Integer.parseInt(multi.getParameter("page"));
		int num = Integer.parseInt(multi.getParameter("num"));
		String book_quality = multi.getParameter("book_quality").trim();
		int book_sell = Integer.parseInt(multi.getParameter("book_sell").trim());
		String book_status = multi.getParameter("books_status").trim();
		int book_qty = Integer.parseInt(multi.getParameter("book_qty").trim());
		String book_direct = multi.getParameter("book_direct").trim();
		int ship_charge = Integer.parseInt(multi.getParameter("ship_charge").trim());
		String book_user_cont = multi.getParameter("book_user_cont").trim();
		
		BookSaleDTO bsale_dto = new BookSaleDTO();
		
		bsale_dto.setS_num(num);
		bsale_dto.setS_qty(book_qty);
		bsale_dto.setS_state(book_status);
		bsale_dto.setS_quality(book_quality);
		bsale_dto.setS_price(book_sell);
		bsale_dto.setS_charge(ship_charge);
		bsale_dto.setS_direct(book_direct);
		bsale_dto.setS_contents(book_user_cont);
		
		File book_user_img = multi.getFile("book_user_img_new");
		
		if(book_user_img != null) {
			
			String fileName = book_user_img.getName();
			
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH)+1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			
			String homedir = saveFolder + "/" + year + "-" + month + "-" + day;
			
			File path1 = new File(homedir);
			if(!path1.exists()) {
				path1.mkdirs();
			}
			
			String refileName = mem_id +"_"+fileName;
			//String refileName = "hong_"+fileName;
			book_user_img.renameTo(new File(homedir+"/"+refileName));
			
			String fileDBName = "/"+year+"-"+month+"-"+day+"/"+refileName;
			
			bsale_dto.setS_image(fileDBName);
			
		}else if(book_user_img == null) {  // 새로운 첨부파일 이미지가 없는 경우
			String book_user_img_old = multi.getParameter("book_user_img_old");
			
			bsale_dto.setS_image(book_user_img_old);
		}
		
		BookSaleDAO dao = BookSaleDAO.getInstance();
		
		int result = dao.updateBookSale(bsale_dto);
		
		ActionForward forward = new ActionForward();
		
		if(result > 0) {
			forward.setRedirect(true);
			forward.setPath("book_sale_list.do?page="+page);
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('도서 수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
