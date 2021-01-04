package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.MemberDAO;

public class MemberIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String mem_id = request.getParameter("mem_id");
		MemberDAO dao = MemberDAO.getInstance();
		String res = dao.idCheck(mem_id);
		PrintWriter out = response.getWriter();
		out.println(res);
		out.close();
		return null;
	}

}
