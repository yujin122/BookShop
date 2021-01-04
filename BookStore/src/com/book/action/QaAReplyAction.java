package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.QnABoardDAO;
import com.book.model.QnABoardDTO;

public class QaAReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		String txt = request.getParameter("qna_txt").trim();
		int qna_num = Integer.parseInt(request.getParameter("s_num"));
		
		QnABoardDAO dao = QnABoardDAO.getInstance();
		QnABoardDTO dto = dao.getQnA(qna_num);
		
		dao.replyUpdate(dto);
		dto.setQ_contents(txt);
		dto.setQ_mem(mem_id);
		
		int res = dao.replyQnA(dto);
		
		PrintWriter out = response.getWriter();
		out.println(res);
		out.close();
		
		return null;
	}

}
