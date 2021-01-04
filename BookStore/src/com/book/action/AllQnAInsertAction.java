package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.AllQnADTO;

public class AllQnAInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		
		String writer, check;
		String pwd = null;
		
		if(session.getAttribute("session_mem_id") != null) {
			writer = session.getAttribute("session_mem_id").toString();
		}else {
			writer = request.getParameter("name");
		}
		
		if(request.getParameter("pwd") != null) {
			pwd = request.getParameter("pwd");
		}
		
		if(request.getParameter("check") != null) {
			check = "1";
		}else {
			check = "0";
		}
		
		String category = request.getParameter("category");
		String title = request.getParameter("title").trim();
		String cont = request.getParameter("cont").trim();

		AllQnADTO dto = new AllQnADTO();
		
		dto.setAq_category(category);
		dto.setAq_title(title);
		dto.setAq_content(cont);
		dto.setAq_writer(writer);
		dto.setAq_pwd(pwd);
		dto.setAq_lock(check);
		
		AllQnADAO dao = AllQnADAO.getInstance();
		
		int res = dao.insertAllQnA(dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("qna_list.do");
		}else {
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('등록 실패')");
			out.println("history.back()");
			out.println("</script>");
		}
		
		return forward;
	}

}
