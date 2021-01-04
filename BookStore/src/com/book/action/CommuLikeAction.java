package com.book.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CommuDAO;

public class CommuLikeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int c_num = Integer.parseInt(request.getParameter("c_num"));
		int page = Integer.parseInt(request.getParameter("page"));
		CommuDAO dao = CommuDAO.getInstance();
		dao.upLike(c_num);
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("commu_cont.do?c_num="+c_num+"&page="+page);
		return forward;
	}

}
