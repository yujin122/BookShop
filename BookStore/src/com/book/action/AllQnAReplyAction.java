package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.AllQnADTO;

public class AllQnAReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		int page = Integer.parseInt(request.getParameter("page"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		String category = request.getParameter("category");
		String title = request.getParameter("title");
		String cont = request.getParameter("cont");
		
		AllQnADAO dao = AllQnADAO.getInstance();
		AllQnADTO dto = dao.getAllQna(num);
		
		dao.replyUpdate(dto);
		
		dto.setAq_category(category);
		dto.setAq_title(title);
		dto.setAq_content(cont);
		dto.setAq_writer(mem_id);
		
		int res = dao.ReplyAllQnA(dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			if(request.getParameter("cate") != null) {
				String cate = request.getParameter("cate");
				
				String categ = URLEncoder.encode(cate, "utf-8");
				
				forward.setRedirect(true);
				forward.setPath("qna_list_cate.do?page="+page+"&cate="+categ);
				
			}else {
				forward.setRedirect(true);
				forward.setPath("qna_list.do?page="+page);
			}
			
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
