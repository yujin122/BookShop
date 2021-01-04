package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookNoticeDAO;
import com.book.model.BookNoticeDTO;

public class NoticeInsertOkAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 공지사항 추가
		String n_title = request.getParameter("n_title").trim();
		String n_cont = request.getParameter("n_cont").trim();
		
		BookNoticeDTO dto = new BookNoticeDTO();
		
		dto.setN_title(n_title);
		dto.setN_cont(n_cont);
		
		BookNoticeDAO dao = BookNoticeDAO.getInstance();
		int res = dao.insertNotice(dto);
		
		ActionForward forward = new ActionForward();
		PrintWriter out = response.getWriter();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("notice_admin.do");
		}else {
			out.println("<script>");
			out.println("alert('공지사항 글 추가 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
