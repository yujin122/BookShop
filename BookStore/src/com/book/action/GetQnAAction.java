package com.book.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.BookSaleDAO;
import com.book.model.BookSaleDTO;
import com.book.model.QnABoardDAO;
import com.google.gson.JsonObject;

public class GetQnAAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// 페이징
		
		int rowsize = 6;
		int block = 3;
		int totalRecord = 0;
		int allPage = 0;
		
		int page = 0;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}else {
			page = 1;
		}
		
		int startNo = (page * rowsize) - (rowsize - 1);
		int endNo = (page * rowsize);
		
		int startBlock = (((page - 1) / block) * block) + 1;
		int endBlock = (((page - 1) / block) * block) + block;
		
		QnABoardDAO dao = QnABoardDAO.getInstance();
		int s_num = Integer.parseInt(request.getParameter("snum"));
		
		totalRecord = dao.getQnACount(s_num);
		
		allPage = (int)Math.ceil(totalRecord / (double)rowsize);
		
		if(endBlock > allPage) {
			endBlock = allPage;
		}
		
		BookSaleDAO bs_dao = BookSaleDAO.getInstance();
		BookSaleDTO bs_dto = bs_dao.getBookSale(s_num);
		
		JsonObject list = dao.getQnA(page, rowsize, s_num);
		
		JsonObject paging = new JsonObject();
		
		paging.addProperty("page", page);
		paging.addProperty("rowsize", rowsize);
		paging.addProperty("block", block);
		paging.addProperty("totalRecord", totalRecord);
		paging.addProperty("allPage", allPage);
		paging.addProperty("startNo", startNo);
		paging.addProperty("endNo", endNo);
		paging.addProperty("startBlock", startBlock);
		paging.addProperty("endBlock", endBlock);
		
		list.add("paging", paging);
		
		list.addProperty("s_mem", bs_dto.getS_member());
		
		PrintWriter out = response.getWriter();
		
		out.println(list);
		out.close();
		
		return null;
	}

}
