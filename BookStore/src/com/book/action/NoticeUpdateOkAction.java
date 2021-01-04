package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookNoticeDAO;
import com.book.model.BookNoticeDTO;

public class NoticeUpdateOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 공지사항 수정
		int num = Integer.parseInt(request.getParameter("num").trim());
		int page = Integer.parseInt(request.getParameter("page"));
		String n_title = request.getParameter("n_title").trim();
		String n_cont = request.getParameter("n_cont").trim();
		
		BookNoticeDTO dto = new BookNoticeDTO();
		
		dto.setN_title(n_title);
		dto.setN_cont(n_cont);
		dto.setN_num(num);
		
		BookNoticeDAO dao = BookNoticeDAO.getInstance();
		int res = dao.updateNotice(dto);
		
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("notice_admin.do?num="+num+"&page="+page);
		}else {
			out.println("<script>");
			out.println("alert('공지사항 수정 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
				
		return forward;
	}

}
