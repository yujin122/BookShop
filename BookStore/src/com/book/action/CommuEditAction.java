package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CommuDAO;
import com.book.model.CommuDTO;

public class CommuEditAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		request.setAttribute("c_num",c_num);
		request.setAttribute("page",Integer.parseInt(request.getParameter("page")));
		CommuDAO dao = CommuDAO.getInstance();
		CommuDTO dto = dao.getCont(c_num);
		request.setAttribute("dto", dto);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("commu/commu_edit.jsp");
		return forward;
	}

}
