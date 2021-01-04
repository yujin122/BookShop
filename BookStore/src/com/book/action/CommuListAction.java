package com.book.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.controller.Action;
import com.book.controller.ActionForward;
import com.book.model.CommuDAO;
import com.book.model.CommuDTO;

public class CommuListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 페이징 작업
		int rowsize = 10;       // 한 페이지당 보여질 게시물의 수
		int block = 10;         // 아래에 보여질 페이지의 최대 수 - 예) [1][2][3] / [4][5][6]
		int totalRecord = 0;   // DB 상의 게시물 전체 수
		int allPage = 0;       // 전체 페이지 수
		int page = 0;          // 현재 페이지 변수
		if(request.getParameter("page") != null) {
			page = 
			   Integer.parseInt(request.getParameter("page"));
		}else {
			page = 1;   // 처음으로 "게시물 전체 목록" 태그를 클릭한 경우
		}
		
		// 해당 페이지에서 시작 번호
		int startNo = (page * rowsize) - (rowsize - 1);
		
		// 해당 페이지의 끝 번호
		int endNo = (page * rowsize);
		
		// 해당 페이지의 시작 블럭
		int startBlock = (((page - 1) / block) * block) + 1;
		
		// 해당 페이지의 마지막 블럭
		int endBlock = (((page - 1) / block) * block) + block;
		
		CommuDAO dao = CommuDAO.getInstance();
		totalRecord = dao.getCommutotalNum();
		allPage = (int)Math.ceil(totalRecord / (double)rowsize);
		
		if(endBlock > allPage) {
		    endBlock = allPage;
		}
		List<CommuDTO> list = dao.getCommuList(page,rowsize);
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		request.setAttribute("rowsize", rowsize);
		request.setAttribute("block", block);
		request.setAttribute("totalRecord", totalRecord);
		request.setAttribute("allPage", allPage);
		request.setAttribute("startNo", startNo);
		request.setAttribute("endNo", endNo);
		request.setAttribute("startBlock", startBlock);
		request.setAttribute("endBlock", endBlock);
		
		ActionForward forward = new ActionForward();
		forward.setRedirect(false);
		forward.setPath("commu/commu_list.jsp");
		return forward;
	}

}
