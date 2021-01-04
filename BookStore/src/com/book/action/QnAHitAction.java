package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.QnABoardDAO;
import com.book.model.QnABoardDTO;

public class QnAHitAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		QnABoardDAO dao = QnABoardDAO.getInstance();
		
		dao.hitQnA(num);
		
		QnABoardDTO dto = dao.getQnA(num);
		
		PrintWriter out = response.getWriter();
		
		out.println(dto.getQ_hit());
		
		return null;
	}

}
