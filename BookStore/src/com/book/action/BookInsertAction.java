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
import com.book.model.BookInfoDAO;
import com.book.model.BookInfoDTO;
import com.book.model.BookSaleDAO;
import com.book.model.BookSaleDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BookInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 중고도서 등록
		
		HttpSession session = request.getSession();
		
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		String saveFolder = "C:\\Users\\yyj01\\OneDrive\\문서\\GitHub\\BookShop\\BookShop\\WebContent\\upload\\book";
		
		int fileSize = 10 * 1024 * 1024;
		
		MultipartRequest multi = new MultipartRequest(request, saveFolder, fileSize, "utf-8", new DefaultFileRenamePolicy());
		
		int cate_code = Integer.parseInt(multi.getParameter("category2"));	
		String book_isbn = multi.getParameter("book_isbn").trim();
		String book_title = multi.getParameter("book_title").trim();
		String book_writer = multi.getParameter("book_writer").trim();
		String book_translator = multi.getParameter("book_translator").trim();
		String book_publisher = multi.getParameter("book_publisher").trim();
		String book_date = multi.getParameter("book_date").trim();
		String book_img = multi.getParameter("book_img").trim();
		int book_price = Integer.parseInt(multi.getParameter("book_price").trim());
		String book_cont = multi.getParameter("book_cont").trim();
		
		String book_quality = multi.getParameter("book_quality").trim();
		int book_sell = Integer.parseInt(multi.getParameter("book_sell").trim());
		String book_status = multi.getParameter("books_status").trim();
		int book_qty = Integer.parseInt(multi.getParameter("book_qty").trim());
		String book_direct = multi.getParameter("book_direct").trim();
		int ship_charge = Integer.parseInt(multi.getParameter("ship_charge").trim());
		String book_user_cont = multi.getParameter("book_user_cont").trim();
		
		// 도서 정보
		BookInfoDTO binfo_dto = new BookInfoDTO(); 
		
		binfo_dto.setB_cate_fk(cate_code);
		binfo_dto.setB_isbn(book_isbn);
		binfo_dto.setB_name(book_title);
		binfo_dto.setB_author(book_writer);
		binfo_dto.setB_translator(book_translator);
		binfo_dto.setB_pub_company(book_publisher);
		binfo_dto.setB_pub_date(book_date);
		binfo_dto.setB_image(book_img);
		binfo_dto.setB_price(book_price);
		binfo_dto.setB_contents(book_cont);
		
		BookInfoDAO binfo_dao = BookInfoDAO.getInstance();
		
		// 도서 정보 체크
		int b_num = binfo_dao.checkBookInfo(binfo_dto);
		
		System.out.println(b_num);
		if(b_num == 0) {
			int res = binfo_dao.insertBookInfo(binfo_dto);
			
			if(res <= 0) {
				PrintWriter out = response.getWriter();
				
				out.println("<script>");
				out.println("alert('도서 정보 등록 실패')");
				out.println("history.back()");
				out.println("</script>");
			}
		}
		
		// 도서 번호 출력
		b_num = binfo_dao.checkBookInfo(binfo_dto);
		System.out.println(b_num);
		BookSaleDTO bsale_dto = new BookSaleDTO();
		
		bsale_dto.setS_member(mem_id);
		//bsale_dto.setS_member("hong");
		bsale_dto.setS_bnum(b_num);
		bsale_dto.setS_qty(book_qty);
		bsale_dto.setS_state(book_status);
		bsale_dto.setS_quality(book_quality);
		bsale_dto.setS_price(book_sell);
		bsale_dto.setS_charge(ship_charge);
		bsale_dto.setS_direct(book_direct);
		bsale_dto.setS_contents(book_user_cont);
		
		File book_user_img = multi.getFile("book_user_img");
		
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
			
		}
		
		BookSaleDAO dao = BookSaleDAO.getInstance();
		
		int result = dao.insertBookSale(bsale_dto);
		
		ActionForward forward = new ActionForward();
		
		if(result > 0) {
			forward.setRedirect(true);
			forward.setPath("book_sale_all_list.do");
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('도서 정보 등록 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		return forward;
	}

}
