package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookInfoDAO;

public class MainHitImageAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BookInfoDAO dao = BookInfoDAO.getInstance();
		JSONArray array = dao.getJson_By_CatenHit();
		PrintWriter out = response.getWriter();
		out.println(array);
		out.close();
		return null;
	}

}
