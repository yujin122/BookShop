package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.AllQnADAO;
import com.book.model.AllQnADTO;

public class AllQnAListCateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String cate = request.getParameter("cate");
		
		int rowsize = 10;
		int block = 5;
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
		
		AllQnADAO dao = AllQnADAO.getInstance();
		
		totalRecord = dao.getAllQnACount(cate);
		
		allPage = (int)Math.ceil(totalRecord / (double)rowsize);
		
		if(endBlock > allPage) {
			endBlock = allPage;
		}
		
		List<AllQnADTO> list = dao.getQnA(cate, page, rowsize);
		
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);
		request.setAttribute("list", list);
		request.setAttribute("cate", cate);
		
		ActionForward forward = new ActionForward();
		
		forward.setRedirect(false);
		forward.setPath("qna/all_qna_cate_list.jsp");
		
		return forward;
	}

}
