package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.QnABoardDAO;

public class UpdateQnAAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int num = Integer.parseInt(request.getParameter("num"));
		String txt = request.getParameter("qna_txt").trim();
		
		QnABoardDAO dao = QnABoardDAO.getInstance();
		
		int res = dao.updateQnA(num,txt);
		
		PrintWriter out = response.getWriter();
		
		out.println(res);
		out.close();
		
		return null;
	}

}
