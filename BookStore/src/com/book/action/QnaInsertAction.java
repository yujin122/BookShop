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

public class QnaInsertAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		HttpSession session = request.getSession();
		String mem_id = session.getAttribute("session_mem_id").toString();
		
		String qna_txt = request.getParameter("qna_txt").trim();
		int s_num = Integer.parseInt(request.getParameter("s_num"));
		
		QnABoardDTO dto = new QnABoardDTO();
		
		dto.setQ_mem(mem_id);
		//dto.setQ_mem("lee");
		dto.setQ_sale_num(s_num);
		dto.setQ_contents(qna_txt);
		
		QnABoardDAO dao = QnABoardDAO.getInstance();
		
		int res = dao.insertQnA(dto);
		
		PrintWriter out = response.getWriter();
		
		out.println(res);
		out.close();
		
		return null;
	}

}
