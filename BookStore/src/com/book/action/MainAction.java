package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookInfoDAO;
import com.book.model.ListDTO;

public class MainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BookInfoDAO dao = BookInfoDAO.getInstance();
		
		List<ListDTO> list_Hit = dao.getList_By_CatenHit();
		request.setAttribute("list_Hit", list_Hit);
		
		List<ListDTO> list_Num = dao.getList_By_CatenNum();
		request.setAttribute("list_Num", list_Num);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("main/main.jsp");
		
		return forward;
	}

}
