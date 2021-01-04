package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.FaQDAO;
import com.book.model.FaQDTO;

public class FaQInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String cate = request.getParameter("category");
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		
		FaQDTO dto = new FaQDTO();
		
		dto.setF_category(cate);
		dto.setF_question(question);
		dto.setF_answer(answer);
		
		FaQDAO dao = FaQDAO.getInstance();
		
		int res = dao.insertFaQ(dto);
		
		ActionForward forward = new ActionForward();
		
		if(res > 0) {
			forward.setRedirect(true);
			forward.setPath("faq_list.do");
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
